package hr.fer.jnotepadpp.local;

/**
 * Class that represents a localization provider bridge.
 * 
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
    private boolean connected;

    private String currentLanguage;

    private ILocalizationListener listener;

    private ILocalizationProvider parent;

    /**
     * Constructor.
     * 
     * @param other
     */
    public LocalizationProviderBridge(ILocalizationProvider other) {
        connected = false;

        this.parent = other;
        this.listener = () -> {
            if (connected) {
                fire();
            }
        };

        parent.addLocalizationListener(listener);

        currentLanguage = null;
    }

    /**
     * Connects the bridge.
     */
    public void connect() {
        if (connected)
            return;

        connected = true;

        parent.addLocalizationListener(listener);

        if (currentLanguage == null || !currentLanguage.equals(parent.getString("language"))) {
            currentLanguage = parent.getString("language");
            fire();

            currentLanguage = parent.getString("language");
        }

    }

    /**
     * Disconnects the bridge.
     * 
     */
    public void disconnect() {
        if (!connected)
            return;

        parent.removeLocalizationListener(listener);
        // currentLanguage = null;

        connected = false;
    }

    /**
     * Gets the string for the given key.
     * 
     * @param key
     * @return String
     */
    public String getString(String key) {
        return parent.getString(key);
    }
}
