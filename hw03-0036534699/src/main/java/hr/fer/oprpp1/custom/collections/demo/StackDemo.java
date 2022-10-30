package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("One argument expected, the expression to evalute, got " + args.length + ".");
            return;
        }

        String[] split_input = args[0].split(" ");

        try {
            int res = evaluate(split_input);

            System.out.println("Expression evaluates to " + res + ".");
        } catch (Exception e) {
            System.out.println("Wrong expression!");
            return;
        }
    }

    public static int evaluate(String[] input) {
        ObjectStack<Integer> stack = new ObjectStack<>();

        for (String s : input) {
            if (is_number(s))
                stack.push(Integer.parseInt(s));
            else {
                int a = (int) stack.pop();
                int b = (int) stack.pop();

                stack.push(do_operation(s, b, a));
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Neispravan izraz!");
        }

        return (int) stack.pop();
    }

    private static boolean is_number(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int do_operation(String op, int a, int b) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "/":
                if (b == 0)
                    throw new IllegalArgumentException("Djeljenje s nulom!");
                return a / b;
            case "*":
                return a * b;
            case "%":
                if (b == 0)
                    throw new IllegalArgumentException("Modulo s nulom!");
                return a % b;
            default:
                throw new IllegalArgumentException("Nepoznata operacija!");
        }
    }
}
