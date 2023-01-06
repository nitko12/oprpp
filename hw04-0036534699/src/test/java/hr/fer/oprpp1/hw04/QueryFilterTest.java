package hr.fer.oprpp1.hw04;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.QueryFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class QueryFilterTest {
    @Test
    public void testAccept() {
        StudentRecord record = new StudentRecord("0", "a", "b", 5);

        var parser = new QueryParser("jmbag = \"0\"");
        var filter = new QueryFilter(parser.getQuery());

        assertTrue(filter.accepts(record));

        parser = new QueryParser("jmbag = \"1\"");
        filter = new QueryFilter(parser.getQuery());

        assertFalse(filter.accepts(record));

        parser = new QueryParser("jmbag = \"0\" and lastName = \"a\"");
        filter = new QueryFilter(parser.getQuery());

        assertTrue(filter.accepts(record));

        parser = new QueryParser("jmbag = \"0\" and lastName = \"c\"");
        filter = new QueryFilter(parser.getQuery());

        assertFalse(filter.accepts(record));

    }
}
