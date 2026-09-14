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
            char c = source.charAt(current);
            if (Character.isWhitespace(c)) {
                current++;
                continue;
            }

            if (Character.isDigit(c)) {
                tokens.add(number());
                continue;
            }

            if (Character.isLetter(c) || c == '_') {
                tokens.add(identifier());
                continue;
            }

            current++;
            switch (c) {
                case '+': tokens.add(new Token(TokenType.PLUS, "+")); break;
                case '-': tokens.add(new Token(TokenType.MINUS, "-")); break;
                case '*': tokens.add(new Token(TokenType.STAR, "*")); break;
                case '/': tokens.add(new Token(TokenType.SLASH, "/")); break;
                case '=': tokens.add(new Token(TokenType.ASSIGN, "=")); break;
                case ';': tokens.add(new Token(TokenType.SEMICOLON, ";")); break;
                default:
                    throw new RuntimeException("Caractere desconhecido: " + c);
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private Token number() {
        int start = current;
        while (current < source.length() && Character.isDigit(source.charAt(current))) {
            current++;
        }
        return new Token(TokenType.NUM, source.substring(start, current));
    }

    private Token identifier() {
        int start = current;
        while (current < source.length() && (Character.isLetterOrDigit(source.charAt(current)) || source.charAt(current) == '_')) {
            current++;
        }
        String text = source.substring(start, current);
        if ("let".equals(text)) return new Token(TokenType.LET, text);
        if ("print".equals(text)) return new Token(TokenType.PRINT, text);
        return new Token(TokenType.ID, text);
    }
}