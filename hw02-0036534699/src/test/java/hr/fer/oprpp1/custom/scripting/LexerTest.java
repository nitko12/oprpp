package hr.fer.oprpp1.custom.scripting;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.LexerStateEnum;
import hr.fer.oprpp1.custom.scripting.lexer.TokenEnum;

import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {

    @Test
    public void testNormal() {
        String data = "{ bla } blu \\{$=1$}. Nothing interesting {=here}. \\\\{$ FOR";

        Lexer lexer = new Lexer(data.toCharArray());

        assertEquals(TokenEnum.TEXT, lexer.getNextToken().getType());
        assertEquals("{ bla } blu {$=1$}. Nothing interesting {=here}. \\", lexer.getToken().getValue());

        assertEquals(TokenEnum.TAGSTART, lexer.getNextToken().getType());
        assertEquals("FOR", lexer.getToken().getValue());

        assertEquals(TokenEnum.EOF, lexer.getNextToken().getType());
    }

    @Test
    public void testInTag() {
        String data = "{$ FOR i 1 10 1 $}";

        Lexer lexer = new Lexer(data.toCharArray());

        assertEquals(TokenEnum.TAGSTART, lexer.getNextToken().getType());
        assertEquals("FOR", lexer.getToken().getValue());

        lexer.setState(LexerStateEnum.INTAG);

        // lexer.getNextToken();

        assertEquals(TokenEnum.VARIABLE, lexer.getNextToken().getType());
        assertEquals("i", lexer.getToken().getValue());

        assertEquals(TokenEnum.INT, lexer.getNextToken().getType());
        assertEquals(1, lexer.getToken().getValue());

        assertEquals(TokenEnum.INT, lexer.getNextToken().getType());
        assertEquals(10, lexer.getToken().getValue());

        assertEquals(TokenEnum.INT, lexer.getNextToken().getType());
        assertEquals(1, lexer.getToken().getValue());

        assertEquals(TokenEnum.TAGEND, lexer.getNextToken().getType());
        assertEquals(null, lexer.getToken().getValue());

        lexer.setState(LexerStateEnum.NORMAL);

        assertEquals(TokenEnum.EOF, lexer.getNextToken().getType());

        //

        data = "{$ END $}";

        lexer = new Lexer(data.toCharArray());

        assertEquals(TokenEnum.TAGSTART, lexer.getNextToken().getType());
        assertEquals("END", lexer.getToken().getValue());

        lexer.setState(LexerStateEnum.INTAG);

        assertEquals(TokenEnum.TAGEND, lexer.getNextToken().getType());
        assertEquals(null, lexer.getToken().getValue());

        lexer.setState(LexerStateEnum.NORMAL);

        assertEquals(TokenEnum.EOF, lexer.getNextToken().getType());

        //

        data = "{$ FOR sco_re \"-1\"10 \"1\" $}";

        lexer = new Lexer(data.toCharArray());

        assertEquals(TokenEnum.TAGSTART, lexer.getNextToken().getType());
        assertEquals("FOR", lexer.getToken().getValue());

        lexer.setState(LexerStateEnum.INTAG);

        assertEquals(TokenEnum.VARIABLE, lexer.getNextToken().getType());
        assertEquals("sco_re", lexer.getToken().getValue());

        assertEquals(TokenEnum.STRING, lexer.getNextToken().getType());
        assertEquals("\"-1\"", lexer.getToken().getValue());

        assertEquals(TokenEnum.INT, lexer.getNextToken().getType());
        assertEquals(10, lexer.getToken().getValue());

        assertEquals(TokenEnum.STRING, lexer.getNextToken().getType());
        assertEquals("\"1\"", lexer.getToken().getValue());

        assertEquals(TokenEnum.TAGEND, lexer.getNextToken().getType());
        assertEquals(null, lexer.getToken().getValue());

        lexer.setState(LexerStateEnum.NORMAL);

        assertEquals(TokenEnum.EOF, lexer.getNextToken().getType());

    }
}
