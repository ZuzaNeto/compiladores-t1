package br.com.warmup.compiler;

import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private final String source;
    private int current = 0;

    public Scanner(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        List<Token> tokens = new ArrayList<>();
        while (current < source.length()) {
            char c = source.charAt(current++);
            if (Character.isWhitespace(c)) {
                continue;
            }
            switch (c) {
                case '+': tokens.add(new Token(TokenType.PLUS, "+")); break;
                case '-': tokens.add(new Token(TokenType.MINUS, "-")); break;
                case '*': tokens.add(new Token(TokenType.STAR, "*")); break;
                case '/': tokens.add(new Token(TokenType.SLASH, "/")); break;
                default:
                    if (Character.isDigit(c)) {
                        tokens.add(new Token(TokenType.NUM, String.valueOf(c)));
                    } else {
                        throw new RuntimeException("Caractere desconhecido: " + c);
                    }
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}