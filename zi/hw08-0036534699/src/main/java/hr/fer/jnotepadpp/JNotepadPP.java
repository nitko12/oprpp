package hr.fer.jnotepadpp;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import hr.fer.jnotepadpp.documentModel.DefaultMultipleDocumentModel;
import hr.fer.jnotepadpp.documentModel.MultipleDocumentListener;
import hr.fer.jnotepadpp.documentModel.MultipleDocumentModel;
import hr.fer.jnotepadpp.documentModel.SingleDocumentModel;
import hr.fer.jnotepadpp.documentModel.StatisticsInfo;
import hr.fer.jnotepadpp.documentModel.StatisticsListener;
import hr.fer.jnotepadpp.local.LocalizableAction;
import hr.fer.jnotepadpp.local.LocalizationProvider;
import hr.fer.jnotepadpp.local.LocalizedJFrame;
import hr.fer.jnotepadpp.local.LocalizedJMenu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * JNotepadPP app class
 */
public class JNotepadPP extends LocalizedJFrame {

    /**
     * Main method that starts the app
     * 
     * @param args
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            LocalizationProvider.getInstance().setLanguage("en");

            JNotepadPP app = new JNotepadPP();

            app.setVisible(true);
            app.setSize(800, 600);
        });
    }

    private DefaultMultipleDocumentModel model;

    private Timer timer = new Timer();
    private LocalDateTime now = LocalDateTime.now();

    private KeyListener activeComponentKeyListener;
    private HashMap<String, Action> actions;

    /**
     * Constructor for JNotepadPP
     * 
     */
    public JNotepadPP() {

        actions = getActions();

        model = new DefaultMultipleDocumentModel();
        // ==================== closing ====================

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        final JNotepadPP _this = this;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // ask user if he wants to save unsaved documents using showOptionDialogv

                for (SingleDocumentModel doc : model) {
                    if (doc.isModified()) {
                        String[] options = {
                                flp.getString("yes"),
                                flp.getString("no"),
                                flp.getString("cancel"),
                        };

                        // flp.getString("closingQuestionTitle"),
                        // flp.getString("closingQuestion"),

                        // do you want to save unsaved document?

                        int result = JOptionPane.showOptionDialog(
                                _this,
                                flp.getString("closingQuestion"),
                                flp.getString("closingQuestionTitle") + " " + doc.getFilePath().getFileName(),
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);

                        if (result == JOptionPane.YES_OPTION) {
                            saveSingleDocument(doc);

                        } else if (result == JOptionPane.NO_OPTION) {
                            // close document
                        } else if (result == JOptionPane.CANCEL_OPTION) {
                            // do nothing
                            return;
                        }
                    }
                }

                _this.setVisible(false);
                _this.dispose();
            }
        });

        // =================================================

        // ==================== menuBar ====================

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new LocalizedJMenu("file", flp);

        fileMenu.add(actions.get("new"));
        fileMenu.add(actions.get("open"));
        fileMenu.add(actions.get("save"));
        fileMenu.add(actions.get("saveas"));
        fileMenu.add(actions.get("close"));

        JMenu editMenu = new LocalizedJMenu("edit", flp);

        LocalizableAction cut = new LocalizableAction("cut", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getCurrentDocument().getTextComponent().cut();
            }
        };
        actions.put("cut", cut);

        LocalizableAction copy = new LocalizableAction("copy", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getCurrentDocument().getTextComponent().copy();
            }
        };
        actions.put("copy", copy);

        LocalizableAction paste = new LocalizableAction("paste", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getCurrentDocument().getTextComponent().paste();
            }
        };
        actions.put("paste", paste);

        JMenu toolsMenu = new LocalizedJMenu("tools", flp);

        // to uppercase
        // • to lowercase
        // • invert case

        LocalizableAction toUpperCase = new LocalizableAction("to_uppercase", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = model.getCurrentDocument().getTextComponent().getSelectedText();

                if (text == null) {
                    return;
                }

                model.getCurrentDocument().getTextComponent().replaceSelection(text.toUpperCase());
            }
        };
        actions.put("to_uppercase", toUpperCase);

        LocalizableAction toLowerCase = new LocalizableAction("to_lowercase", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = model.getCurrentDocument().getTextComponent().getSelectedText();

                if (text == null) {
                    return;
                }

                model.getCurrentDocument().getTextComponent().replaceSelection(text.toLowerCase());
            }
        };
        actions.put("to_lowercase", toLowerCase);

        LocalizableAction invertCase = new LocalizableAction("invert_case", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = model.getCurrentDocument().getTextComponent().getSelectedText();

                if (text == null) {
                    return;
                }

                StringBuilder sb = new StringBuilder();

                for (char c : text.toCharArray()) {
                    if (Character.isUpperCase(c)) {
                        sb.append(Character.toLowerCase(c));
                    } else {
                        sb.append(Character.toUpperCase(c));
                    }
                }

                model.getCurrentDocument().getTextComponent().replaceSelection(sb.toString());
            }
        };
        actions.put("invert_case", invertCase);

        toolsMenu.add(toUpperCase);
        toolsMenu.add(toLowerCase);
        toolsMenu.add(invertCase);

        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(toolsMenu);

        JToolBar toolBar = new JToolBar();

        toolBar.add(menuBar);

        add(toolBar, BorderLayout.NORTH);

        // =================================================

        // ================== statusBar ====================

        JPanel statusBar = new JPanel();

        statusBar.setLayout(new GridLayout(1, 3));

        JLabel l1 = new JLabel("length: 0");
        JLabel l2 = new JLabel("ln: 0");

        StatisticsListener statisticsListener = new StatisticsListener() {

            @Override
            public void statisticsUpdated(StatisticsInfo info) {
                // System.out.println("length: " + info.getLength() + " lines: " +
                // info.getLines());
                l1.setText("length: " + info.getLength());

                int caretPosition = model.getCurrentDocument().getTextComponent().getCaretPosition();

                int line = 0;
                int column = 0;
                int selected = 0;

                try {
                    line = model.getCurrentDocument().getTextComponent().getLineOfOffset(caretPosition);
                    column = caretPosition - model.getCurrentDocument().getTextComponent().getLineStartOffset(line);
                    selected = Math.abs(model.getCurrentDocument().getTextComponent().getSelectionEnd()
                            - model.getCurrentDocument().getTextComponent().getSelectionStart());
                } catch (BadLocationException e) {
                }

                l2.setText("ln: " + (line + 1) + " col: " + (column + 1) + " sel: " + selected);

                l1.revalidate();
                l2.revalidate();
            }

        };

        model.addMultipleDocumentListener(
                new MultipleDocumentListener() {
                    @Override
                    public void currentDocumentChanged(SingleDocumentModel previousModel,
                            SingleDocumentModel currentModel) {
                        statisticsListener.statisticsUpdated(new StatisticsInfo(
                                currentModel.getTextComponent().getText()));
                    }

                    @Override
                    public void documentAdded(SingleDocumentModel model) {
                    }

                    @Override
                    public void documentRemoved(SingleDocumentModel model) {
                    }
                });

        activeComponentKeyListener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                hotkeyAction(e);
                if (statisticsListener != null) {
                    statisticsListener.statisticsUpdated(new StatisticsInfo(
                            model.getCurrentDocument().getTextComponent().getText()));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        statusBar.add(l1);
        statusBar.add(l2);

        JPanel timerPanel = new JPanel();
        statusBar.add(timerPanel);

        add(statusBar, BorderLayout.SOUTH);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                now = LocalDateTime.now();

                timerPanel.removeAll(); // nije najbolje rjesenje

                String timeDateString = String.format("%02d:%02d:%02d %02d.%02d.%04d.",
                        now.getHour(), now.getMinute(), now.getSecond(),
                        now.getDayOfMonth(), now.getMonthValue(), now.getYear());

                timerPanel.add(new JLabel(timeDateString));

                timerPanel.revalidate();
            }
        }, 0, 1000);

        // =================================================

        // ================== tabbedView ===================

        model.addKeyListener(activeComponentKeyListener);

        // model.subscribeToStatistics(statisticsListener);

        add(model.getVisualComponent(), BorderLayout.CENTER);

        // =================================================

        // ================== tabbedView ===================

        JMenu languagesMenu = new LocalizedJMenu("languages", flp);

        LocalizableAction englishAction = new LocalizableAction("english", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("en");
            }
        };

        LocalizableAction germanAction = new LocalizableAction("german", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("de");
            }
        };

        LocalizableAction croatianAction = new LocalizableAction("croatian", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("hr");
            }
        };

        languagesMenu.add(englishAction);
        languagesMenu.add(germanAction);
        languagesMenu.add(croatianAction);

        menuBar.add(languagesMenu);

        // =================================================

        setLocationRelativeTo(null);
        setTitle("JNotepad++");
        setVisible(true);

        // model.createNewDocument();
    }

    /**
     * @return HashMap<String, Action>
     */
    private HashMap<String, Action> getActions() {
        HashMap<String, Action> actions = new HashMap<>();

        LocalizableAction exitAction = new LocalizableAction("exit", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        actions.put("exit", exitAction);

        LocalizableAction englishAction = new LocalizableAction("english", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("en");
            }
        };
        actions.put("english", englishAction);

        LocalizableAction germanAction = new LocalizableAction("german", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("de");
            }
        };
        actions.put("german", germanAction);

        LocalizableAction croatianAction = new LocalizableAction("croatian", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalizationProvider.getInstance().setLanguage("hr");
            }
        };
        actions.put("croatian", croatianAction);

        LocalizableAction statisticsAction = new LocalizableAction("statistics", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingleDocumentModel currentDocument = model.getCurrentDocument();

                if (currentDocument == null) {
                    return;
                }

                String text = currentDocument.getTextComponent().getText();

                int nonBlank = 0;
                int lines = 0;

                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == '\n') {
                        lines++;
                    } else if (!Character.isWhitespace(text.charAt(i))) {
                        nonBlank++;
                    }
                }

                JOptionPane.showMessageDialog(JNotepadPP.this,
                        String.format(flp.getString("stats_string"),
                                text.length(), nonBlank, lines),
                        flp.getString("stats_title"), JOptionPane.INFORMATION_MESSAGE);

            }
        };
        actions.put("statistics", statisticsAction);

        LocalizableAction newAction = new LocalizableAction("new", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.createNewDocument();
            }
        };
        actions.put("new", newAction);

        LocalizableAction openAction = new LocalizableAction("open", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle(flp.getString("open_file"));
                if (fileChooser.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                File fileName = fileChooser.getSelectedFile();
                Path filePath = fileName.toPath();

                if (!Files.isReadable(filePath)) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("file_not_readable"),
                            flp.getString("error"), JOptionPane.ERROR_MESSAGE);
                    return;
                }

                model.loadDocument(filePath);
            }
        };
        actions.put("open", openAction);

        // save

        LocalizableAction saveAction = new LocalizableAction("save", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingleDocumentModel currentDocument = model.getCurrentDocument();

                if (currentDocument == null) {
                    return;
                }

                if (currentDocument.getFilePath() == null) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle(flp.getString("save_file"));
                    if (fileChooser.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                        JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("save_cancelled"),
                                flp.getString("warning"), JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    File fileName = fileChooser.getSelectedFile();
                    Path filePath = fileName.toPath();

                    if (Files.exists(filePath)) {
                        int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
                                flp.getString("file_exists"), flp.getString("warning"),
                                JOptionPane.YES_NO_OPTION);

                        if (result != JOptionPane.YES_OPTION) {
                            return;
                        }
                    }

                    model.saveDocument(currentDocument, filePath);
                } else {
                    model.saveDocument(currentDocument, currentDocument.getFilePath());
                }
            }
        };

        actions.put("save", saveAction);

        LocalizableAction saveAsAction = new LocalizableAction("saveas", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingleDocumentModel currentDocument = model.getCurrentDocument();

                if (currentDocument == null) {
                    return;
                }

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle(flp.getString("save_file"));
                if (fileChooser.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("save_cancelled"),
                            flp.getString("warning"), JOptionPane.WARNING_MESSAGE);
                    return;
                }

                File fileName = fileChooser.getSelectedFile();
                Path filePath = fileName.toPath();

                if (Files.exists(filePath)) {
                    int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
                            flp.getString("file_exists"), flp.getString("warning"),
                            JOptionPane.YES_NO_OPTION);

                    if (result != JOptionPane.YES_OPTION) {
                        return;
                    }
                }

                model.saveDocument(currentDocument, filePath);
            }
        };

        actions.put("saveas", saveAsAction);

        LocalizableAction closeAction = new LocalizableAction("close", flp) {
            @Override
            public void actionPerformed(ActionEvent e) {
                SingleDocumentModel currentDocument = model.getCurrentDocument();

                if (currentDocument == null) {
                    return;
                }

                if (currentDocument.isModified()) {
                    int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
                            flp.getString("save_before_close"), flp.getString("warning"),
                            JOptionPane.YES_NO_CANCEL_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        saveAction.actionPerformed(e);
                    } else if (result == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                try {
                    model.closeDocument(currentDocument);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("close_error_last_file"),
                            flp.getString("close_error_last_file_title"), JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        actions.put("close", closeAction);

        return actions;
    }

    /**
     * Saves the current document.
     * 
     * @param currentDocument
     */
    private void saveSingleDocument(SingleDocumentModel currentDocument) {
        if (currentDocument.getFilePath() == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(flp.getString("save_file"));
            if (fileChooser.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("save_cancelled"),
                        flp.getString("warning"), JOptionPane.WARNING_MESSAGE);
                return;
            }

            File fileName = fileChooser.getSelectedFile();
            Path filePath = fileName.toPath();

            if (Files.exists(filePath)) {
                int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
                        flp.getString("file_exists"), flp.getString("warning"),
                        JOptionPane.YES_NO_OPTION);

                if (result != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            model.saveDocument(currentDocument, filePath);
        } else {
            model.saveDocument(currentDocument, currentDocument.getFilePath());
        }
    }

    /**
     * Method that listens to the hotkeys.
     * 
     * @param event
     */
    private void hotkeyAction(KeyEvent event) {
        if (event.isMetaDown() && !event.isConsumed()) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_N:
                    actions.get("new").actionPerformed(null);
                    event.consume();
                    break;
                case KeyEvent.VK_O:
                    actions.get("open").actionPerformed(null);
                    event.consume();
                    break;
                case KeyEvent.VK_S:
                    actions.get("save").actionPerformed(null);
                    event.consume();
                    break;
                case KeyEvent.VK_W:
                    actions.get("close").actionPerformed(null);
                    event.consume();
                    break;
                case KeyEvent.VK_C:
                    actions.get("copy").actionPerformed(null);
                    event.consume();
                    break;
                case KeyEvent.VK_X:
                    actions.get("cut").actionPerformed(null);
                    event.consume();
                    break;
                case KeyEvent.VK_V:
                    actions.get("paste").actionPerformed(null);
                    event.consume();
                    break;
                case KeyEvent.VK_M:
                    actions.get("statistics").actionPerformed(null);
                    event.consume();
                    break;
                case KeyEvent.VK_1:
                    actions.get("english").actionPerformed(null);
                    event.consume();
                    break;
                case KeyEvent.VK_2:
                    actions.get("german").actionPerformed(null);
                    event.consume();
                    break;
                case KeyEvent.VK_3:
                    actions.get("croatian").actionPerformed(null);
                    event.consume();
                    break;
            }
        }
    }
}
