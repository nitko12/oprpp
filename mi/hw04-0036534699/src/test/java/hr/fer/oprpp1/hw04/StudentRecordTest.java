package hr.fer.oprpp1.hw04;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.IFilter;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class StudentRecordTest {

    @Test
    public void testContructors() {

        StudentRecord record1 = new StudentRecord("0", "a", "b", 5);
        StudentRecord record2 = new StudentRecord("0", "a", "b", 5);
        StudentRecord record3 = new StudentRecord("1", "a", "c", 5);

        assertEquals(record1, record1);
        assertTrue(record1.equals(record1));
        assertTrue(record1.equals(record2));
        assertFalse(record1.equals(record3));

        assertEquals(record1.hashCode(), record1.hashCode());
        assertEquals(record1.hashCode(), record2.hashCode());
        assertNotEquals(record1.hashCode(), record3.hashCode());
    }

    @Test
    public void testFilter() {

        StudentRecord record1 = new StudentRecord("0", "a", "b", 5);
        StudentRecord record2 = new StudentRecord("0", "a", "b", 5);
        StudentRecord record3 = new StudentRecord("1", "a", "c", 5);

        IFilter filter = new IFilter() {

            @Override
            public boolean accepts(StudentRecord record) {
                return record.getJmbag().equals("0");
            }
        };

        assertTrue(filter.accepts(record1));
        assertTrue(filter.accepts(record2));
        assertFalse(filter.accepts(record3));
    }

}
