package vsdl.wrepo.manager;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import vsdl.wrepo.connector.CassandraConnector;

public abstract class AbstractDatabaseManager {

    private final CassandraConnector cassandraConnector = new CassandraConnector();

    private Session getSession() {
        return cassandraConnector.getSession();
    }

    protected abstract String getNode();

    protected abstract int getPort();

    public void startup() {
        cassandraConnector.connect(getNode(), getPort());
    }

    public void shutdown() {
        cassandraConnector.close();
    }

    public ResultSet query(String query) {
        return getSession().execute(query);
    }
}
