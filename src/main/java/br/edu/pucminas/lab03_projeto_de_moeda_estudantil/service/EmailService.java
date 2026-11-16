package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.ResgateVantagem;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.TransferenciaMoedas;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final ExecutorService emailExecutor = Executors.newSingleThreadExecutor();

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Template 1 (Aluno): Notificação de recebimento de moedas
     */
    public void notificarRecebimento(TransferenciaMoedas transferencia) {
        String destinatario = transferencia.getDestinatario().getEmail();
        String assunto = "Você recebeu moedas de reconhecimento!";
        String remetenteNome = transferencia.getRemetente() != null
                ? transferencia.getRemetente().getNome()
                : (transferencia.getEmpresaParceira() != null ? transferencia.getEmpresaParceira().getNome() : "Parceiro");

        String mensagem = String.format(
            "Olá %s,\n\nVocê recebeu %d moedas de %s.\nMotivo: %s\n\nSeu saldo de moedas foi atualizado com sucesso!",
            transferencia.getDestinatario().getNome(),
            transferencia.getValor(),
            remetenteNome,
            transferencia.getMotivo()
        );

        enviarEmail(destinatario, assunto, mensagem);
    }

    /**
     * Template 2 (Professor): Confirmação de envio de moedas
     */
    public void notificarEnvioProfessor(TransferenciaMoedas transferencia) {
        if (transferencia.getRemetente() == null || transferencia.getRemetente().getEmail() == null) {
            return;
        }

        String destinatario = transferencia.getRemetente().getEmail();
        String assunto = "Confirmação de envio de moedas de reconhecimento";
        String mensagem = String.format(
            "Olá Professor(a) %s,\n\nSua transferência de %d moedas para o aluno %s foi realizada com sucesso!\nMotivo informado: %s\n\nSeu saldo atualizado é de %d moedas.",
            transferencia.getRemetente().getNome(),
            transferencia.getValor(),
            transferencia.getDestinatario().getNome(),
            transferencia.getMotivo(),
            transferencia.getRemetente().getSaldo()
        );

        enviarEmail(destinatario, assunto, mensagem);
    }

    /**
     * US05 – Enviar cupom de resgate de vantagem para o Aluno
     */
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

    /**
     * US12 – Enviar conferência do cupom de resgate para a Empresa Parceira
     */
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

    /**
     * Envia o e-mail via SMTP com log completo no console e fallback em caso de falha
     */
    private void enviarEmail(String para, String assunto, String corpo) {
        emailExecutor.submit(() -> {
            try {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(para);
                mailMessage.setSubject(assunto);
                mailMessage.setText(corpo);

                try {
                    mailSender.send(mailMessage);
                } catch (Exception firstEx) {
                    if (firstEx.getMessage() != null && firstEx.getMessage().contains("550")) {
                        Thread.sleep(10000);
                        mailSender.send(mailMessage);
                    } else {
                        throw firstEx;
                    }
                }

                System.out.println("==================================================");
                System.out.println("[E-MAIL ENVIADO COM SUCESSO VIA SMTP]");
                System.out.println("Para: " + para);
                System.out.println("Assunto: " + assunto);
                System.out.println("Conteúdo:\n" + corpo);
                System.out.println("==================================================\n");

                Thread.sleep(10000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println("==================================================");
                System.out.println("[FALLBACK DE ENVIO DE E-MAIL]");
                System.out.println("Para: " + para);
                System.out.println("Assunto: " + assunto);
                System.out.println("Conteúdo:\n" + corpo);
                System.out.println("Info SMTP: " + e.getMessage());
                System.out.println("==================================================\n");
            }
        });
    }
}