package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.EmpresaParceiraRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.EmpresaParceiraResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.EnderecoDTO;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.EmpresaParceira;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Endereco;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.EmpresaParceiraRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmpresaParceiraService {

    private final EmpresaParceiraRepository repository;

    public EmpresaParceiraService(EmpresaParceiraRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public EmpresaParceiraResponse criar(EmpresaParceiraRequest request) {
        if (repository.existsByCnpj(request.cnpj())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CNPJ ja cadastrado");
        }
        EmpresaParceira empresa = new EmpresaParceira();
        aplicar(empresa, request);
        return toResponse(repository.save(empresa));
    }

    @Transactional(readOnly = true)
    public List<EmpresaParceiraResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EmpresaParceiraResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public EmpresaParceiraResponse atualizar(Long id, EmpresaParceiraRequest request) {
        EmpresaParceira empresa = buscarEntidade(id);
        if (!request.cnpj().equals(empresa.getCnpj()) && repository.existsByCnpj(request.cnpj())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CNPJ ja cadastrado");
        }
        aplicar(empresa, request);
        return toResponse(repository.save(empresa));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private EmpresaParceira buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa parceira nao encontrada"));
    }

    private void aplicar(EmpresaParceira empresa, EmpresaParceiraRequest request) {
        empresa.setNome(request.nome());
        empresa.setEmail(request.email());
        empresa.setLogin(request.login());
        empresa.setSenha(request.senha());
        empresa.setCnpj(request.cnpj());
        empresa.setEndereco(toEndereco(request.endereco()));
    }

    private Endereco toEndereco(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        return endereco;
    }

    private EnderecoDTO toEnderecoDTO(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoDTO(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep());
    }

    private EmpresaParceiraResponse toResponse(EmpresaParceira empresa) {
        return new EmpresaParceiraResponse(
                empresa.getId(),
                empresa.getNome(),
                empresa.getEmail(),
                empresa.getLogin(),
                empresa.getCnpj(),
                toEnderecoDTO(empresa.getEndereco()));
    }
}
