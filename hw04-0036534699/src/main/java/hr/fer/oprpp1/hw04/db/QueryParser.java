package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for parsing a query.
 * 
 */
public class QueryParser {

    boolean directQuery;
    ArrayList<ConditionalExpression> queryList = new ArrayList<>();

    /**
     * Creates a new QueryParser.
     * 
     * @param query query
     */
    public QueryParser(String query) {
        if (query == null) {
            throw new IllegalArgumentException("Query mora biti definiran.");
        }

        query = query.trim();

        // Method must return true if query was of of the form jmbag="xxx" (i.e. it must
        // have only one
        // comparison, on attribute jmbag, and operator must be equals). We will call
        // queries of this form direct queries.

        query = query.replace("=", " = ");
        query = query.replace("<", " < ");
        query = query.replace(">", " > ");
        query = query.replace("<=", " <= ");
        query = query.replace(">=", " >= ");
        query = query.replace("!=", " != ");

        String[] splitQuery = query.split("\\s+");

        if (splitQuery.length == 3 &&
                splitQuery[0].equals("jmbag")
                && splitQuery[1].equals("=")
                && splitQuery[2].startsWith("\"")
                && splitQuery[2].endsWith("\"")
                && splitQuery[2].substring(1, splitQuery[2].length() - 1).indexOf("\"") == -1) {

            directQuery = true;

            queryList.add(new ConditionalExpression(
                    FieldValueGetters.JMBAG,
                    splitQuery[2].substring(1, splitQuery[2].length() - 1),
                    ComparisonOperators.EQUALS));

        } else {
            directQuery = false;

            if (splitQuery[splitQuery.length - 1].toLowerCase().equals("and")) {
                throw new IllegalArgumentException("Query ne smije zavrsavati sa AND.");
            }

            for (int i = 0; i < splitQuery.length; i += 4) {
                if (i + 1 >= splitQuery.length || i + 2 >= splitQuery.length) {
                    throw new IllegalArgumentException("Neispravna upitna naredba.");
                }

                if (i + 3 < splitQuery.length && !splitQuery[i + 3].toLowerCase().equals("and")) {
                    throw new IllegalArgumentException("Neispravna upitna naredba.");
                }

                IFieldValueGetter fieldValueGetter = null;

                switch (splitQuery[i].toLowerCase()) {
                    case "jmbag":
                        fieldValueGetter = FieldValueGetters.JMBAG;
                        break;
                    case "lastname":
                        fieldValueGetter = FieldValueGetters.LAST_NAME;
                        break;
                    case "firstname":
                        fieldValueGetter = FieldValueGetters.FIRST_NAME;
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Neispravna upitna naredba, nepoznato polje s lijeve strane.");
                }

                IComparisonOperator comparisonOperator = null;

                switch (splitQuery[i + 1]) {
                    case "=":
                        comparisonOperator = ComparisonOperators.EQUALS;
                        break;
                    case "<":
                        comparisonOperator = ComparisonOperators.LESS;
                        break;
                    case ">":
                        comparisonOperator = ComparisonOperators.GREATER;
                        break;
                    case "<=":
                        comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
                        break;
                    case ">=":
                        comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
                        break;
                    case "!=":
                        comparisonOperator = ComparisonOperators.NOT_EQUALS;
                        break;
                    case "LIKE":
                        comparisonOperator = ComparisonOperators.LIKE;
                        break;
                    default:
                        throw new IllegalArgumentException("Neispravna upitna naredba, nepoznati operator.");
                }

                if (splitQuery[i + 2].startsWith("\"") && splitQuery[i + 2].endsWith("\"")) {

                    if (splitQuery[i + 2].substring(1, splitQuery[i + 2].length() - 1).indexOf("\"") != -1) {
                        throw new IllegalArgumentException(
                                "Neispravna upitna naredba, ne smije biti \" unutar stringa");
                    }

                    queryList.add(new ConditionalExpression(
                            fieldValueGetter,
                            splitQuery[i + 2].substring(1, splitQuery[i + 2].length() - 1),
                            comparisonOperator));
                } else {
                    throw new IllegalArgumentException(
                            "Neispravna upitna naredba, desna strana izraza mora biti string.");
                }

            }
        }
    }

    /**
     * Returns true if query is direct.
     * 
     * @return boolean
     */
    public boolean isDirectQuery() {
        return directQuery;
    }

    /**
     * Returns jmbag if query is direct.
     * 
     * @return String
     */
    public String getQueriedJMBAG() {
        if (!directQuery) {
            throw new IllegalStateException("Query nije direktan.");
        }

        return queryList.get(0).getStringLiteral();
    }

    /**
     * Returns list of conditional expressions.
     * 
     * @return List<ConditionalExpression>
     */
    public List<ConditionalExpression> getQuery() {
        return queryList;
    }
}
