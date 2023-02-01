package hr.fer.vjezba;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
    private boolean connected;

    private String currentLanguage;

    private ILocalizationListener listener;

    private ILocalizationProvider parent;

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

    public void disconnect() {
        if (!connected)
            return;

        parent.removeLocalizationListener(listener);
        // currentLanguage = null;

        connected = false;
    }

    public String getString(String key) {
        return parent.getString(key);
    }
}
