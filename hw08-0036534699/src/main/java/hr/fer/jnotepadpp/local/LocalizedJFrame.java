package hr.fer.jnotepadpp.local;

import javax.swing.JFrame;

/**
 * Class that represents a localized JFrame.
 * 
 * 
 */
public class LocalizedJFrame extends JFrame {
    protected FormLocalizationProvider flp;

    public LocalizedJFrame() {
        super();

        flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
    }
}
