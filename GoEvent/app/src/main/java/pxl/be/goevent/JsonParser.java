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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by 11500046 on 24/10/2017.
 */

public class JsonParser {
    public Event[] JsonToEventArray(String jsonString)
            throws JSONException {
        JSONArray events = new JSONArray(jsonString);
        Event[] results = new Event[events.length()];
        for (int i = 0; i<events.length() ; i++) {
            JSONObject j = events.getJSONObject(i);
            String attendees = j.get("Attendees")+"";
            Event result = new Event();
            result.setCity(j.getString("City"));
            result.setCoverPhoto(null);
                try{
                   result.setAttendees(Arrays.asList(JsonToAppUserArray(attendees)));
               }catch (Exception ex){
                   result.setAttendees(null);
               }




            try {
                String organisator = j.getString("Organisator");

                if (organisator.equals("null")){
                    result.setOrganisator(null);
                }else {
                    result.setOrganisator(JsonToAppUser(organisator));
                }
            }catch (Exception ex){
                result.setOrganisator(null);
            }
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date = format.parse(j.getString("Date"));
                Date time =  format.parse(j.getString("StartTime"));
                Date endTime =  format.parse(j.getString("EndTime"));
                result.setDate(date);
                result.setEndTime(endTime);
                result.setStartTime(time);
            } catch (ParseException e1) {
                 e1.printStackTrace();
            }
            result.setDescription(j.getString("Description"));
            result.setHouseNumber(j.getInt("houseNumber"));
            result.setId(j.getInt("Id"));
            result.setLatitude(j.getDouble("Latitude"));
            result.setLongitude(j.getDouble("Longitude"));
            result.setName(j.getString("Name"));
            result.setStreet(j.getString("Street"));
            result.setVenue(j.getString("Venue"));
            result.setPostalCode(j.getInt("PostalCode"));
            result.setCategory(j.getString("Type"));
            results[i] = result;

        }
        return results;

    }

    public Event JsonToEvent(String jsonString)
            throws JSONException {

            JSONObject event = new JSONObject(jsonString);
            String attendees = event.get("Attendees")+"";
            Event result = new Event();
            /*String organisator = event.getString("Organisator");
            if (organisator != null){
                result.setOrganisator(JsonToAppUser(organisator));
            }else {
                result.setOrganisator(null);
            }*/

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
        JSONArray iets = user.getJSONArray("Events");
        for (int i = 0 ; i <iets.length() ; i++){
            Log.d("aaa", "JsonToAppUser: " + iets.get(i));
        }
        Log.d("iets" , Arrays.asList(JsonToEventArray(iets +"")) +"");
        //Event[] events = JsonToEventArray(iets);
        //result.setEvents(Arrays.asList(events));
        //Log.d("Resultaat users" , events.length +"");
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
            result.setEvents(Arrays.asList(JsonToEventArray(user.get("Events") + "")));
            results[i] = result;
        }
        return results;
    }

    public String AppUserToJson(AppUser user){
       Gson gson =  new Gson();
        return gson.toJson(user);
    }
    public String EventToJson(Event event){
        Gson gson = new Gson();
        return gson.toJson(event);
    }
}


