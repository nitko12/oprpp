package hr.fer.jnotepadpp.local;

/**
 * Interface that represents a localization provider.
 * 
 */
public interface ILocalizationProvider {
    public void addLocalizationListener(ILocalizationListener l);

    public void removeLocalizationListener(ILocalizationListener l);

    public String getString(String key);
}
