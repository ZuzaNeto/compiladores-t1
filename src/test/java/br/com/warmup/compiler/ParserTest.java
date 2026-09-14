package br.com.warmup.compiler;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void deveGerarCodigoCorretoComPrecedencia() {
        String input = "let a = 42 + 5; print a + 6; let b = a * 2 + 10 / 2; print b;";
        Scanner scanner = new Scanner(input);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<String> instructions = parser.parse();

        // 42 + 5 -> push 42, push 5, add, pop a
        assertEquals("push 42", instructions.get(0));
        assertEquals("push 5", instructions.get(1));
        assertEquals("add", instructions.get(2));
        assertEquals("pop a", instructions.get(3));

        // print a + 6 -> push a, push 6, add, print
        assertEquals("push a", instructions.get(4));
        assertEquals("push 6", instructions.get(5));
        assertEquals("add", instructions.get(6));
        assertEquals("print", instructions.get(7));

        // a * 2 + 10 / 2 -> push a, push 2, mult, push 10, push 2, div, add, pop b
        assertEquals("push a", instructions.get(8));
        assertEquals("push 2", instructions.get(9));
        assertEquals("mult", instructions.get(10));
        assertEquals("push 10", instructions.get(11));
        assertEquals("push 2", instructions.get(12));
        assertEquals("div", instructions.get(13));
        assertEquals("add", instructions.get(14));
        assertEquals("pop b", instructions.get(15));

        // print b -> push b, print
        assertEquals("push b", instructions.get(16));
        assertEquals("print", instructions.get(17));
    }
}