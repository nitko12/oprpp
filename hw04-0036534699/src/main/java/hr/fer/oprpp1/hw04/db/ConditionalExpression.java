package hr.fer.oprpp1.hw04.db;

public class ConditionalExpression {

    private IFieldValueGetter fieldGetter;
    private String value;
    private IComparisonOperator comparisonOperator;

    public ConditionalExpression(IFieldValueGetter fieldGetter,
            String value,
            IComparisonOperator comparisonOperator) {
        this.fieldGetter = fieldGetter;
        this.value = value;
        this.comparisonOperator = comparisonOperator;
    }

    public boolean satisfied(StudentRecord record) {
        return comparisonOperator.satisfied(fieldGetter.get(record), value);
    }

    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    public String getStringLiteral() {
        return value;
    }
}
