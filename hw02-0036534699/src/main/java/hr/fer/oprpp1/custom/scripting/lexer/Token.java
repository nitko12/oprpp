package hr.fer.oprpp1.custom.scripting.lexer;

public class Token {
    private final TokenEnum type;
    private final Object value;

    public Token(TokenEnum type, Object value) {
        this.type = type;
        this.value = value;
    }

    public TokenEnum getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
