package hr.fer.vjezba;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor extends LocalizedJFrame {
    private static final long serialVersionUID = 1L;

    private JButton gumb;

    public Prozor() throws HeadlessException {
        super();

        gumb = new JButton(
                new LocalizableAction("login", flp) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Kliknuto");
                    }
                });

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(0, 0);
        setTitle("Demo");
        initGUI();
        pack();

    }

    private void initGUI() {
        getContentPane().setLayout(new BorderLayout());

        // add menubar

        JMenuBar menubar = new JMenuBar();

        JMenu languages = new JMenu("Languages");

        JButton englishButton = new JButton("English");
        englishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("en");
            }
        });

        JButton croatianButton = new JButton("Hrvatski");
        croatianButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("hr");
            }
        });

        JButton germanButton = new JButton("Deutsch");
        germanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("de");
            }
        });

        languages.add(englishButton);
        languages.add(croatianButton);
        languages.add(germanButton);

        menubar.add(languages);

        getContentPane().add(menubar, BorderLayout.NORTH);

        getContentPane().add(gumb, BorderLayout.CENTER);

        LocalizableJLabel label = new LocalizableJLabel("login", flp);

        getContentPane().add(label, BorderLayout.SOUTH);

        gumb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        // Napravi prijavu...
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Očekivao sam oznaku jezika kao argument!");
            System.err.println("Zadajte kao parametar hr ili en.");
            System.exit(-1);
        }
        final String jezik = args[0];
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LocalizationProvider.getInstance().setLanguage(jezik);
                new Prozor().setVisible(true);
            }
        });
    }

}