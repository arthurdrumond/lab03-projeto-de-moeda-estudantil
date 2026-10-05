# Histórias de Usuário: Sistema de Moeda Estudantil

## Autenticação

### US01 – Fazer login
**Como** usuário (aluno, professor ou empresa parceira),
**quero** entrar no sistema com meu login e senha,
**para** acessar as funcionalidades do meu perfil.

**Critérios de aceitação:**
- O sistema só libera o acesso se o login e a senha estiverem corretos.
- Com dados errados, aparece uma mensagem de erro e o acesso não é liberado.
- Todas as funcionalidades, exceto os cadastros de aluno e de empresa, exigem login.

---

## Aluno

### US02 – Cadastrar-se como aluno
**Como** aluno,
**quero** fazer meu cadastro informando nome, email, CPF, RG, endereço, instituição e curso,
**para** participar do sistema de mérito.

**Critérios de aceitação:**
- Todos os campos são obrigatórios.
- A instituição é escolhida em uma lista de instituições já cadastradas.
- O aluno define um login e uma senha no cadastro.
- Não é possível cadastrar dois alunos com o mesmo CPF ou email.
- O aluno começa com saldo zero.

### US03 – Consultar meu extrato
**Como** aluno,
**quero** ver meu saldo e o histórico das minhas transações,
**para** saber quantas moedas tenho e de onde elas vieram.

**Critérios de aceitação:**
- O extrato mostra o saldo atual.
- Mostra as moedas recebidas, com data, valor, professor e motivo.
- Mostra os resgates feitos, com data, vantagem, valor e código do cupom.

### US04 – Ver as vantagens disponíveis
**Como** aluno,
**quero** ver a lista de vantagens oferecidas pelas empresas parceiras,
**para** escolher em que trocar minhas moedas.

**Critérios de aceitação:**
- Cada vantagem mostra nome, descrição, foto, custo em moedas e empresa.
- Só aparecem as vantagens ativas.

### US05 – Resgatar uma vantagem
**Como** aluno,
**quero** trocar minhas moedas por uma vantagem,
**para** aproveitar o reconhecimento que recebi.

**Critérios de aceitação:**
- O resgate só acontece se eu tiver saldo suficiente.
- O custo da vantagem é descontado do meu saldo.
- O sistema gera um código de cupom único.
- Recebo um email com o cupom e o código.
- A empresa parceira recebe um email com o mesmo código.
- O resgate aparece no meu extrato.

### US06 – Ser avisado quando receber moedas
**Como** aluno,
**quero** receber um email quando um professor me enviar moedas,
**para** saber que fui reconhecido e por qual motivo.

**Critérios de aceitação:**
- O email é enviado logo após o envio das moedas.
- O email informa a quantidade de moedas, o professor e o motivo.

---

## Professor

### US07 – Enviar moedas para um aluno
**Como** professor,
**quero** enviar moedas para um aluno explicando o motivo,
**para** reconhecer o bom desempenho dele.

**Critérios de aceitação:**
- Escolho o aluno, a quantidade de moedas e escrevo o motivo.
- O motivo é obrigatório.
- O envio só acontece se eu tiver saldo suficiente.
- A quantidade é descontada do meu saldo e somada ao saldo do aluno.
- O envio aparece no meu extrato e no do aluno.

### US08 – Consultar meu extrato
**Como** professor,
**quero** ver meu saldo e os envios que fiz,
**para** controlar quantas moedas ainda posso distribuir.

**Critérios de aceitação:**
- O extrato mostra o saldo atual.
- Mostra cada envio com data, aluno, valor e motivo.
- Mostra os créditos semestrais recebidos.

### US09 – Receber moedas a cada semestre
**Como** professor,
**quero** receber 1.000 moedas no início de cada semestre,
**para** ter saldo para reconhecer meus alunos.

**Critérios de aceitação:**
- O crédito acontece automaticamente no início do semestre.
- As moedas que sobraram do semestre anterior continuam no saldo.
- O crédito aparece no extrato.

---

## Empresa Parceira

### US10 – Cadastrar minha empresa
**Como** empresa parceira,
**quero** me cadastrar no sistema,
**para** oferecer vantagens aos alunos.

**Critérios de aceitação:**
- Informo os dados da empresa, um login e uma senha.
- Não é possível cadastrar duas empresas com o mesmo CNPJ.
- Posso já cadastrar vantagens durante o cadastro da empresa.

### US11 – Cadastrar uma vantagem
**Como** empresa parceira,
**quero** cadastrar as vantagens que ofereço,
**para** que os alunos possam trocá-las por moedas.

**Critérios de aceitação:**
- Informo nome, descrição, foto e custo em moedas.
- Descrição, foto e custo são obrigatórios.
- O custo precisa ser maior que zero.
- A vantagem passa a aparecer na lista dos alunos.

### US12 – Conferir um resgate
**Como** empresa parceira,
**quero** receber um email com o código de cada resgate,
**para** conferir o cupom quando o aluno vier fazer a troca.

**Critérios de aceitação:**
- O email chega quando o aluno resgata uma vantagem minha.
- O email traz o nome do aluno, a vantagem e o código do cupom.
- O código é o mesmo enviado ao aluno.

---
