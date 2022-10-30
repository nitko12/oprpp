package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Implementacija kolekcije koja se temelji na polju.
 */
public class ArrayIndexedCollection<T> implements List<T> {

    public static final int DEFAULT_CAPACITY = 16;
    private int size;
    private int modificationCount = 0;

    private T[] elements;

    /**
     * Konstruktor koji postavlja kapacitet na 16.
     */
    public ArrayIndexedCollection() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Konstruktor s specificnim kapacitetom.
     * 
     * @param initialCapacity
     */
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Inicijalni kapacitet mora biti veći od 0!");
        }

        @SuppressWarnings("unchecked")
        T[] t = (T[]) new Object[1];
        elements = (T[]) Arrays.copyOf(t, initialCapacity);
        size = 0;
    }

    /**
     * Konstruktor koji kopira elemente iz kolekcije collection u novu kolekciju.
     */
    public ArrayIndexedCollection(Collection<T> other) {
        this(other, DEFAULT_CAPACITY);
    }

    /**
     * Konstruktor koji kopira elemente iz kolekcije collection u novu kolekciju.
     * Ako je kapacitet manji od veličine kolekcije, kapacitet se postavlja na
     * veličinu kolekcije.
     */
    public ArrayIndexedCollection(Collection<T> other, int initialCapacity) {
        if (other == null) {
            throw new NullPointerException("Kolekcija ne smije biti null!");
        }

        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Inicijalni kapacitet mora biti veći od 0!");
        }

        if (other.size() > initialCapacity) {
            @SuppressWarnings("unchecked")
            T[] t = (T[]) new Object[1];
            elements = (T[]) Arrays.copyOf(t, other.size());

        } else {
            @SuppressWarnings("unchecked")
            T[] t = (T[]) new Object[1];
            elements = (T[]) Arrays.copyOf(t, initialCapacity);
        }

        addAll(other);
    }

    /**
     * Vraća veličinu kolekcije.
     * 
     * @return int
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Dodaje element na kraj kolekcije.
     * Amortizirana složenost je O(1), prosjecna O(log n).
     * 
     * @param value
     */
    @Override
    public void add(T value) {
        if (value == null) {
            throw new NullPointerException("Vrijednost ne smije biti null!");
        }

        insert(value, size);
    }

    /**
     * Briše sve elemente iz kolekcije.
     * 
     * @param index
     * @return T
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }

        size = 0;
        ++modificationCount;
    }

    /**
     * Vraća element na zadanom indeksu.
     * 
     * @throws IndexOutOfBoundsException ako je indeks manji od 0 ili veći od
     *                                   veličine kolekcije.
     * 
     * @param index
     * @return T
     */
    // complexity of O(1)
    public T get(int index) {
        if (!(0 <= index && index < size)) {
            throw new IndexOutOfBoundsException("Indeks je izvan raspona!");
        }

        return elements[index];
    }

    /**
     * Dodaje element value na zadanu poziciju.
     * 
     * @throws IndexOutOfBoundsException ako je indeks manji od 0 ili veći od
     *                                   veličine kolekcije.
     * 
     * @param value
     * @param position
     */
    // complexity of O(n)
    public void insert(T value, int position) {
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
        ++modificationCount;
    }

    /**
     * Vraća indeks prvog pojavljivanja elementa value u kolekciji.
     * 
     * @param value
     * @return int
     */
    // complexity of O(n)
    public int indexOf(T value) {
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

    /**
     * Briše element na zadanom indeksu.
     * 
     * @throws IndexOutOfBoundsException ako je indeks manji od 0 ili veći od
     *                                   veličine kolekcije.
     * 
     * @param index
     */
    public void remove(int index) {
        if (!(0 <= index && index < size)) {
            throw new IndexOutOfBoundsException("Indeks je izvan raspona!");
        }

        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null;
        --size;
        ++modificationCount;

        if (size < elements.length / 2 - 1) {
            shrinkInHalf();
        }
    }

    /**
     * Vraća true ako kolekcija sadrži element value, inače false.
     * 
     * @param value
     * @return boolean
     */
    @Override
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    /**
     * Briše prvi element value iz kolekcije.
     * 
     * @param value
     * @return boolean
     */
    @Override
    public boolean remove(T value) {
        int index = indexOf(value);

        if (index == -1) {
            return false;
        }

        remove(index);

        return true;
    }

    /**
     * Pretvara kolekciju u polje.
     * 
     * @return T[]
     */
    @Override
    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] t = (T[]) new Object[1];
        T[] array = (T[]) Arrays.copyOf(t, size);

        for (int i = 0; i < size; i++) {
            array[i] = elements[i];
        }

        return array;
    }

    /**
     * Povećava kapacitet polja za duplo.
     */
    private void doubleArraySize() {
        @SuppressWarnings("unchecked")
        T[] t = (T[]) new Object[1];
        T[] newArray = (T[]) Arrays.copyOf(t, elements.length * 2);

        for (int i = 0; i < elements.length; i++) {
            newArray[i] = elements[i];
        }

        elements = newArray;
    }

    /**
     * Smanjuje elements kada imamo previše mjesta.
     * 
     * Ne provjerava koristimo li drugu polovinu!
     */
    private void shrinkInHalf() {
        @SuppressWarnings("unchecked")
        T[] t = (T[]) new Object[1];
        T[] newArray = (T[]) Arrays.copyOf(t, elements.length / 2);

        for (int i = 0; i < size; i++) {
            newArray[i] = elements[i];
        }

        elements = newArray;
    }

    /**
     * Vraca MyElementsGetter koji dohvaca elemente iz kolekcije.
     * 
     * @return MyElementsGetter
     */
    public ArrayIndexedColletionElementsGetter<T> createElementsGetter() {
        return new ArrayIndexedColletionElementsGetter<T>(this);
    }

    /**
     * Klasa koja implementira ElementsGetter za ovu klasu.
     * 
     * Paziti da se ne koristi dok se mjenja kolekcija.
     */
    private static class ArrayIndexedColletionElementsGetter<T> implements ElementsGetter<T> {
        private int idx = 0;
        private final ArrayIndexedCollection<T> collection;
        private long savedModificationCount = 0;

        /**
         * Konstruktor, prima referencu na klasu s kojom se koristi.
         * 
         * @param collection
         */
        public ArrayIndexedColletionElementsGetter(ArrayIndexedCollection<T> collection) {
            // Ne moze se testirati
            // if (collection == null) {
            // throw new NullPointerException("Kolekcija ne smije biti null!");
            // }

            this.collection = collection;
            savedModificationCount = collection.modificationCount;
        }

        /**
         * Vraca true ako ima jos elemenata u kolekciji, inace false.
         * 
         * @return boolean
         */
        @Override
        public boolean hasNextElement() {
            checkModification();

            return idx < collection.size;
        }

        /**
         * Vraca sljedeci element u kolekciji.
         * 
         * @throws NoSuchElementException ako nema vise elemenata u kolekciji.
         * 
         * @return T
         */
        @Override
        public T getNextElement() {
            if (!hasNextElement()) {
                throw new NoSuchElementException("Kraj kolekcije!");
            }

            checkModification();

            return collection.elements[idx++];
        }

        /**
         * Provjerava uvjet da se kolekcija ne smije mjenjati.
         * 
         * @throws ConcurrentModificationException ako je kolekcija mijenjana
         */
        private void checkModification() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException("Elements getter ne dopusta promjenu kolekcije!");
            }
        }
    }
}
