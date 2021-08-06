package vsdl.wrepo.cql;

import vsdl.wrepo.cql.CassandraUtilities;
import vsdl.wrepo.cql.ColumnInfo;

public class Data {

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

    static final String CREATE_KEYSPACE_USER =
            CassandraUtilities
                    .createKeyspace(
                            KS_USER,
                            SIMPLE_REPLICATION_STRATEGY,
                            ONE
                    );

    static final String CREATE_TABLE_USER_LOGON =
            CassandraUtilities
                    .createTable(
                            KS_USER,
                            CF_USER_LOGON,
                            0,
                            COL_USER_LOGON_USERNAME_INFO,
                            COL_USER_LOGON_SALT_INFO,
                            COL_USER_LOGON_HASHED_PASSWORD_INFO
                    );
}
