package br.com.warmup.compiler;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;
    private final List<String> instructions = new ArrayList<>();

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<String> parse() {
        while (!isAtEnd()) {
            statement();
        }
        return instructions;
    }

    private void statement() {
        if (match(TokenType.LET)) {
            letStatement();
        } else if (match(TokenType.PRINT)) {
            printStatement();
        } else {
            throw new RuntimeException("Erro Sintático: Esperado 'let' ou 'print', obtido: " + peek().getLexeme());
        }
    }

    private void letStatement() {
        Token varToken = consume(TokenType.ID, "Esperado nome da variável.");
        consume(TokenType.ASSIGN, "Esperado '=' após o identificador.");
        expr();
        consume(TokenType.SEMICOLON, "Esperado ';' ao final do comando let.");
        emit("pop " + varToken.getLexeme());
    }

    private void printStatement() {
        expr();
        consume(TokenType.SEMICOLON, "Esperado ';' ao final do comando print.");
        emit("print");
    }

    private void expr() {
        term();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token op = previous();
            term();
            emit(op.getType() == TokenType.PLUS ? "add" : "sub");
        }
    }

    private void term() {
        factor();
        while (match(TokenType.STAR, TokenType.SLASH)) {
            Token op = previous();
            factor();
            emit(op.getType() == TokenType.STAR ? "mult" : "div");
        }
    }

    private void factor() {
        if (match(TokenType.NUM) || match(TokenType.ID)) {
            emit("push " + previous().getLexeme());
        } else {
            throw new RuntimeException("Erro Sintático: Fator inesperado '" + peek().getLexeme() + "'");
        }
    }

    private void emit(String instr) {
        instructions.add(instr);
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();
        throw new RuntimeException("Erro Sintático: " + message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}