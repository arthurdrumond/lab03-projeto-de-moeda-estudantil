# Registro de Contribuição — Sprint 2 (Lab 03 - Moeda Estudantil)

**Integrante:** Arthur Drumond

---

## Atividades Desenvolvidas na Sprint 2

**Contribuição:** Estruturação inicial do projeto Spring Boot e implementação do CRUD de Empresa Parceira e de Aluno, com persistência em banco H2 via JPA.

### Detalhamento das Entregas:

1. **Estrutura inicial do projeto:**
   * Configuração do projeto Spring Boot (Java 25) com as dependências `spring-boot-starter-data-jpa`, `spring-boot-starter-webmvc` e `h2`.
   * Configuração da fonte de dados H2 em arquivo (`application.properties`), com Hibernate para geração/atualização automática do schema.

2. **Entidades de domínio:**
   * `Usuario`: classe base (`@MappedSuperclass`) com `id`, `nome`, `email`, `login`, `senha` e o método `autenticar`.
   * `Endereco`: tipo embutido (`@Embeddable`) reutilizado pelas entidades que precisam de endereço.
   * `EmpresaParceira`: entidade com `cnpj` e `endereco`, herdando de `Usuario`.
   * `Aluno`: entidade com `cpf`, `rg`, `curso`, `saldo` e vínculo com `InstituicaoEnsino`, herdando de `Usuario`.
   * `InstituicaoEnsino`: entidade de apoio (`id`, `nome`, `cnpj`) usada pelo cadastro de Aluno.

3. **CRUD de Empresa Parceira:**
   * DTOs de requisição e resposta (`EmpresaParceiraRequest`, `EmpresaParceiraResponse`, `EnderecoDTO`).
   * `EmpresaParceiraRepository` com verificação de CNPJ duplicado.
   * `EmpresaParceiraService` e `EmpresaParceiraController` com os endpoints de criação, listagem, busca por id, atualização e exclusão.

4. **CRUD de Aluno:**
   * DTOs de requisição e resposta (`AlunoRequest`, `AlunoResponse`, `InstituicaoEnsinoRequest`, `InstituicaoEnsinoResponse`).
   * `AlunoRepository` com verificação de CPF e email duplicados.
   * `AlunoService` e `AlunoController` com os endpoints de criação, listagem, busca por id, atualização e exclusão, validando o vínculo obrigatório com uma instituição de ensino já cadastrada.
   * Cadastro e listagem de `InstituicaoEnsino`, utilizados como pré-requisito do cadastro de Aluno.

5. **Persistência com JPA e H2:**
   * Definição da estratégia de persistência via Spring Data JPA (`JpaRepository`) para todas as entidades.
   * Testes manuais de todos os endpoints (criação, listagem, busca, atualização, exclusão e validação de duplicidade), incluindo verificação de que os dados permanecem após reiniciar a aplicação.

6. **Hotfix — concorrência de conexão no H2:**
   * Ajuste da URL de conexão do H2 com `AUTO_SERVER=TRUE`, permitindo múltiplas conexões simultâneas ao mesmo arquivo de banco (por exemplo, aplicação rodando via terminal e via IDE ao mesmo tempo) sem erro de arquivo travado.

---
