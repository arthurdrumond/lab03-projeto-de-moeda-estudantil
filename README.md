# Moeda Estudantil

Sistema acadêmico de reconhecimento e benefícios baseado em uma moeda estudantil.
O projeto permite que professores e empresas parceiras reconheçam alunos com moedas,
enquanto os alunos podem trocar o saldo por vantagens oferecidas pelas empresas.

O repositório contém uma API REST em Spring Boot e uma aplicação web em React com
Tailwind CSS. A aplicação foi desenvolvida no contexto do laboratório de
desenvolvimento de software da PUC Minas.

## Status do projeto

O sistema possui os principais fluxos de negócio implementados:

- autenticação de alunos, professores e empresas parceiras;
- cadastro de alunos e empresas parceiras;
- instituições e professores pré-cadastrados por seed;
- envio de moedas por professores e empresas parceiras;
- associação de cada aluno a uma empresa parceira;
- consulta de extrato;
- catálogo de vantagens;
- resgate de vantagens com geração de cupom;
- notificações de e-mail simuladas no console do backend.

> A autenticação atual é baseada em login e senha e não utiliza tokens JWT ou
> Spring Security. A sessão do frontend é mantida no `localStorage` do navegador.

## Índice

- [Sobre o domínio](#sobre-o-domínio)
- [Atores e regras de negócio](#atores-e-regras-de-negócio)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e execução](#instalação-e-execução)
- [Dados iniciais](#dados-iniciais)
- [API REST](#api-rest)
- [Frontend](#frontend)
- [Banco de dados](#banco-de-dados)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Testes e validação](#testes-e-validação)
- [Limitações conhecidas](#limitações-conhecidas)
- [Documentação do domínio](#documentação-do-domínio)
- [Contribuição](#contribuição)

## Sobre o domínio

A moeda estudantil representa uma forma de reconhecimento dentro de instituições
participantes. Professores recebem créditos semestrais e distribuem moedas aos
alunos por bom comportamento, participação, desempenho ou outros motivos.

Empresas parceiras também podem participar do ecossistema. Elas possuem saldo
ilimitado para reconhecer alunos associados à própria empresa e podem cadastrar
vantagens personalizadas. Os alunos utilizam suas moedas para resgatar essas
vantagens.

O sistema registra cada movimentação como uma transação. A hierarquia persistente
é formada por:

- `Transacao`: entidade abstrata com identificador, data/hora e valor;
- `CreditoSemestral`: crédito atribuído a um professor;
- `TransferenciaMoedas`: envio de moedas de um professor ou empresa para um aluno;
- `ResgateVantagem`: troca de moedas por uma vantagem, com código de cupom.

## Atores e regras de negócio

### Aluno

O aluno realiza o próprio cadastro informando:

- nome;
- e-mail;
- login e senha;
- CPF e RG;
- curso;
- endereço;
- uma instituição de ensino ou uma empresa parceira no campo de associação.

O campo de associação do cadastro reúne as duas opções em grupos separados:
“Instituições acadêmicas” e “Empresas parceiras”. O aluno escolhe uma única
entidade. Alunos associados a uma empresa podem receber moedas dela; alunos
associados somente a uma instituição não podem receber transferências de
empresas parceiras.

Depois de autenticado, o aluno pode consultar seu saldo, visualizar vantagens
ativas, resgatar uma vantagem e consultar as transferências recebidas e os
resgates realizados.

### Professor

Professores não possuem cadastro público. Eles são inseridos previamente a partir
da lista fornecida pela instituição e entram no sistema por meio do seed.

Cada professor possui:

- nome;
- CPF;
- departamento;
- instituição participante;
- login e senha;
- saldo de moedas.

O professor pode enviar moedas a qualquer aluno, desde que tenha saldo suficiente.
O motivo da transferência é obrigatório. O crédito semestral é de 1.000 moedas
por professor e pode ser acumulado entre semestres.

### Empresa parceira

Empresas parceiras podem realizar seu próprio cadastro com dados da empresa,
login, senha e CNPJ.

Uma empresa parceira:

- possui saldo ilimitado para fins de reconhecimento;
- pode enviar moedas somente a alunos associados a ela;
- pode consultar o extrato dos próprios envios;
- pode cadastrar vantagens personalizadas;
- deve informar nome, descrição, URL da foto e custo da vantagem.

### Instituição de ensino

Instituições participantes são pré-cadastradas pelo seed. Elas são utilizadas no
cadastro de alunos e na vinculação dos professores. Não há cadastro público de
instituições no frontend.

## Funcionalidades

### Autenticação

O endpoint `POST /auth/login` procura o login entre alunos, professores e
empresas parceiras. Em caso de sucesso, retorna o identificador, o nome, o
perfil e o saldo do usuário.

O frontend apresenta mensagens amigáveis para erros de autenticação, validação,
conflito, indisponibilidade e falha de conexão.

### Transferência de moedas

Uma transferência exige:

- remetente;
- aluno destinatário;
- valor maior que zero;
- motivo preenchido.

O remetente pode ser:

- professor, com desconto do valor no saldo;
- empresa parceira, sem desconto de saldo.

O backend valida novamente a associação entre empresa e aluno, mesmo que o
frontend já filtre a lista de alunos.

### Resgate de vantagem

O aluno somente consegue resgatar uma vantagem quando seu saldo é suficiente.
O custo é debitado, a transação é registrada e um código de cupom é gerado.
O envio de e-mail é simulado no console para o aluno e para a empresa parceira.

### Extrato

Alunos consultam:

- saldo atual;
- moedas recebidas;
- motivo de cada recebimento;
- resgates;
- código dos cupons.

Professores consultam:

- saldo atual;
- transferências realizadas;
- créditos semestrais.

Empresas consultam:

- saldo ilimitado;
- transferências realizadas aos seus alunos associados.

## Arquitetura

### Backend

O backend utiliza uma arquitetura em camadas:

1. **Controllers** recebem requisições HTTP e expõem endpoints REST.
2. **DTOs** definem os formatos de entrada e saída da API.
3. **Services** concentram validações e regras de negócio.
4. **Repositories** fazem o acesso aos dados via Spring Data JPA.
5. **Models** representam as entidades persistidas.
6. **Config** contém o seed executado na inicialização.

As transações não possuem CRUD público. Elas são criadas pelas operações de
negócio de transferência, crédito semestral e resgate.

### Frontend

O frontend é uma SPA React construída com Vite. A aplicação possui:

- tela de login;
- cadastro de aluno;
- cadastro de empresa parceira;
- dashboard adaptado ao perfil;
- consumo centralizado da API em `src/api.js`;
- proxy de desenvolvimento configurado para `http://localhost:8080`;
- componentes de formulário, modal, extrato e cards;
- estilos utilitários com Tailwind CSS.

## Tecnologias

### Backend

- Java 25;
- Spring Boot 4.1.1;
- Spring Web MVC;
- Spring Data JPA;
- Hibernate;
- H2 Database;
- Maven Wrapper.

### Frontend

- React 18;
- Vite;
- Tailwind CSS;
- PostCSS e Autoprefixer;
- `lucide-react` para ícones.

## Pré-requisitos

Instale:

- JDK 25;
- Node.js em versão LTS;
- npm;
- Git.

Não é necessário instalar PostgreSQL ou Docker para executar o projeto atual. O
backend utiliza H2 em arquivo.

## Instalação e execução

### 1. Clonar o repositório

```bash
git clone https://github.com/arthurdrumond/lab03-projeto-de-moeda-estudantil.git
cd lab03-projeto-de-moeda-estudantil
```

### 2. Instalar dependências do frontend

```bash
cd frontend
npm install
cd ..
```

As dependências são registradas em `frontend/package.json` e as versões
resolvidas ficam em `frontend/package-lock.json`.

### 3. Iniciar o backend

Em um terminal:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

Na inicialização, o Spring Boot atualiza o schema H2 e executa
[`SeedDataConfig.java`](src/main/java/br/edu/pucminas/lab03_projeto_de_moeda_estudantil/config/SeedDataConfig.java).

### 4. Iniciar o frontend

Em outro terminal:

```bash
cd frontend
npm run dev
```

A aplicação web ficará disponível em:

```text
http://localhost:5173
```

O Vite redireciona chamadas iniciadas por `/api` para o backend local. Por
exemplo, uma chamada frontend para `/api/auth/login` é encaminhada para
`http://localhost:8080/auth/login`.

### 5. Gerar o build de produção

Frontend:

```bash
cd frontend
npm run build
```

O resultado é gerado em `frontend/dist/`.

Backend:

```bash
./mvnw clean package
```

O arquivo executável é gerado em `target/`.

## Dados iniciais

O seed é executado toda vez que a aplicação inicia, mas é idempotente. Os
registros são encontrados por CNPJ, CPF ou nome e empresa, portanto não são
duplicados em reinicializações.

### Instituições

| Instituição | CNPJ |
|---|---|
| Pontifícia Universidade Católica de Minas Gerais | `17217838000180` |
| Instituto Federal de Minas Gerais | `10838815000157` |

### Professores

| Professor | Login | Senha | Instituição |
|---|---|---|---|
| Ana Paula Martins | `professor.puc` | `professor123` | PUC Minas |
| Carlos Eduardo Lima | `professor.ifmg` | `professor123` | IFMG |

### Empresa parceira de demonstração

| Empresa | Login | Senha | CNPJ |
|---|---|---|---|
| Benefícios Institucionais | `beneficios.institucionais` | `beneficios123` | `00000000000191` |

### Vantagens de demonstração

A empresa parceira de demonstração recebe três vantagens ativas:

| Vantagem | Custo |
|---|---:|
| Desconto no restaurante universitário | 20 moedas |
| Kit de materiais acadêmicos | 50 moedas |
| Desconto na mensalidade | 100 moedas |

Para testar o fluxo completo, cadastre um aluno selecionando a empresa
`Benefícios Institucionais`. Assim, o aluno ficará apto a receber moedas dessa
empresa e poderá resgatar suas vantagens.

## API REST

Todas as rotas abaixo são relativas a `http://localhost:8080`.

### Autenticação

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/login` | Autentica aluno, professor ou empresa |

Exemplo:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"beneficios.institucionais","senha":"beneficios123"}'
```

### Alunos

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/alunos` | Cadastra aluno |
| `GET` | `/alunos` | Lista alunos |
| `GET` | `/alunos/{id}` | Busca aluno |
| `PUT` | `/alunos/{id}` | Atualiza aluno |
| `DELETE` | `/alunos/{id}` | Remove aluno |

O cadastro exige exatamente um dos identificadores `instituicaoId` ou
`empresaParceiraId`, além dos dados de endereço.

### Professores

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/professores` | Lista professores pré-cadastrados |
| `GET` | `/professores/{id}` | Busca professor |
| `PUT` | `/professores/{id}` | Atualiza professor |
| `DELETE` | `/professores/{id}` | Remove professor |

Não existe `POST /professores`: professores são inseridos pelo seed.

### Empresas parceiras

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/empresas-parceiras` | Cadastra empresa |
| `GET` | `/empresas-parceiras` | Lista empresas |
| `GET` | `/empresas-parceiras/{id}` | Busca empresa |
| `PUT` | `/empresas-parceiras/{id}` | Atualiza empresa |
| `DELETE` | `/empresas-parceiras/{id}` | Remove empresa |

### Instituições

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/instituicoes` | Lista instituições para seleção |
| `GET` | `/instituicoes/{id}` | Busca instituição |
| `POST` | `/instituicoes` | Cria instituição administrativa |
| `PUT` | `/instituicoes/{id}` | Atualiza instituição |
| `DELETE` | `/instituicoes/{id}` | Remove instituição |

O frontend utiliza principalmente `GET /instituicoes`; os registros iniciais vêm
do seed.

### Vantagens

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/vantagens` | Cadastra vantagem |
| `GET` | `/vantagens` | Lista vantagens |
| `GET` | `/vantagens/ativas` | Lista vantagens ativas |
| `GET` | `/vantagens/{id}` | Busca vantagem |
| `PUT` | `/vantagens/{id}` | Atualiza vantagem |
| `DELETE` | `/vantagens/{id}` | Remove vantagem |

Uma vantagem exige descrição, URL de foto, custo maior que zero e uma empresa
parceira responsável.

### Operações de moeda

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/moedas/distribuir-semestral` | Distribui 1.000 moedas a cada professor |
| `POST` | `/moedas/transferir` | Transfere moedas para aluno |
| `POST` | `/moedas/resgatar` | Resgata vantagem |
| `GET` | `/alunos/{id}/extrato` | Extrato do aluno |
| `GET` | `/professores/{id}/extrato` | Extrato do professor |
| `GET` | `/empresas-parceiras/{id}/extrato` | Extrato da empresa |

Uma transferência utiliza `professorId` ou `empresaParceiraId`, nunca os dois.
Quando a empresa é remetente, o aluno precisa estar associado a ela.

Exemplo de transferência por empresa:

```bash
curl -X POST http://localhost:8080/moedas/transferir \
  -H "Content-Type: application/json" \
  -d '{
    "empresaParceiraId": 3,
    "alunoId": 1,
    "valor": 50,
    "motivo": "Participação no projeto"
  }'
```

## Frontend

O frontend está em [`frontend/`](frontend/).

Principais arquivos:

- [`App.jsx`](frontend/src/App.jsx): telas, dashboards, formulários e modais;
- [`api.js`](frontend/src/api.js): cliente HTTP e tradução de erros;
- [`styles.css`](frontend/src/styles.css): estilos globais e componentes Tailwind;
- [`main.jsx`](frontend/src/main.jsx): ponto de entrada React;
- [`vite.config.js`](frontend/vite.config.js): proxy de desenvolvimento.

### Fluxos disponíveis por perfil

| Perfil | Funcionalidades |
|---|---|
| Aluno | cadastro, login, saldo, vantagens, resgate e extrato |
| Professor | login, saldo, envio de moedas e extrato |
| Empresa | cadastro, login, saldo ilimitado, envio restrito, vantagens e extrato |

O frontend mostra mensagens descritivas para erros como credenciais inválidas,
conflitos de cadastro, dados inválidos, falta de conexão e indisponibilidade do
servidor.

## Banco de dados

O projeto utiliza H2 em modo arquivo, configurado em
[`application.properties`](src/main/resources/application.properties):

```properties
spring.datasource.url=jdbc:h2:file:./data/demo;AUTO_SERVER=TRUE
spring.jpa.hibernate.ddl-auto=update
```

O arquivo do banco fica em `data/demo.mv.db`. A pasta `data/` não deve ser
versionada. O Hibernate atualiza o schema durante a inicialização.

Para começar com um banco vazio em ambiente local, pare a aplicação e remova
somente os arquivos do banco em `data/`. Na próxima inicialização, o schema e os
dados iniciais serão recriados pelo seed.

O console H2 também está habilitado em:

```text
http://localhost:8080/h2-console
```

Configuração padrão do console:

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:file:./data/demo` |
| Usuário | `sa` |
| Senha | em branco |

## Estrutura do projeto

```text
.
├── docs/                         # Diagramas, histórias e documentação acadêmica
├── frontend/                     # Aplicação React + Vite + Tailwind
│   ├── src/
│   │   ├── App.jsx               # Interface e fluxos por perfil
│   │   ├── api.js                # Chamadas para a API
│   │   ├── main.jsx              # Inicialização do React
│   │   └── styles.css            # Tailwind e estilos globais
│   ├── index.html
│   ├── package.json
│   ├── package-lock.json
│   ├── postcss.config.js
│   ├── tailwind.config.js
│   └── vite.config.js
├── src/
│   ├── main/java/.../
│   │   ├── config/               # Seed inicial
│   │   ├── controller/           # Endpoints REST
│   │   ├── dto/                  # Requests e responses
│   │   ├── model/                # Entidades JPA
│   │   ├── repository/           # Interfaces Spring Data
│   │   └── service/              # Regras de negócio
│   ├── main/resources/
│   │   ├── application.properties
│   │   └── static/index.html
│   └── test/java/                # Testes automatizados
├── data/                         # Banco H2 local, não versionado
├── .gitignore
├── mvnw                          # Maven Wrapper para Linux/macOS
├── mvnw.cmd                      # Maven Wrapper para Windows
├── pom.xml                       # Dependências e build backend
└── README.md
```

## Testes e validação

### Testes do backend

Na raiz do projeto:

```bash
./mvnw test
```

Esse comando compila o backend, inicia o contexto Spring para o teste de
integração existente e verifica se as entidades, repositories, services,
controllers e o seed podem ser carregados juntos.

### Build do frontend

```bash
cd frontend
npm run build
```

Esse comando valida a compilação do React e gera o bundle de produção em
`frontend/dist/`.

### Verificação de formatação do patch

```bash
git diff --check
```

## Limitações conhecidas

- A autenticação ainda não usa JWT, sessão no servidor ou Spring Security.
- As senhas são armazenadas diretamente para fins acadêmicos e de demonstração;
  isso não deve ser utilizado em produção.
- O envio de e-mails é simulado com `System.out.println` no backend.
- A aplicação usa H2 local em arquivo, não PostgreSQL.
- O endpoint de distribuição semestral pode ser chamado manualmente pela API;
  não há ainda um agendador automático que execute a operação no início de cada
  semestre.
- A empresa parceira recebe saldo ilimitado representado tecnicamente por
  `Integer.MAX_VALUE` na resposta de login; o serviço não debita saldo da
  empresa.
- A associação aluno-empresa é obrigatória para novos cadastros, mas pode haver
  alunos antigos sem associação em bancos criados antes dessa regra. Esses
  alunos não podem receber transferências de empresas até serem associados.
- Não há controle de autorização por endpoint; o frontend controla a experiência
  por perfil, mas uma aplicação de produção deve adicionar autorização no backend.

## Documentação do domínio

Materiais disponíveis em [`docs/`](docs/):

- [Histórias de usuário](docs/historias-de-usuario.md)
- [Diagrama de classes](docs/diagramas/diagrama-de-classe.png)
- [Casos de uso](docs/diagramas/casos-de-uso.png)
- [Diagrama de componentes](docs/diagramas/diagrama-de-componentes.png)
- [Modelo entidade-relacionamento](docs/diagramas/modelo-er.jpeg)

## Contribuição

1. Crie uma branch a partir da branch de trabalho.
2. Faça uma alteração pequena e relacionada ao objetivo da branch.
3. Execute `./mvnw test` e `cd frontend && npm run build`.
4. Execute `git diff --check`.
5. Descreva no pull request a regra de negócio alterada e como ela foi validada.

Não inclua arquivos sensíveis, bancos locais, `node_modules/` ou
`frontend/dist/` nos commits.

## Licença

Este é um projeto acadêmico. O repositório não contém atualmente um arquivo
`LICENSE` formal; portanto, os direitos de uso e distribuição devem ser
combinados com os responsáveis pelo projeto antes de reutilização externa.
