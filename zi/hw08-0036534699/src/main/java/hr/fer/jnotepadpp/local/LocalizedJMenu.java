package hr.fer.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Class that represents a localized JMenu.
 * 
 * 
 */
public class LocalizedJMenu extends JMenu {
    /**
     * Constructor that initializes a localized JMenu.
     * 
     * @param key
     * @param provider
     */
    public LocalizedJMenu(String key, ILocalizationProvider provider) {
        super(provider.getString(key));
        provider.addLocalizationListener(() -> {
            setText(provider.getString(key));
        });
    }
}
