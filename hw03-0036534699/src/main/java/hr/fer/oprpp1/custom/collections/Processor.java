package hr.fer.oprpp1.custom.collections;

public interface Processor<T> {

    /**
     * Obrada elementa.
     * 
     * @param value
     */
    public void process(T value);
}
