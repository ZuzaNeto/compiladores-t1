package br.com.warmup.compiler;

import java.io.IOException;

public class SimpleTranslator {
    private int lookahead;

    public SimpleTranslator() throws IOException {
        lookahead = System.in.read();
    }

    private void match(int t) throws IOException {
        if (lookahead == t) {
            lookahead = System.in.read();
        } else {
            throw new RuntimeException("Erro de sintaxe: esperado '" + (char) t + "', obtido '" + (char) lookahead + "'");
        }
    }

    public void expr() throws IOException {
        term();
        while (true) {
            if (lookahead == '+') {
                match('+');
                term();
                System.out.print('+');
            } else if (lookahead == '-') {
                match('-');
                term();
                System.out.print('-');
            } else {
                return;
            }
        }
    }

    private void term() throws IOException {
        if (Character.isDigit((char) lookahead)) {
            char val = (char) lookahead;
            match(lookahead);
            System.out.print(val);
        } else {
            throw new RuntimeException("Erro de sintaxe: esperado digito");
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Digite a expressao (ex: 9-5+2) e pressione Enter:");
        SimpleTranslator translator = new SimpleTranslator();
        translator.expr();
        System.out.println();
    }
}