package hr.fer.oprpp1.hw04.db;

/**
 * Interface for getting a field value of StudentRecord.
 */
public class FieldValueGetters {
    /**
     * Getter for first name.
     */
    private static class firstNameGetter implements IFieldValueGetter {
        @Override
        public String get(StudentRecord record) {
            return record.getFirstName();
        }
    }

    /**
     * Getter for last name.
     */
    private static class lastNameGetter implements IFieldValueGetter {
        @Override
        public String get(StudentRecord record) {
            return record.getLastName();
        }
    }

    /**
     * Getter for jmbag.
     */
    private static class jmbagGetter implements IFieldValueGetter {
        @Override
        public String get(StudentRecord record) {
            return record.getJmbag();
        }
    }

    /**
     * Static instances of getters.
     * 
     */
    public static final IFieldValueGetter FIRST_NAME = new firstNameGetter();
    public static final IFieldValueGetter LAST_NAME = new lastNameGetter();
    public static final IFieldValueGetter JMBAG = new jmbagGetter();
}
