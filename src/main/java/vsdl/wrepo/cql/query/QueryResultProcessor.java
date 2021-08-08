package vsdl.wrepo.cql.query;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import vsdl.datavector.crypto.CryptoUtilities;
import vsdl.wl.wom.BoolWOM;
import vsdl.wl.wom.WickedObjectModel;

import java.util.ArrayList;
import java.util.List;

import static vsdl.wrepo.cql.query.Database.*;

public class QueryResultProcessor {

    public List<WickedObjectModel> processResults(QueryType queryType, List<Row> allResults, Object... args) {
        int numberOfResults = allResults.size();
        ArrayList<WickedObjectModel> woms = new ArrayList<>();
        switch (queryType) {
            case LOGON_CREATE_USER:
                //todo
                break;
            case LOGON_USER_AUTH:
                onlyOne(numberOfResults);
                evaluateUserPasswordMatches(allResults.get(0), (String)args[1], woms);
                break;
            case LOGON_USER_EXISTS:
                onlyOne(numberOfResults);
                evaluateUserExists(numberOfResults, woms);
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

    private void evaluateUserExists(int numberOfResults, ArrayList<WickedObjectModel> woms) {
        woms.add(new BoolWOM(numberOfResults == 1));
    }

    private void evaluateUserPasswordMatches(Row row, String unHashedPassword, ArrayList<WickedObjectModel> woms) {
        String salt = row.getString(COL_USER_LOGON_SALT);
        String hashedPass = row.getString(COL_USER_LOGON_HASHED_PASSWORD);
        woms.add(new BoolWOM(hashedPass.equals(CryptoUtilities.hash(CryptoUtilities.salt(unHashedPassword, salt)))));
    }
}
