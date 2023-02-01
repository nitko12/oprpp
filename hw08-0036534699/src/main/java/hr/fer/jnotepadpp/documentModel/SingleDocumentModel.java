package hr.fer.jnotepadpp.documentModel;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * SingleDocumentModel represents a single document in the application.
 */
public interface SingleDocumentModel {
    JTextArea getTextComponent();

    Path getFilePath();

    void setFilePath(Path path);

    boolean isModified();

    void setModified(boolean modified);

    void addSingleDocumentListener(SingleDocumentListener l);

    void removeSingleDocumentListener(SingleDocumentListener l);
}