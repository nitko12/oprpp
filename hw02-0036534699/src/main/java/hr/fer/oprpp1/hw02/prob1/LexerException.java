package hr.fer.oprpp1.hw02.prob1;

/*
 * Iznimke lexera.
 */
public class LexerException extends RuntimeException {

    /**
     * Defaultni konstruktor.
     */
    public LexerException() {
        super();
    }

    /**
     * Konstruktor koji prima poruku.
     * 
     * @param message poruka
     */
    public LexerException(String message) {
        super(message);
    }

}
