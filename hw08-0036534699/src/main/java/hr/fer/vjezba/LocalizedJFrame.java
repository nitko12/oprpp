package hr.fer.vjezba;

import javax.swing.JFrame;

public class LocalizedJFrame extends JFrame {
    protected FormLocalizationProvider flp;

    public LocalizedJFrame() {
        super();

        flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
    }
}
