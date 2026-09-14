package br.com.warmup.compiler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Interpreter {
    private final Stack<Integer> stack = new Stack<>();
    private final Map<String, Integer> memory = new HashMap<>();

    public void execute(List<String> instructions) {
        for (String instr : instructions) {
            String[] parts = instr.split(" ");
            String op = parts[0];

            switch (op) {
                case "push":
                    String val = parts[1];
                    if (val.matches("\\d+")) {
                        stack.push(Integer.parseInt(val));
                    } else {
                        if (!memory.containsKey(val)) {
                            throw new RuntimeException("Variavel nao definida: " + val);
                        }
                        stack.push(memory.get(val));
                    }
                    break;
                case "pop":
                    memory.put(parts[1], stack.pop());
                    break;
                case "add": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a + b);
                    break;
                }
                case "sub": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);
                    break;
                }
                case "mult": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a * b);
                    break;
                }
                case "div": {
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a / b);
                    break;
                }
                case "print":
                    System.out.println(stack.pop());
                    break;
                default:
                    throw new RuntimeException("Instrucao desconhecida: " + instr);
            }
        }
    }
}