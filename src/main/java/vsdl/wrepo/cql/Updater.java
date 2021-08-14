package vsdl.wrepo.cql;

import com.datastax.driver.core.Session;

public class Updater {

    //todo - set this to true whenever an update is required
    public static final boolean IS_UPDATE_REQUIRED = false;

    public static final String[] QUERIES = {
            /**
             * Example queries:
             * todo - TYPE: ADD NEW COLUMN:
             *
             *             "ALTER TABLE USR.LOGON ADD CRTTMSTMP timestamp;",
             *             "ALTER TABLE USR.LOGON ADD ACTSTTS tinyint;",
             *
             * todo - TYPE: UPDATE VALUES TO DEFAULT IN NEW COLUMN
             * (note - this should usually be taken care of in host when the null value is loaded, not here):
             * 
             *             "UPDATE USR.LOGON SET CRTTMSTMP = " + System.currentTimeMillis() + " WHERE USRNM = 'root'",
             *             "UPDATE USR.LOGON SET ACTSTTS = 0 WHERE USRNM = 'root'",
             */

    };

    public static Session session;

    public static void update(Session session) {
        if (!IS_UPDATE_REQUIRED) return;
        Updater.session = session;
        for (String query : QUERIES) {
            session.execute(query);
        }
    }
}
