package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa koja predstavlja funkciju.
 * 
 */
public class ElementFunction extends Element {
    private final String name;

    /*
     * Konstruktor koji prima ime funkcije.
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    /**
     * Vraća ime funkcije kao String.
     */
    @Override
    public String asText() {
        return name;
    }
}