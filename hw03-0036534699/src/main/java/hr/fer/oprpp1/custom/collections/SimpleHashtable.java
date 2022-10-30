package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
    public static class TableEntry<K, V> {
        private K key;
        private V value;
        private TableEntry<K, V> next;

        public TableEntry(K key, V value) {
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
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_MAX_LOAD_FACTOR = 0.75;

    private int size;
    private int capacity;

    private TableEntry<K, V>[] table;

    public SimpleHashtable() {
        this(DEFAULT_CAPACITY);
    }

    public SimpleHashtable(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Kapacitet mora biti barem 1!");
        }

        int pow2Capacity = 1;
        while (pow2Capacity <= capacity)
            pow2Capacity *= 2;

        this.capacity = pow2Capacity;

        @SuppressWarnings({ "unchecked" })
        TableEntry<K, V>[] t = (TableEntry<K, V>[]) new TableEntry[pow2Capacity];
        this.table = (TableEntry<K, V>[]) Arrays.copyOf(t, pow2Capacity);
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Kljuc ne smije biti null!");
        }

        checkLoadAndRehash();

        int idx = Math.abs(key.hashCode()) % capacity;

        TableEntry<K, V> slot = table[idx];

        if (slot == null) {
            table[idx] = new TableEntry<>(key, value);
            size++;
            return null;
        }

        TableEntry<K, V> last = slot;
        while (slot != null) {
            if (slot.getKey().equals(key)) {
                V oldValue = slot.getValue();
                slot.setValue(value);
                return oldValue;
            }
            last = slot;
            slot = slot.next;
        }

        last.next = new TableEntry<>(key, value);
        size++;

        return null;

    }

    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("Kljuc ne smije biti null!");
        }

        int idx = Math.abs(key.hashCode()) % capacity;

        TableEntry<K, V> slot = table[idx];

        if (slot == null) {
            return null;
        }

        while (slot != null) {
            if (slot.getKey().equals(key)) {
                return slot.getValue();
            }
            slot = slot.next;
        }

        return null;

    }

    public int size() {
        return size;
    }

    public boolean containsKey(K key) {
        if (key == null) {
            throw new NullPointerException("Kljuc ne smije biti null!");
        }

        int idx = Math.abs(key.hashCode()) % capacity;

        TableEntry<K, V> slot = table[idx];

        if (slot == null) {
            return false;
        }

        while (slot != null) {
            if (slot.getKey().equals(key)) {
                return true;
            }
            slot = slot.next;
        }

        return false;
    }

    public boolean containsValue(V value) {
        for (TableEntry<K, V> slot : table) {
            while (slot != null) {
                if (slot.getValue().equals(value)) {
                    return true;
                }
                slot = slot.next;
            }
        }

        return false;
    }

    public V remove(K key) {
        int idx = Math.abs(key.hashCode()) % capacity;

        TableEntry<K, V> slot = table[idx];

        if (slot == null) {
            return null;
        }

        if (slot.getKey().equals(key)) {
            table[idx] = slot.next;
            size--;
            return slot.getValue();
        }

        while (slot.next != null) {
            if (slot.next.getKey().equals(key)) {
                V oldValue = slot.next.getValue();
                slot.next = slot.next.next;
                size--;
                return oldValue;
            }
            slot = slot.next;
        }

        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        for (TableEntry<K, V> slot : table) {
            while (slot != null) {
                sb.append(slot.getKey() + "=" + slot.getValue() + ", ");
                slot = slot.next;
            }
        }

        if (size > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append("]");

        return sb.toString();
    }

    public TableEntry<K, V>[] toArray() {
        @SuppressWarnings("unchecked")
        TableEntry<K, V>[] t = (TableEntry<K, V>[]) new TableEntry[1];
        TableEntry<K, V>[] arr = Arrays.copyOf(t, size);

        int i = 0;
        for (TableEntry<K, V> slot : table) {
            while (slot != null) {
                arr[i++] = new TableEntry<>(slot.getKey(), slot.getValue());
                slot = slot.next;
            }
        }

        return arr;
    }

    private void checkLoadAndRehash() {
        if (size / (double) capacity > DEFAULT_MAX_LOAD_FACTOR) {
            rehash();
        }
    }

    private void rehash() {
        capacity *= 2;
        size = 0;

        @SuppressWarnings({ "unchecked" })
        TableEntry<K, V>[] t = (TableEntry<K, V>[]) new TableEntry[capacity];
        TableEntry<K, V>[] oldTable = table;
        table = (TableEntry<K, V>[]) Arrays.copyOf(t, capacity);

        for (TableEntry<K, V> slot : oldTable) {
            while (slot != null) {
                put(slot.getKey(), slot.getValue());
                slot = slot.next;
            }
        }
    }

    public void clear() {
        // GC bi se trebao pobrinuti obrisati rekurzivno dalje
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }

        size = 0;
    }

    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
        int i = 0;
        int j = 0;
        TableEntry<K, V> current = null;

        public IteratorImpl() {
            while (j < table.length && table[j] == null) {
                j++;
            }
            current = table[j];
        }

        @Override
        public boolean hasNext() {
            if (i < size) {
                return true;
            }

            return false;
        }

        @Override
        public SimpleHashtable.TableEntry<K, V> next() {
            if (!hasNext() || current == null) {
                throw new NoSuchElementException("Nema vise elemenata u tablici!");
            }

            TableEntry<K, V> next = current;
            stepCurrent();

            ++i;
            return next;
        }

        @Override
        public void remove() {
            if (current == null) {
                throw new IllegalStateException("Nema trenutnog elementa!");
            }

            if (current.next == null) {
                table[j] = null;
                stepCurrent();
            } else {
                current.setValue(current.next.getValue());
                current.key = current.next.key;
                current.next = current.next.next;
            }
        }

        private void stepCurrent() {
            if (current.next != null) {
                current = current.next;
            } else {
                j++;
                while (j < table.length && table[j] == null) {
                    j++;
                }
                if (j < table.length) {
                    current = table[j];
                } else {
                    current = null;
                }
            }
        }
    }

    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }
}
