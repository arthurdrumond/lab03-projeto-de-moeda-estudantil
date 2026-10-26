package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.InstituicaoEnsinoResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.ProfessorRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.ProfessorResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.InstituicaoEnsino;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Professor;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.InstituicaoEnsinoRepository;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;
    private final InstituicaoEnsinoRepository instituicaoRepository;

    public ProfessorService(ProfessorRepository repository, InstituicaoEnsinoRepository instituicaoRepository) {
        this.repository = repository;
        this.instituicaoRepository = instituicaoRepository;
    }

    public List<ProfessorResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProfessorResponse buscarPorId(Long id) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado com o ID: " + id));
        return toResponse(professor);
    }

    public ProfessorResponse atualizar(Long id, ProfessorRequest request) {
        Professor professor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado com o ID: " + id));

        if (!professor.getCpf().equals(request.cpf()) && repository.existsByCpf(request.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado para outro professor.");
        }

        InstituicaoEnsino instituicao = instituicaoRepository.findById(request.instituicaoId())
                .orElseThrow(() -> new IllegalArgumentException("Instituição de ensino não encontrada."));

        professor.setNome(request.nome());
        professor.setEmail(request.email());
        professor.setLogin(request.login());
        professor.setSenha(request.senha());
        professor.setCpf(request.cpf());
        professor.setDepartamento(request.departamento());
        professor.setInstituicao(instituicao);

        Professor atualizado = repository.save(professor);
        return toResponse(atualizado);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Professor não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    private ProfessorResponse toResponse(Professor p) {
        InstituicaoEnsinoResponse instResponse = p.getInstituicao() != null ?
                new InstituicaoEnsinoResponse(p.getInstituicao().getId(), p.getInstituicao().getNome(), p.getInstituicao().getCnpj()) : null;

        return new ProfessorResponse(
                p.getId(),
                p.getNome(),
                p.getEmail(),
                p.getLogin(),
                p.getCpf(),
                p.getDepartamento(),
                p.getSaldo(),
                instResponse
        );
    }
}