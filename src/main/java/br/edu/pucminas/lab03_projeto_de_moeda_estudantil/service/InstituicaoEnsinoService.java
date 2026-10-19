package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.InstituicaoEnsinoRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.InstituicaoEnsinoResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.InstituicaoEnsino;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.InstituicaoEnsinoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InstituicaoEnsinoService {

    private final InstituicaoEnsinoRepository repository;

    public InstituicaoEnsinoService(InstituicaoEnsinoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public InstituicaoEnsinoResponse criar(InstituicaoEnsinoRequest request) {
        if (repository.existsByCnpj(request.cnpj())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CNPJ ja cadastrado");
        }
        InstituicaoEnsino instituicao = new InstituicaoEnsino();
        instituicao.setNome(request.nome());
        instituicao.setCnpj(request.cnpj());
        return toResponse(repository.save(instituicao));
    }

    @Transactional(readOnly = true)
    public List<InstituicaoEnsinoResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public InstituicaoEnsino buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instituicao de ensino nao encontrada"));
    }

    InstituicaoEnsinoResponse toResponse(InstituicaoEnsino instituicao) {
        return new InstituicaoEnsinoResponse(
                instituicao.getId(),
                instituicao.getNome(),
                instituicao.getCnpj());
    }
}
