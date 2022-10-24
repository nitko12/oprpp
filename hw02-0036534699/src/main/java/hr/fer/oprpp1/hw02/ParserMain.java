package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class ParserMain {

    public static void main(String[] args) {

        String filepath = "PATH";

        String docBody;
        try {
            docBody = new String(
                    Files.readAllBytes(Paths.get(filepath)),
                    StandardCharsets.UTF_8);
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        System.out.println(docBody);

        System.out.println("-------------------------------------");

        SmartScriptParser parser = null;

        try {
            parser = new SmartScriptParser(docBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();

        System.out.println(originalDocumentBody); // should write
                                                  // something like
                                                  // original
                                                  // content of docBody
    }

}
