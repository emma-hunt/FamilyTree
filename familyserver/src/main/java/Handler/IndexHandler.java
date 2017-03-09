package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.nio.file.*;

import javax.xml.ws.http.HTTPBinding;

/**
 * Created by emmag on 2/27/2017.
 */

//localhost:/port#
public class IndexHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if(httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                //httpExchange.getResponseHeaders().add("Content-Type","text/html");
                //httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);

                String filePathStr;// = "./ResourceFiles/HTML";
                //String requesturi = httpExchange.getRequestURI().toString();
                //String[] parse = requesturi.split("/");
                //split along
                //filePathStr = filePathStr + parse[1];

                if(httpExchange.getRequestURI().toString().contains("css")){

                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    filePathStr = "./ResourceFiles/main.css";
                }
                else {

                    httpExchange.getResponseHeaders().add("Content-Type","text/html");
                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                    filePathStr = "./ResourceFiles/index.html";
                }
                Path filePath = FileSystems.getDefault().getPath(filePathStr);
                Files.copy(filePath, httpExchange.getResponseBody());
                OutputStream respBody = httpExchange.getResponseBody();
                respBody.close();
            }
            else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                httpExchange.getResponseBody().close();
            }
        }catch (IOException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            e.printStackTrace();
            httpExchange.getResponseBody().close();
        }
    }
}
