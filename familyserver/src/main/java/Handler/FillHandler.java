package Handler;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import EncoderDecoder.Coder;
import RequestResult.ErrorResult;
import RequestResult.FillRequest;
import RequestResult.FillResult;
import Service.FillService;

/**
 * Created by emmag on 2/10/2017.
 * class to handle the /fill/[username/{generations} call
 */

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Headers reqHeaders = exchange.getRequestHeaders();
            String query = exchange.getRequestURI().toString();
            String[] sections = query.split("/");
            if (sections.length >= 3) {
                String user = sections[2];
                int gens = 4;
                if (sections.length == 4) {
                    gens = Integer.parseInt(sections[3]);
                }
                FillRequest request = new FillRequest(user, gens);
                //InputStream reqBody = exchange.getRequestBody();
                //FillRequest request = (LoginRequest) Coder.decode(new LoginRequest(), reqBody);
                FillService service = new FillService();
                FillResult result = service.fill(request);
                String respData = null;
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                respData = Coder.encode(result, respData);

//                if (result == null) {
//                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                    ErrorResult error = new ErrorResult("Invalid fill request. Please Try Again.");
//                    respData = Coder.encode(error, respData);
//                } else {
//                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                    respData = Coder.encode(result, respData);
//                }
                //System.out.println("printing resp body");
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
                //}
            }
            else {
                String respData = null;
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                ErrorResult error = new ErrorResult("Invalid fill request. Please Try Again.");
                respData = Coder.encode(error, respData);
            }
        } catch (IOException e) {
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
