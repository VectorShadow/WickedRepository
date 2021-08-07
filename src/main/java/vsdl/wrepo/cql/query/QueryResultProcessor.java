package vsdl.wrepo.cql.query;

import com.datastax.driver.core.ResultSet;
import vsdl.wl.wom.BoolWOM;
import vsdl.wl.wom.WickedObjectModel;

import java.util.ArrayList;
import java.util.List;

public class QueryResultProcessor {

    public List<WickedObjectModel> processResults(QueryType queryType, ResultSet resultSet) {
        int numberOfResults = resultSet.all().size();
        ArrayList<WickedObjectModel> woms = new ArrayList<>();
        switch (queryType) {
            case LOGIN_EXISTS:
                evaluateLoginExists(numberOfResults, woms);
                break;
            default:
                throw new IllegalArgumentException("Unhandled QueryType: " + queryType);
        }
        return woms;
    }

    private void evaluateLoginExists(int numberOfResults, ArrayList<WickedObjectModel> woms) {
        if (numberOfResults > 1)
            throw new IllegalStateException("Duplicate username.");
        woms.add(new BoolWOM(numberOfResults == 1));
    }
}
