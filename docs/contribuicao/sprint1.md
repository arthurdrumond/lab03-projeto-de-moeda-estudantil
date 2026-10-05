# Sistema de Moeda Estudantil: Diagramas UML

## O que foi criado

- **Diagrama de Casos de Uso**
  - Mostra quem usa o sistema e o que cada um pode fazer.
  - Atores principais: Aluno, Professor e Empresa Parceira. Os três são tipos de Usuário.
  - Atores de apoio: Serviço de Email (manda os emails) e Tempo (dispara o crédito de moedas no início de cada semestre).
  - O aluno pode se cadastrar, consultar o extrato e resgatar vantagens.
  - O professor pode enviar moedas e consultar o extrato.
  - A empresa pode se cadastrar e cadastrar vantagens.

- **Diagrama de Classes**
  - Mostra as "peças" de informação do sistema e como elas se ligam.
  - `Usuario` é a classe base, com login e senha. Aluno, Professor e Empresa Parceira herdam dela.
  - Aluno e Professor guardam o próprio saldo de moedas.
  - Cada aluno e cada professor pertencem a uma Instituição de Ensino.
  - A empresa oferece várias Vantagens, cada uma com descrição, foto e custo em moedas.
  - Toda movimentação de moedas é uma `Transacao`, que pode ser:
    - envio de moedas do professor para o aluno (com motivo obrigatório);
    - resgate de vantagem pelo aluno (com código de cupom);
    - crédito semestral de 1.000 moedas para o professor.
  - Duas classes de serviço cuidam das regras: uma das moedas e outra dos emails.

- **Diagrama de Componentes**
  - Mostra como o sistema é dividido em partes técnicas.
  - Front-end web, por onde os usuários acessam o sistema.
  - Uma API que recebe os pedidos do front-end.
  - Módulos separados para: autenticação, usuários, instituições, moedas, vantagens, extrato, cupons, notificações e o agendador semestral.
  - Banco de dados, servidor de email (SMTP) e um local para guardar as fotos das vantagens.

## Decisões tomadas

- **Herança de usuário:** Aluno, Professor e Empresa herdam de Usuário, porque todos precisam de login e senha.
- **Login como nota:** em vez de ligar o "Realizar Login" a cada caso de uso com uma seta, foi usada uma nota dizendo que todos dependem dele. Isso deixou o diagrama muito mais limpo.
- **Cupom em um caso só:** o envio do cupom ao aluno e ao parceiro virou um único caso de uso, já que os dois emails levam o mesmo código.
- **Ator "Tempo":** foi criado para mostrar que o crédito de 1.000 moedas acontece sozinho, a cada semestre, sem ninguém clicar em nada.
- **Saldo acumulável:** o saldo do professor soma as moedas novas com as que sobraram do semestre anterior. Isso aparece como nota no diagrama de classes.
- **Transações com uma classe pai:** os três tipos de movimentação herdam de `Transacao`. Assim, o extrato lista tudo junto, tanto para o aluno quanto para o professor.
- **Instituições e professores pré-cadastrados:** não existe caso de uso para cadastrá-los, porque o enunciado diz que eles já vêm prontos no sistema.