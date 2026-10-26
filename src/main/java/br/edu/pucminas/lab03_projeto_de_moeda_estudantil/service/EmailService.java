package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.ResgateVantagem;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.TransferenciaMoedas;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void notificarRecebimento(TransferenciaMoedas transferencia) {
        String destinatario = transferencia.getDestinatario().getEmail();
        String assunto = "Você recebeu moedas de reconhecimento!";
        String mensagem = String.format(
            "Olá %s,\n\nVocê recebeu %d moedas do professor %s.\nMotivo: %s\n\nSeu saldo foi atualizado com sucesso!",
            transferencia.getDestinatario().getNome(),
            transferencia.getValor(),
            transferencia.getRemetente().getNome(),
            transferencia.getMotivo()
        );

        enviarEmail(destinatario, assunto, mensagem);
    }

    public void enviarCupomAluno(ResgateVantagem resgate) {
        String destinatario = resgate.getResgatadoPor().getEmail();
        String assunto = "Seu cupom de vantagem foi gerado com sucesso!";
        String nomeEmpresa = resgate.getReferente().getEmpresaParceira() != null ? 
                             resgate.getReferente().getEmpresaParceira().getNome() : "Parceira";

        String mensagem = String.format(
            "Olá %s,\n\nVocê resgatou a vantagem '%s' oferecida pela empresa %s.\nCódigo do Cupom: %s\nValor descontado: %d moedas.\n\nApresente este código na empresa parceira para realizar a troca presencial.",
            resgate.getResgatadoPor().getNome(),
            resgate.getReferente().getNome(),
            nomeEmpresa,
            resgate.getCodigoCupom(),
            resgate.getValor()
        );

        enviarEmail(destinatario, assunto, mensagem);
    }

    public void enviarCupomParceiro(ResgateVantagem resgate) {
        if (resgate.getReferente().getEmpresaParceira() == null) {
            return;
        }

        String destinatario = resgate.getReferente().getEmpresaParceira().getEmail();
        String assunto = "Novo resgate de vantagem realizado — Cupom " + resgate.getCodigoCupom();
        String mensagem = String.format(
            "Olá %s,\n\nO aluno %s (E-mail: %s) realizou o resgate da vantagem '%s'.\nCódigo do Cupom para conferência: %s\n\nValide este código no momento da troca presencial.",
            resgate.getReferente().getEmpresaParceira().getNome(),
            resgate.getResgatadoPor().getNome(),
            resgate.getResgatadoPor().getEmail(),
            resgate.getReferente().getNome(),
            resgate.getCodigoCupom()
        );

        enviarEmail(destinatario, assunto, mensagem);
    }

    private void enviarEmail(String para, String assunto, String corpo) {
        System.out.println("==================================================");
        System.out.println("[SIMULAÇÃO DE ENVIO DE E-MAIL]");
        System.out.println("Para: " + para);
        System.out.println("Assunto: " + assunto);
        System.out.println("Conteúdo:\n" + corpo);
        System.out.println("==================================================\n");
    }
}