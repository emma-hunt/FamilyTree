package Handler;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import EncoderDecoder.Coder;
import RequestResult.ErrorResult;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;
import Service.LoginService;

/**
 * Created by emmag on 2/10/2017.
 * class to handle the /user/login call
 */

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            //if (exchange.getRequestMethod().toLowerCase().equals("post")) {
            InputStream reqBody = exchange.getRequestBody();
            //System.out.println("now in login handler");
            LoginRequest request = (LoginRequest)Coder.decode(new LoginRequest(), reqBody);
            LoginService service = new LoginService();
            LoginResult result = service.login(request);
            //System.out.println("back from login service");
            String respData = null;
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            respData = Coder.encode(result,respData);
//            if(result == null) {
//                //System.out.println("null result");
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                //System.out.println("responce sent");
//                ErrorResult error = new ErrorResult("Invalid username or password. Please Try Again.");
//                respData = Coder.encode(error, respData);
//            }
//            else {
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                respData = Coder.encode(result,respData);
//            }
            //System.out.println("printing resp body");
            OutputStream respBody = exchange.getResponseBody();
            writeString(respData, respBody);
            respBody.close();
            //}
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
