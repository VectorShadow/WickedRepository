package vsdl.wrepo.cql.query;

import vsdl.datavector.crypto.CryptoUtilities;

import static vsdl.wrepo.cql.query.Constants.*;
import static vsdl.wrepo.cql.query.Database.*;

public class QueryLibrary {

    private static final String REPL = "replication";
    private static final String CLASS = "'class':";
    private static final String REPL_FACTOR = "'replication_factor':";

    private static final int RANDOM_SALT_LENGTH = 16;

    public String buildQuery(QueryType queryType, Object... args) {
        switch (queryType) {
            case CREATE_KEYSPACE:
                return createKeyspace(
                        (String) args[0],
                        (String) args[1],
                        (int) args[2]
                );
            case CREATE_TABLE:
                return createTable(
                        (String) args[0],
                        (String) args[1],
                        (int) args[2],
                        (ColumnInfo[]) args[3]
                );
            case LOGIN_AUTH:
                //todo
                break;
            case LOGIN_CREATE:
                return createNewUser((String) args[0], (String) args[1]);
            case LOGIN_EXISTS:
                return findUserIfExists((String) args[0]);
        }
        throw new IllegalArgumentException("Unhandled QueryType: " + queryType);
    }

    private String createKeyspace(
            String keySpaceName,
            String replicationStrategy,
            int replicationFactor
    ) {
        return CREATE_KEYSPACE + SPC + keySpaceName + SPC +
                WITH + SPC + REPL + SPC + EQUALS + SPC +
                L_BRACE + CLASS + wrapString(replicationStrategy) + COMMA +
                REPL_FACTOR + replicationFactor + R_BRACE + SEMI;
    }

    private String createTable(
            String keySpaceName,
            String tableName,
            int primaryKeyIndex,
            ColumnInfo... columnInfos
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append(CREATE_TABLE).append(SPC).append(getFullTableName(keySpaceName, tableName)).append(L_PAREN);
        for (int i = 0; i < columnInfos.length; ++i) {
            ColumnInfo ci = columnInfos[i];
            if (i > 0) {
                sb.append(COMMA);
            }
            sb.append(ci.getName()).append(SPC).append(ci.getDataType());
            if (i == primaryKeyIndex) {
                sb.append(SPC).append(PRIMARY_KEY);
            }
        }
        sb.append(R_PAREN).append(SEMI);
        return sb.toString();
    }

    private String findUserIfExists(String userName) {
        return selectFrom(KS_USER, CF_USER_LOGON) + SPC +
                WHERE + SPC + COL_USER_LOGON_USERNAME + SPC + EQUALS + SPC + wrapString(userName) + SEMI;
    }

    private String createNewUser(String userName, String decryptedPassword) {
        String salt = CryptoUtilities.randomAlphaNumericString(RANDOM_SALT_LENGTH);
        return insertInto(KS_USER, CF_USER_LOGON, new String[]{
                COL_USER_LOGON_USERNAME,
                COL_USER_LOGON_SALT,
                COL_USER_LOGON_HASHED_PASSWORD
        }, new String[]{
                wrapString(userName),
                wrapString(salt),
                wrapString(CryptoUtilities.hash(CryptoUtilities.salt(decryptedPassword, salt)))
        });
    }

}
