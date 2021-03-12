#Professional Assessment API

---

### 1. Introdução

Essa aplicação visa prover uma forma de acompanhar o crescimento profissional de membros de um time.

![alt text](help/assessment_test.png "Title")

---

### 2. Tecnologias

- SpringBoot - Java 11;

---

### 3. Instalação de componentes básicos

1. Instalar o SDKMAN

    ```
    $ curl -s "https://get.sdkman.io" | bash
    $ source "$HOME/.sdkman/bin/sdkman-init.sh"
    $ sdk version
    ```

2. Instalar e usar o Java 8:

    ```
    $ sdk list java
    $ sdk use java 8.0.222-zulu
    ```

3. ( Se não for rodar pelo Intellij ) Instalar e usar o Gradle:

    ```
    $ sdk use gradle 6.3
    ```

4. [OPCIONAL] Instalar a IDE do Intellij e o Lombok Plugin

    4.1 Instalar o [Intellij](https://www.jetbrains.com/idea/);
    
    4.2 Abrir Intellij, ir para o menu de plugins (File > Settings... > Plugins) e instalar o Lombok;
    
    4.3 Após reiniciar clique no menu: File > Settings... > Build, Execution, Deployment >
   Compiler > Annotation Processors e marque o checkbox "Enable Annotation Processing";

---

### 5. Executanto a aplicação

```
$ gradle bootRun
```