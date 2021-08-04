package vsdl.wrepo.cql;

import static vsdl.wrepo.cql.Constants.*;

public class CassandraUtilities {

    private static final String REPL = "replication";
    private static final String CLASS = "'class':";
    private static final String REPL_FACTOR = "'replication_factor';";

    private static String getFullTableName(String keySpaceName, String tableName) {
        return keySpaceName + DOT + tableName;
    }

    public static String createKeyspace(
            String keySpaceName,
            String replicationStrategy,
            int replicationFactor
    ) {
        return CREATE_KEYSPACE + SPACE + keySpaceName + SPACE +
                WITH + SPACE + REPL + SPACE + EQUALS + SPACE +
                L_BRACE + CLASS + replicationStrategy + COMMA +
                REPL_FACTOR + replicationFactor + R_BRACE + SEMI;
    }

    public static String createTable(
            String keySpaceName,
            String tableName,
            int primaryKeyIndex,
            ColumnInfo... columnInfos
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append(CREATE_TABLE).append(SPACE).append(getFullTableName(keySpaceName, tableName)).append(L_PAREN);
        for (int i = 0; i < columnInfos.length; ++i) {
            ColumnInfo ci = columnInfos[i];
            if (i > 0) {
                sb.append(COMMA);
            }
            sb.append(ci.getName()).append(SPACE).append(ci.getDataType());
            if (i == primaryKeyIndex) {
                sb.append(SPACE).append(PRIMARY_KEY);
            }
        }
        sb.append(R_PAREN).append(SEMI);
        return sb.toString();
    }
}
