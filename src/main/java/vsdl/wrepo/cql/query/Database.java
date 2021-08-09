package vsdl.wrepo.cql.query;

import com.datastax.driver.core.Session;

public class Database {

    public static final String ROOT_USR = "root";

    static final String DT_TEXT = "text";

    static final String KS_USER = "USR";

    static final String CF_USER_LOGON = "LOGON";

    static final String COL_USER_LOGON_USERNAME = "USRNM";
    static final ColumnInfo COL_USER_LOGON_USERNAME_INFO =
            new ColumnInfo(COL_USER_LOGON_USERNAME, DT_TEXT);
    static final String COL_USER_LOGON_SALT = "SLT";
    static final ColumnInfo COL_USER_LOGON_SALT_INFO =
            new ColumnInfo(COL_USER_LOGON_SALT, DT_TEXT);
    static final String COL_USER_LOGON_HASHED_PASSWORD = "HSHDPWD";
    static final ColumnInfo COL_USER_LOGON_HASHED_PASSWORD_INFO =
            new ColumnInfo(COL_USER_LOGON_HASHED_PASSWORD, DT_TEXT);

    static final String SIMPLE_REPLICATION_STRATEGY = "SimpleStrategy";
    static final int ONE = 1;

    static final Object[] CREATE_KEYSPACE_USER = new Object[] {
            KS_USER,
            SIMPLE_REPLICATION_STRATEGY,
            ONE
    };

    static final Object[] CREATE_TABLE_USER_LOGON = new Object[] {
            KS_USER,
            CF_USER_LOGON,
            0,
            new ColumnInfo[] {
                    COL_USER_LOGON_USERNAME_INFO,
                    COL_USER_LOGON_SALT_INFO,
                    COL_USER_LOGON_HASHED_PASSWORD_INFO
            }
    };

    public static void create(Session session, QueryLibrary queryLibrary) {
        //keyspace user:
        session.execute(queryLibrary.buildQuery(QueryType.CREATE_KEYSPACE, CREATE_KEYSPACE_USER));
        //column family logon:
        session.execute(queryLibrary.buildQuery(QueryType.CREATE_TABLE, CREATE_TABLE_USER_LOGON));
        //todo
    }
}
