package pxl.be.goevent;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Created by 11500046 on 24/10/2017.
 */

public class JsonParser {
    public Event[] JsonToEventArray(String jsonString)
            throws JSONException {
        Log.d("JSONSTRING" , jsonString);
        JSONArray events = new JSONArray(jsonString);
        Event[] results = new Event[events.length()];
        for (int i = 0; i < events.length(); i++) {
            JSONObject e = events.getJSONObject(i);

            String attendees = e.get("Attendees")+"";
            Event result = new Event();
            result.setCity(e.getString("City"));
            result.setCoverPhoto(null);

            if (!attendees.equals(null +"")) {
                result.setAttendees(Arrays.asList(JsonToAppUserArray(attendees)));
            } else {
             result.setAttendees(null);
            }


            String organisator = e.getString("Organisator");
            result.setOrganisator(JsonToAppUser(organisator));


            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date = format.parse(e.getString("Date"));
                Date time =  format.parse(e.getString("StartTime"));
                Date endTime =  format.parse(e.getString("EndTime"));
                result.setDate(date);
                result.setEndTime(endTime);
                result.setStartTime(time);
            } catch (ParseException e1) {
                 e1.printStackTrace();
            }
            //result.setDate((Date) e.get("Date"));
            result.setDescription(e.getString("Description"));
            result.setHouseNumber(e.getInt("houseNumber"));
            result.setId(e.getInt("Id"));
            result.setLatitude(e.getDouble("Latitude"));
            result.setLongitude(e.getDouble("Longitude"));
            result.setName(e.getString("Name"));
            result.setStreet(e.getString("Street"));
            result.setVenue(e.getString("Venue"));
            result.setPostalCode(e.getInt("PostalCode"));
            result.setCategory(e.getString("Type"));
            results[i] = result;
        }
        Log.d("Parse" , "succes");
        return results;

    }

    public Event JsonToEvent(String jsonString)
            throws JSONException {

            JSONObject event = new JSONObject(jsonString);
            String attendees = event.get("Attendees")+"";
            Event result = new Event();
            String organisator = event.getString("Organisator");
            if (organisator != null){
                result.setOrganisator(JsonToAppUser(organisator));
            }else {
                result.setOrganisator(null);
            }

             if (!attendees.equals(null +"")) {
            result.setAttendees(Arrays.asList(JsonToAppUserArray(attendees)));
            } else {
            result.setAttendees(null);}
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
            Date time =  format.parse(event.getString("StartTime"));
            Date endTime =  format.parse(event.getString("EndTime"));
            result.setDate(date);
            result.setEndTime(endTime);
            result.setStartTime(time);
        } catch (ParseException e1) {
            Log.d("DATUM" , e1.getMessage() +"");
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

    public String AppUserToJson(AppUser user){
       Gson gson =  new Gson();
        return gson.toJson(user);
    }
}


