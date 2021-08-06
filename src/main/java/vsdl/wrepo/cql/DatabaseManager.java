package vsdl.wrepo.cql;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import vsdl.wrepo.connector.CassandraConnector;

public class DatabaseManager {
    private static final String NODE = "0.0.0.0";
    private static final int PORT = 9042;

    private final CassandraConnector cassandraConnector = new CassandraConnector();

    private Session getSession() {
        return cassandraConnector.getSession();
    }

    public void startup() {
        cassandraConnector.connect(NODE, PORT);
        query(Data.CREATE_KEYSPACE_USER);
        query(Data.CREATE_TABLE_USER_LOGON);

    }

    public void shutdown() {
        cassandraConnector.close();
    }

    public ResultSet query(String query) {
        return getSession().execute(query);
    }
}
