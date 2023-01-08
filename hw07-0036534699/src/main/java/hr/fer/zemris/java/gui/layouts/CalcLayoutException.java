package hr.fer.zemris.java.gui.layouts;

/**
 * Iznimka koja se baca u slučaju da se pokuša dodati komponenta na mjesto koje
 * već zauzima druga komponenta ili ako se pokuša dodati komponenta na mjesto
 * koje nije podržano.
 * 
 */
public class CalcLayoutException extends RuntimeException {

    /**
     * Konstruktor.
     */
    public CalcLayoutException() {
        super();
    }

    /**
     * Konstruktor.
     * 
     * @param message poruka koja opisuje pogrešku
     */
    public CalcLayoutException(String message) {
        super(message);
    }
}
