package hr.fer.oprpp1.jmbag0036534699;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        JPanel p = new JPanel(new CalcLayout(3));

        p.add(new JLabel("x"), new RCPosition(1, 1));
        p.add(new JLabel("y"), new RCPosition(2, 3));
        p.add(new JLabel("z"), new RCPosition(1, 7));
        p.add(new JLabel("w"), new RCPosition(4, 2));
        p.add(new JLabel("a"), new RCPosition(4, 5));
        p.add(new JLabel("b"), new RCPosition(4, 7));
    }

}
