package hr.fer.jnotepadpp.documentModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import java.awt.event.KeyListener;

import hr.fer.jnotepadpp.Util;

/**
 * Default implementation of MultipleDocumentModel.
 * 
 */
public class DefaultMultipleDocumentModel implements MultipleDocumentModel {

    List<SingleDocumentModel> documents;
    SingleDocumentModel activeDocument;

    JTabbedPane tabbedPane;

    ImageIcon redDisk;
    ImageIcon greenDisk;

    List<MultipleDocumentListener> listeners;
    List<StatisticsListener> statisticsListeners;

    KeyListener keyListener = null;

    /**
     * Creates a new DefaultMultipleDocumentModel.
     */
    public DefaultMultipleDocumentModel() {
        documents = new ArrayList<>();
        listeners = new ArrayList<>();
        tabbedPane = new JTabbedPane();
        statisticsListeners = new ArrayList<>();

        try (InputStream is1 = this.getClass().getResourceAsStream("icons/redDisk.png");
                InputStream is2 = this.getClass().getResourceAsStream("icons/greenDisk.png")) {
            if (is1 == null || is2 == null) {
                throw new RuntimeException("Resource not found");
            }
            byte[] bytes = is1.readAllBytes();
            byte[] bytes2 = is2.readAllBytes();

            this.redDisk = new ImageIcon(bytes);
            this.greenDisk = new ImageIcon(bytes2);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tabbedPane.addChangeListener(e -> {
            // System.out.println("Change");

            int index = tabbedPane.getSelectedIndex();
            SingleDocumentModel prev = activeDocument;
            activeDocument = documents.get(index);

            SingleDocumentListener listener = new SingleDocumentListener() {

                @Override
                public void documentModifyStatusUpdated(SingleDocumentModel model) {
                    boolean modified = model.isModified();

                    // System.out.println(modified);

                    if (modified) {
                        tabbedPane.setIconAt(index, redDisk);
                    } else {
                        tabbedPane.setIconAt(index, greenDisk);
                    }

                }

                @Override
                public void documentFilePathUpdated(SingleDocumentModel model) {
                    Path path = model.getFilePath();

                    if (path == null) {
                        tabbedPane.setIconAt(index, redDisk);
                    } else {
                        tabbedPane.setIconAt(index, greenDisk);
                    }
                }
            };
            prev.removeSingleDocumentListener(listener);
            activeDocument.addSingleDocumentListener(listener);

            // tabbedPane.setIconAt(index, redDisk);

            notifyListenersCurrentDocumentChanged(prev, activeDocument);

            mouseHoverOnTabsUpdate();
        });

    }

    /**
     * Returns the iterator of the documents
     * 
     * @return Iterator<SingleDocumentModel>
     */
    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documents.iterator();
    }

    /**
     * Creates new document
     * 
     * @return SingleDocumentModel
     */
    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel prev = activeDocument;

        this.activeDocument = new DefaultSingleDocumentModel(null, "");
        documents.add(activeDocument);

        tabbedPane.addTab("unnamed", redDisk, activeDocument.getTextComponent());

        notifyListenersDocumentAdded(activeDocument);
        notifyListenersCurrentDocumentChanged(prev, activeDocument);

