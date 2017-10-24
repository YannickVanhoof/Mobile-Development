package pxl.be.goevent;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

/**
 * Created by 11500046 on 24/10/2017.
 */

public class JsonParser {
    public String[] PrintJson(String jsonString)
            throws JSONException {

        //JSONObject event = new JSONObject(jsonString);
        //JSONArray events = event.getJSONArray("list");
        JSONArray events = new JSONArray(jsonString);
        String[] resultStrs = new String[events.length()];
        for (int i = 0; i < events.length(); i++) {
            // Get the JSON object representing the day
            JSONObject e = events.getJSONObject(i);

            // description is in a child array called "weather", which is 1 element long.
            // JSONObject weatherObject = e.getJSONArray(OWM_WEATHER).getJSONObject(0);
            // description = weatherObject.getString(OWM_DESCRIPTION);

           // JSONObject temperatureObject = e.getJSONObject(OWM_TEMPERATURE);
            int id = e.getInt("Id");
            String name = e.getString("Name");
            resultStrs[i] = id + " - " + name;
        }

        for (String s : resultStrs) {
            Log.v("JSONPARSER", "" + s);
        }
        return resultStrs;

    }
}


