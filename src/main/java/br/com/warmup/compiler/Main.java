package br.com.warmup.compiler;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input =
                "let a = 42 + 5;\n" +
                        "print a + 6;\n" +
                        "let b = a * 2 + 10 / 2;\n" +
                        "print b;\n";

        System.out.println("Entrada:\n" + input);

        Scanner scanner = new Scanner(input);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<String> instructions = parser.parse();

        System.out.println("Instrucoes Pos-fixas:");
        for (String instr : instructions) {
            System.out.println("  " + instr);
        }

        System.out.println("\nSaida do Interpretador:");
        Interpreter interpreter = new Interpreter();
        interpreter.execute(instructions);
    }
}