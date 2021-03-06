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

        String name = "Avalia????o de Desenvolvedores";

        List<Competence> competences = new ArrayList<>();

        competences.add(new Competence("Disciplina",
                "Quando o time precisa combinar hor??rios, consegue comprir o acordo. Cumpre as normas da empresa e os compromissos de trabalho (reuni??es, treinamentos, etc).",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Capacidade de Concentra????o",
                "Mant??m, durante o tempo necess??rio, a aten????o focada nos processos e nos assuntos que est??o sendo tratados.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Autodesenvolvimento",
                "Procura evoluir pessoal, profissional e intelectualmente, buscando aperfei??oamento e atualiza????o cont??nua de seus conhecimentos.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Resultados",
                "Apresenta resultados eficazes nas atividades que desenvolve, levando em conta o tempo e a qualidade.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Autonomia",
                "Consegue desenvolver suas atividades sem a supervi??o direta da lideran??a.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Vis??o do Neg??cio",
                "Compreende como sua ??rea opera, sua miss??o e seus objetivos a longo prazo.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Adaptabilidade",
                "O quanto est?? preparado para o desempenho eficaz das atividades, mesmo sem experi??ncia na fun????o atual.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Gera????o de Conhecimento",
                "Todo conhecimento absorvido ?? registrado e disseminado atrav??s de documenta????es e conte??dos para estudo posterior, de modo a transform??-lo em vantagem para a equipe.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Intera????o",
                "Interage e mant??m bom relacionamento com seus pares, superiores e outras equipes,  contribuindo para o trabalho das outras ??reas.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Interlocu????o / Comunica????o",
                "Expressa-se de maneira clara e objetiva, ouve os outros e d?? respostas consistentes e educadas. Escuta e transmite ideias de forma efetiva, utilizando procedimentos formais e informais e proporcionando dados concretos para apoiar  observa????es e conclus??es.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Empatia",
                "Consegue colocar-se no lugar do outro para compreend??-lo sob o ponto de vista dele.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Motivar o Time",
                "Demonstra capacidade de motivar seus colegas e colaboradores.\n" +
                        "\n" +
                        "Obs.: ?? diferente de auto-motiva????o.",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Coopera????o",
                "Coopera no compartilhamento de ideias, atividades e solu????es com os membros da equipe e das demais ??reas. P??e-se ?? disposi????o espontaneamente para executar outros servi??os e auxiliar colegas, de acordo com as necessidades e possibilidades.\n" +
                        "\n" +
                        "Obs.: N??o confundir com o t??pico \"Gera????o de conhecimento\"",
                CompetenceTypeEnum.BEHAVIORAL));

        competences.add(new Competence("Algoritmos e estrutura de dados",
                "- B??sico: Capacidade de compor algoritmos que solucionam os problemas, utilizando estruturas comuns de dados.\n" +
                        "\n" +
                        "- Intermedi??rio: J?? demonstra capacidade para desenvolver algoritmos de certa complexidade, demonstrando preocupa????o com a performance.\n" +
                        "\n" +
                        "- Proficiente: Desenvolve algor??timos perform??ticos para problemas de alta complexidade, an??lise cr??tica sobre as estruturas de dados e seu impacto sobre a performance.\n" +
                        "\n" +
                        "- Avan??ado: Refer??ncia para algor??timos complexos",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Lingua. de prog.",
                "- B??sico: Conhece uma linguagem em n??vel que permite executar tarefas rotineiras.\n" +
                        "\n" +
                        "- Intermedi??rio: Conhece mais de uma linguagem com certo dom??nio, ou demonstra facilidade em aprender outras linguagens.\n" +
                        "\n" +
                        "- Proficiente: Domina uma linguagem a n??vel de suas peculiaridades, conhece outras linguagens bem o bastante para performar com elas, apresenta facilidade no aprendizados de novas linguagens.\n" +
                        "\n" +
                        "- Avan??ado: Capaz de responder sobre as caracter??sticas de uma ou mais linguagens, e apresenta dom??nio sobre o paradigma.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Testes auto.",
                "- B??sico: Consegue implementar testes simples e corrigir testes rotineiros.\n" +
                        "\n" +
                        "- Intermedi??rio: Conhece ferramentas de testes automatizados, capaz de escrever testes concisos e eficientes.\n" +
                        "\n" +
                        "- Proficiente: Demonstra preocupa????o com os testes das aplica????es, escreve testes eficientes e eficazes.\n" +
                        "\n" +
                        "- Avan??ado: Dom??nio sobre diversos paradigmas de testes, com aten????o a qualidade e cobertura dos testes",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Arquit. de SO",
                "- B??sico: Entende como o SO funciona e consegue oper??-lo com facilidade.\n" +
                        "\n" +
                        "- Intermedi??rio: Conhece como a linguagem de programa????o trabalha em cima do sistema operacional, e entende os pontos onde eles se cruzam.\n" +
                        "\n" +
                        "- Proficiente: Conhece os impactos que o SO tem sobre a execu????o dos programas, sendo apto a propor solu????es que envolvam o sistema como um todo.\n" +
                        "\n" +
                        "- Avan??ado: Conhecimento avan??ado sobre o a arquitetura dos SO, capaz de explorar seus recursos conhecendo suas limita????es.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Versionamento de c??digo",
                "- B??sico: Domina as funcionalidades b??sicas de versionamento.\n" +
                        "\n" +
                        "- Intermedi??rio: Sente-se confort??vel com a realiza????o de merges, resolu????o de conflitos e administra????o de branchs e stashs.\n" +
                        "\n" +
                        "- Proficiente: Dom??nio sobre ferramentas de versionamento, realiza rebases, cherrypicks.\n" +
                        "\n" +
                        "- Avan??ado: ?? esperado que lidar com versionamento seja natural, estando apto a ajudar os demais membros do time.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Persist??ncia de dados",
                "- B??sico: Consegue trabalhar com persist??ncia, utilizando camadas de abstra????o.\n" +
                        "\n" +
                        "- Intermedi??rio: Apesar de ainda precisar de abstra????o, ?? capaz de implementar solu????es que demandam certo n??vel de personaliza????o, tamb??m se preocupando com a performance.\n" +
                        "\n" +
                        "- Proficiente: Pensa em performance, conhece vantagens e desvantagens de diferentes DB, se preocupa com a sanidade da base.\n" +
                        "\n" +
                        "- Avan??ado: Seria capaz de administrar bases de persist??ncia, e implementa solu????es utilizando a correta rela????o com o banco de dados",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Metodologias ??geis",
                "- Intermedi??rio: Capaz de trabalhar com uma terminada metodologia, cumprindo seus contratos e sugerindo adapta????es a rotina da equipe.\n" +
                        "\n" +
                        "- Proficiente: Conhece bem uma ou mais metodologias, est?? sempre atento ao manuten????o da metodologia.\n" +
                        "\n" +
                        "- Avan??ado: Mestre da metodologia.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Integra????o/Entrega Cont??nua (CI / CD)",
                "- Intermedi??rio: Estar familiarizado com a utiliza????o de CI/CD, capaz de montar e manter um pipeline.\n" +
                        "\n" +
                        "- Proficiente: Monta ??timos pipelines e ?? capaz de melhorar os existentes, entende suas fases e os projeta de maneira clara.\n" +
                        "\n" +
                        "- Avan??ado: Cria esteiras claras e perform??ticas, que se adequam a necessidade do neg??cio.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Servi??os de mensageria",
                "- Intermedi??rio: Estar familiarizado com os conceitos de fila (FIFO, LIFO, etc) e T??picos.\n" +
                        "\n" +
                        "- Proficiente: Utiliza filas de maneira eficiente, capaz de desenhar arquiteturas complexas utilizando filas e/ou T??picos.\n" +
                        "\n" +
                        "- Avan??ado: Cria arquiteturas utilizando filas e/ou t??picos performativamente e garantindo a integridade dos dados.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Melhores pr??ticas",
                "- Intermedi??rio: Conhece e aplica boas pr??ticas em seu c??digo, capaz de analisar PR's e sugerir melhorias.\n" +
                        "\n" +
                        "- Proficiente: Produz c??digo seguindo as melhores pr??ticas, serve como refer??ncia de qualidade a equipe.\n" +
                        "\n" +
                        "- Avan??ado: Somente produz c??digo de qualidade e f??cil legibilidade, conhece a melhor pr??tica para cada cen??rio (SOLID, KISS, DRY, YAGNI)",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Padr??es de arquitetura",
                "- Intermedi??rio: Conhece e se utiliza dos padr??es de desenvolvimento.\n" +
                        "\n" +
                        "- Proficiente: Conhece as vantagens e desvantagens de cada padr??o, e demonstra capacidade de escolher o melhor modelo para cada situa????o.\n" +
                        "\n" +
                        "- Avan??ado: Capaz de argumentar com clareza e elucidar sobre as caracter??sticas dos padr??es, de forma a estabelecer a melhor utiliza????o dos mesmos.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Servi??os em nuvem",
                "- Intermedi??rio: Conhece algum servi??o de nuvem e consegue dar manuten????o em uma aplica????o j?? existente no mesmo.\n" +
                        "\n" +
                        "- Proficiente: Familiaridade com a utiliza????o de nuvens, entende os conceitos e arquiteturas de forma a tornar a sua utiliza????o agn??stica, analisando e propondo melhorias para melhor utiliza????o.\n" +
                        "\n" +
                        "- Avan??ado: Profundo conhecimento dos servi??os, conhecendo as solu????es e definindo os melhores recursos, tendo em vista a rela????o performance x custo.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Seguran??a",
                "B??sico - Capaz de criar aplica????es autentic??veis;\n" +
                        "\n" +
                        "Intermedi??rio - Consegue definir camadas de seguran??a para uma determinada aplica????o afim de mitigar riscos de ataques pela rede (Exemplo.: Slow HTTP POST vulnerability, Clickjacking, PRSSI vulnerability, Insecure Transport, ...);\n" +
                        "\n" +
                        "Proficiente - Capaz de fazer uso e manuten????o de um ambiente VPN e VPC, sempre montando restri????es necess??rias para garantiar a seguran??a da informa????o; \n" +
                        "\n" +
                        "Avan??ado - Capaz de criar, configurar e gerir todo um ambiente seguran??a, bem como inspecionar vulnerabilidades dos sistemas envolvidos;",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Gest??o de Projetos",
                "- Intermedi??rio: Capaz de administar melhorias e manuten????es em um projeto j?? existente sem acompanhamento\n" +
                        "\n" +
                        "- Proficiente: Capaz de administrar a execu????o de um projeto sem acompanhamento.\n" +
                        "\n" +
                        "- Avan??ado: Consegue definir escopos de um projeto, administrando o tempo de cada fase e delegando corretamente as tarefas.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Performance de projetos",
                "- Intermedi??rio: Capaz de aplicar melhorias de performance em cima da arquitetura da aplica????o (cache, indexa????o, enfileiramento, ...)\n" +
                        "\n" +
                        "- Proficiente: Utiliza teste de carga. Analisa o impacto das suas aplica????es em cen??rios complexos, preocupando-se com a rela????o carga x performance. Consegue propor solu????es para resolu????o de problemas relacionados a performance.\n" +
                        "\n" +
                        "- Avan??ado: Utiliza teste de carga. Ativamente monitora a performance do ambiente, prop??e solu????es e otimiza????es de performance.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("T??cnicas de Programa????o",
                "- Intermedi??rio: Conhece bem uma linguagem e capaz de analisar c??digos de maneira agn??stica.\n" +
                        "\n" +
                        "- Proficiente: Domina m??ltiplas linguagens e paradigmas, capacidade de analisar c??digos de maneira agn??stica.\n" +
                        "\n" +
                        "- Avan??ado: Entende intimamente um paradigma e possui conhecimentos avan??ados linguagens de programa????o como um todo.",
                CompetenceTypeEnum.TEHCNICAL));

        competences.add(new Competence("Arquit. de Sist. Distribu??dos",
                "- Intermedi??rio: J?? trabalhou ou trabalha em uma arquitetura que cont??m mais de um microservi??o.\n" +
                        "\n" +
                        "- Proficiente: J?? entende os pr??s e contras de arquiteturas distribu??das, desenhando e oferecendo solu????es que sejam perform??ticas e confi??veis.\n" +
                        "\n" +
                        "- Avan??ado: Conhecimento avan??ados n??o s?? sobre as melhores pr??ticas quanto sobre as melhores tecnologias e protocolos a serem utilizados.",
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
                    new Grade("N??o conhece",0),
                    new Grade("B??sico",1),
                    new Grade("Intermedi??rio",2),
                    new Grade("Proeficiente",3),
                    new Grade("Avan??ado",4)
            )));
            LOGGER.info("[Bootstrap.run] Info: GradeType " + CompetenceTypeEnum.TEHCNICAL.name() + " created.");
        }
    }

}
