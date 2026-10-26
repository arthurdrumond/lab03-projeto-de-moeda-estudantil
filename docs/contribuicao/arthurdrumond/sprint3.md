# Registro de Contribuição — Sprint 3 (Lab 03 - Moeda Estudantil)

**Integrante:** Arthur Drumond  

---

## Atividades Desenvolvidas na Sprint 3

**Contribuição:** Finalização do CRUD de Instituição de Ensino, implementação do módulo de Vantagens, criação da hierarquia de transações e desenvolvimento da aplicação web em React com Tailwind CSS para os fluxos do sistema.

### Detalhamento das Entregas

1. **CRUD de Instituição de Ensino:**
   * Completação do `InstituicaoEnsinoController`, `InstituicaoEnsinoService` e `InstituicaoEnsinoRepository`.
   * Implementação dos endpoints de busca por identificador (`GET /instituicoes/{id}`), atualização (`PUT /instituicoes/{id}`) e exclusão (`DELETE /instituicoes/{id}`).
   * Manutenção dos endpoints de criação e listagem para disponibilizar as instituições no cadastro de alunos.
   * Validação de CNPJ duplicado durante a criação e a atualização.
   * Tratamento de instituição inexistente com mensagem de erro apropriada.

2. **Módulo de Vantagens:**
   * Criação da entidade `Vantagem`, com os atributos `id`, `nome`, `descricao`, `foto`, `custoMoedas` e `ativa`.
   * Implementação do relacionamento `ManyToOne` com `EmpresaParceira`, representando a empresa que oferece cada vantagem.
   * Criação de `VantagemRepository`, DTOs, `VantagemService` e `VantagemController`.
   * Implementação do CRUD completo de vantagens, incluindo criação, listagem, busca, atualização e exclusão.
   * Validação dos campos obrigatórios de descrição, foto e custo em moedas.
   * Validação para impedir vantagens com custo menor ou igual a zero.
   * Implementação da listagem pública de vantagens ativas por meio do endpoint `GET /vantagens/ativas`.
   * Restrição para que a empresa parceira gerencie as próprias vantagens.

3. **Hierarquia de Transações:**
   * Criação da entidade abstrata `Transacao`, contendo `id`, `dataHora` e `valor`.
   * Configuração da herança JPA com `@Inheritance`, permitindo armazenar diferentes tipos de movimentação em uma mesma hierarquia.
   * Implementação de `CreditoSemestral`, relacionado a `Professor`, para representar o crédito periódico de moedas.
   * Implementação de `TransferenciaMoedas`, com motivo obrigatório e relacionamentos com remetente e destinatário.
   * Implementação de `ResgateVantagem`, relacionado a `Aluno` e `Vantagem`, com geração de código de cupom.
   * As transações são criadas pelas operações de negócio e não possuem endpoints de CRUD livre.
   * Integração da hierarquia com o repositório de transações e com as consultas de extrato.

4. **Aplicação web em React e Tailwind CSS:**
   * Criação do frontend na pasta `frontend/`, utilizando React, Vite e Tailwind CSS.
   * Implementação de tela de login para alunos, professores e empresas parceiras.
   * Implementação do cadastro de alunos e empresas parceiras.
   * Remoção do cadastro público de professores, considerando que eles são pré-cadastrados pela instituição.
   * Implementação do cadastro de aluno com seleção de instituição acadêmica ou empresa parceira.
   * Criação de dashboards específicos para cada perfil:
     * **Aluno:** consulta de saldo, vantagens disponíveis, resgate de benefícios e extrato.
     * **Professor:** consulta de saldo, seleção de alunos da própria instituição, envio de moedas e extrato.
     * **Empresa parceira:** cadastro de vantagens, envio de moedas para alunos associados e extrato.
   * Implementação dos formulários de transferência com valor, aluno destinatário e motivo obrigatório.
   * Filtragem dos alunos no frontend para impedir que professores visualizem alunos de outras instituições no dropdown de transferência.
   * Filtragem dos alunos associados à empresa parceira durante o envio de moedas pela empresa.
   * Implementação do fluxo de resgate de vantagens com confirmação e exibição do código de cupom.
   * Criação de cliente HTTP centralizado em `frontend/src/api.js`.
   * Tratamento de erros da API com mensagens descritivas para o usuário, substituindo mensagens técnicas como `Unauthorized` e `Internal Server Error`.
   * Implementação de estados de carregamento, mensagens de sucesso e atualização dos dados após as operações.

5. **Validação das entregas:**
   * Execução dos testes do backend com `./mvnw test`.
   * Compilação do frontend com `cd frontend && npm run build`.
   * Verificação de formatação das alterações com `git diff --check`.

---
