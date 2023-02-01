package hr.fer.jnotepadpp.documentModel;

/**
 * Class that represents a statistics info.
 * 
 */
public class StatisticsInfo {
    private int length;
    private int nonBlank;
    private int lines;

    public StatisticsInfo(String text) {
        length = text.length();

        nonBlank = 0;
        lines = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ' && text.charAt(i) != '\t' && text.charAt(i) != '\n') {
                nonBlank++;
            }
            if (text.charAt(i) == '\n') {
                lines++;
            }
        }
    }

    public int getLength() {
        return length;
    }

    public int getNonBlank() {
        return nonBlank;
    }

    public int getLines() {
        return lines;
    }
}
