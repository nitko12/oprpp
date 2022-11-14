package hr.fer.oprpp1.hw04.db;

import java.util.List;

public class ComparisonOperators {

    private static class Less implements IComparisonOperator {
        @Override
        public boolean satisfied(String value1, String value2) {
            return value1.compareTo(value2) < 0;
        }
    };

    private static class LessOrEquals implements IComparisonOperator {
        @Override
        public boolean satisfied(String value1, String value2) {
            return value1.compareTo(value2) <= 0;
        }
    };

    private static class Greater implements IComparisonOperator {
        @Override
        public boolean satisfied(String value1, String value2) {
            return value1.compareTo(value2) > 0;
        }
    };

    private static class GreaterOrEquals implements IComparisonOperator {
        @Override
        public boolean satisfied(String value1, String value2) {
            return value1.compareTo(value2) >= 0;
        }
    };

    private static class Equals implements IComparisonOperator {
        @Override
        public boolean satisfied(String value1, String value2) {
            return value1.compareTo(value2) == 0;
        }
    };

    private static class NotEquals implements IComparisonOperator {
        @Override
        public boolean satisfied(String value1, String value2) {
            return value1.compareTo(value2) != 0;
        }
    };

    private static int countOccurance(String value, String substring) {
        int count = 0;

        int index = value.indexOf(substring);
        while (index != -1) {
            count++;
            index = value.indexOf(substring, index + 1);
        }

        return count;
    }

    private static class Like implements IComparisonOperator {
        @Override
        public boolean satisfied(String value1, String value2) {
            if (value1 == null || value2 == null) {
                throw new NullPointerException();
            }

            if (countOccurance(value2, "*") > 1) {
                throw new IllegalArgumentException("Najviše jedan * je dozvoljen.");
            }

            if (value2 == "*")
                return true;

            if (value2.startsWith("*"))
                return value1.endsWith(value2.substring(1));

            if (value2.endsWith("*"))
                return value1.startsWith(value2.substring(0, value2.length() - 1));

            int index = value2.indexOf("*");
            if (index == -1)
                return value1.equals(value2);

            String substring = value2.substring(0, index);
            if (!value1.startsWith(substring))
                return false;

            String substring2 = value2.substring(index + 1);

            if (!value1.endsWith(substring2))
                return false;

            return substring.length() + substring2.length() <= value1.length();

        }
    };

    public static final IComparisonOperator LESS = new Less();
    public static final IComparisonOperator LESS_OR_EQUALS = new LessOrEquals();
    public static final IComparisonOperator GREATER = new Greater();
    public static final IComparisonOperator GREATER_OR_EQUALS = new GreaterOrEquals();
    public static final IComparisonOperator EQUALS = new Equals();
    public static final IComparisonOperator NOT_EQUALS = new NotEquals();
    public static final IComparisonOperator LIKE = new Like();
}
