package hr.fer.oprpp1.hw04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.ComparisonOperators;
import hr.fer.oprpp1.hw04.db.ConditionalExpression;
import hr.fer.oprpp1.hw04.db.FieldValueGetters;
import hr.fer.oprpp1.hw04.db.IFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.StudentDB;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class QueryParserTest {

    @Test
    public void testQueryParser() {
        QueryParser parser = new QueryParser("jmbag = \"0000000003\"");

        assertTrue(parser.isDirectQuery());
        assertEquals("0000000003", parser.getQueriedJMBAG());

        QueryParser parser2 = new QueryParser("jmbag = \"0000000003\" and lastName LIKE \"B*\"");

        assertFalse(parser2.isDirectQuery());
        assertThrows(IllegalStateException.class, () -> parser2.getQueriedJMBAG());
        assertEquals(2, parser2.getQuery().size());

        ConditionalExpression filter = parser2.getQuery().get(0);

        assertTrue(filter.getComparisonOperator().satisfied(
                filter.getFieldGetter().get(new StudentRecord("0000000003", "Bosnić", "Andrea", 5)),
                filter.getStringLiteral()));

        assertTrue(filter.getComparisonOperator().satisfied(
                filter.getFieldGetter().get(new StudentRecord("0000000003", "Dosnić", "Andrea", 5)),
                filter.getStringLiteral()));

        assertFalse(filter.getComparisonOperator().satisfied(
                filter.getFieldGetter().get(new StudentRecord("0000000003", "Bosnić", "Andrea", 5)),
                "Bos*"));

        filter = parser2.getQuery().get(1);

        assertTrue(filter.getComparisonOperator().satisfied(
                filter.getFieldGetter().get(new StudentRecord("0000000003", "Bosnić", "Andrea", 5)),
                filter.getStringLiteral()));

        assertFalse(filter.getComparisonOperator().satisfied(
                filter.getFieldGetter().get(new StudentRecord("0000000003", "Dosnić", "Andrea", 5)),
                filter.getStringLiteral()));

    }

    @Test
    public void testQueryParser2() {
        QueryParser parser = new QueryParser("jmbag = \"0000000003\"");

        assertTrue(parser.isDirectQuery());
        assertEquals("0000000003", parser.getQueriedJMBAG());

        parser = new QueryParser("jmbag = \"0000000003\" and lastName LIKE \"B*\"");

        parser = new QueryParser("jmbag = \"0000000003\" and lastName>\"J\"");

        assertFalse(parser.isDirectQuery());
    }

    @Test
    public void testInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new QueryParser("jmbag = \"0000000003\" and lastName>\"J\" and"));

        assertThrows(IllegalArgumentException.class,
                () -> new QueryParser("jmbag = \"0000000003\" and \"J\"<lastName"));

        assertThrows(IllegalArgumentException.class,
                () -> new QueryParser("\"0000000003\" LIKE jmbag  and lastName>\"J\" and jmbag = \"0000000003\""));
    }
}
