package hr.fer.oprpp1.custom.collections;

public interface List<T> extends Collection<T> {
    /*
     * Vraca objekt na indeksu idx.
     */
    T get(int idx);

    /*
     * Postavlja vrijednost na indeksu idx.
     */
    void insert(T val, int pos);

    /*
     * Vraca indeks prvog pojavljivanja objekta val u kolekciji.
     */
    int indexOf(T val);

    /*
     * Brise objekt na indeksu idx.
     */
    void remove(int idx);
}
