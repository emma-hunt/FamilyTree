package Server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import DataAccess.DatabaseDao;
import Handler.ClearHandler;
import Handler.EventHandler;
import Handler.FillHandler;
import Handler.IndexHandler;
import Handler.LoadHandler;
import Handler.LoginHandler;
import Handler.PersonHandler;
import Handler.RegisterHandler;

/**
 * Created by emmag on 2/14/2017.
 * Super Server class that connects through the web to the client, handles actual calls on the Server as a whole
 * contains main function
 */

public class Server {
    private HttpServer server;
    /**
     * main function for server
     */
    public static void main(String args[]) {
        //call databaseDao.initalize()
        // new Server.run();

        new Server().run();

    }
    private void run() {
        //this is where you create the server
        DatabaseDao.initialize();

        try {
            server = HttpServer.create(new InetSocketAddress(8080),10);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/", new IndexHandler()); // or TestHandler
        server.start();
    }
}
