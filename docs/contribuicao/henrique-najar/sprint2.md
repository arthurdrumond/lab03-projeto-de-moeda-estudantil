# Registro de Contribuição — Sprint 2 (Lab 03 - Moeda Estudantil)

**Integrante:** Henrique Najar  
**Caminho no Repositório:** `docs/contribuicoes/henrique-najar/sprint2.md`

---

## Atividades Desenvolvidas na Sprint 2

**Contribuição:** Elaboração do Modelo Entidade-Relacionamento (Modelo ER) conceitual/gráfico, reestruturação do repositório no GitHub conforme o template oficial do professor e desenvolvimento da interface web (front-end) integrada com os serviços REST do back-end.

### Detalhamento das Entregas:

1. **Modelagem Entidade-Relacionamento (`docs/diagramas/modelo-er.*`):**
   * Mapeamento visual e conceitual de todas as entidades do sistema (`INSTITUICAO_ENSINO`, `PROFESSOR`, `ALUNO`, `ENDERECO`, `EMPRESA_PARCEIRA`, `VANTAGEM`, `TRANSFERENCIA_MOEDAS`, `CREDITO_SEMESTRAL`, `RESGATE_VANTAGEM`).
   * Definição explícita das chaves primárias (PKs), atributos essenciais de cada entidade e cardinalidades de relacionamento (`1:N`, `N:1`, `1:1`).
   * Mapeamento dos relacionamentos de negócio, como o vínculo com a instituição de ensino, registro de endereço, emissão de créditos semestrais, transferências de moedas e resgate de vantagens.

2. **Reestruturação do Repositório (Template Oficial do Professor):**
   * Remodelagem da organização de pastas e documentos no GitHub de acordo com as diretrizes e o template disponibilizado pelo professor para a apresentação do projeto.
   * Padronização do layout visual das entregas da Sprint 2 para facilitar a navegação e avaliação dos artefatos.

3. **Implementação do Front-end Inicial (`src/main/resources/static/index.html`):**
   * Criação da interface em HTML5 e CSS3 responsivo para o painel de cadastro e acompanhamento das entidades do sistema.
   * Integração assíncrona utilizando JavaScript nativo (`fetch API`) consumindo as rotas `/alunos`, `/empresas-parceiras` e `/instituicoes`.
   * Implementação do formulário de cadastro de alunos com construção do payload JSON aninhado contendo os campos de endereço (`EnderecoDTO`) e seleção dinâmica da instituição de ensino cadastrada.
   * Implementação do formulário de cadastro de empresas parceiras com suporte a envio de JSON para a API e atualização em tempo real das listagens após novos cadastros.
