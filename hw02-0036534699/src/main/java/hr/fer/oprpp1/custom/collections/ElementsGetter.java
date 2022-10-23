package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter {

    /**
     * Vraća true ako ima još objekata za dohvat, inače false.
     * 
     * @return boolean
     */
    public boolean hasNextElement();

    /**
     * Vraća sljedeći objekt.
     * 
     * @return Object
     */
    public Object getNextElement();

    /**
     * Procesira ostatak kolekcije metodom process u Processor klasi.
     * 
     * @param processor
     */
    public default void processRemaining(Processor processor) {
        while (hasNextElement()) {
            processor.process(getNextElement());
        }
    }

}
