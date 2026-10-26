package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.AlunoRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.AlunoResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.EnderecoDTO;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Aluno;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Endereco;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.EmpresaParceira;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.InstituicaoEnsino;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.AlunoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AlunoService {

    private final AlunoRepository repository;
    private final InstituicaoEnsinoService instituicaoService;
    private final EmpresaParceiraService empresaParceiraService;

    public AlunoService(
            AlunoRepository repository,
            InstituicaoEnsinoService instituicaoService,
            EmpresaParceiraService empresaParceiraService) {
        this.repository = repository;
        this.instituicaoService = instituicaoService;
        this.empresaParceiraService = empresaParceiraService;
    }

    @Transactional
    public AlunoResponse criar(AlunoRequest request) {
        if (repository.existsByCpf(request.cpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF ja cadastrado");
        }
        if (repository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ja cadastrado");
        }
        Aluno aluno = new Aluno();
        aplicar(aluno, request);
        return toResponse(repository.save(aluno));
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AlunoResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public AlunoResponse atualizar(Long id, AlunoRequest request) {
        Aluno aluno = buscarEntidade(id);
        if (!request.cpf().equals(aluno.getCpf()) && repository.existsByCpf(request.cpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF ja cadastrado");
        }
        if (!request.email().equals(aluno.getEmail()) && repository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ja cadastrado");
        }
        aplicar(aluno, request);
        return toResponse(repository.save(aluno));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private Aluno buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno nao encontrado"));
    }

    private void aplicar(Aluno aluno, AlunoRequest request) {
        if (request.instituicaoId() == null && request.empresaParceiraId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Selecione uma instituicao academica ou empresa parceira");
        }
        InstituicaoEnsino instituicao = request.instituicaoId() == null
                ? null : instituicaoService.buscarEntidade(request.instituicaoId());
        EmpresaParceira empresaParceira = request.empresaParceiraId() == null
                ? null : empresaParceiraService.buscarEntidade(request.empresaParceiraId());
        aluno.setNome(request.nome());
        aluno.setEmail(request.email());
        aluno.setLogin(request.login());
        aluno.setSenha(request.senha());
        aluno.setCpf(request.cpf());
        aluno.setRg(request.rg());
        aluno.setCurso(request.curso());
        aluno.setInstituicao(instituicao);
        aluno.setEmpresaParceira(empresaParceira);
        aluno.setEndereco(toEndereco(request.endereco()));
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

    private AlunoResponse toResponse(Aluno aluno) {
        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getLogin(),
                aluno.getCpf(),
                aluno.getRg(),
                aluno.getCurso(),
                aluno.getSaldo(),
                instituicaoService.toResponse(aluno.getInstituicao()),
                empresaParceiraService.toResponse(aluno.getEmpresaParceira()),
                toEnderecoDTO(aluno.getEndereco()));
    }
}
