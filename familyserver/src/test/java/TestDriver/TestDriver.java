package TestDriver;



/**
 * Created by emmag on 3/8/2017.
 */

public class TestDriver {
    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main(
//                "Model.AuthTokenTest",
                "DataAccess.DatabaseDaoTest",
                "DataAccess.AuthTokenDaoTest",
                "DataAccess.UserDaoTest",
                "DataAccess.PersonDaoTest",
                "DataAccess.EventDaoTest",
                "ServerProxy.ServerProxyTest"
        );

    }
}
