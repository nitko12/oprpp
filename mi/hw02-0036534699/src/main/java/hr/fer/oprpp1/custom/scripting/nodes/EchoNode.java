package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/*
 * Node koji ispisuje.
 */
public class EchoNode extends Node {
    Element[] elements;

    /*
     * Konstruktor koji prima polje elemenata.
     */
    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

    /*
     * Metoda za ispis.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{$ = ");
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i].asText());
            if (i != elements.length - 1) {
                sb.append(" ");
            }
        }
        sb.append(" $}");

        return sb.toString();
    }
}
