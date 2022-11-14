package hr.fer.oprpp1.hw04.db;

/*
 * Interface for testing if a StudentRecord satisfies a condition.
 */
public interface IFilter {
    /*
     * Checks if the StudentRecord satisfies a condition.
     */
    boolean accepts(StudentRecord record);
}
