package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.Lexer;
import hr.fer.oprpp1.custom.scripting.lexer.LexerStateEnum;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.TokenEnum;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class SmartScriptParser {
    final private char[] data;
    final private Lexer lexer;

    private DocumentNode root;

    public SmartScriptParser(String documentBody) {
        data = documentBody.toCharArray();

        lexer = new Lexer(data);

        try {
            parse();
        } catch (Exception e) {
            throw new SmartScriptParserException("Error while parsing. - " + e.getMessage());
        }
    }

    public DocumentNode getDocumentNode() {
        return root;
    }

    public void parse() {

        ObjectStack stack = new ObjectStack();

        root = new DocumentNode();
        stack.push(root);

        while (lexer.getNextToken().getType() != TokenEnum.EOF) {

            Token token = lexer.getToken();

            if (token.getType() == TokenEnum.TEXT) {
                TextNode textNode = new TextNode((String) token.getValue());

                ((Node) stack.peek()).addChildNode(textNode);

            } else if (token.getType() == TokenEnum.TAGSTART) {

                lexer.setState(LexerStateEnum.INTAG);

                if (token.getValue().equals("FOR")) {

                    ForLoopNode forLoopNode = parseForLoopNode();

                    ((Node) stack.peek()).addChildNode(forLoopNode);
                    stack.push(forLoopNode);

                } else if (token.getValue().equals("END")) {

                    stack.pop();

                } else if (token.getValue().equals("=")) {
                    token = lexer.getNextToken();
                    EchoNode echoNode = parseEchoNode();

                    ((Node) stack.peek()).addChildNode(echoNode);

                    if (lexer.getToken().getType() == TokenEnum.TAGEND) {
                        lexer.setState(LexerStateEnum.NORMAL);
                    }

                } else {
                    throw new SmartScriptParserException("Ime tag-a nije valjano.");
                }
            } else if (token.getType() == TokenEnum.TAGEND) {
                lexer.setState(LexerStateEnum.NORMAL);
            }
        }

        if (stack.size() != 1) {
            throw new SmartScriptParserException("Struktura dokumenta nije dobra.");
        }
    }

    public ForLoopNode parseForLoopNode() {
        ElementVariable variable = null;
        Element start = null;
        Element end = null;
        Element step = null;

        Token token = lexer.getNextToken();

        if (token.getType() != TokenEnum.VARIABLE) {
            throw new SmartScriptParserException("For petlju mora zapocinjati varijabla.");
        }

        variable = new ElementVariable((String) token.getValue());

        token = lexer.getNextToken();
        start = tokenToElementForLoop(token);

        token = lexer.getNextToken();
        end = tokenToElementForLoop(token);

        token = lexer.peekNextToken();

        try {
            step = tokenToElementForLoop(token);
        } catch (SmartScriptParserException ex) {
            step = null;
        }

        return new ForLoopNode(variable, start, end, step);
    }

    public EchoNode parseEchoNode() {
        Token token = lexer.getToken();

        ArrayIndexedCollection elements = new ArrayIndexedCollection();

        while (token.getType() != TokenEnum.TAGEND) {
            elements.add(tokenToElementEchoNode(token));

            token = lexer.getNextToken();
        }

        Element[] elementsArray = new Element[elements.size()];

        for (int i = 0; i < elements.size(); i++) {
            elementsArray[i] = (Element) elements.get(i);
        }

        return new EchoNode(elementsArray);
    }

    private Element tokenToElementForLoop(Token t) {
        Element element = null;

        if (t.getType() == TokenEnum.VARIABLE)
            element = new ElementVariable((String) t.getValue());
        else if (t.getType() == TokenEnum.INT)
            element = new ElementConstantInteger((Integer) t.getValue());
        else if (t.getType() == TokenEnum.DOUBLE)
            element = new ElementConstantDouble((Double) t.getValue());
        else if (t.getType() == TokenEnum.STRING)
            element = new ElementString((String) t.getValue());
        else
            throw new SmartScriptParserException("For petlja mora imati pocetnu vrijednost.");

        return element;
    }

    private Element tokenToElementEchoNode(Token t) {
        try {
            return tokenToElementForLoop(t); // malo los nacin
        } catch (SmartScriptParserException ex) {
        }

        if (t.getType() == TokenEnum.FUNCTION)
            return new ElementFunction((String) t.getValue());
        else if (t.getType() == TokenEnum.OPERATOR)
            return new ElementOperator("" + t.getValue());
        return null;
    }
}
