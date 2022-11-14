package hr.fer.oprpp1.hw04;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.IFilter;
import hr.fer.oprpp1.hw04.db.StudentDB;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class StudentDBTest {

    @Test
    public void testContructors() {

        ArrayList<String> lines = new ArrayList<>(
                Arrays.asList(
                        "0000000018	Gužvinec	Matija	3",
                        "0000000019	Gvardijan	Slaven	4"));

        StudentDB db = new StudentDB(lines);

        StudentRecord record1 = db.forJMBAG("0000000018");

        assertEquals(record1.getJmbag(), "0000000018");
        assertEquals(record1.getFirstName(), "Matija");

        ArrayList<String> lines2 = new ArrayList<>(
                Arrays.asList(
                        "0000000018	Gužvinec	Matija	3",
                        "0000000018	Gužvinec	Ilija	3",
                        "0000000019	Gvardijan	Slaven	4"));

        assertThrows(IllegalArgumentException.class, () -> new StudentDB(lines2));
    }

    @Test
    public void testFilter() {

        ArrayList<String> lines = new ArrayList<>(
                Arrays.asList(
                        "0000000018	Gužvinec	Matija	3",
                        "0000000019	Gvardijan	Slaven	4"));

        StudentDB db = new StudentDB(lines);

        IFilter filter = new IFilter() {

            @Override
            public boolean accepts(StudentRecord record) {
                return record.getJmbag().equals("0000000018");
            }
        };

        assertEquals(db.filter(filter).size(), 1);
    }
}
