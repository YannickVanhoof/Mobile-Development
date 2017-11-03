package pxl.be.goevent;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class AddEventActivity extends AppCompatActivity {

    private Button addButton;

    private EditText id, name, street, housenumber, city, postcode, date, start, end, description;

    private Spinner spinner;

    private Event e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        spinner = (Spinner)findViewById(R.id.type_spinner);
        addItemsOnSpinner();

        id = (EditText)findViewById(R.id.id);
        name = (EditText)findViewById(R.id.name_editText);
        street = (EditText)findViewById(R.id.street_editText);
        housenumber = (EditText)findViewById(R.id.houseNumber_editText);
        city = (EditText)findViewById(R.id.city_editText);
        postcode = (EditText)findViewById(R.id.postcode_editText);
        date = (EditText)findViewById(R.id.date_editText);
        start = (EditText)findViewById(R.id.startTime_editText);
        end = (EditText)findViewById(R.id.endTime_editText);
        description = (EditText)findViewById(R.id.description_editText);

        addButton = (Button)findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    e = createEvent();
                    String json = new JsonParser().EventToJson(e);
                    ApiCaller caller = new ApiCaller();
                    try {
                        String result = caller.execute("http://goevent.azurewebsites.net/api/Event" , "POST" , json).get();
                        Log.d("result" , result);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (ExecutionException e1) {
                        e1.printStackTrace();
                    }

                } catch (ParseException e1) {
                    e1.printStackTrace();
                    Log.d("error" , e1.getMessage());
                }
                Intent myIntent = new Intent(AddEventActivity.this, EventsActivity.class)
                        .putExtra("Type", e.getCategory());
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            case R.id.myevents:
                startActivity(new Intent(this, MyEventsActivity.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.logout:
                LoginManager.getInstance().logOut();
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Event createEvent() throws ParseException {
        Event event = new Event();
        event.setId(Integer.parseInt(id.getText().toString()));
        event.setName(name.getText().toString());
        event.setStreet(street.getText().toString());
        event.setHouseNumber(Integer.parseInt(housenumber.getText().toString()));
        event.setCity(city.getText().toString());
        event.setPostalCode(Integer.parseInt(postcode.getText().toString()));
        event.setCategory(String.valueOf(spinner.getSelectedItem()));
        event.setDate(createDate(date.getText().toString()));
        event.setStartTime(createTime(start.getText().toString()));
        event.setEndTime(createTime(end.getText().toString()));
        event.setDescription(description.getText().toString());

        LatLng latlng = getLocationFromAddress(this, event.getStreet() + ", " + event.getHouseNumber() + ", " + event.getCity());

        event.setLongitude(latlng.longitude);
        event.setLatitude(latlng.latitude);
        return event;
    }

    private Date createTime(String time) throws ParseException {
        String data = date.getText().toString() + " " + time + ":00";
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(data);
    }

    private Date createDate(String date) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    private LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public void addItemsOnSpinner() {
        List<String> list = new ArrayList<>();
        list.add("Music");
        list.add("Dance");
        list.add("Party");
        list.add("Expo");
        list.add("Sport");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }
}

