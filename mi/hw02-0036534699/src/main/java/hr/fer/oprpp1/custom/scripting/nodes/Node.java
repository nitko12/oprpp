package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/*
 * Bazna klasa nodea.
 */
public class Node {
    private ArrayIndexedCollection children;

    /*
     * Dodaje child node u node.
     * 
     * @param child child node
     */
    public void addChildNode(Node child) {
        if (child == null) {
            throw new IllegalArgumentException("Dijete ne smije biti null!");
        }

        if (children == null) {
            children = new ArrayIndexedCollection();
        }

        children.add(child);
    }

    /*
     * Vraća broj djece.
     * 
     * @return int
     */
    public int numberOfChildren() {
        if (children == null) {
            return 0;
        }

        return children.size();
    }

    /*
     * Vraća child node na indexu.
     * 
     * @param index index
     * 
     * @return Node
     */
    public Node getChild(int index) {
        if (children == null) {
            throw new IndexOutOfBoundsException("Nema djeteta na indeksu " + index + "!");
        }
        if (!(0 <= index && index < children.size())) {
            throw new IndexOutOfBoundsException("Nema djeteta na indeksu " + index + "!");
        }

        return (Node) children.get(index);
    }

    /*
     * Metoda za ispis.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numberOfChildren(); i++) {
            sb.append(getChild(i).toString());
        }

        return sb.toString();
    }
}
