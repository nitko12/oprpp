package hr.fer.oprpp1.hw04;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.StudentRecord;
import hr.fer.oprpp1.hw04.db.StudentTableFormatter;

public class StudentTableFormatterTest {

    @Test
    public void testFormat() {
        List<StudentRecord> records = new ArrayList<>();

        records.add(new StudentRecord("0", "a", "b", 5));
        records.add(new StudentRecord("1", "a", "b", 5));
        records.add(new StudentRecord("2", "a", "b", 5));

        StudentTableFormatter formatter = new StudentTableFormatter(records);

        String out = "+===+===+===+===+\n| 0 | a | b | 5 |\n"
                + "+===+===+===+===+\n| 1 | a | b | 5 |\n"
                + "+===+===+===+===+\n| 2 | a | b | 5 |\n"
                + "+===+===+===+===+";

        assertEquals(out, formatter.toString());
    }

}
