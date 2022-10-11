package hr.fer.oprpp1.custom.collections;

public class ArrayIndexedCollection extends Collection {

    public static final int DEFAULT_CAPACITY = 16;
    private int size;

    private Object[] elements;

    public ArrayIndexedCollection() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Inicijalni kapacitet mora biti veći od 0!");
        }
        elements = new Object[initialCapacity];
    }

    public ArrayIndexedCollection(Collection other) {
        this(other, DEFAULT_CAPACITY);
    }

    public ArrayIndexedCollection(Collection other, int initialCapacity) {
        if (other == null) {
            throw new NullPointerException("Kolekcija ne smije biti null!");
        }

        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Inicijalni kapacitet mora biti veći od 0!");
        }

        if (other.size() > initialCapacity) {
            elements = new Object[other.size()];
        } else {
            elements = new Object[initialCapacity];
        }

        size = elements.length;

        addAll(other);
    }

    @Override
    public int size() {
        return size;
    }

    // complexity of ammortized O(1) to O(log n)
    @Override
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException("Vrijednost ne smije biti null!");
        }

        insert(value, size);
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }

        size = 0;
    }

    // complexity of O(1)
    public Object get(int index) {
        if (!(0 <= index && index < size)) {
            throw new IndexOutOfBoundsException("Indeks je izvan raspona!");
        }

        return elements[index];
    }

    // complexity of O(n)
    public void insert(Object value, int position) {
        if (value == null) {
            throw new NullPointerException("Vrijednost ne smije biti null!");
        }

        if (!(0 <= position && position <= size)) {
            throw new IndexOutOfBoundsException("Indeks je izvan raspona!");
        }

        if (size == elements.length) {
            doubleArraySize();
        }

        for (int i = size; i > position; i--) {
            elements[i] = elements[i - 1];
        }

        elements[position] = value;
        ++size;
    }

    // complexity of O(n)
    public int indexOf(Object value) {
        if (value == null) {
            return -1;
        }

        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    public void remove(int index) {
        if (!(0 <= index && index < size)) {
            throw new IndexOutOfBoundsException("Indeks je izvan raspona!");
        }

        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null;
        --size;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    @Override
    public boolean remove(Object value) {
        int index = indexOf(value);

        if (index == -1) {
            return false;
        }

        remove(index);
        return true;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];

        for (int i = 0; i < size; i++) {
            array[i] = elements[i];
        }

        return array;
    }

    @Override
    public void forEach(Processor processor) {
        for (int i = 0; i < size; i++) {
            processor.process(elements[i]);
        }
    }

    private void doubleArraySize() {
        Object[] newArray = new Object[elements.length * 2];

        for (int i = 0; i < elements.length; i++) {
            newArray[i] = elements[i];
        }

        elements = newArray;
    }
}
