package hr.fer.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * Class that represents a localizable label.
 */
public class LocalizableJLabel extends JLabel {
    String key;
    ILocalizationProvider flp;

    /**
     * Constructor.
     * 
     * @param key
     * @param flp
     */
    public LocalizableJLabel(String key, ILocalizationProvider flp) {
        super();

        this.key = key;
        this.flp = flp;

        this.flp.addLocalizationListener(() -> {
            this.setText(flp.getString(key));
        });

    }
}
