package hr.fer.jnotepadpp.documentModel;

import java.awt.event.KeyListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;

/**
 * DefaultSingleDocumentModel
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

    private JTextArea textArea;
    private Path path;
    private boolean modified;

    private List<SingleDocumentListener> listeners;

    private String originalString;

    /**
     * Constructor for DefaultSingleDocumentModel
     * 
     * 
     * @param path
     * @param text
     */
    public DefaultSingleDocumentModel(Path path, String text) {
        textArea = new JTextArea();
        this.path = path;
        modified = false;

        textArea.setText(text);

        originalString = text;

        listeners = new ArrayList<>();

        class DocumentListener implements javax.swing.event.DocumentListener {

            @Override
            public void insertUpdate(DocumentEvent e) {
                setModified(!originalString.equals(textArea.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setModified(!originalString.equals(textArea.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setModified(!originalString.equals(textArea.getText()));
            }
        }

        textArea.getDocument().addDocumentListener(
                new DocumentListener());
    }

    /**
     * Gets the textArea
     * 
     * @return JTextArea
     */
    @Override
    public JTextArea getTextComponent() {
        return textArea;
    }

    /**
     * Gets the path
     * 
     * @return Path
     */
    @Override
    public Path getFilePath() {
        return path;
    }

    /**
     * Sets path for the document
     * 
     * @param path
     */
    @Override
    public void setFilePath(Path path) {
        if (path == null)
            throw new IllegalArgumentException("Path can't be null");

        this.path = path;

        notifyListenersPath();
    }

    /**
     * Returns true if document is modified
     * 
     * @return boolean
     */
    @Override
    public boolean isModified() {
        return modified;
    }

    /**
     * Sets modified status for the document
     * 
     * @param modified
     */
    @Override
    public void setModified(boolean modified) {
        this.modified = modified;

        notifyListenersStatus();
    }

    /**
     * Adds listener to the document
     * 
     * @param l
     */
    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        if (l == null)
            throw new IllegalArgumentException("Listener can't be null");

        listeners.add(l);
    }

    /**
     * Removes listener from the document
     * 
     * @param l
     */
    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        if (l == null)
            throw new IllegalArgumentException("Listener can't be null");

        listeners.remove(l);
    }

    /**
     * Notifies listeners about the status of the document
     * 
     */
    private void notifyListenersStatus() {
        for (SingleDocumentListener l : listeners) {
            l.documentModifyStatusUpdated(this);
        }
    }

    /**
     * Notifies listeners about the path of the document
     * 
     */
    private void notifyListenersPath() {
        for (SingleDocumentListener l : listeners) {
            l.documentFilePathUpdated(this);
        }
    }

}
