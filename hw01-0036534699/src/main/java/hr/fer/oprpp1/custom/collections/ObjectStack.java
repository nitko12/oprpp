package hr.fer.oprpp1.custom.collections;

/**
 * Obicna implementacija stoga.
 */
public class ObjectStack {
    private ArrayIndexedCollection container;

    /**
     * Obican konstruktor.
     */

    public ObjectStack() {
        container = new ArrayIndexedCollection();
    }

    /**
     * Vraca je li stog prazan.
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return container.isEmpty();
    }

    /**
     * Vraca velicinu stoga.
     * 
     * @return int
     */
    public int size() {
        return container.size();
    }

    /**
     * Postavlja vrijednost na vrh stoga.
     * 
     * @param value
     */
    public void push(Object value) {
        if (value == null) { // redundantno
            throw new NullPointerException("Vrijednost ne smije biti null!");
        }

        container.add(value);
    }

    /**
     * Vraca vrijednost na vrhu i brise je.
     * 
     * @return Object
     */
    public Object pop() {
        if (container.isEmpty()) {
            throw new EmptyStackException("Stog je prazan!");
        }

        Object value = container.get(container.size() - 1);
        container.remove(container.size() - 1);

        return value;
    }

    /**
     * Vraca vrijednost na vrhu, no ne brise je sa stoga.
     * 
     * @return Object
     */
    public Object peek() {
        if (container.isEmpty()) {
            throw new EmptyStackException("Stog je prazan!");
        }

        return container.get(container.size() - 1);
    }

    /**
     * Pobrise sve elemente sa stoga.
     */
    public void clear() {
        container.clear();
    }
}
