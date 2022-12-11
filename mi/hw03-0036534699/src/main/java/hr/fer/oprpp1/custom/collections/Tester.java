package hr.fer.oprpp1.custom.collections;

public interface Tester<T> {

    /**
     * Testira zadovoljava li objekt zadani uvjet.
     *
     * @param obj
     * @return true ako zadovoljava, false inače
     */
    boolean test(T obj);
}
