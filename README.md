# Tradutor de Aquecimento: Expressões Aritméticas → Notação Pós-fixa

Este projeto implementa um tradutor de expressões aritméticas dirigido por sintaxe com geração de código para uma máquina de pilha abstrata e um interpretador de pilha integrado, correspondente ao **Trabalho 1** da disciplina de **Compiladores**.

O desenvolvimento segue a transição do Capítulo 2 do livro-texto da disciplina (da abordagem caractere a caractere para uma arquitetura clássica com Scanner, Parser descendente recursivo e Interpretador), incluindo a extensão obrigatória dos operadores de multiplicação (`*`) e divisão (`/`) com precedência gramatical correta.

---

## Estrutura e Arquitetura

O projeto está modularizado dentro do pacote `br.com.warmup.compiler`[cite: 2]:

```text
compiladores-t1/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── br/
    │           └── com/
    │               └── warmup/
    │                   └── compiler/
    │                       ├── SimpleTranslator.java  # Passo 1 & extensão: tradutor caractere a caractere
    │                       ├── TokenType.java         # Tipos de tokens da linguagem
    │                       ├── Token.java             # Representação léxica (tipo e lexema)
    │                       ├── Scanner.java           # Analisador léxico com suporte a números, IDs e palavras-chave
    │                       ├── Parser.java            # Analisador sintático descendente recursivo com SDT
    │                       ├── Interpreter.java       # Máquina virtual baseada em pilha com mapa de variáveis
    │                       └── Main.java              # Pipeline completo (Scanner -> Parser -> Interpreter)
    └── test/
        └── java/
            └── br/
                └── com/
                    └── warmup/
                        └── compiler/
                            └── ParserTest.java        # Testes de unidade e validação da tradução

```
---

# 📐 Gramática Livre de Contexto e Esquema de Tradução (SDT)

A gramática livre de contexto adota uma hierarquia de regras que garante a precedência de operadores (* e / sobre + e -), a associatividade à esquerda e o suporte aos comandos de declaração/atribuição (let) e de impressão (print):

```
program    -> (statement)* EOF
statement  -> 'let' ID '=' expr ';'   { emit("pop " + id) }
| 'print' expr ';'        { emit("print") }
expr       -> term ( ('+' term { emit("add") }) | ('-' term { emit("sub") }) )*
term       -> factor ( ('*' factor { emit("mult") }) | ('/' factor { emit("div") }) )*
factor     -> NUM                     { emit("push " + num) }
| ID                      { emit("push " + id) }
| '(' expr ')'
```

### Conjunto de Instruções Gerado
- **push <num | id>:** Empilha o valor imediato ou o valor carregado da variável indicada.

- **pop <id>:** Desempilha o elemento do topo e armazena na variável identificada.

- **add:** Desempilha os dois valores do topo, realiza a soma e empilha o resultado.

- **sub:** Desempilha o subtraendo e o minuendo, realiza a subtração e empilha o resultado.

- **mult:** Desempilha os dois valores do topo, realiza a multiplicação e empilha o resultado.

- **div:** Desempilha divisor e dividendo, realiza a divisão inteira e empilha o resultado.

- **print:** Desempilha o elemento do topo da pilha e imprime o resultado na saída padrão.

# 🛠️ Etapas do Tutorial Implementadas
1. Passo 1 (Tradutor básico): Implementação inicial caractere a caractere em SimpleTranslator.java, utilizando System.in.read() para operadores + e -.

2. Extensão obrigatória: Adaptação da gramática com a introdução do não-terminal term e do terminal factor para suportar * e / com precedência correta.

3. Passos 2 e 3 (Análise Léxica Desacoplada): Criação de TokenType, Token e do componente Scanner.java, eliminando chamadas diretas de leitura do fluxo de entrada e ignorando espaços em branco e tabulações.

4. Passos 4 e 5 (Suporte a números inteiros multidígitos): Scanner atualizado para agregar sequências contínuas de dígitos numéricos em um único token do tipo NUM.

5. Passo 6 (Identificadores e comando let): Reconhecimento de nomes de variáveis e da palavra-chave let, emitindo instruções pop <id> no encerramento da atribuição.

6. Passo 7 (Comando print e pontuação): Suporte à instrução print, pontuação de término de comando (;) e geração da instrução print.

7. Passo 8 (Interpretador de Pilha): Criação da classe Interpreter.java, operando sobre uma pilha de inteiros (Stack<Integer>) e um mapa em memória (Map<String, Integer>) para execução do código gerado.

# 💻 Exemplo de Execução
## Entrada de Código de Teste (Main.java)

```
let a = 42 + 5;
print a + 6;
let b = a * 2 + 10 / 2;
print b;
```

## Instruções Pós-fixas Geradas pelo Parser
```
push 42
push 5
add
pop a
push a
push 6
add
print
push a
push 2
mult
push 10
push 2
div
add
pop b
push b
print
```

# Saída no Console pelo Interpretador
```
53
99
```

# 🧪 Testes Automatizados
O projeto conta com testes unitários em JUnit 5 na pasta `src/test/java/br/com/warmup/compiler/ParserTest.java`, cobrindo:

- A ordem correta de emissão dos operadores pós-fixos respeitando a precedência (mult e div antes de add e sub).

- A formação correta das instruções push, pop e print.

# ⚙️ Pré-requisitos e Execução

- Java JDK: Versão 17 ou superior[cite: 2]

- Apache Maven: Versão 3.8 ou superior

## Compilar e Rodar os Testes
```
mvn test
```

## Executar a Aplicação Principal
```
mvn compile exec:java -Dexec.mainClass="br.com.warmup.compiler.Main"
```

## Compilar e Executar Manualmente via Linha de Comando
```
javac -d target/classes src/main/java/br/com/warmup/compiler/*.java
java -cp target/classes br.com.warmup.compiler.Main
```

## 👥 Créditos

**Aluno:** José Nunes de Sousa Neto
**Disciplina:** EECP0026 — Compiladores  
**Professor:** Prof. Dr. Sergio Souza Costa  
**Instituição:** UFMA — Universidade Federal do Maranhão  
**Semestre:** 2026.2

---

<div align="center">

**Este repositório possui fins acadêmicos.**

</div>

---