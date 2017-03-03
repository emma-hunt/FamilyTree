package Handler;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by emmag on 2/10/2017.
 * class to handle the /user/register call
 */

public class RegisterHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //get request
        //convert with decoder
        //process request
        //convert with encoder
        //send response

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
        OutputStream os = httpExchange.getResponseBody();
        //os.write();//array of bites
    }
}
