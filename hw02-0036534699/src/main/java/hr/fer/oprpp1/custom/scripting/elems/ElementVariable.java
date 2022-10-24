package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa koja predstavlja varijablu.
 * 
 */
public class ElementVariable extends Element {
    private final String name;

    /*
     * Konstruktor koji prima ime varijable.
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    /**
     * Vraća ime varijable kao String.
     */
    @Override
    public String asText() {
        return name;
    }
}
