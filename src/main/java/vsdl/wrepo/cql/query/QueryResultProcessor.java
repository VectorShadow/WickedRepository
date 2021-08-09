package vsdl.wrepo.cql.query;

import com.datastax.driver.core.Row;
import vsdl.wl.wom.BoolWOM;
import vsdl.wl.wom.UserLogonWOM;
import vsdl.wl.wom.WickedObjectModel;

import java.util.ArrayList;
import java.util.List;

import static vsdl.wrepo.cql.query.Database.*;

public class QueryResultProcessor {

    public List<WickedObjectModel> processResults(QueryType queryType, List<Row> allResults, Object... args) {
        int numberOfResults = allResults.size();
        Row firstResult = numberOfResults == 0 ? null : allResults.get(0);
        ArrayList<WickedObjectModel> woms = new ArrayList<>();
        switch (queryType) {
            case LOGON_CREATE_USER:
                //todo
                break;
            case LOGON_USER_EXISTS:
                onlyOne(numberOfResults);
                evaluateUserExists(firstResult, woms);
                break;
            default:
                throw new IllegalArgumentException("Unhandled QueryType: " + queryType);
        }
        return woms;
    }

    private void onlyOne(int numberOfResults) {
        if (numberOfResults > 1)
            throw new IllegalStateException("Multiple results returned for unique key.");
    }

    private void evaluateUserExists(Row firstResult, ArrayList<WickedObjectModel> woms) {
        woms.add(
                firstResult == null ? null : new UserLogonWOM(
                        firstResult.getString(COL_USER_LOGON_USERNAME),
                        firstResult.getString(COL_USER_LOGON_SALT),
                        firstResult.getString(COL_USER_LOGON_HASHED_PASSWORD)
                )
        );
    }
}
