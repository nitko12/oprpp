package hr.fer.oprpp1.hw04.db;

import java.util.List;

public class QueryFilter implements IFilter {

    private List<ConditionalExpression> expressions;

    public QueryFilter(List<ConditionalExpression> expressions) {
        this.expressions = expressions;
    }

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
