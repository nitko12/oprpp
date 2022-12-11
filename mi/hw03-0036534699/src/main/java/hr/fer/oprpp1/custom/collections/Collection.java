package hr.fer.oprpp1.custom.collections;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Apstraktna kolekcija koja sadrži metode koje su zajedničke svim kolekcijama,
 * no nisu jednako implementirane.
 */
public interface Collection<T> {
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
    public void add(T value);

    /**
     * Vraća true ako kolekcija sadrži element value, inače false.
     * 
     * @param value
     * @return boolean
     */
    public boolean contains(T value);

    /**
     * Briše prvi element value iz kolekcije.
     * 
     * @param value
     * @return boolean
     */
    public boolean remove(T value);

    /**
     * Pretvara kolekciju u polje.
     * Ne mjenja kolekciju, samo vrati polje.
     * 
     * @return T[]
     */
    public T[] toArray();

    /**
     * Procesira svaki element kolekcije metodom process u processor klasi.
     * 
     * @param processor
     */
    public default void forEach(Processor<T> processor) {
        ElementsGetter<T> getter = createElementsGetter();
        while (getter.hasNextElement()) {
            processor.process(getter.getNextElement());
        }
    }

    /**
     * Dodaje sve elemente iz kolekcije other u kolekciju.
     * 
     * @param other
     */
    public default void addAll(Collection<T> other) {
        class LocalProcessor implements Processor<T> {
            @Override
            public void process(T value) {
                add(value);
            }
        }

        other.forEach(new LocalProcessor());

    }

    /**
     * Briše sve elemente iz kolekcije.
     */
    public void clear();

    /**
     * Vraća objekt koji implementira ElementsGetter.
     * 
     * @return ElementsGetter
     */
    public ElementsGetter<T> createElementsGetter();

    /**
     * Dodaje sve objekte iz col koji zadovoljavaju uvjet predan u testeru u
     * kolekciju.
     * 
     * @param col
     * @param tester
     */
    public default void addAllSatisfying(Collection<T> col, Tester<T> tester) {
        ElementsGetter<T> getter = col.createElementsGetter();

        while (getter.hasNextElement()) {
            T element = getter.getNextElement();

            if (tester.test(element)) {
                add(element);
            }
        }
    }

    public default <V> void copyTransformedIntoIfAllowed(
            Collection<? super V> dest,
            Function<? super T, ? extends V> transformer,
            Predicate<? super V> tester) {

        ElementsGetter<T> getter = createElementsGetter();

        while (getter.hasNextElement()) {
            T element = getter.getNextElement();

            V transformed = transformer.apply(element);

            if (tester.test(transformed)) {
                dest.add(transformed);
            }
        }
    }

}
