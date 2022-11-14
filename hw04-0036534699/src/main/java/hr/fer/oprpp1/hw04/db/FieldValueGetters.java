package hr.fer.oprpp1.hw04.db;

public class FieldValueGetters {
    private static class firstNameGetter implements IFieldValueGetter {
        @Override
        public String get(StudentRecord record) {
            return record.getFirstName();
        }
    }

    private static class lastNameGetter implements IFieldValueGetter {
        @Override
        public String get(StudentRecord record) {
            return record.getLastName();
        }
    }

    private static class jmbagGetter implements IFieldValueGetter {
        @Override
        public String get(StudentRecord record) {
            return record.getJmbag();
        }
    }

    public static final IFieldValueGetter FIRST_NAME = new firstNameGetter();
    public static final IFieldValueGetter LAST_NAME = new lastNameGetter();
    public static final IFieldValueGetter JMBAG = new jmbagGetter();
}
