package hr.fer.oprpp1.hw04.db.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.oprpp1.hw04.db.QueryFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.StudentDB;
import hr.fer.oprpp1.hw04.db.StudentRecord;
import hr.fer.oprpp1.hw04.db.StudentTableFormatter;

/**
 * CLI application that demonstrates the usage of the studentDB class.
 */
class StudentDBdemo {

    /**
     * Main method
     * 
     * Runs a command line application for queryinf students.
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        List<String> lines;

        try {
            // lines = Files.readAllLines(Path.of("database.txt"));

            lines = Files.readAllLines(
                    Paths.get("./prva.txt"),
                    StandardCharsets.UTF_8);

            System.out.println(lines.size());
        } catch (IOException e) {
            System.out.println("Error while reading file");

            return;
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        StudentDB db = new StudentDB(lines);

        while (true) {
            System.out.print("> ");
            String line = reader.readLine();

            if (line.startsWith("query")) {

                QueryParser parser;

                try {
                    parser = new QueryParser(line.substring(5));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                List<StudentRecord> records;

                if (parser.isDirectQuery()) {
                    System.out.println("Using index for record retrieval.");

                    records = List.of(db.forJMBAG(parser.getQueriedJMBAG()));

                } else {
                    records = db.filter(new QueryFilter(parser.getQuery()));
                }

                StudentTableFormatter formatter = new StudentTableFormatter(records);

                System.out.println(formatter);

                System.out.println("Records selected: " + records.size());

            } else if (line.equals("exit")) {
                System.out.println("Goodbye!");

                break;
            } else {
                System.out.println("Unknown command");

            }

        }
    }
}