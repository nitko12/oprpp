package hr.fer.jnotepadpp.documentModel;

/**
 * Interface that represents a listener for multiple document model.
 * 
 */
public interface MultipleDocumentListener {
    void currentDocumentChanged(SingleDocumentModel previousModel,
            SingleDocumentModel currentModel);

    void documentAdded(SingleDocumentModel model);

    void documentRemoved(SingleDocumentModel model);
}
