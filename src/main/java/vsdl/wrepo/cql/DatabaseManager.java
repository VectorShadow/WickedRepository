package vsdl.wrepo.cql;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import vsdl.wl.wom.WickedObjectModel;
import vsdl.wrepo.connector.CassandraConnector;
import vsdl.wrepo.cql.query.Database;
import vsdl.wrepo.cql.query.QueryLibrary;
import vsdl.wrepo.cql.query.QueryResultProcessor;
import vsdl.wrepo.cql.query.QueryType;

import java.util.List;

public class DatabaseManager {
    private static final String NODE = "0.0.0.0";
    private static final int PORT = 9042;

    private final CassandraConnector cassandraConnector = new CassandraConnector();

    private final QueryLibrary queryLibrary = new QueryLibrary();
    private final QueryResultProcessor queryResultProcessor = new QueryResultProcessor();

    private Session getSession() {
        return cassandraConnector.getSession();
    }

    public void startup() {
        cassandraConnector.connect(NODE, PORT);
        Database.create(getSession(), queryLibrary);
        Updater.update(getSession());
    }

    public void shutdown() {
        cassandraConnector.close();
        //todo - anything else here?
    }

    public List<WickedObjectModel> query(QueryType type, Object... args) {
        String queryString = queryLibrary.buildQuery(type, args);
        System.out.println("Executing query: " + queryString);
        return queryResultProcessor.processResults(
                type,
                executeQueryString(queryString).all(),
                args
        );
    }

    private ResultSet executeQueryString(String query) {
        return getSession().execute(query);
    }
}
