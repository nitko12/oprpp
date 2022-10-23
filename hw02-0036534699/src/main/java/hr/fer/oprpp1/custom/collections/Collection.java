package hr.fer.oprpp1.custom.collections;

/**
 * Apstraktna kolekcija koja sadrži metode koje su zajedničke svim kolekcijama,
 * no nisu jednako implementirane.
 */
public interface Collection {
    /**
     * Vraća true ako je kolekcija prazna, inače vraća false.
     * 
     * @return boolean
     */
    public default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Vraća broj elemenata u kolekciji.
     * 
     * @return int
     */
    public int size();

    /**
     * Dodaje element u kolekciju.
     * 
     * @param value
     */
    public void add(Object value);

    /**
     * Vraća true ako kolekcija sadrži element value, inače false.
     * 
     * @param value
     * @return boolean
     */
    public boolean contains(Object value);

    /**
     * Briše prvi element value iz kolekcije.
     * 
     * @param value
     * @return boolean
     */
    public boolean remove(Object value);

    /**
     * Pretvara kolekciju u polje.
     * Ne mjenja kolekciju, samo vrati polje.
     * 
     * @return Object[]
     */
    public Object[] toArray();

    /**
     * Procesira svaki element kolekcije metodom process u processor klasi.
     * 
     * @param processor
     */
    public void forEach(Processor processor);

    /**
     * Dodaje sve elemente iz kolekcije other u kolekciju.
     * 
     * @param other
     */
    public default void addAll(Collection other) {
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
    public void clear();

}
