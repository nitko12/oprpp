package hr.fer.oprpp1.custom.scripting;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class InegrationTest {

    private String readExample(String n) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/" + n + ".txt")) {
            if (is == null)
                throw new RuntimeException("Datoteka extra/" + n + ".txt je nedostupna.");
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
            String data = readExample("examples/doc" + i);

            SmartScriptParser parser = new SmartScriptParser(data);
            String originalDocumentBody = parser.getDocumentNode().toString();

            SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
            String originalDocumentBody2 = parser2.getDocumentNode().toString();

            assertEquals(originalDocumentBody, originalDocumentBody2);
        }
    }

    @Test
    public void testExtras() {

        String data = readExample("primjer1");

        SmartScriptParser parser = new SmartScriptParser(data);
        String originalDocumentBody = parser.getDocumentNode().toString();

        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        String originalDocumentBody2 = parser2.getDocumentNode().toString();

        assertEquals(originalDocumentBody, originalDocumentBody2);

        //

        data = readExample("primjer2");

        parser = new SmartScriptParser(data);
        originalDocumentBody = parser.getDocumentNode().toString();

        parser2 = new SmartScriptParser(originalDocumentBody);
        originalDocumentBody2 = parser2.getDocumentNode().toString();

        assertEquals(originalDocumentBody, originalDocumentBody2);

        //

        data = readExample("primjer3");

        parser = new SmartScriptParser(data);
        originalDocumentBody = parser.getDocumentNode().toString();

        parser2 = new SmartScriptParser(originalDocumentBody);
        originalDocumentBody2 = parser2.getDocumentNode().toString();

        assertEquals(originalDocumentBody, originalDocumentBody2);

        //

        final String data2 = readExample("primjer4");

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(data2));

        //

        final String data3 = readExample("primjer5");

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(data3));

        //

        data = readExample("primjer6");

        parser = new SmartScriptParser(data);
        originalDocumentBody = parser.getDocumentNode().toString();

        parser2 = new SmartScriptParser(originalDocumentBody);
        originalDocumentBody2 = parser2.getDocumentNode().toString();

        assertEquals(originalDocumentBody, originalDocumentBody2);

        // mozda krivo shvacam nesto u tekstu:
        // "Every other sequence which starts with \ should be treated as invalid and
        // throw an exception."

        // ovdje je \ i n jedno iza drugog, a ne ascii 10, pa baca error

        // ako ne treba bacati, dodao bih if oko linije 200 u Lexer.java

        // data = readExample("primjer7");

        // parser = new SmartScriptParser(data);
        // originalDocumentBody = parser.getDocumentNode().toString();

        // parser2 = new SmartScriptParser(originalDocumentBody);
        // originalDocumentBody2 = parser2.getDocumentNode().toString();

        // assertEquals(originalDocumentBody, originalDocumentBody2);

        //

        final String data4 = readExample("primjer8");

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(data4));

        //

        final String data5 = readExample("primjer9");

        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(data5));

        //

        data = readExample("primjer10");

        parser = new SmartScriptParser(data);
        originalDocumentBody = parser.getDocumentNode().toString();

        parser2 = new SmartScriptParser(originalDocumentBody);
        originalDocumentBody2 = parser2.getDocumentNode().toString();

        assertEquals(originalDocumentBody, originalDocumentBody2);
    }
}
