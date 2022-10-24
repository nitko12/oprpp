package hr.fer.oprpp1.custom.scripting;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    public void testPlainText() {
        String data = "This is sample text.";

        SmartScriptParser parser = new SmartScriptParser(data);

        DocumentNode d = parser.getDocumentNode();

        assertEquals(1, d.numberOfChildren());
        assertEquals(data, ((TextNode) d.getChild(0)).getText());
    }

    @Test
    public void testSimpleTagAndEcho() {
        String data = "A tag follows {$=$}.";

        SmartScriptParser parser = new SmartScriptParser(data);

        DocumentNode d = parser.getDocumentNode();

        assertEquals(3, d.numberOfChildren());

        assertEquals("A tag follows ", ((TextNode) d.getChild(0)).getText());
        assertEquals(".", ((TextNode) d.getChild(2)).getText());

        data = "A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.";

        parser = new SmartScriptParser(data);

        d = parser.getDocumentNode();

        assertEquals(3, d.numberOfChildren());

        assertEquals("A tag follows ", ((TextNode) d.getChild(0)).getText());
        // "\"Joe \"Long\" Smith\""

        assertEquals(".", ((TextNode) d.getChild(2)).getText());

        data = "{$= i i * @sin  \"0.000\" @decfmt $}";

        parser = new SmartScriptParser(data);

        d = parser.getDocumentNode();

        assertEquals(1, d.numberOfChildren());
    }

    @Test
    public void testExample() {
        String data = "This is sample text.\r\n" +
                "{$ FOR i 1 10 1 $}\r\n" +
                "This is {$= i $}-th time this message is generated.\r\n" +
                "{$END$}\r\n" +
                "{$FOR i 0 10 2 $}\r\n" +
                "sin({$=i$}^2) = {$= i i * @sin  \"0.000\" @decfmt $}\r\n" +
                "{$END$}\r\n";

        SmartScriptParser parser = new SmartScriptParser(data);

        DocumentNode d = parser.getDocumentNode();

        assertEquals(5, d.numberOfChildren());
    }

    @Test
    public void testExceptions() {
        assertThrows(SmartScriptParserException.class,
                () -> new SmartScriptParser("{$abc$}"));

        assertThrows(SmartScriptParserException.class,
                () -> new SmartScriptParser("{$ {$"));

        assertThrows(SmartScriptParserException.class,
                () -> new SmartScriptParser("{$ FOR i 1.0 -1 1 $}"));

        assertThrows(SmartScriptParserException.class,
                () -> new SmartScriptParser("{$="));

        assertThrows(SmartScriptParserException.class,
                () -> new SmartScriptParser("{$"));
    }
}
