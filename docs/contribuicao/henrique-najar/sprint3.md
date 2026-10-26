# Registro de Contribuição — Sprint 3 (Lab 03 - Moeda Estudantil)

**Integrante:** Henrique Najar  
**Caminho no Repositório:** `docs/contribuicoes/henrique-najar/sprint3.md`

---

## Atividades Desenvolvidas na Sprint 3

**Contribuição:** Implementação do serviço de regras de negócio de moedas (`MoedaService`), desenvolvimento do serviço de notificações simuladas de e-mail (`EmailService`), estruturação da consulta de extrato por DTO/projeção (`ExtratoResponse`), criação de endpoints REST e consolidação da interface web do front-end (`index.html`).

### Detalhamento das Entregas:

1. **Serviço de Regras de Negócio de Moedas (`MoedaService`):**
   * **Distribuição Semestral (US09):** Implementação do método `distribuirMoedasSemestrais()`, responsável por creditar automaticamente 1.000 moedas no saldo de todos os professores cadastrados e gerar o registro correspondente de `CreditoSemestral`.
   * **Transferência de Moedas (US07):** Implementação do método `transferir()`, validando a obrigatoriedade da mensagem de motivo do reconhecimento, verificação de saldo suficiente do professor, atualização de saldos (débito do professor e crédito do aluno) e registro da entidade `TransferenciaMoedas`.
   * **Resgate de Vantagens (US05):** Implementação do método `resgatar()`, responsável por validar o saldo do aluno em relação ao custo da vantagem, debitar o valor, gerar um código de cupom único via `UUID` (`CUPOM-XXXXXX`) e registrar a entidade `ResgateVantagem`.

2. **Módulo de Extrato e Histórico de Transações (`ExtratoResponse` / DTOs):**
   * **Modelagem Desacoplada:** Implementação do extrato via DTO e projeção (`ExtratoResponse` e `TransacaoResponse`), calculando as informações dinamicamente a partir do saldo atual e da lista de transações do usuário, sem persistir uma entidade desnecessária em banco de dados.
   * **Consultas Polimórficas:** Mapeamento de consultas personalizadas em `TransacaoRepository` para filtrar o histórico do Aluno (`TransferenciaMoedas` recebidas e `ResgateVantagem`) e do Professor (`TransferenciaMoedas` enviadas e `CreditoSemestral`).
   * **Endpoints REST (`MoedaController`):** Exposição das rotas `GET /alunos/{id}/extrato` e `GET /professores/{id}/extrato`.

3. **Serviço de Notificação por E-mail (`EmailService`):**
   * **Notificação de Recebimento (US06):** Envio de e-mail informando o aluno sobre a quantidade de moedas recebidas, o professor remetente e o motivo do reconhecimento.
   * **Cupom de Resgate para Aluno (US05):** Envio de e-mail ao aluno com a confirmação do resgate da vantagem e o código do cupom gerado.
   * **Cupom de Resgate para Empresa Parceira (US12):** Envio de e-mail de conferência à empresa parceira com os dados do aluno e o código do cupom para validação presencial.
   * **Arquitetura Stub/Log:** Implementação por log formatado no console do servidor e integrado em tempo real ao console visual do front-end.

4. **Integração e Consolidação do Front-end (`index.html`):**
   * Integração completa em Single Page Application (SPA) contendo formulários e listas para Alunos, Professores, Empresas Parceiras e Vantagens.
   * Mapeamento dos fluxos de execução de envio de moedas, resgate de cupons, consulta de extrato por perfil e painel visual de logs.
