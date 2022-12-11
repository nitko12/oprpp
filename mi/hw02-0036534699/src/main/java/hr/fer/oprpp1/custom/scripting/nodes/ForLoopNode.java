package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/*
 * Klasa for petlja nodea.
 */
public class ForLoopNode extends Node {
    ElementVariable variable;
    Element startExpression;
    Element endExpression;
    Element stepExpression;

    /*
     * Konstruktor koji prima varijablu, početnu, završnu i korak.
     */
    public ForLoopNode(ElementVariable variable,
            Element startExpression,
            Element endExpression,
            Element stepExpression) {

        if (variable == null) {
            throw new IllegalArgumentException("Varijabla ne smije biti null.");
        }
        if (startExpression == null) {
            throw new IllegalArgumentException("Početna vrijednost ne smije biti null.");
        }
        if (endExpression == null) {
            throw new IllegalArgumentException("Krajnja vrijednost ne smije biti null.");
        }

        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /*
     * Metoda za ispis.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{$ FOR ");
        sb.append(variable.asText());
        sb.append(" ");
        sb.append(startExpression.asText());
        sb.append(" ");
        sb.append(endExpression.asText());
        if (stepExpression != null) {
            sb.append(" ");
            sb.append(stepExpression.asText());
        }
        sb.append(" $}");

        for (int i = 0; i < numberOfChildren(); i++) {
            sb.append(getChild(i).toString());
        }

        sb.append("{$ END $}");

        return sb.toString();
    }
}
