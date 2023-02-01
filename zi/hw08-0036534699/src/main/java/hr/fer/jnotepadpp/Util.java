package hr.fer.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.ImageIcon;

/**
 * Helper class for loading assets and files.
 */
public class Util {

    /**
     * Loads aa text file from the given path.
     * 
     * @param path
     * @return String
     * @throws IOException
     */
    public static String loadFile(Path path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path can't be null");
        }

        byte[] bytes = Files.readAllBytes(path);

        return new String(bytes);
    }

    /**
     * Saves a text file to the given path.
     * 
     * @param newPath
     * @param text
     */
    public static void saveFile(Path newPath, String text) {
        if (newPath == null) {
            throw new IllegalArgumentException("Path can't be null");
        }

        if (text == null) {
            throw new IllegalArgumentException("Text can't be null");
        }

        try {
            Files.write(newPath, text.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't save file");
        }
    }

}
