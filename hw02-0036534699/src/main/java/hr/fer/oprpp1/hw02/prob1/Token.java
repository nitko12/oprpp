package hr.fer.oprpp1.hw02.prob1;

/*
 * Tokeni leksičkog analizatora.
 */
public class Token {

    private TokenType type;
    private Object value;

    /*
     * Konstruktor koji prima tip i vrijednost tokena.
     */
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /*
     * Getter za tip tokena.
     */
    public Object getValue() {
        return value;
    }

    /*
     * Getter za vrijednost tokena.
     */
    public TokenType getType() {
        return type;
    }
}
