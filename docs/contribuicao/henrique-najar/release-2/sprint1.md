# Registro de Contribuição — Sprint 1 (Release 2) (Lab 04 - Moeda Estudantil)

**Integrante:** Henrique Najar  
**Caminho no Repositório:** `docs/contribuicoes/henrique-najar/sprint1.md`

---

## Atividades Desenvolvidas na Sprint 1 (Release 2)

**Contribuição:** Implementação das validações de regra de negócio do fluxo de envio de moedas, garantia de atomicidade transacional e criação da camada de tratamento global de exceções para a API REST.

### Detalhamento das Entregas

1. **Reforço de Validações de Regra de Negócio (`MoedaService`):**
   * **Verificação de Saldo:** Validação rigorosa garantindo que o professor possua saldo suficiente antes de executar qualquer débito, retornando mensagem detalhada com o saldo atual e o valor solicitado em caso de falha.
   * **Obrigatoriedade e Sanitize do Motivo:** Garantia de que a mensagem de motivo do reconhecimento seja preenchida e não nula, aplicando `.trim()` para evitar armazenamento de entradas vazias.
   * **Restrição Institucional e Valor Válido:** Validação para bloquear transferências de valor menor ou igual a zero e restrição para que professores enviem moedas apenas para alunos da mesma instituição de ensino.

2. **Tratamento Global de Exceções (`GlobalExceptionHandler`):**
   * **Interceptação Centralizada:** Criação da classe `@RestControllerAdvice` no pacote `exception` para capturar exceções do tipo `IllegalArgumentException` lançadas pela camada de serviço.
   * **Padronização da Resposta REST:** Configuração de retornos em formato JSON com status HTTP `400 Bad Request`, incluindo *timestamp*, código de status e mensagem amigável para exibição no front-end.

3. **Arquitetura Limpa nos Controllers (`MoedaController`):**
   * **Desacoplamento de Tratamento de Erros:** Removidos blocos manuais `try-catch` nos endpoints, delegando a responsabilidade de exceção ao `GlobalExceptionHandler`.
   * **Tipagem Forte:** Ajustada a assinatura dos endpoints de envio e resgate para `ResponseEntity<TransferenciaMoedas>` e `ResponseEntity<ResgateVantagem>`.

4. **Garantia de Atomicidade (`@Transactional`):**
   * Aplicação da anotação `@Transactional` nos métodos de gravação em banco de dados para assegurar que a alteração do saldo do professor, o acréscimo no saldo do aluno e a inserção do registro de transação ocorram de forma totalmente atômica.

