package hr.fer.zemris.java.gui.calc.components;

import javax.swing.JButton;

/**
 * Klasa koja predstavlja gumb za unos brojeva.
 */
public class NumberButton extends JButton {

    /**
     * Interface koji predstavlja akciju koja se izvršava pritiskom na gumb.
     */
    public interface Press {
        void pressed();
    }

    /**
     * Konstruktor koji prima tekst koji se prikazuje na gumbu i akciju koja se
     * pokreće pritiskom na gumb.
     * 
     * @param text
     * @param press
     */
    public NumberButton(String text, Press press) {
        super(text);

        addActionListener(e -> {
            press.pressed();
        });
    }
}
