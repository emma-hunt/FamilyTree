package ServerProxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import EncoderDecoder.Coder;
import RequestResult.ClearResult;
import RequestResult.ErrorResult;
import RequestResult.EventFamilyResult;
import RequestResult.EventRequest;
import RequestResult.EventSingleResult;
import RequestResult.FillRequest;
import RequestResult.FillResult;
import RequestResult.LoadRequest;
import RequestResult.LoadResult;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;
import RequestResult.PersonFamilyResult;
import RequestResult.PersonRequest;
import RequestResult.PersonSingleResult;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;
import RequestResult.Result;

/**
 * Created by emmag on 2/10/2017.
 */

public class ServerProxy {

    static String serverHost;
    static String serverPort;

    public ServerProxy() {
        serverHost = "localhost";
        serverPort = "8080";
    }

    public void setServerHost(String host) {
        serverHost = host;
    }
    public void setServerPort(String port) {
        serverPort = port;
    }

    public String getServerHost() {
        return serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }


    /**
     *  Creates a new user account, generates 4 generations of ancestor data for the new
     *  user, logs the user in, and returns an auth token.
     * @param r register request
     * @return result
     */
    public Result register(RegisterRequest r){

        Result result = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body


            //http.addRequestProperty("Authorization", "afj232hj2332");
            //http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            String reqData = null;
            reqData = Coder.encode(r, reqData);

            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                result = (RegisterResult) Coder.decode(new RegisterResult(), respBody);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * logs in user and returns an auth token
     * @param r login request
     * @return  login result
     */
    public Result login(LoginRequest r){
        Result result = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body

            //http.addRequestProperty("Authorization", "afj232hj2332");
            //http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            String reqData = null;
            reqData = Coder.encode(r, reqData);

            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                result = (LoginResult) Coder.decode(new LoginResult(), respBody);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * clears all data from the database
     * @return clear result
     */
    public Result clear(){
        Result result = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/clear");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);	// There is not a request body

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                result = (ClearResult) Coder.decode(new ClearResult(), respBody);
            }
            else {
                InputStream respBody = http.getInputStream();
                result = (ErrorResult) Coder.decode(new ErrorResult(), respBody);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * fills the database with generated data for the user for the specified number of generations
     * @param r fill request
     * @return fill result
     */
    public Result fill(FillRequest r){
        Result result = null;
        try {
            URL url = null;

            if(r.getGenerations() != 4) {
                url = new URL("http://" + serverHost + ":" + serverPort + "/fill/" + r.getUsername() + "/" + r.getGenerations());
            }
            else {
                url = new URL("http://" + serverHost + ":" + serverPort + "/fill/" + r.getUsername());
            }

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);	// There is not a request body

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                result = (FillResult) Coder.decode(new FillResult(), respBody);
            }
            else {
                InputStream respBody = http.getInputStream();
                result = (ErrorResult) Coder.decode(new ErrorResult(), respBody);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * clears the database, then loads the user, person, and event data
     * @param r load request
     * @return  load result
     */
    public Result load(LoadRequest r){
        Result result = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/load");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body

            //http.addRequestProperty("Authorization", "afj232hj2332");
            //http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            String reqData = null;
            reqData = Coder.encode(r, reqData);

            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                result = (LoadResult) Coder.decode(new LoadResult(), respBody);
            }
            else {
                InputStream respBody = http.getInputStream();
                result = (ErrorResult) Coder.decode(new ErrorResult(), respBody);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * returns single person with specified id
     * @param r person single request
     * @return  person single result
     */
    public Result person(PersonRequest r){
        Result result = null;
        try {
            URL url = null;
            if(r.getPersonID() != null) {
                url = new URL("http://" + serverHost + ":" + serverPort + "/person" + "/" + r.getPersonID());
            }
            else{
                url = new URL("http://" + serverHost + ":" + serverPort + "/person");
            }

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);	// There is a request body

            http.addRequestProperty("Authorization", r.getAuth());

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                if(r.getPersonID() == null) {
                    result = (PersonFamilyResult) Coder.decode(new PersonFamilyResult(), respBody);
                }
                else {
                    result = (PersonSingleResult) Coder.decode(new PersonSingleResult(), respBody);
                }
            }
            else {
                InputStream respBody = http.getInputStream();
                result = (ErrorResult) Coder.decode(new ErrorResult(), respBody);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * returns a single event with specified id
     * @param r event single request
     * @return event single result
     */
    public Result event(EventRequest r) {
        Result result = null;
        try {
            URL url = null;
            if(r.getEventID() != null) {
                url = new URL("http://" + serverHost + ":" + serverPort + "/event" + "/" + r.getEventID());
            }
            else {
                url = new URL("http://" + serverHost + ":" + serverPort + "/event");
            }

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);	// There is a request body

            http.addRequestProperty("Authorization", r.getAuth());

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                if(r.getEventID() == null) {
                    result = (EventFamilyResult) Coder.decode(new EventFamilyResult(), respBody);
                }
                else {
                    result = (EventSingleResult) Coder.decode(new EventSingleResult(), respBody);
                }
            }
            else {
                InputStream respBody = http.getInputStream();
                result = (ErrorResult) Coder.decode(new ErrorResult(), respBody);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
