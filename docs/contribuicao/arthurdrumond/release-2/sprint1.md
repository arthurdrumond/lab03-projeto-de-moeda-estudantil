# Registro de Contribuição — Sprint 1 (Release 2) (Lab 03 - Moeda Estudantil)

**Integrante:** Arthur Drumond

---

## Atividades Desenvolvidas na Sprint 1 (Release 2)

**Contribuição:** Habilitação da visualização do extrato de transferências para o perfil de Aluno, reutilizando o mesmo componente de frontend já existente para Professores e Empresas Parceiras, agora exibindo as transações de recebimento de moedas e de resgate de vantagens do aluno.

### Detalhamento das Entregas

1. **Reaproveitamento do componente de extrato:**
   * Reutilização do componente `Statement` em `frontend/src/App.jsx`, antes exibido apenas para Professor e Empresa Parceira, passando a renderizá-lo também para o perfil Aluno.
   * Remoção da condição que ocultava o extrato para o perfil Aluno (`user.perfil !== 'ALUNO'`), unificando a exibição do extrato para todos os perfis com um único componente, sem duplicação de código.
   * Manutenção do carregamento do extrato do aluno já existente via `api.studentStatement(user.id)`, que consome o endpoint `GET /alunos/{id}/extrato`.

2. **Conteúdo do extrato do aluno:**
   * Exibição das transações de **recebimento de moedas** (`TransferenciaMoedas`), com a origem (professor ou empresa parceira) e o motivo do reconhecimento.
   * Exibição das transações de **resgate de vantagens** (`ResgateVantagem`), com o nome da vantagem e o respectivo código de cupom.
   * Apresentação do saldo atual do aluno junto ao histórico de movimentações, ordenado da transação mais recente para a mais antiga.

3. **Ajuste de ícone por tipo de transação:**
   * Correção da lógica de ícone do componente `Statement`, que comparava `item.tipo === 'RESGATE'`, valor que nunca correspondia ao retorno da API (nome da classe: `ResgateVantagem` / `TransferenciaMoedas`).
   * Passou a exibir o ícone de presente (`Gift`) para resgates de vantagem e o ícone de envio (`Send`) para recebimentos, distinguindo visualmente os dois tipos de transação no extrato do aluno.

4. **Aproveitamento do backend existente:**
   * Confirmação de que o endpoint `GET /alunos/{id}/extrato` e o método `MoedaService.obterExtratoAluno` já estavam implementados e retornavam o `ExtratoResponse` com saldo e lista de transações, não sendo necessária alteração no backend.
   * Reutilização da consulta `TransacaoRepository.findByAlunoId`, que já unifica recebimentos e resgates do aluno em uma única hierarquia de transações.

5. **Validação das entregas:**
   * Compilação do frontend com `cd frontend && npm run build`.
   * Verificação do fluxo do aluno: login, consulta de saldo, recebimento de moedas, resgate de vantagem e visualização do extrato consolidado.

---
