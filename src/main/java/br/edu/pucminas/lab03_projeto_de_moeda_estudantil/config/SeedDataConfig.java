package br.edu.pucminas.lab03_projeto_de_moeda_estudantil.config;

import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.InstituicaoEnsino;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.EmpresaParceira;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Professor;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.model.Vantagem;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.EmpresaParceiraRepository;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.InstituicaoEnsinoRepository;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.ProfessorRepository;
import br.edu.pucminas.lab03_projeto_de_moeda_estudantil.repository.VantagemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDataConfig {

    @Bean
    CommandLineRunner seedData(
            InstituicaoEnsinoRepository instituicaoRepository,
            ProfessorRepository professorRepository,
            EmpresaParceiraRepository empresaParceiraRepository,
            VantagemRepository vantagemRepository) {
        return args -> {
            InstituicaoEnsino pucMinas = instituicaoRepository.findByCnpj("17217838000180")
                    .orElseGet(() -> instituicaoRepository.save(instituicao(
                            "Pontifícia Universidade Católica de Minas Gerais",
                            "17217838000180")));
            InstituicaoEnsino institutoFederal = instituicaoRepository.findByCnpj("10838815000157")
                    .orElseGet(() -> instituicaoRepository.save(instituicao(
                            "Instituto Federal de Minas Gerais",
                            "10838815000157")));

            seedProfessor(professorRepository, "11111111111", "professor.puc",
                    "Ana Paula Martins", "ana.martins@pucminas.br",
                    "Computação", pucMinas);
            seedProfessor(professorRepository, "22222222222", "professor.ifmg",
                    "Carlos Eduardo Lima", "carlos.lima@ifmg.edu.br",
                    "Administração", institutoFederal);

            EmpresaParceira parceiro = empresaParceiraRepository.findByCnpj("00000000000191")
                    .orElseGet(() -> empresaParceiraRepository.save(empresaParceira()));
            seedVantagem(vantagemRepository, parceiro, "Desconto no restaurante universitário",
                    "Desconto de R$ 10,00 no restaurante universitário da instituição.",
                    "https://images.unsplash.com/photo-1498837167922-ddd27525d352", 20);
            seedVantagem(vantagemRepository, parceiro, "Kit de materiais acadêmicos",
                    "Kit com materiais básicos para apoiar sua rotina de estudos.",
                    "https://images.unsplash.com/photo-1456324504439-367cee3b3c32", 50);
            seedVantagem(vantagemRepository, parceiro, "Desconto na mensalidade",
                    "Benefício especial para abatimento na mensalidade acadêmica.",
                    "https://images.unsplash.com/photo-1523050854058-8df90110c9f1", 100);
        };
    }

    private InstituicaoEnsino instituicao(String nome, String cnpj) {
        InstituicaoEnsino instituicao = new InstituicaoEnsino();
        instituicao.setNome(nome);
        instituicao.setCnpj(cnpj);
        return instituicao;
    }

    private EmpresaParceira empresaParceira() {
        EmpresaParceira empresa = new EmpresaParceira();
        empresa.setNome("Benefícios Institucionais");
        empresa.setEmail("beneficios@instituicoes.edu.br");
        empresa.setLogin("beneficios.institucionais");
        empresa.setSenha("beneficios123");
        empresa.setCnpj("00000000000191");
        return empresa;
    }

    private void seedVantagem(
            VantagemRepository repository,
            EmpresaParceira empresa,
            String nome,
            String descricao,
            String foto,
            int custoMoedas) {
        Vantagem vantagem = repository.findByNomeAndEmpresaParceiraId(nome, empresa.getId())
                .orElseGet(Vantagem::new);
        vantagem.setNome(nome);
        vantagem.setDescricao(descricao);
        vantagem.setFoto(foto);
        vantagem.setCustoMoedas(custoMoedas);
        vantagem.setAtiva(true);
        vantagem.setEmpresaParceira(empresa);
        repository.save(vantagem);
    }

    private void seedProfessor(
            ProfessorRepository repository,
            String cpf,
            String login,
            String nome,
            String email,
            String departamento,
            InstituicaoEnsino instituicao) {
        Professor professor = repository.findByCpf(cpf).orElseGet(Professor::new);
        professor.setNome(nome);
        professor.setEmail(email);
        professor.setLogin(login);
        professor.setSenha("professor123");
        professor.setCpf(cpf);
        professor.setDepartamento(departamento);
        professor.setInstituicao(instituicao);
        repository.save(professor);
    }
}
