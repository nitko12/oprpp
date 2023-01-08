package hr.fer.zemris.java.gui.prim.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/*
 * Model generatora prostih brojeva za prikaz u JListi.
 */
public class PrimListModel implements ListModel<Integer> {

    List<Integer> primes = new ArrayList<>();

    List<ListDataListener> listeners = new ArrayList<>();

    /**
     * Konstruktor.
     */
    public PrimListModel() {
        primes.add(1);
    }

    /**
     * Metoda koja generira sljedeći prosti broj.
     * 
     * @return int
     */
    @Override
    public int getSize() {
        return primes.size();
    }

    /**
     * Vraca element na odredenom indexu liste.
     * 
     * @param index
     * @return Integer
     */
    @Override
    public Integer getElementAt(int index) {
        return primes.get(index);
    }

    /**
     * Metoda za dodavanje listenera.
     * 
     * @param l
     */
    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    /**
     * Metoda za uklanjanje listenera.
     * 
     * @param l
     */
    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /**
     * Metoda koja obavještava sve listener-e da je došlo do promjene.
     */
    private void notifyListeners() {
        ListDataListener[] listenersArray = listeners.toArray(new ListDataListener[0]);
        for (ListDataListener l : listenersArray) {
            l.contentsChanged(null);
        }
    }

    /**
     * Metoda koja generira sljedeći prosti broj.
     */
    public void next() {
        int next = primes.get(primes.size() - 1) + 1;

        while (!isPrime(next)) {
            next++;
        }

        primes.add(next);
        notifyListeners();
    }

    /**
     * Testira je li broj prost.
     * 
     * @param n
     * @return boolean
     */
    private boolean isPrime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

}
