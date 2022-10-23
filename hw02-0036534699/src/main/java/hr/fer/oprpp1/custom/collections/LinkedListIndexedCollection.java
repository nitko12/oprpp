package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Implementacija duplo povezane liste.
 */
public class LinkedListIndexedCollection implements List {
    private static class ListNode {
        ListNode prev, next;

        Object val;
    }

    private int size;
    private ListNode first, last;

    private long modificationCount;

    public LinkedListIndexedCollection() {
        first = null;
        last = null;
        size = 0;
    }

    public LinkedListIndexedCollection(Collection collection) {
        if (collection == null) {
            throw new NullPointerException("Kolekcija ne smije biti null!");
        }

        addAll(collection);
    }

    /**
     * Vraca velicinu kolekcije.
     * 
     * @return int
     */
    public int size() {
        return size;
    }

    /**
     * Dodaje element na kraj kolekcije.
     * 
     * @param value
     */
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException("Vrijednost ne smije biti null!");
        }

        ListNode node = new ListNode();
        node.val = value;

        if (first == null) {
            first = node;
            last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }

        ++size;
        ++modificationCount;
    }

    /**
     * Dohvaća element na zadanom indeksu.
     * 
     * @param index
     * @return Object
     */
    public Object get(int index) {
        return getNode(index).val;
    }

    public void clear() {
        // nadam se da ce GC obrisati sve rekurzivno

        first = null;
        last = null;
        size = 0;
        ++modificationCount;
    }

    /**
     * Umece element na zadanu poziciju.
     * 
     * @throws IndexOutOfBoundsException ako je index manji od 0 ili veci od size
     *                                   (smije biti jednak size).
     * 
     * @param value
     * @param position
     */
    public void insert(Object value, int position) {
        if (value == null) {
            throw new NullPointerException("Vrijednost ne smije biti null!");
        }

        if (!(0 <= position && position <= size)) { // dozvoljeno je dodavanje na kraj
            throw new IndexOutOfBoundsException("Indeks je izvan raspona!");
        }

        ListNode node = new ListNode();
        node.val = value;

        if (position == size) {
            last.next = node;
            last.next.prev = last;
            last = last.next;

            ++size;
            return;
        }

        ListNode toInsert = getNode(position);

        if (toInsert.prev == null) {
            first = node;
            first.next = toInsert;
            toInsert.prev = first;
        } else {
            toInsert.prev.next = node;
            node.prev = toInsert.prev;
            node.next = toInsert;
            toInsert.prev = node;
        }

        ++size;
        ++modificationCount;
    }

    /**
     * Vraca poziciju na kojoj se neka vrijednost nalazi.
     * 
     * @param value
     * @return int
     */
    public int indexOf(Object value) {
        if (value == null) {
            return -1;
        }

        ListNode node = first;

        for (int i = 0; i < size; ++i) {
            if (node.val.equals(value)) {
                return i;
            }

            node = node.next;
        }

        return -1;
    }

    /**
     * Uklanja element na zadanom indeksu.
     * 
     * @param index
     */
    public void remove(int index) {

        ListNode node = getNode(index);

        if (node.prev == null && node.next == null) {
            first = null;
            last = null;
        } else if (node.prev == null) {
            first = node.next;
            first.prev = null;
        } else if (node.next == null) {
            last = node.prev;
            last.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        --size;
        ++modificationCount;
    }

    /**
     * Briše prvi element value iz kolekcije.
     * 
     * @param value
     * @return boolean
     */
    public boolean remove(Object value) {
        int index = indexOf(value);

        if (index == -1) {
            return false;
        }

        // malo redundantno dvaput iterirati,
        // inace bi trebala jos jedna metoda
        remove(index);

        return true;
    }

    /**
     * Pretvara kolekciju u polje.
     * Ne mjenja kolekciju, samo vrati polje.
     * 
     * @return Object[]
     */
    public Object[] toArray() {
        Object[] arr = new Object[size];

        ListNode node = first;

        int i = 0;
        while (node != null) {
            arr[i++] = node.val;

            node = node.next;
        }

        return arr;
    }

    /**
     * @param value
     * @return boolean
     */
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    /**
     * Pomocna metoda koja vraca node na zadanom indeksu.
     * 
     * @throws IndexOutOfBoundsException ako je indeks izvan raspona
     * 
     * @param index
     * @return ListNode
     */
    private ListNode getNode(int index) {
        if (!(0 <= index && index < size)) {
            throw new IndexOutOfBoundsException("Indeks je izvan raspona!");
        }

        if (index < size / 2) {
            ListNode node = first;

            while (index-- > 0) {
                node = node.next;
            }

            return node;
        }

        ListNode node = last;
        while (index++ < size - 1) {
            node = node.prev;
        }

        return node;
    }

    public ElementsGetter createElementsGetter() {
        return new LinkedListIndexedCollectionElementsGetter(this);
    }

    /**
     * Klasa koja implementira sucelje ElementsGetter za ovu kolekciju.
     *
     * Paziti da se ne koristi dok se mjenja kolekcija.
     */
    private static class LinkedListIndexedCollectionElementsGetter implements ElementsGetter {

        private LinkedListIndexedCollection collection;
        private ListNode runner;
        private long savedModificationCount = 0;

        /*
         * Konstruktor.
         */
        public LinkedListIndexedCollectionElementsGetter(LinkedListIndexedCollection collection) {
            // Ne moze se testirati
            // if (first == null) {
            // throw new NullPointerException("Element liste ne smije biti null!");
            // }

            runner = collection.first;
            savedModificationCount = collection.modificationCount;
            this.collection = collection;
        }

        /*
         * Vraca true ako ima jos objekata, inace false.
         * 
         * @return boolean
         */
        @Override
        public boolean hasNextElement() {
            checkModification();

            return runner != null;
        }

        /*
         * Vraca sljedeci element.
         * 
         * @throws NoSuchElementException ako nema vise elemenata
         * 
         * @return Object
         */
        @Override
        public Object getNextElement() {
            if (runner == null) {
                throw new NoSuchElementException("Kraj kolekcije!");
            }

            checkModification();

            ListNode t = runner;
            runner = runner.next;

            return t.val;
        }

        /*
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
