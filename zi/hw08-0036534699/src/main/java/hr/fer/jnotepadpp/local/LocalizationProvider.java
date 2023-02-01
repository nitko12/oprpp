package hr.fer.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class that represents a localization provider.
 * 
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
    private String language;
    private ResourceBundle bundle;

    private static LocalizationProvider instance = new LocalizationProvider();

    /**
     * Constructor.
     * 
     */
    private LocalizationProvider() {
        language = "en";
        bundle = ResourceBundle.getBundle("hr.fer.jnotepadpp.local.prijevodi",
                Locale.forLanguageTag(language));
    }

    /**
     * Gets an instance of the localization provider.
     * 
     * @return LocalizationProvider
     */
    public static LocalizationProvider getInstance() {
        return instance;
    }

    /**
     * Sets the language.
     * 
     * @param language
     */
    public void setLanguage(String language) {
        this.language = language;
        bundle = ResourceBundle.getBundle("hr.fer.jnotepadpp.local.prijevodi", Locale.forLanguageTag(language));

        fire();
    }

    /**
     * Gets the language.
     * 
     * @param key
     * @return String
     */
    public String getString(String key) {
        return bundle.getString(key);
    }

}
