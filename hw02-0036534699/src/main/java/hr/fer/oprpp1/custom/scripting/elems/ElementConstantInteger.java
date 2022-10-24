package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Elemenet koji sadrži integer vrijednost.
 * 
 */
public class ElementConstantInteger extends Element {
    private final int value;

    /*
     * Konstruktor koji prima vrijednost.
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Vraća vrijednost elementa kao String.
     */
    @Override
    public String asText() {
        return Integer.toString(value);
    }
}
