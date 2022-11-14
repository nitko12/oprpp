package hr.fer.oprpp1.custom.collections;

/**
 * Rijecnicka struktura podataka,
 * 
 * jednostavna implementacija, nije efikasna.
 */
public class Dictionary<K, V> {

    private ArrayIndexedCollection<Pair<K, V>> container;

    /*
     * Konstruktor koji kreira novu praznu strukturu.
     */
    public Dictionary() {
        container = new ArrayIndexedCollection<>();
    }

    /**
     * Vraca je li struktura prazna.
     * 
     * @return boolean
     */
    boolean isEmpty() {
        return container.isEmpty();
    }

    /**
     * Vraca velicinu strukture.
     * 
     * @return int
     */
    int size() {
        return container.size();
    }

    void clear() {
        container.clear();
    }

    /**
     * Sprema par kljuc-vrijednost u strukturu.
     * 
     * @param key
     * @param value
     * @return V
     */
    V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Kljuc ne smije biti null!");
        }

        for (int i = 0, n = container.size(); i < n; i++) {
            var pair = container.get(i);

            if (pair.getKey().equals(key)) {
                V old = pair.getValue();
                pair.setValue(value);
                return old;
            }
        }

        container.add(new Pair<>(key, value));
        return null;
    }

    /**
     * Dobavlja vrijednost za zadani kljuc.
     * 
     * @param key
     * @return V
     */
    V get(Object key) {
        if (key == null) {
            throw new NullPointerException("Kljuc ne smije biti null!");
        }

        for (int i = 0; i < container.size(); i++) {
            if (container.get(i).getKey().equals(key)) {
                return container.get(i).getValue();
            }
        }

        return null;
    }

    /**
     * Uklanja par kljuc-vrijednost iz strukture prema kljucu.
     * 
     * @param key
     * @return V
     */
    V remove(K key) {
        if (key == null) {
            throw new NullPointerException("Kljuc ne smije biti null!");
        }

        for (int i = 0; i < container.size(); i++) {
            if (container.get(i).getKey().equals(key)) {
                V old = container.get(i).getValue();
                container.remove(i);
                return old;
            }
        }

        return null;
    }

    /**
     * Pomocna klasa koja predstavlja par kljuc-vrijednost.
     * 
     * @param <K>
     * @param <V>
     */
    private static class Pair<K, V> {
        private K key;
        private V value;

        /**
         * Konstruktor koji kreira novi par kljuc-vrijednost.
         * 
         * @param key
         * @param value
         */
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Vraca kljuc.
         * 
         * @return K
         */
        public K getKey() {
            return key;
        }

        /**
         * Vraca vrijednost.
         * 
         * @return V
         */
        public V getValue() {
            return value;
        }

        /**
         * Postavlja vrijednost.
         * 
         * @param value
         */
        public void setValue(V value) {
            this.value = value;
        }

        // public boolean equals(Object obj) { // mozda nekad zatreba
        // if (!(obj instanceof Pair)) {
        // return false;
        // }

        // @SuppressWarnings("unchecked")
        // Pair<K, V> pair = (Pair<K, V>) obj;
        // return key.equals(pair.getKey()) && value.equals(pair.getValue());
        // }
    }
}
