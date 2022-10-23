package hr.fer.oprpp1.custom.scripting;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class InegrationTest {

    private String readExample(int n) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/examples/doc" + n +
                ".txt")) {
            if (is == null)
                throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
            byte[] data = is.readAllBytes();
            String text = new String(data, StandardCharsets.UTF_8);
            return text;
        } catch (IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }

    }

    @Test
    public void testNormal() {
        for (int i = 1; i <= 3; i++) {
            String data = readExample(i);

            SmartScriptParser parser = new SmartScriptParser(data);
            String originalDocumentBody = parser.getDocumentNode().toString();

            SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
            String originalDocumentBody2 = parser2.getDocumentNode().toString();

            assertEquals(originalDocumentBody, originalDocumentBody2);
        }
    }
}
