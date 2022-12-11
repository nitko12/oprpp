package hr.fer.oprpp1.hw04.db;

import java.util.Collection;
import java.util.List;

public class StudentTableFormatter {
    private final List<StudentRecord> records;

    private int jmbagLength = 0;
    private int lastNameLength = 0;
    private int firstNameLength = 0;

    private Collection<String> showingAttributes;

    public StudentTableFormatter(List<StudentRecord> records) {
        this.records = records;

        for (StudentRecord record : records) {
            jmbagLength = Math.max(jmbagLength, record.getJmbag().length());
            lastNameLength = Math.max(lastNameLength, record.getLastName().length());
            firstNameLength = Math.max(firstNameLength, record.getFirstName().length());
        }
    }

    public StudentTableFormatter(List<StudentRecord> records, Collection<String> showingAttributes) {
        this(records);

        this.showingAttributes = showingAttributes;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(makeEmptyRow());
        for (StudentRecord record : records) {
            sb.append(makeRow(record.getJmbag(),
                    record.getLastName(),
                    record.getFirstName(),
                    Integer.toString(record.getFinalGrade())));

            sb.append(makeEmptyRow());
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    private String makeEmptyRow() {
        StringBuilder sb = new StringBuilder();

        if (showingAttributes == null) {
            sb.append("+");
            sb.append("=".repeat(Math.max(0, jmbagLength + 2)));
            sb.append("+");
            sb.append("=".repeat(Math.max(0, lastNameLength + 2)));
            sb.append("+");
            sb.append("=".repeat(Math.max(0, firstNameLength + 2)));
            sb.append("+===+\n");
        } else {
            for (String attribute : showingAttributes) {
                switch (attribute) {
                    case "jmbag" -> {
                        sb.append("+");
                        sb.append("=".repeat(Math.max(0, jmbagLength + 2)));
                    }
                    case "lastName" -> {
                        sb.append("+");
                        sb.append("=".repeat(Math.max(0, lastNameLength + 2)));
                    }
                    case "firstName" -> {
                        sb.append("+");
                        sb.append("=".repeat(Math.max(0, firstNameLength + 2)));
                    }
                    case "finalGrade" -> {
                        sb.append("+===+");
                    }
                }
            }
            sb.append("+\n");
        }

        return sb.toString();
    }

    private String makeRow(String jmbag, String lastName, String firstName, String finalGrade) {
        StringBuilder sb = new StringBuilder();

        if (showingAttributes == null) {

            sb.append("| ");
            sb.append(jmbag);
            sb.append(" ".repeat(Math.max(0, jmbagLength - jmbag.length())));
            sb.append(" | ");
            sb.append(lastName);
            sb.append(" ".repeat(Math.max(0, lastNameLength - lastName.length())));
            sb.append(" | ");
            sb.append(firstName);
            sb.append(" ".repeat(Math.max(0, firstNameLength - firstName.length())));
            sb.append(" | ");
            sb.append(finalGrade);
            sb.append(" |\n");
        } else {
            sb.append("| ");
            for (String attribute : showingAttributes) {
                switch (attribute) {
                    case "jmbag":
                        sb.append(jmbag);
                        sb.append(" ".repeat(Math.max(0, jmbagLength - jmbag.length())));
                        sb.append(" | ");
                        break;
                    case "lastName":
                        sb.append(lastName);
                        sb.append(" ".repeat(Math.max(0, lastNameLength - lastName.length())));
                        sb.append(" | ");
                        break;
                    case "firstName":
                        sb.append(firstName);
                        sb.append(" ".repeat(Math.max(0, firstNameLength - firstName.length())));
                        sb.append(" | ");
                        break;
                    case "finalGrade":
                        sb.append(finalGrade);
                        sb.append(" |\n");
                        break;
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
