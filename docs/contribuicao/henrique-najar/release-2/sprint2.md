# Registro de Contribuição — Sprint 2 (Release 2) — Lab 04 (Moeda Estudantil)

**Integrante:** Henrique Najar  
**Branch:** `henrique-najar/sprint2`  
**Caminho no Repositório:** `docs/contribuicoes/henrique-najar/sprint2.md`

---

## Resumo das Entregas

Nesta etapa, foram implementados o serviço de notificação por e-mail via SMTP com templates personalizados, o reforço das validações e tratamento global de erros no fluxo de transferência de moedas, além da modelagem e documentação de quatro Diagramas de Sequência UML do sistema.

---

## 1. Configuração do Serviço de E-mail e Templates (`EmailService`)

* **Integração SMTP (`spring-boot-starter-mail`):**
  * Adição da dependência `spring-boot-starter-mail` no `pom.xml`.
  * Configuração de propriedades SMTP no `application.properties` utilizando variáveis de ambiente (`MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`) para integração com servidor de testes (Mailtrap) sem exposição de credenciais sensíveis no repositório.
* **Templates de E-mail da Transferência:**
  * **Template 1 — Notificação ao Aluno (`notificarRecebimento`):** E-mail enviado ao aluno destinatário informando a quantidade de moedas recebidas, o nome do professor/empresa remetente e o motivo do reconhecimento.
  * **Template 2 — Confirmação ao Professor (`notificarEnvioProfessor`):** E-mail enviado ao professor remetente confirmando a conclusão da transferência, o destinatário, o motivo registrado e o saldo atualizado após o débito.
* **Fila Assíncrona, Controle de Taxa e Fallback:**
  * Implementação de fila sequencial (`SingleThreadExecutor`) com intervalo controlado e retentativa automática para respeitar o *rate-limit* do servidor SMTP gratuito sem bloquear a thread da requisição HTTP.
  * Registro detalhado dos disparos no console da aplicação e mecanismo de *fallback* em log caso o servidor SMTP esteja indisponível, garantindo que falhas de rede não causem *rollback* na transação financeira.

---

## 2. Validação do Fluxo de Envio e Tratamento de Erros

* **Regras de Negócio e Atomicidade (`MoedaService`):**
  * Garantia de execução atômica (`@Transactional`) no método `transferir`, assegurando que o débito do professor, o crédito do aluno e a persistência da transação ocorram de forma consistente.
  * Validações de valor positivo ($> 0$), obrigatoriedade e higienização (`.trim()`) do motivo do reconhecimento, verificação de vínculo institucional entre professor e aluno e checagem de saldo suficiente com mensagens detalhadas.
* **Tratamento Global de Exceções (`GlobalExceptionHandler` e `MoedaController`):**
  * Criação da classe `@RestControllerAdvice` no pacote `exception` para interceptar exceções de regra de negócio (`IllegalArgumentException`) e retornar respostas JSON padronizadas com status HTTP `400 Bad Request`.
  * Remoção de blocos `try-catch` manuais no `MoedaController`, mantendo os endpoints limpos e fortemente tipados.
* **Ajuste de Mapeamento JPA (`TransferenciaMoedas`):**
  * Alinhamento da coluna `remetente_id` (`nullable = true`) para suportar transferências originadas tanto por professores quanto por empresas parceiras.

---

## 3. Modelagem UML — Diagramas de Sequência

Elaboração dos diagramas de sequência representando o fluxo completo entre interface (`Boundary`), controladores (`Controller`), regras de negócio (`Service`) e persistência (`Repository`), incluindo fluxos alternativos (`alt`) e laços de repetição (`loop`):

1. **UC01 — Realizar Login (`docs/diagramas/sequencia/uc01-realizar-login.png`):**
   * Detalha a autenticação de usuários (Aluno, Professor ou Empresa Parceira) via `POST /auth/login`, validação de credenciais/hash de senha, geração de token no fluxo de sucesso (`200 OK`) e tratamento de falha de autenticação (`401 Unauthorized`).
2. **UC02 — Cadastrar Aluno (`docs/diagramas/sequencia/uc02-cadastrar-aluno.png`):**
   * Representa o carregamento prévio das instituições de ensino (`GET /instituicoes` — UC12), validação de unicidade de e-mail/CPF (`409 Conflict` em caso de duplicidade), criptografia de senha, inicialização com saldo zero e criação do registro (`201 Created`).
3. **UC08 — Creditar Moedas Semestrais (`docs/diagramas/sequencia/uc08-creditar-moedas-semestrais.png`):**
   * Descreve o disparo agendado (`CreditoSemestralJob`) no início do semestre, verificação de idempotência para evitar duplicidade no mesmo semestre e iteração (`loop`) sobre todos os professores para acréscimo acumulativo de $1.000$ moedas e registro da transação.
4. **UC11 — Conferir Troca de Vantagem (`docs/diagramas/sequencia/uc11-conferir-troca-vantagem.png`):**
   * Modela a validação presencial do cupom pela empresa parceira (`GET /cupons/{codigo}`), contemplando os fluxos alternativos de cupom inexistente (`404 Not Found`), pertencente a outra empresa (`403 Forbidden`), já utilizado (`409 Conflict`) e confirmação da entrega (`PATCH /cupons/{codigo}/utilizar` — `200 OK`).

---
