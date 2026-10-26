package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.service;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.LoginRequest;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.dto.LoginResponse;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Aluno;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.EmpresaParceira;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Professor;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.AlunoRepository;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.EmpresaParceiraRepository;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.ProfessorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final EmpresaParceiraRepository empresaRepository;

    public AuthService(
            AlunoRepository alunoRepository,
            ProfessorRepository professorRepository,
            EmpresaParceiraRepository empresaRepository) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional(readOnly = true)
    public LoginResponse autenticar(LoginRequest request) {
        for (Aluno aluno : alunoRepository.findAll()) {
            if (aluno.autenticar(request.login(), request.senha())) {
                return new LoginResponse(aluno.getId(), aluno.getNome(), aluno.getEmail(),
                        aluno.getLogin(), "ALUNO", aluno.getSaldo());
            }
        }
        for (Professor professor : professorRepository.findAll()) {
            if (professor.autenticar(request.login(), request.senha())) {
                return new LoginResponse(professor.getId(), professor.getNome(), professor.getEmail(),
                        professor.getLogin(), "PROFESSOR", professor.getSaldo());
            }
        }
        for (EmpresaParceira empresa : empresaRepository.findAll()) {
            if (empresa.autenticar(request.login(), request.senha())) {
                return new LoginResponse(empresa.getId(), empresa.getNome(), empresa.getEmail(),
                        empresa.getLogin(), "EMPRESA", Integer.MAX_VALUE);
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login ou senha invalidos");
    }
}
