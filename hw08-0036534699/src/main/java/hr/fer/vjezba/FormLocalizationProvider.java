package hr.fer.vjezba;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge {

    public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
        super(parent);

        frame.addWindowListener(
                new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                        super.windowOpened(windowEvent);
                        connect();
                    }

                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        super.windowClosed(windowEvent);
                        disconnect();
                    }
                });
    }

}
