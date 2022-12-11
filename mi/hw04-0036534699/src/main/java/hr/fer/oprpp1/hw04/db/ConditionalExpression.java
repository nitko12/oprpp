package hr.fer.oprpp1.hw04.db;

/**
 * Conditional expression class.
 */
public class ConditionalExpression {

    private IFieldValueGetter fieldGetter;
    private String value;
    private IComparisonOperator comparisonOperator;

    /**
     * Constructor for conditional expression.
     * 
     * @param fieldGetter        field getter
     * @param value              value
     * @param comparisonOperator comparison operator
     */
    public ConditionalExpression(IFieldValueGetter fieldGetter,
            String value,
            IComparisonOperator comparisonOperator) {
        this.fieldGetter = fieldGetter;
        this.value = value;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * Method that checks if the record satisfies the expression given in
     * constructor.
     * 
     * @param record
     * @return boolean
     */
    public boolean satisfied(StudentRecord record) {
        return comparisonOperator.satisfied(fieldGetter.get(record), value);
    }

    /**
     * Getter for field getter.
     * 
     * @return IComparisonOperator
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    /**
     * Getter for field getter.
     * 
     * @return IFieldValueGetter
     */
    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    /**
     * Getter for StringLiteral.
     * 
     * @return String
     */
    public String getStringLiteral() {
        return value;
    }
}
