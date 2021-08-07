package vsdl.wrepo.cql.query;

public class Constants {
    static final String CREATE_KEYSPACE = "CREATE KEYSPACE IF NOT EXISTS";
    static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS";

    static final String FROM = "FROM";
    static final String INSERT = "INSERT";
    static final String INTO = "INTO";
    static final String SELECT = "SELECT";
    static final String VALUES = "VALUES";
    static final String WITH = "WITH";
    static final String WHERE = "WHERE";

    static final String PRIMARY_KEY = "PRIMARY KEY";

    static final String STAR = "*";
    static final String DOT = ".";
    static final String COMMA = ",";
    static final String SEMI = ";";
    static final String EQUALS = "=";
    static final String L_BRACE = "{";
    static final String R_BRACE = "}";
    static final String L_PAREN = "(";
    static final String R_PAREN = ")";
    static final String SPC = " ";
    static final String S_QUOT = "'";


    static String getFullTableName(String keyspaceName, String tableName) {
        return keyspaceName + DOT + tableName;
    }

    static String insertInto(String keyspaceName, String tableName, String[] columnNames, String[] values) {
        int numberOfArgs = columnNames.length;
        if (values.length != numberOfArgs) {
            throw new IllegalArgumentException("Number of values does not match number of columns.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(INSERT).append(SPC).append(INTO).append(SPC)
                .append(getFullTableName(keyspaceName, tableName)).append(L_PAREN);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(VALUES).append(L_PAREN);
        for (int i = 0; i < numberOfArgs; ++i) {
            if (i > 0) {
                sb.append(COMMA).append(SPC);
                sb2.append(COMMA).append(SPC);
            }
            sb.append(columnNames[i]);
            sb2.append(values[i]);
        }
        sb.append(R_PAREN);
        sb2.append(R_PAREN);
        sb.append(SPC).append(sb2).append(SEMI);
        return sb.toString();
    }

    static String selectFrom(String keyspaceName, String tableName) {
        return selectFrom(keyspaceName, tableName, new String[]{});
    }
    static String selectFrom(String keyspaceName, String tableName, String... selections) {
        StringBuilder sb = new StringBuilder();
        sb.append(SELECT).append(SPC);
        int numberOfSelections = selections.length;
        if (numberOfSelections > 0) {
            for (int i = 0; i < numberOfSelections; ++i) {
                if (i > 0) sb.append(COMMA).append(SPC);
                sb.append(selections[i]);
            }
        } else {
            sb.append(STAR);
        }
        sb.append(SPC).append(FROM).append(SPC).append(getFullTableName(keyspaceName, tableName));
        return sb.toString();
    }

    static String wrapString(String s) {
        return S_QUOT + s + S_QUOT;
    }
}
