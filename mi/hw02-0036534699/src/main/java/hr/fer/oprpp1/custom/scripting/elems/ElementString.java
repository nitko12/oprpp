package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa koja predstavlja string.
 * 
 */
public class ElementString extends Element {
    private final String value;

    /*
     * Konstruktor koji prima vrijednost stringa.
     */
    public ElementString(String value) {
        this.value = value;
    }

    /**
     * Vraća vrijednost elementa kao String.
     * 
     * Escapea string nazad.
     */
    @Override
    public String asText() {

        String s = value.substring(1, value.length() - 1);

        s = s.replace("\\", "\\\\");
        s = s.replace("\"", "\\\"");

        s = '"' + s + '"';

        return s;
    }
}