        return this.activeDocument;
    }

    /**
     * Returns the current document
     * 
     * @return SingleDocumentModel
     */
    @Override
    public SingleDocumentModel getCurrentDocument() {
        return activeDocument;
    }

    /**
     * Loads the document from the given path
     * 
     * @param path
     * @return SingleDocumentModel
     */
    @Override
    public SingleDocumentModel loadDocument(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("Path can't be null");
        }

        SingleDocumentModel doc = findForPath(path);

        if (doc != null) {
            return doc;
        }

        String text;

        try {
            text = Util.loadFile(path);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't load file");
        }

        SingleDocumentModel prev = activeDocument;

        this.activeDocument = new DefaultSingleDocumentModel(path, text);
        documents.add(activeDocument);

        notifyListenersDocumentAdded(activeDocument);
        notifyListenersCurrentDocumentChanged(prev, activeDocument);

        tabbedPane.addTab(path.getFileName().toString(), greenDisk, activeDocument.getTextComponent());

        return this.activeDocument;
    }

    /**
     * Saves the document to the given path
     * 
     * @param model
     * @param newPath
     */
    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        if (model == null) {
            throw new IllegalArgumentException("Model can't be null");
        }

        if (newPath == null) {
            throw new IllegalArgumentException("Path can't be null");
        }

        try {
            Util.saveFile(newPath, model.getTextComponent().getText());
        } catch (Exception e) {
            throw new IllegalArgumentException("Can't save file");
        }

        int index = documents.indexOf(model);

        tabbedPane.setTitleAt(index, newPath.getFileName().toString());
        tabbedPane.setIconAt(index, greenDisk);

        model.setFilePath(newPath);
    }

    /**
     * Closes the document
     * 
     * @param model
     */
    @Override
    public void closeDocument(SingleDocumentModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Model can't be null");
        }

        if (documents.size() == 1) {
            throw new IllegalArgumentException("Can't close last document");
        }

        if (model.equals(activeDocument)) {
            int index = tabbedPane.getSelectedIndex();
            tabbedPane.remove(index);
            documents.remove(index);

            if (index == 0) {
                activeDocument = documents.get(0);
            } else {
                activeDocument = documents.get(index - 1);
            }

            notifyListenersCurrentDocumentChanged(model, activeDocument);
        } else {
            int index = documents.indexOf(model);
            tabbedPane.remove(index);
            documents.remove(index);
        }

        notifyListenersDocumentRemoved(model);

        documents.remove(model);
    }

    /**
     * Adds listener to the list of listeners
     * 
     * @param l
     */
    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    /**
     * Removes listener from the list of listeners
     * 
     * @param l
     */
    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    /**
     * Returns the number of documents
     * 
     * @return int
     */
    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    /**
     * Gets the document at the given index
     * 
     * @param index
     * @return SingleDocumentModel
     */
    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    /**
     * Finds the document for the given path
     * 
     * @param path
     * @return SingleDocumentModel
     */
    @Override
    public SingleDocumentModel findForPath(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("Path can't be null");
        }

        for (SingleDocumentModel doc : documents) {
            if (doc.getFilePath().equals(path)) {
                return doc;
            }
        }
        return null;
    }

    /**
     * Subscribes to the statistics
     * 
     * @param listener
     */
    public void subscribeToStatistics(StatisticsListener listener) {
        statisticsListeners.add(listener);
    }

    /**
     * 
     * Gets the index of the given document
     * 
     * @param doc
     * @return int
     */
    @Override
    public int getIndexOfDocument(SingleDocumentModel doc) {
        return documents.indexOf(doc);
    }

    /**
     * Returns the visual component
     * 
     * @return JComponent
     */
    @Override
    public JComponent getVisualComponent() {
        return tabbedPane;
    }

    /**
     * Notifies listeners that the current document has changed
     * 
     * @param prev
     * @param newDoc
     */
    private void notifyListenersCurrentDocumentChanged(SingleDocumentModel prev,
            SingleDocumentModel newDoc) {

        if (keyListener != null) {
            if (prev != null) {
                prev.getTextComponent().removeKeyListener(keyListener);
            }
            newDoc.getTextComponent().addKeyListener(keyListener);
        }

        for (MultipleDocumentListener l : listeners) {
            l.currentDocumentChanged(prev, newDoc);
        }
    }

    /**
     * Notifies listeners that a document has been added
     * 
     * @param doc
     */
    private void notifyListenersDocumentAdded(SingleDocumentModel doc) {
        mouseHoverOnTabsUpdate();
        for (MultipleDocumentListener l : listeners) {
            l.documentAdded(doc);
        }
    }

    /**
     * Notifies listeners that a document has been removed
     * 
     * @param doc
     */
    private void notifyListenersDocumentRemoved(SingleDocumentModel doc) {
        mouseHoverOnTabsUpdate();
        for (MultipleDocumentListener l : listeners) {
            l.documentRemoved(doc);
        }
    }

    /**
     * Adds a key listener
     * 
     * @param listener
     */
    public void addKeyListener(KeyListener listener) {
        keyListener = listener;
    }

    /**
     * Sets the tooltip text on all tabs
     */
    private void mouseHoverOnTabsUpdate() {
        // setToolTipTextAt on all tabs

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setToolTipTextAt(i, documents.get(i).getFilePath().toString());
        }

    }
}
