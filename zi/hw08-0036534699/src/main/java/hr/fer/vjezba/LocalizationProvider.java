package hr.fer.vjezba;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
    private String language;
    private ResourceBundle bundle;

    private static LocalizationProvider instance = new LocalizationProvider();

    private LocalizationProvider() {
        language = "en";
        bundle = ResourceBundle.getBundle("hr.fer.vjezba.prijevodi", Locale.forLanguageTag(language));
    }

    public static LocalizationProvider getInstance() {
        return instance;
    }

    public void setLanguage(String language) {
        this.language = language;
        bundle = ResourceBundle.getBundle("hr.fer.vjezba.prijevodi", Locale.forLanguageTag(language));

        fire();
    }

    public String getString(String key) {
        return bundle.getString(key);
    }

}
