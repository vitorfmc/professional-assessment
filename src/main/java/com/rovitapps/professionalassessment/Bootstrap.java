package com.rovitapps.professionalassessment;

import com.rovitapps.professionalassessment.model.*;
import com.rovitapps.professionalassessment.repository.AssessmentTemplateRepository;
import com.rovitapps.professionalassessment.repository.GradeTypeRepository;
import com.rovitapps.professionalassessment.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Bootstrap implements ApplicationRunner {

    private static final Logger LOGGER = LogManager.getLogger(Bootstrap.class);

    private final GradeTypeRepository gradeTypeRepository;
    private final AssessmentTemplateRepository assessmentTemplateRepository;
    private final UserRepository userRepository;

    @Autowired
    public Bootstrap(GradeTypeRepository gradeTypeRepository, AssessmentTemplateRepository assessmentTemplateRepository,
                     UserRepository userRepository) {

        this.gradeTypeRepository = gradeTypeRepository;
        this.assessmentTemplateRepository = assessmentTemplateRepository;
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        LOGGER.info("[Bootstrap.run] Info: Initializing...");

        try{
            createGradeTypes();
            createAssessmentTemplates();

            createUser("user1", "User 1", "y@y.com");
            createUser("user2", "User 1", "x@x.com");

        }catch (Exception e){
            LOGGER.error("[Bootstrap.run] Error: " + e.getMessage(), e);
        }

        LOGGER.info("[Bootstrap.run] Info: End!");
    }

    private void createUser(String username, String name, String email){
        if(userRepository.findByUsername(username) == null){
            userRepository.save(new User(name, username, email, (new BCryptPasswordEncoder()).encode(username),
                    Arrays.asList(
                        new Role("ONEAONE_LIST"),
                        new Role("ONEAONE_FIND_ONE"),
                        new Role("ONEAONE_CREATE"),
                        new Role("ONEAONE_UPDATE"),
                        new Role("ONEAONE_DELETE"),
                        new Role("USER_LIST"),
                        new Role("ASSESSMENT_TEMPLATE_LIST"),
                        new Role("ASSESSMENT_LIST"),
                        new Role("ASSESSMENT_FIND_ONE")

            )));
            LOGGER.info("[Bootstrap.run] Info: User " + name + " created.");
        }
    }

    private void createAssessmentTemplates(){

        String name = "Avaliação de Desenvolvedores";

        List<Competence> competences = new ArrayList<>();

        competences.add(new Competence("Disciplina",
                "Quando o time precisa combinar horários, consegue comprir o acordo. Cumpre as normas da empresa e os compromissos de trabalho (reuniões, treinamentos, etc).",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Capacidade de Concentração",
                "Mantém, durante o tempo necessário, a atenção focada nos processos e nos assuntos que estão sendo tratados.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Autodesenvolvimento",
                "Procura evoluir pessoal, profissional e intelectualmente, buscando aperfeiçoamento e atualização contínua de seus conhecimentos.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Resultados",
                "Apresenta resultados eficazes nas atividades que desenvolve, levando em conta o tempo e a qualidade.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Autonomia",
                "Consegue desenvolver suas atividades sem a supervião direta da liderança.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Visão do Negócio",
                "Compreende como sua área opera, sua missão e seus objetivos a longo prazo.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Adaptabilidade",
                "O quanto está preparado para o desempenho eficaz das atividades, mesmo sem experiência na função atual.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Geração de Conhecimento",
                "Todo conhecimento absorvido é registrado e disseminado através de documentações e conteúdos para estudo posterior, de modo a transformá-lo em vantagem para a equipe.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Interação",
                "Interage e mantém bom relacionamento com seus pares, superiores e outras equipes,  contribuindo para o trabalho das outras áreas.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Interlocução / Comunicação",
                "Expressa-se de maneira clara e objetiva, ouve os outros e dá respostas consistentes e educadas. Escuta e transmite ideias de forma efetiva, utilizando procedimentos formais e informais e proporcionando dados concretos para apoiar  observações e conclusões.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Empatia",
                "Consegue colocar-se no lugar do outro para compreendê-lo sob o ponto de vista dele.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Motivar o Time",
                "Demonstra capacidade de motivar seus colegas e colaboradores.\n" +
                        "\n" +
                        "Obs.: É diferente de auto-motivação.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Cooperação",
                "Coopera no compartilhamento de ideias, atividades e soluções com os membros da equipe e das demais áreas. Põe-se à disposição espontaneamente para executar outros serviços e auxiliar colegas, de acordo com as necessidades e possibilidades.\n" +
                        "\n" +
                        "Obs.: Não confundir com o tópico \"Geração de conhecimento\"",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Algoritmos e estrutura de dados",
                "- Básico: Capacidade de compor algoritmos que solucionam os problemas, utilizando estruturas comuns de dados.\n" +
                        "\n" +
                        "- Intermediário: Já demonstra capacidade para desenvolver algoritmos de certa complexidade, demonstrando preocupação com a performance.\n" +
                        "\n" +
                        "- Proficiente: Desenvolve algorítimos performáticos para problemas de alta complexidade, análise crítica sobre as estruturas de dados e seu impacto sobre a performance.\n" +
                        "\n" +
                        "- Avançado: Referência para algorítimos complexos",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Lingua. de prog.",
                "- Básico: Conhece uma linguagem em nível que permite executar tarefas rotineiras.\n" +
                        "\n" +
                        "- Intermediário: Conhece mais de uma linguagem com certo domínio, ou demonstra facilidade em aprender outras linguagens.\n" +
                        "\n" +
                        "- Proficiente: Domina uma linguagem a nível de suas peculiaridades, conhece outras linguagens bem o bastante para performar com elas, apresenta facilidade no aprendizados de novas linguagens.\n" +
                        "\n" +
                        "- Avançado: Capaz de responder sobre as características de uma ou mais linguagens, e apresenta domínio sobre o paradigma.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Testes auto.",
                "- Básico: Consegue implementar testes simples e corrigir testes rotineiros.\n" +
                        "\n" +
                        "- Intermediário: Conhece ferramentas de testes automatizados, capaz de escrever testes concisos e eficientes.\n" +
                        "\n" +
                        "- Proficiente: Demonstra preocupação com os testes das aplicações, escreve testes eficientes e eficazes.\n" +
                        "\n" +
                        "- Avançado: Domínio sobre diversos paradigmas de testes, com atenção a qualidade e cobertura dos testes",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Arquit. de SO",
                "- Básico: Entende como o SO funciona e consegue operá-lo com facilidade.\n" +
                        "\n" +
                        "- Intermediário: Conhece como a linguagem de programação trabalha em cima do sistema operacional, e entende os pontos onde eles se cruzam.\n" +
                        "\n" +
                        "- Proficiente: Conhece os impactos que o SO tem sobre a execução dos programas, sendo apto a propor soluções que envolvam o sistema como um todo.\n" +
                        "\n" +
                        "- Avançado: Conhecimento avançado sobre o a arquitetura dos SO, capaz de explorar seus recursos conhecendo suas limitações.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Versionamento de código",
                "- Básico: Domina as funcionalidades básicas de versionamento.\n" +
                        "\n" +
                        "- Intermediário: Sente-se confortável com a realização de merges, resolução de conflitos e administração de branchs e stashs.\n" +
                        "\n" +
                        "- Proficiente: Domínio sobre ferramentas de versionamento, realiza rebases, cherrypicks.\n" +
                        "\n" +
                        "- Avançado: É esperado que lidar com versionamento seja natural, estando apto a ajudar os demais membros do time.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Persistência de dados",
                "- Básico: Consegue trabalhar com persistência, utilizando camadas de abstração.\n" +
                        "\n" +
                        "- Intermediário: Apesar de ainda precisar de abstração, é capaz de implementar soluções que demandam certo nível de personalização, também se preocupando com a performance.\n" +
                        "\n" +
                        "- Proficiente: Pensa em performance, conhece vantagens e desvantagens de diferentes DB, se preocupa com a sanidade da base.\n" +
                        "\n" +
                        "- Avançado: Seria capaz de administrar bases de persistência, e implementa soluções utilizando a correta relação com o banco de dados",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Metodologias Ágeis",
                "- Intermediário: Capaz de trabalhar com uma terminada metodologia, cumprindo seus contratos e sugerindo adaptações a rotina da equipe.\n" +
                        "\n" +
                        "- Proficiente: Conhece bem uma ou mais metodologias, está sempre atento ao manutenção da metodologia.\n" +
                        "\n" +
                        "- Avançado: Mestre da metodologia.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Integração/Entrega Contínua (CI / CD)",
                "- Intermediário: Estar familiarizado com a utilização de CI/CD, capaz de montar e manter um pipeline.\n" +
                        "\n" +
                        "- Proficiente: Monta ótimos pipelines e é capaz de melhorar os existentes, entende suas fases e os projeta de maneira clara.\n" +
                        "\n" +
                        "- Avançado: Cria esteiras claras e performáticas, que se adequam a necessidade do negócio.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Serviços de mensageria",
                "- Intermediário: Estar familiarizado com os conceitos de fila (FIFO, LIFO, etc) e Tópicos.\n" +
                        "\n" +
                        "- Proficiente: Utiliza filas de maneira eficiente, capaz de desenhar arquiteturas complexas utilizando filas e/ou Tópicos.\n" +
                        "\n" +
                        "- Avançado: Cria arquiteturas utilizando filas e/ou tópicos performativamente e garantindo a integridade dos dados.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Melhores práticas",
                "- Intermediário: Conhece e aplica boas práticas em seu código, capaz de analisar PR's e sugerir melhorias.\n" +
                        "\n" +
                        "- Proficiente: Produz código seguindo as melhores práticas, serve como referência de qualidade a equipe.\n" +
                        "\n" +
                        "- Avançado: Somente produz código de qualidade e fácil legibilidade, conhece a melhor prática para cada cenário (SOLID, KISS, DRY, YAGNI)",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Padrões de arquitetura",
                "- Intermediário: Conhece e se utiliza dos padrões de desenvolvimento.\n" +
                        "\n" +
                        "- Proficiente: Conhece as vantagens e desvantagens de cada padrão, e demonstra capacidade de escolher o melhor modelo para cada situação.\n" +
                        "\n" +
                        "- Avançado: Capaz de argumentar com clareza e elucidar sobre as características dos padrões, de forma a estabelecer a melhor utilização dos mesmos.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Serviços em nuvem",
                "- Intermediário: Conhece algum serviço de nuvem e consegue dar manutenção em uma aplicação já existente no mesmo.\n" +
                        "\n" +
                        "- Proficiente: Familiaridade com a utilização de nuvens, entende os conceitos e arquiteturas de forma a tornar a sua utilização agnóstica, analisando e propondo melhorias para melhor utilização.\n" +
                        "\n" +
                        "- Avançado: Profundo conhecimento dos serviços, conhecendo as soluções e definindo os melhores recursos, tendo em vista a relação performance x custo.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Segurança",
                "Básico - Capaz de criar aplicações autenticáveis;\n" +
                        "\n" +
                        "Intermediário - Consegue definir camadas de segurança para uma determinada aplicação afim de mitigar riscos de ataques pela rede (Exemplo.: Slow HTTP POST vulnerability, Clickjacking, PRSSI vulnerability, Insecure Transport, ...);\n" +
                        "\n" +
                        "Proficiente - Capaz de fazer uso e manutenção de um ambiente VPN e VPC, sempre montando restrições necessárias para garantiar a segurança da informação; \n" +
                        "\n" +
                        "Avançado - Capaz de criar, configurar e gerir todo um ambiente segurança, bem como inspecionar vulnerabilidades dos sistemas envolvidos;",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Gestão de Projetos",
                "- Intermediário: Capaz de administar melhorias e manutenções em um projeto já existente sem acompanhamento\n" +
                        "\n" +
                        "- Proficiente: Capaz de administrar a execução de um projeto sem acompanhamento.\n" +
                        "\n" +
                        "- Avançado: Consegue definir escopos de um projeto, administrando o tempo de cada fase e delegando corretamente as tarefas.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Performance de projetos",
                "- Intermediário: Capaz de aplicar melhorias de performance em cima da arquitetura da aplicação (cache, indexação, enfileiramento, ...)\n" +
                        "\n" +
                        "- Proficiente: Utiliza teste de carga. Analisa o impacto das suas aplicações em cenários complexos, preocupando-se com a relação carga x performance. Consegue propor soluções para resolução de problemas relacionados a performance.\n" +
                        "\n" +
                        "- Avançado: Utiliza teste de carga. Ativamente monitora a performance do ambiente, propõe soluções e otimizações de performance.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Técnicas de Programação",
                "- Intermediário: Conhece bem uma linguagem e capaz de analisar códigos de maneira agnóstica.\n" +
                        "\n" +
                        "- Proficiente: Domina múltiplas linguagens e paradigmas, capacidade de analisar códigos de maneira agnóstica.\n" +
                        "\n" +
                        "- Avançado: Entende intimamente um paradigma e possui conhecimentos avançados linguagens de programação como um todo.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Arquit. de Sist. Distribuídos",
                "- Intermediário: Já trabalhou ou trabalha em uma arquitetura que contém mais de um microserviço.\n" +
                        "\n" +
                        "- Proficiente: Já entende os prós e contras de arquiteturas distribuídas, desenhando e oferecendo soluções que sejam performáticas e confiáveis.\n" +
                        "\n" +
                        "- Avançado: Conhecimento avançados não só sobre as melhores práticas quanto sobre as melhores tecnologias e protocolos a serem utilizados.",
                CompetenceTypeEnum.TEHCNICAL));

        if(assessmentTemplateRepository.findByName(name) == null){
            assessmentTemplateRepository.save(new AssessmentTemplate(name, competences));
            LOGGER.info("[Bootstrap.run] Info: Assessment " + name + " created.");
        }
    }

    private void createGradeTypes(){
        if(gradeTypeRepository.findByType(CompetenceTypeEnum.BEHAVIORAL.name()) == null){
            gradeTypeRepository.save(new GradeType(CompetenceTypeEnum.BEHAVIORAL, Arrays.asList(
                    new Grade("Selecione",0),
                    new Grade("Deve Melhorar",1),
                    new Grade("Regular",2),
                    new Grade("Bom",3),
                    new Grade("Excelente",4)
            )));
            LOGGER.info("[Bootstrap.run] Info: GradeType " + CompetenceTypeEnum.BEHAVIORAL.name() + " created.");
        }

        if(gradeTypeRepository.findByType(CompetenceTypeEnum.TEHCNICAL.name()) == null){
            gradeTypeRepository.save(new GradeType(CompetenceTypeEnum.TEHCNICAL, Arrays.asList(
                    new Grade("Não conhece",0),
                    new Grade("Básico",1),
                    new Grade("Intermediário",2),
                    new Grade("Proeficiente",3),
                    new Grade("Avançado",4)
            )));
            LOGGER.info("[Bootstrap.run] Info: GradeType " + CompetenceTypeEnum.TEHCNICAL.name() + " created.");
        }
    }

}
