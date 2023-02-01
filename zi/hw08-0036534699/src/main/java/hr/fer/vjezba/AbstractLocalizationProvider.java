package hr.fer.vjezba;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    private List<ILocalizationListener> listeners = new ArrayList<>();

    public AbstractLocalizationProvider() {
    }

    public void addLocalizationListener(ILocalizationListener l) {
        listeners.add(l);
    }

    public void removeLocalizationListener(ILocalizationListener l) {
        listeners.remove(l);
    }

    public void fire() {
        for (ILocalizationListener l : listeners) {
            l.localizationChanged();
        }
    }
}
