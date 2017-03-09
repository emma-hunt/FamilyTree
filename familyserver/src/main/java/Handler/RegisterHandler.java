package Handler;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import EncoderDecoder.Coder;
import RequestResult.ErrorResult;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;
import Service.RegisterService;

/**
 * Created by emmag on 2/10/2017.
 * class to handle the /user/register call
 */

public class RegisterHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStream reqBody = exchange.getRequestBody();
            RegisterRequest request = (RegisterRequest) Coder.decode(new RegisterRequest(), reqBody);
            RegisterService service = new RegisterService();
            RegisterResult result = service.register(request);
            String respData = null;
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            respData = Coder.encode(result,respData);

//            if(result == null) {
//
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                //exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                ErrorResult error = new ErrorResult("Invalid register information. Please Try Again.");
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

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            //exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
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
