package hr.fer.zemris.java.gui.calc.components;

import javax.swing.JButton;

/**
 * Predstavlja gumb za operacije.
 */
public class OperationButton extends JButton {

    /**
     * Interface koji predstavlja akciju koja se izvršava pritiskom na gumb.
     * 
     */
    public interface Press {
        void pressed();
    }

    /**
     * Interface koji predstavlja akciju koja invertira gumb.
     * 
     * 
     */
    public interface Invert {
        void invert(boolean inverted);
    }

    Press press;
    String text, invText;

    /**
     * Konstruktor koji prima tekst koji se prikazuje na gumbu, tekst koji se
     * prikazuje nakon inverzije i akciju koja se pokreće pritiskom na gumb.
     * 
     * 
     * @param text
     * @param invText
     * @param press
     */
    public OperationButton(String text, String invText, Press press) {
        super(text);

        this.press = press;

        addActionListener(e -> {
            this.press.pressed();
        });

        this.text = text;
        this.invText = invText;
    }

    /**
     * Vraća invertor gumba.
     * 
     * @return Invert
     */
    public Invert getInvertor() {
        return inverted -> {
            if (inverted) {
                setText(invText);
            } else {
                setText(text);
            }
        };
    }

}
