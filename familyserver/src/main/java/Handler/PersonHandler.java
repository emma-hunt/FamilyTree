package Handler;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import EncoderDecoder.Coder;
import RequestResult.ErrorResult;
import RequestResult.PersonFamilyResult;
import RequestResult.PersonRequest;
import RequestResult.PersonSingleResult;
import Service.PersonService;

/**
 * Created by emmag on 2/10/2017.
 * class to handle the /person/[personid] call
 */

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Headers reqHeaders = exchange.getRequestHeaders();
            String query = exchange.getRequestURI().toString();
            String[] sections = query.split("/");
            String personID = null;
            if (sections.length >= 3) {
                personID = sections[2];
            }

            if(reqHeaders.containsKey("Authorization")){
                String authCode = reqHeaders.getFirst("Authorization");
//                String query = exchange.getRequestURI().toString();
//                String[] sections = query.split("/");
//                String personID = null;
//                if (sections.length >= 3) {
//                    personID = sections[2];
//                }
                String respData = null;
                PersonRequest request = new PersonRequest(authCode, personID);
                PersonService service = new PersonService();
                if(personID != null) {
                    PersonSingleResult result = service.single(request);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    respData = Coder.encode(result, respData);

//                    if (result == null) {
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                        ErrorResult error = new ErrorResult("Invalid person request. Please Try Again.");
//                        respData = Coder.encode(error, respData);
//                    } else {
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                        respData = Coder.encode(result, respData);
//                    }

                }
                else {
                    PersonFamilyResult result = service.family(request);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    respData = Coder.encode(result, respData);

//                    if (result == null) {
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                        ErrorResult error = new ErrorResult("Invalid person request. Please Try Again.");
//                        respData = Coder.encode(error, respData);
//                    } else {
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//                        respData = Coder.encode(result, respData);
//                    }

                }


                //PersonResult result = service.fill(request);

                OutputStream respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();

            }
            else {
                if(personID == null) {
                    PersonFamilyResult result = new PersonFamilyResult("Invalid authentication. Please login again.");
                    String respData = null;
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    respData = Coder.encode(result, respData);
                }
                else {
                    PersonSingleResult result = new PersonSingleResult("Invalid authentication. Please login again.");
                    String respData = null;
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    respData = Coder.encode(result, respData);

                }

//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                ErrorResult error = new ErrorResult("Invalid authorization. Please login again.");
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
