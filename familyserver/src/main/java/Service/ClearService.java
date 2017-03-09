package Service;

import DataAccess.DatabaseDao;
import RequestResult.ClearResult;

/**
 * Created by emmag on 2/14/2017.
 * Class to service the clear call
 */

public class ClearService {
    /**
     * clears all data from the database
     * @return clear result
     */
    public ClearResult clear(){
        ClearResult result = null;
        DatabaseDao db = new DatabaseDao();
        boolean success = db.clearDatabase();
        db.closeConnection(success);
        if(success) {
            result = new ClearResult();
            result.setMessage("Clear succeeded.");
        }
        else {
            result = new ClearResult();
            result.setMessage("Error: Clear failed.");
        }
        //db.closeConnection(success);
        return result;
    }
}
