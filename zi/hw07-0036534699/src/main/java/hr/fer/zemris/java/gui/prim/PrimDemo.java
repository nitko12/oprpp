package hr.fer.zemris.java.gui.prim;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.prim.models.PrimListModel;

/**
 * Klasa PrimDemo koja otvara prozor u kojem se nalaze 2 liste i gumb. Klikom na
 * gumb se u listu se dodaju sljedeći prosti brojevi.
 *
 */
public class PrimDemo extends JFrame {

    /**
     * Konstruktor koji prima model.
     */
    public PrimDemo(PrimListModel model) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("PrimDemo");
        setSize(500, 500);
        setLocationRelativeTo(null);

        initGUI();
    }

    /**
     * Metoda koja pokreće program.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // draw 2 lists of prime numbers

        PrimListModel model = new PrimListModel();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new PrimDemo(model);
            frame.setVisible(true);
        });

    }

    /**
     * Metoda koja inicijalizira GUI.
     */
    private void initGUI() {
        // draw 2 lists and a button

        PrimListModel model = new PrimListModel();

        JList<Integer> list1 = new JList<>(model);
        JList<Integer> list2 = new JList<>(model);

        // use border layout

        JButton button = new JButton("Next");
        button.addActionListener(e -> {
            model.next();
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(button, BorderLayout.SOUTH);

        // use grid layout

        JPanel center = new JPanel();
        GridLayout centerLayout = new GridLayout(1, 2);

        center.setLayout(centerLayout);

        JScrollPane list1Scroll = new JScrollPane(list1);
        JScrollPane list2Scroll = new JScrollPane(list2);

        center.add(list1Scroll);
        center.add(list2Scroll);

        getContentPane().add(center, BorderLayout.CENTER);
    }
}
