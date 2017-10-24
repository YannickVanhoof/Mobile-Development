package pxl.be.goevent;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by 11500046 on 24/10/2017.
 */

public class JsonParser {
    public Event[] JsonToEventArray(String jsonString)
            throws JSONException {

        JSONArray events = new JSONArray(jsonString);
        Event[] results = new Event[events.length()];
        for (int i = 0; i < events.length(); i++) {
            // Get the JSON object representing the day
            JSONObject e = events.getJSONObject(i);
            // description is in a child array called "weather", which is 1 element long.
            // JSONObject weatherObject = e.getJSONArray(OWM_WEATHER).getJSONObject(0);
            // description = weatherObject.getString(OWM_DESCRIPTION);
            Event result = new Event();
            result.setAttendees(null);
            result.setCity(e.getString("City"));
            result.setCoverPhoto(null);
            result.setDate(null);
            result.setDescription(e.getString("Description"));
            result.setEndTime(null);
            result.setHouseNumber(e.getInt("houseNumber"));
            result.setId(e.getInt("Id"));
            result.setLatitude(e.getDouble("Latitude"));
            result.setLongitude(e.getDouble("Longitude"));
            result.setName(e.getString("Name"));
            result.setOrganisator(null);
            result.setStreet(e.getString("Street"));
            result.setVenue(e.getString("Venue"));
            result.setPostalCode(e.getInt("PostalCode"));
            results[i] = result;
        }
        return results;

    }

    public Event JsonToEvent(String jsonString)
            throws JSONException {

            JSONObject event = new JSONObject(jsonString);
            Event result = new Event();
            result.setAttendees(null);
            result.setCity(event.getString("City"));
            result.setCoverPhoto(null);
            result.setDate(null);
            result.setDescription(event.getString("Description"));
            result.setEndTime(null);
            result.setHouseNumber(event.getInt("houseNumber"));
            result.setId(event.getInt("Id"));
            result.setLatitude(event.getDouble("Latitude"));
            result.setLongitude(event.getDouble("Longitude"));
            result.setName(event.getString("Name"));
            result.setOrganisator(null);
            result.setStreet(event.getString("Street"));
            result.setVenue(event.getString("Venue"));
            result.setPostalCode(event.getInt("PostalCode"));

        return result;

    }

    public AppUser JsonToAppUser(String jsonString) throws JSONException {
        AppUser result = new AppUser();
        JSONObject user = new JSONObject(jsonString);
        result.setId(user.getInt("Id"));
        result.setUserName(user.getString("UserName"));
        result.setPassword(user.getString("Password"));
        result.setEmail(user.getString("Email"));
        result.setFirstname(user.getString("Firstname"));
        result.setLastName(user.getString("Lastname"));
        result.setAddress(user.getString("Adress"));
        result.setCity(user.getString("City"));
        result.setPostalCode(user.getInt("PostalCode"));
        result.setOrganisedEvents(null);
        result.setEvents(null);
        return result;
    }

    public AppUser[] JsonToAppUserArray(String jsonString) throws JSONException {

        JSONArray users = new JSONArray(jsonString);
        AppUser[] results = new AppUser[users.length()];
        for (int i =0; i < users.length() ; i++){
            AppUser result = new AppUser();
            JSONObject user = users.getJSONObject(i);
            result.setId(user.getInt("Id"));
            result.setUserName(user.getString("UserName"));
            result.setPassword(user.getString("Password"));
            result.setEmail(user.getString("Email"));
            result.setFirstname(user.getString("Firstname"));
            result.setLastName(user.getString("Lastname"));
            result.setAddress(user.getString("Adress"));
            result.setCity(user.getString("City"));
            result.setPostalCode(user.getInt("PostalCode"));
            result.setOrganisedEvents(null);
            result.setEvents(null);
            results[i] = result;
        }
        return results;
    }
}


