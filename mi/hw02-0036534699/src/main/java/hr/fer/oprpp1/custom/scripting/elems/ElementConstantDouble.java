package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Elemenet koji sadrži double vrijednost.
 * 
 */
public class ElementConstantDouble extends Element {
    private final double value;

    /*
     * Konstruktor.
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Vraća vrijednost elementa kao String.
     */
    @Override
    public String asText() {
        return Double.toString(value);
    }
}
