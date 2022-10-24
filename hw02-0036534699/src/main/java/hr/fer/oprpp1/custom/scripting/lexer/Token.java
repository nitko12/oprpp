package hr.fer.oprpp1.custom.scripting.lexer;

/*
 * Razred koji predstavlja token.
 */
public class Token {
    private final TokenEnum type;
    private final Object value;

    /*
     * Konstruktor koji prima tip i vrijednost tokena.
     */
    public Token(TokenEnum type, Object value) {
        this.type = type;
        this.value = value;
    }

    /*
     * Getter za tip tokena.
     */
    public TokenEnum getType() {
        return type;
    }

    /*
     * Getter za vrijednost tokena.
     */
    public Object getValue() {
        return value;
    }
}
