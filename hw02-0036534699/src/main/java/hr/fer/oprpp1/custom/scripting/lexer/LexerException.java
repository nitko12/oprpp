package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Klasa koja predstavlja iznimku koja se baca u slučaju pogreške u leksičkoj
 * analizi.
 * 
 */
public class LexerException extends RuntimeException {
    public LexerException() {
        super();
    }

    public LexerException(String errorMsg) {
        super(errorMsg);
    }
}