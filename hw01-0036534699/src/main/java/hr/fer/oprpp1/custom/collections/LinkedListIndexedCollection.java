package hr.fer.oprpp1.custom.collections;

public class LinkedListIndexedCollection extends Collection {
    private static class ListNode {
        ListNode prev, next;

        Object val;
    }

    private int size;
    private ListNode first, last;

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

    public int size() {
        return size;
    }

    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException("Vrijednost ne smije biti null!");
        }

        ListNode node = new ListNode();
        node.val = value;

        if (first == null && last == null) {
            first = node;
            last = node;
        } else {
            node.prev = last;
            last.next = node;
            last = node;
        }

        ++size;
    }

    public Object get(int index) {
        return getNode(index).val;
    }

    public void clear() {
        // nadam se da ce GC obrisati sve rekurzivno

        first = null;
        last = null;
        size = 0;
    }

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
    }

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

    public void remove(int index) {

        ListNode node = getNode(index);

        if (node.prev == null) {
            first = node.next;
        }

        node.prev.next = node.next;
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

        while (node != null) {
            arr[size - 1] = node.val;

            node = node.next;
        }

        return arr;
    }

    /**
     * Procesira svaki element kolekcije metodom process u processor klasi.
     * 
     * @param processor
     */
    public void forEach(Processor processor) {
        ListNode node = first;

        while (node != null) {
            processor.process(node.val);

            node = node.next;
        }
    }

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
}
