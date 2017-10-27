package pxl.be.goevent;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

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

public class ApiCaller extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... urls) {
        URLConnection urlConnection;
        BufferedReader reader;
        String json;
            try {
            //Create URL
            // URL url = new URL("http:/10.0.2.2:61015/api/event");
            URL url = new URL(urls[0]);
            Log.d("url", url.toString());
            //Create request and open connection
            urlConnection = url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
            Log.d("apicaller", "connection");
            Log.d("apicaller", urlConnection.getContentLength() + "");
            //Read input
            Log.e("APICALLER", urlConnection.getContent().toString());
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
                Log.d("APICaller" , "Empty");
                return "Empty";
            }
            json = builder.toString();
            Log.d("APICaller" , json);
            return json;

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("APICaller exception", ex.getMessage());

            return ex.getMessage();
        }
    }

    private void xmlTesting() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;


        try {
            dBuilder = factory.newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String url = "https://api.met.no/weatherapi/locationforecast/1.9/?lat=50&lon=5";
            URL link = new URL(url);
            URLConnection connection = link.openConnection();
            InputStream source = connection.getInputStream();
            Document parsed = dBuilder.parse(source);


            try {
                DOMSource domSource = new DOMSource(parsed);
                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(domSource, result);
                Log.e("XMLTEST", writer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
