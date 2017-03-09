package Handler;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import EncoderDecoder.Coder;
import RequestResult.ClearResult;
import RequestResult.ErrorResult;
import Service.ClearService;

/**
 * Created by emmag on 2/10/2017.
 * Class to handle the /clear call
 */

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            ClearService service = new ClearService();
            ClearResult result = service.clear();
            String respData = null;
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            respData = Coder.encode(result,respData);
//            if(result == null) {
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                ErrorResult error = new ErrorResult("Bad Request. Please Try Again.");
//                respData = Coder.encode(error, respData);
//            }
//            else {
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                respData = Coder.encode(result,respData);
//            }
            OutputStream respBody = exchange.getResponseBody();
            writeString(respData, respBody);
            respBody.close();
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            e.printStackTrace();
            exchange.getResponseBody().close();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
