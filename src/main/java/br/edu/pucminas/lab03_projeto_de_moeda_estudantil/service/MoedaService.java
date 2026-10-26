package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.*;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.*;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MoedaService {

    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final VantagemRepository vantagemRepository;
    private final TransacaoRepository transacaoRepository;
    private final EmailService emailService;

    public MoedaService(ProfessorRepository professorRepository,
                        AlunoRepository alunoRepository,
                        VantagemRepository vantagemRepository,
                        TransacaoRepository transacaoRepository,
                        EmailService emailService) {
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
        this.vantagemRepository = vantagemRepository;
        this.transacaoRepository = transacaoRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void distribuirMoedasSemestrais() {
        List<Professor> professores = professorRepository.findAll();
        for (Professor prof : professores) {
            prof.setSaldo(prof.getSaldo() + 1000);

            CreditoSemestral credito = new CreditoSemestral();
            credito.setDataHora(LocalDateTime.now());
            credito.setValor(1000);
            credito.setSemestre("2026/2");
            credito.setProfessor(prof);

            professorRepository.save(prof);
            transacaoRepository.save(credito);
        }
    }

    @Transactional
    public TransferenciaMoedas transferir(TransferenciaRequest req) {
        if (req.valor() <= 0) {
            throw new IllegalArgumentException("O valor do envio deve ser maior que zero.");
        }
        if (req.motivo() == null || req.motivo().isBlank()) {
            throw new IllegalArgumentException("O motivo do reconhecimento é obrigatório.");
        }

        Professor professor = professorRepository.findById(req.professorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));
        Aluno aluno = alunoRepository.findById(req.alunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        if (professor.getSaldo() < req.valor()) {
            throw new IllegalArgumentException("Saldo insuficiente do professor (" + professor.getSaldo() + " moedas).");
        }

        professor.setSaldo(professor.getSaldo() - req.valor());
        aluno.setSaldo(aluno.getSaldo() + req.valor());

        TransferenciaMoedas transf = new TransferenciaMoedas();
        transf.setDataHora(LocalDateTime.now());
        transf.setValor(req.valor());
        transf.setMotivo(req.motivo());
        transf.setRemetente(professor);
        transf.setDestinatario(aluno);

        professorRepository.save(professor);
        alunoRepository.save(aluno);
        TransferenciaMoedas salva = transacaoRepository.save(transf);

        emailService.notificarRecebimento(salva);
        return salva;
    }

    @Transactional
    public ResgateVantagem resgatar(ResgateRequest req) {
        Aluno aluno = alunoRepository.findById(req.alunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
        Vantagem vantagem = vantagemRepository.findById(req.vantagemId())
                .orElseThrow(() -> new IllegalArgumentException("Vantagem não encontrada."));

        if (aluno.getSaldo() < vantagem.getCustoMoedas()) {
            throw new IllegalArgumentException("Saldo insuficiente para resgatar esta vantagem.");
        }

        aluno.setSaldo(aluno.getSaldo() - vantagem.getCustoMoedas());
        String codigo = "CUPOM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        ResgateVantagem resgate = new ResgateVantagem();
        resgate.setDataHora(LocalDateTime.now());
        resgate.setValor(vantagem.getCustoMoedas());
        resgate.setCodigoCupom(codigo);
        resgate.setResgatadoPor(aluno);
        resgate.setReferente(vantagem);

        alunoRepository.save(aluno);
        ResgateVantagem salvo = transacaoRepository.save(resgate);

        emailService.enviarCupomAluno(salvo);
        emailService.enviarCupomParceiro(salvo);

        return salvo;
    }

    public ExtratoResponse obterExtratoAluno(Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        List<TransacaoResponse> lista = transacaoRepository.findByAlunoId(alunoId).stream().map(t -> {
            String desc = "";
            String cupom = null;
            if (t instanceof TransferenciaMoedas tm) {
                desc = "Recebido de " + tm.getRemetente().getNome() + " — Motivo: " + tm.getMotivo();
            } else if (t instanceof ResgateVantagem rv) {
                desc = "Resgate: " + rv.getReferente().getNome();
                cupom = rv.getCodigoCupom();
            }
            return new TransacaoResponse(t.getId(), t.getClass().getSimpleName(), t.getDataHora(), t.getValor(), desc, cupom);
        }).toList();

        return new ExtratoResponse(aluno.getSaldo(), lista);
    }

    public ExtratoResponse obterExtratoProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        List<TransacaoResponse> lista = transacaoRepository.findByProfessorId(professorId).stream().map(t -> {
            String desc = "";
            if (t instanceof TransferenciaMoedas tm) {
                desc = "Enviado para " + tm.getDestinatario().getNome() + " — Motivo: " + tm.getMotivo();
            } else if (t instanceof CreditoSemestral cs) {
                desc = "Crédito Semestral automático (" + cs.getSemestre() + ")";
            }
            return new TransacaoResponse(t.getId(), t.getClass().getSimpleName(), t.getDataHora(), t.getValor(), desc, null);
        }).toList();

        return new ExtratoResponse(professor.getSaldo(), lista);
    }
}