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
        //String baseFolder = "HTML" + File.separator; // only use this line in this handler
        //parse the right file
        //instead of returning, write string to internet (found in exchange)

        /*
        find the file
        get the stuff in the file and put it in an appropriate container (not a string)
        add approprate headers
        write the file data to response body
        close it
        if it breaks send empty body and 404 error
         */

        //these things are universal
        try {
            if(httpExchange.getRequestMethod().toLowerCase().equals("get")) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                String filePathStr;
                if(httpExchange.getRequestURI().toString().contains("css")){
                    filePathStr = "C://Users/emmag/AndroidStudioProjects/FamilyMapServer/ResourceFiles/main.css";
                }
                else {
                    filePathStr = "C://Users/emmag/AndroidStudioProjects/FamilyMapServer/ResourceFiles/index.html";
                }
                Path filePath = FileSystems.getDefault().getPath(filePathStr);
                Files.copy(filePath, httpExchange.getResponseBody());
                OutputStream respBody = httpExchange.getResponseBody();
                //writeString(respData, respBody);
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



        //can send bad request, forbidden request, think about your cases
        //now can write to response body
        //OutputStreamWriter os = new OutputStreamWriter(httpExchange.getResponseBody());
        //os.write("");
        }
    }
}
