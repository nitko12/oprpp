package hr.fer.oprpp1.hw04.db;

/**
 * Interface for comparing two strings.
 */
public interface IComparisonOperator {
    /*
     * Checks if the first string satisfies the condition with the second string.
     */
    public abstract boolean satisfied(String value1, String value2);
}
