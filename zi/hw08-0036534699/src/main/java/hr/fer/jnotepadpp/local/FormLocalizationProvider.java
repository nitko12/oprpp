package hr.fer.jnotepadpp.local;

import javax.swing.JFrame;

/**
 * Class that represents a localization provider bridge.
 * Used for connecting and disconnecting listeners due to GC
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

    /**
     * Constructor.
     * 
     * @param parent
     * @param frame
     */
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
