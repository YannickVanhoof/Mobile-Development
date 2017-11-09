package pxl.be.goevent;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
            JSONObject j = events.getJSONObject(i);
            Event result = JsonToEvent(j.toString());
            results[i] = result;
        }
        return results;

    }

    public Event JsonToEvent(String jsonString)
            throws JSONException {

        JSONObject event = new JSONObject(jsonString);
        String attendees = event.get("Attendees") + "";
        Event result = new Event();
        if (!attendees.equals(null + "")) {
            result.setAttendees(Arrays.asList(JsonToAppUserArray(attendees)));
        } else {
            result.setAttendees(null);
        }
        result.setCity(event.getString("City"));
        result.setCoverPhoto(null);
        result.setDescription(event.getString("Description"));
        result.setHouseNumber(event.getInt("houseNumber"));
        result.setId(event.getInt("Id"));
        result.setLatitude(event.getDouble("Latitude"));
        result.setLongitude(event.getDouble("Longitude"));
        result.setName(event.getString("Name"));
        result.setStreet(event.getString("Street"));
        result.setVenue(event.getString("Venue"));
        result.setPostalCode(event.getInt("PostalCode"));
        result.setCategory(event.getString("Type"));

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = format.parse(event.getString("Date"));
            Date time = format.parse(event.getString("StartTime"));
            Date endTime = format.parse(event.getString("EndTime"));
            result.setDate(date);
            result.setEndTime(endTime);
            result.setStartTime(time);
        } catch (ParseException e1) {

            e1.printStackTrace();
        }

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
        return result;
    }

    public AppUser[] JsonToAppUserArray(String jsonString) throws JSONException {

        JSONArray users = new JSONArray(jsonString);
        AppUser[] results = new AppUser[users.length()];
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            AppUser result = JsonToAppUser(user.toString());
            results[i] = result;
        }
        return results;
    }

    public String AppUserToJson(AppUser user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public String EventToJson(Event event) {
        Gson gson = new Gson();
        return gson.toJson(event);
    }
}


