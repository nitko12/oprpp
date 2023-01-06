package hr.fer.oprpp1.hw04;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.FieldValueGetters;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class FieldValueGettersTest {
    @Test
    public void testGetFirstName() {
        StudentRecord record = new StudentRecord("0", "a", "b", 5);
        assertEquals("b", FieldValueGetters.FIRST_NAME.get(record));
        assertEquals("a", FieldValueGetters.LAST_NAME.get(record));
        assertEquals("0", FieldValueGetters.JMBAG.get(record));

    }
}
