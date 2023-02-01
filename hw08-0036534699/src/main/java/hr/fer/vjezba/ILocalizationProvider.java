package hr.fer.vjezba;

public interface ILocalizationProvider {
    public void addLocalizationListener(ILocalizationListener l);

    public void removeLocalizationListener(ILocalizationListener l);

    public String getString(String key);
}
