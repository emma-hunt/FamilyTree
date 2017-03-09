package EncoderDecoder;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import RequestResult.LoginRequest;

/**
 * Created by emmag on 2/10/2017.
 * Class for the encoding and decoding of Java and JSON objects
 */

public class Coder {

    /**
     * decodes JSON into the given object
     * @param o object to decode JSON into
     * @param j object containing JSON
     */
    public static Object decode(Object o, InputStream j) {
        Reader reader = new InputStreamReader(j);
        o = new Gson().fromJson(reader, o.getClass());
        return o;
        //Reader reader = new InputStreamReader(httpExch.getRequestBody());
        //LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);

    }

    /**
     * encodes given object into JSON
     * @param o object to encode into JSON
     * @param j object to hold JSON
     */
    public static String encode(Object o, String j) {
        //LoginRequest loginRequest = ...
        //Writer writer = new OutputStreamWriter(j);
        //System.out.println("now encoding");
        j = new Gson().toJson(o, o.getClass());
        return j;
        //LoginRequest loginRequest = new LoginRequest();
        //Writer write = new OutputStreamWriter(httpConn.getOutputStream());
        //String jsonStr = new Gson().toJson(loginRequest);

    }
}
