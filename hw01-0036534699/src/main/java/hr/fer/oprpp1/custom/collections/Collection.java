package hr.fer.oprpp1.custom.collections;

public class Collection {
    /**
     * Default konstruktor.
     */
    protected Collection() {
    }

    /**
     * Vraća true ako je kolekcija prazna, inače vraća false.
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Vraća broj elemenata u kolekciji.
     * 
     * @return int
     */
    public int size() {
        return 0;
    }

    /**
     * Dodaje element u kolekciju.
     * 
     * @param value
     */
    public void add(Object value) {
    }

    /**
     * Vraća true ako kolekcija sadrži element value, inače false.
     * 
     * @param value
     * @return boolean
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * Briše prvi element value iz kolekcije.
     * 
     * @param value
     * @return boolean
     */
    public boolean remove(Object value) {
        return false;
    }

    /**
     * Pretvara kolekciju u polje.
     * Ne mjenja kolekciju, samo vrati polje.
     * 
     * @return Object[]
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Procesira svaki element kolekcije metodom process u processor klasi.
     * 
     * @param processor
     */
    public void forEach(Processor processor) {
    }

    /**
     * Dodaje sve elemente iz kolekcije other u kolekciju.
     * 
     * @param other
     */
    public void addAll(Collection other) {
        class LocalProcessor extends Processor {
            @Override
            public void process(Object value) {
                add(value);
            }
        }

        other.forEach(new LocalProcessor());

    }

    /**
     * Briše sve elemente iz kolekcije.
     */
    public void clear() {
    }

}
