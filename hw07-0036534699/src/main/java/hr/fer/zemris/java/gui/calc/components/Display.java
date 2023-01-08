package hr.fer.zemris.java.gui.calc.components;

import javax.swing.JLabel;

/**
 * Klasa koja predstavlja display kalkulatora.
 */
public class Display extends JLabel {
    /**
     * Konstruktor komponente.
     */
    public Display() {
        super("0");
        setHorizontalAlignment(JLabel.RIGHT);

        setOpaque(true);
        setBackground(java.awt.Color.YELLOW);

        setFont(getFont().deriveFont(30f));
    }

    /**
     * Postavlja tekst na display.
     * 
     * @param text
     */
    public void setText(String text) {
        super.setText(text);
    }

    /**
     * Vraća tekst koji se trenutno nalazi na displayu.
     * 
     * @return String
     */
    public String getText() {
        return super.getText();
    }

}
