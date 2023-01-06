package hr.fer.oprpp1.hw05.shell;

/**
 * Iznimka koja se baca kada se dogodi pogreška prilikom čitanja ili pisanja u
 * shell.
 * 
 */
public class ShellIOException extends RuntimeException {

    public ShellIOException() {
        super();
    }

    public ShellIOException(String message) {
        super(message);
    }

}
