package hr.fer.vjezba;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class LocalizableAction extends AbstractAction {

    String key;
    ILocalizationProvider flp;

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
