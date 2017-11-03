package pxl.be.goevent;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiPost extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection urlConnection = null;
        BufferedWriter writer;

        try {
            // Create url
            URL url = new URL(urls[0]);
            Log.e("url", url.toString());

            // Create request and open connection
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.e("connection", "connection");

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Write output
            OutputStream outPutStream = urlConnection.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(outPutStream));
            Log.e("writer", "writer");
            Log.e("json", urls[1]);
            writer.write(urls[1]);
            writer.flush();
            writer.close();
            Log.e("done", "done");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }
}
