package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa koja predstavlja operator.
 * 
 */
public class ElementOperator extends Element {
    private final String symbol;

    /*
     * Konstruktor koji prima simbol operatora.
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Vraća simbol operatora kao String.
     */
    @Override
    public String asText() {
        return symbol;
    }
}
