package hr.fer.vjezba;

import javax.swing.JLabel;

public class LocalizableJLabel extends JLabel {
    String key;
    ILocalizationProvider flp;

    public LocalizableJLabel(String key, ILocalizationProvider flp) {
        super();

        this.key = key;
        this.flp = flp;

        this.flp.addLocalizationListener(() -> {
            this.setText(flp.getString(key));
        });

    }
}
