package hr.fer.oprpp1.prob1.demo;

import hr.fer.oprpp1.prob1.Lexer;
import hr.fer.oprpp1.prob1.Token;
import hr.fer.oprpp1.prob1.TokenType;

public class LexerDemo {
    public static void main(String[] args) {
        String ulaz = "Ovo je 123ica, ab57.\nKraj";

        Lexer lexer = new Lexer(ulaz);

        Token t = lexer.nextToken();

        System.out.println(t);

        while (t.getType() != TokenType.EOF) {
            System.out.println(t);
            t = lexer.nextToken();
        }
    }
}
