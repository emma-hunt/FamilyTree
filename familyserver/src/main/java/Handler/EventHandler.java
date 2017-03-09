package Handler;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import EncoderDecoder.Coder;
import RequestResult.ErrorResult;
import RequestResult.EventFamilyResult;
import RequestResult.EventRequest;
import RequestResult.EventSingleResult;
import Service.EventService;
import Service.PersonService;

/**
 * Created by emmag on 2/10/2017.
 * class to handle the /event/[eventid] call
 */

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Headers reqHeaders = exchange.getRequestHeaders();
            String query = exchange.getRequestURI().toString();
            String[] sections = query.split("/");
            String eventID = null;
            if (sections.length >= 3) {
                eventID = sections[2];
            }

            if(reqHeaders.containsKey("Authorization")){
                String authCode = reqHeaders.getFirst("Authorization");
//                String query = exchange.getRequestURI().toString();
//                String[] sections = query.split("/");
//                String eventID = null;
//                if (sections.length >= 3) {
//                    eventID = sections[2];
//                }
                String respData = null;
                EventRequest request = new EventRequest(authCode, eventID);
                EventService service = new EventService();
                if(eventID != null) {
                    EventSingleResult result = service.single(request);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    respData = Coder.encode(result, respData);

//                    if (result == null) {
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                        ErrorResult error = new ErrorResult("Invalid event request. Please Try Again.");
//                        respData = Coder.encode(error, respData);
//                    } else {
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                        respData = Coder.encode(result, respData);
//                    }

                }
                else {
                    EventFamilyResult result = service.family(request);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    respData = Coder.encode(result, respData);

                }
                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();

            }
            else {
                if(eventID == null) {
                    String respData = null;
                    EventFamilyResult result = new EventFamilyResult("Invalid authentication. Please login again.");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    respData = Coder.encode(result, respData);
                }
                else {
                    String respData = null;
                    EventSingleResult result = new EventSingleResult("Invalid authentication. Please login again.");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    respData = Coder.encode(result, respData);
                }
//                String respData = null;
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                ErrorResult error = new ErrorResult("Invalid authorization. Please login.");
//                respData = Coder.encode(error, respData);
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
