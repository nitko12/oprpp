package hr.fer.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Class that represents a localizable action.
 * 
 */
public abstract class LocalizableAction extends AbstractAction {

    String key;
    ILocalizationProvider flp;

    /**
     * Constructor.
     * 
     * @param key
     * @param flp
     */
    public LocalizableAction(String key, ILocalizationProvider flp) {
        super();

        this.key = key;
        this.flp = flp;

        this.flp.addLocalizationListener(() -> {
            this.putValue(Action.NAME, flp.getString(key));
        });
        this.putValue(Action.NAME, flp.getString(key));
    }
}
