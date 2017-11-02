package pxl.be.goevent;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by 11500046 on 17/10/2017.
 */

public class ApiCaller extends AsyncTask<String, String, String> {


    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        String method = params[1];
        String data ="";
        if (params.length >2){
            data = params[2];
        }

        if (Objects.equals(method.toUpperCase(), "POST")){
            String result =postData(url , data);
            Log.d("aaaaa" , result);
            return result;
        }
        if (Objects.equals(method.toUpperCase() , "GET")) {
            return getData(url);
        }
        return "";
    }


    private String postData(String urlstring, String data) {
        HttpURLConnection connection;
        OutputStreamWriter request = null;

        URL url = null;
        String response = null;
        //StrictMode.ThreadPolicy policy = new
        //StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        try {

            Log.d("aaaaa" , "POST DATA");
            url = new URL(urlstring);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            request = new OutputStreamWriter(connection.getOutputStream());
            request.write(data);
            request.flush();
            request.close();
            String line = "";
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            // Response from server after login process will be stored in response variable.
            response = sb.toString();
            isr.close();
            reader.close();
            Log.d("aaaaa" , "finished" + response);
            return response;
        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.d("aaaaa" , "error");
            return e.getMessage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("aaaaa" , "error");
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("aaaaa" , "error");
            return e.getMessage();
        }

    }

    private String getData(String u){
        URLConnection urlConnection;
        BufferedReader reader;
        String json;
        try {
            URL url = new URL(u);
            //Create request and open connection
            urlConnection = url.openConnection();
            //Read input
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if (inputStream == null){
                Log.d("APICaller" , "Niets gevonden");
                return "niets gevonden";
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line).append("\n");
            }
            if (builder.length() == 0){
                return "Empty";
            }
            json = builder.toString();
            return json;

        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }
}
