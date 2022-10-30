package hr.fer.oprpp1.custom.collections;

public class Dictionary<K, V> {

    private ArrayIndexedCollection<Pair<K, V>> container;

    public Dictionary() {
        container = new ArrayIndexedCollection<>();
    }

    boolean isEmpty() {
        return container.isEmpty();
    }

    int size() {
        return container.size();
    }

    void clear() {
        container.clear();
    }

    V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Kljuc ne smije biti null!");
        }

        for (int i = 0; i < container.size(); i++) {
            if (container.get(i).getKey().equals(key)) {
                V old = container.get(i).getValue();
                container.get(i).setValue(value);
                return old;
            }
        }

        container.add(new Pair<>(key, value));
        return null;
    }

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

    private static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

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
