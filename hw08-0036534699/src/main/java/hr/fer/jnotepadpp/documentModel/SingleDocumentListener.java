package hr.fer.jnotepadpp.documentModel;

/**
 * Interface that represents a listener for single document model.
 * 
 */
public interface SingleDocumentListener {
    void documentModifyStatusUpdated(SingleDocumentModel model);

    void documentFilePathUpdated(SingleDocumentModel model);
}
