package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

public class EchoNode extends Node {
    Element[] elements;

    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

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
