package hr.fer.oprpp1.hw04;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw04.db.ComparisonOperators;
import hr.fer.oprpp1.hw04.db.ConditionalExpression;
import hr.fer.oprpp1.hw04.db.FieldValueGetters;

import hr.fer.oprpp1.hw04.db.StudentRecord;

public class ComparisonExpressionTest {

    @Test
    public void test() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bos*",
                ComparisonOperators.LIKE);

        StudentRecord record = new StudentRecord("0000000001", "Bostest123", "Ivica", 2);
        StudentRecord record2 = new StudentRecord("0000000001", "neBostest123", "Ivica", 2);

        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record), // returns lastName from given record
                expr.getStringLiteral() // returns "Bos*"
        );

        assertTrue(recordSatisfies);

        boolean recordSatisfies2 = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record2), // returns lastName from given record
                expr.getStringLiteral() // returns "Bos*"
        );

        assertFalse(recordSatisfies2);
    }
}
