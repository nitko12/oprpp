package hr.fer.oprpp1.custom.collections;

/**
 * Dohvaća elemente iz kolekcije.
 * 
 * 
 */
public interface ElementsGetter<T> {

    /**
     * Vraća true ako ima još objekata za dohvat, inače false.
     * 
     * @return boolean
     */
    public boolean hasNextElement();

    /**
     * Vraća sljedeći objekt.
     * 
     * @return T
     */
    public T getNextElement();

    /**
     * Procesira ostatak kolekcije metodom process u Processor klasi.
     * 
     * @param processor
     */
    public default void processRemaining(Processor<T> processor) {
        while (hasNextElement()) {
            processor.process(getNextElement());
        }
    }

}
