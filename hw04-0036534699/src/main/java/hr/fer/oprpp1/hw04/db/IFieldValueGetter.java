package hr.fer.oprpp1.hw04.db;

/*
 * Interface for getting the value of a field from a StudentRecord.
 */
public interface IFieldValueGetter {
    /*
     * Gets the value of a field from a StudentRecord.
     */
    public abstract String get(StudentRecord record);
}
