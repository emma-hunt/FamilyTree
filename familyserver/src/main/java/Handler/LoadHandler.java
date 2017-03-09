package Handler;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import EncoderDecoder.Coder;
import EncoderDecoder.LoadData;
import RequestResult.ErrorResult;
import RequestResult.LoadRequest;
import RequestResult.LoadResult;
import Service.LoadService;

/**
 * Created by emmag on 2/10/2017.
 * class to handle the /load call
 */

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStream reqBody = exchange.getRequestBody();
            LoadRequest request = (LoadRequest) Coder.decode(new LoadRequest(), reqBody);
            //LoadData data = (LoadData) Coder.decode(new LoadData(), reqBody);
            //LoadRequest request = new LoadRequest(data);

            LoadService service = new LoadService();
            LoadResult result = service.load(request);
            String respData = null;
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            respData = Coder.encode(result,respData);

//            if(result == null) {
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                ErrorResult error = new ErrorResult("Invalid load information. Please Try Again.");
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
