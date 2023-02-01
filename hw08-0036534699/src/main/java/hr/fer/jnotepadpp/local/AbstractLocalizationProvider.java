package hr.fer.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an abstract localization provider.
 * 
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    private List<ILocalizationListener> listeners = new ArrayList<>();

    /**
     * Constructor.
     */
    public AbstractLocalizationProvider() {
    }

    /**
     * Adds a localization listener.
     * 
     * @param l
     */
    public void addLocalizationListener(ILocalizationListener l) {
        listeners.add(l);
    }

    /**
     * Removes a localization listener.
     * 
     * @param l
     */
    public void removeLocalizationListener(ILocalizationListener l) {
        listeners.remove(l);
    }

    /**
     * Fires a localization change.
     */
    public void fire() {
        for (ILocalizationListener l : listeners) {
            l.localizationChanged();
        }
    }
}
