package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class for filtering a list of StudentRecords.
 * 
 */
public class QueryFilter implements IFilter {

    private List<ConditionalExpression> expressions;

    /**
     * Creates a new QueryFilter.
     * 
     * @param expressions list of conditional expressions
     */
    public QueryFilter(List<ConditionalExpression> expressions) {
        this.expressions = expressions;
    }

    /**
     * Checks if the StudentRecord satisfies all the conditions.
     * 
     * @param record StudentRecord
     * @return boolean
     */
    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expression : expressions) {
            if (!expression.satisfied(record)) {
                return false;
            }
        }
        return true;
    }
}
