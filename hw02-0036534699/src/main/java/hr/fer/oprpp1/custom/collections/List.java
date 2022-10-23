package hr.fer.oprpp1.custom.collections;

public interface List extends Collection {
    /*
     * Vraca objekt na indeksu idx.
     */
    Object get(int idx);

    /*
     * Postavlja vrijednost na indeksu idx.
     */
    void insert(Object val, int pos);

    /*
     * Vraca indeks prvog pojavljivanja objekta val u kolekciji.
     */
    int indexOf(Object val);

    /*
     * Brise objekt na indeksu idx.
     */
    void remove(int idx);
}
