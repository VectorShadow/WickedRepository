package vsdl.wrepo.cql.query;

import com.datastax.driver.core.Session;

public class Database {

    public static final String ROOT_USR = "root";

    static final String DT_TEXT = "text";
    static final String DT_TIMESTAMP = "timestamp";

    static final String DT_BYTE = "tinyint";

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
    static final String COL_USER_LOGON_CREATION_TIMESTAMP = "CRTTMSTMP";
    static final ColumnInfo COL_USER_LOGON_CREATION_TIMESTAMP_INFO =
            new ColumnInfo(COL_USER_LOGON_CREATION_TIMESTAMP, DT_TIMESTAMP);
    static final String COL_USER_LOGON_ACCOUNT_STATUS = "ACTSTTS";
    static final ColumnInfo COL_USER_LOGON_ACCOUNT_STATUS_INFO =
            new ColumnInfo(COL_USER_LOGON_ACCOUNT_STATUS, DT_BYTE);

    static final String CF_USER_CHARS ="CHARS";

    static final String COL_USER_CHARS_USERNAME = "USRNM";
    static final ColumnInfo COL_USER_CHARS_USERNAME_INFO =
            new ColumnInfo(COL_USER_CHARS_USERNAME, DT_TEXT);
    static final String COL_USER_CHARS_CHARNAME = "CHRNM";
    static final ColumnInfo COL_USER_CHARS_CHARNAME_INFO =
            new ColumnInfo(COL_USER_CHARS_CHARNAME, DT_TEXT);
    //todo - find out how to do a combined primary key - this should be from the above fields
    //todo - find out how to do an object data type - we need character sheet here:

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
                    COL_USER_LOGON_HASHED_PASSWORD_INFO,
                    COL_USER_LOGON_CREATION_TIMESTAMP_INFO,
                    COL_USER_LOGON_ACCOUNT_STATUS_INFO
            }
    };

    static final Object[] CREATE_TABLE_USER_CHARS = new Object[] {
            KS_USER,
            CF_USER_CHARS,
            -1, //todo - this needs to be our combined primary key!
            new ColumnInfo[] {
                    COL_USER_CHARS_USERNAME_INFO,
                    COL_USER_CHARS_CHARNAME_INFO,
                    //todo - character sheet here
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
