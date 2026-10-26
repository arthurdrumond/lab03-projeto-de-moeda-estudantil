package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.VantagemRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.VantagemResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.EmpresaParceira;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Vantagem;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.VantagemRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VantagemService {

    private final VantagemRepository repository;
    private final EmpresaParceiraService empresaParceiraService;

    public VantagemService(VantagemRepository repository, EmpresaParceiraService empresaParceiraService) {
        this.repository = repository;
        this.empresaParceiraService = empresaParceiraService;
    }

    @Transactional
    public VantagemResponse criar(VantagemRequest request) {
        Vantagem vantagem = new Vantagem();
        aplicar(vantagem, request);
        return toResponse(repository.save(vantagem));
    }

    @Transactional(readOnly = true)
    public List<VantagemResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<VantagemResponse> listarAtivas() {
        return repository.findByAtivaTrue().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public VantagemResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public VantagemResponse atualizar(Long id, VantagemRequest request) {
        Vantagem vantagem = buscarEntidade(id);
        aplicar(vantagem, request);
        return toResponse(repository.save(vantagem));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private Vantagem buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vantagem nao encontrada"));
    }

    private void aplicar(Vantagem vantagem, VantagemRequest request) {
        if (request.descricao() == null || request.descricao().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Descricao e obrigatoria");
        }
        if (request.foto() == null || request.foto().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Foto e obrigatoria");
        }
        if (request.custoMoedas() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Custo precisa ser maior que zero");
        }
        if (request.empresaParceiraId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa parceira e obrigatoria");
        }
        EmpresaParceira empresaParceira = empresaParceiraService.buscarEntidade(request.empresaParceiraId());

        vantagem.setNome(request.nome());
        vantagem.setDescricao(request.descricao());
        vantagem.setFoto(request.foto());
        vantagem.setCustoMoedas(request.custoMoedas());
        vantagem.setAtiva(request.ativa() != null ? request.ativa() : true);
        vantagem.setEmpresaParceira(empresaParceira);
    }

    private VantagemResponse toResponse(Vantagem vantagem) {
        return new VantagemResponse(
                vantagem.getId(),
                vantagem.getNome(),
                vantagem.getDescricao(),
                vantagem.getFoto(),
                vantagem.getCustoMoedas(),
                vantagem.isAtiva(),
                empresaParceiraService.toResponse(vantagem.getEmpresaParceira()));
    }
}
