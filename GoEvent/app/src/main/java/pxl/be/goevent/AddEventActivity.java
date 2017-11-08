package pxl.be.goevent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

public class AddEventActivity extends Application implements View.OnClickListener {

    private Button addButton, uploadButton, chooseButton;

    private EditText id, name, street, housenumber, city, postcode, date, start, end, description , venue;

    private Spinner spinner;

    private Event e;

    private static final int PICK_IMAGE_REQUEST = 234;

    private ImageView imageView;

    private Uri filePath;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        mStorageRef = FirebaseStorage.getInstance().getReference();

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
        venue = (EditText)findViewById(R.id.venue_editText);
        chooseButton = (Button)findViewById(R.id.choose);
        uploadButton = (Button)findViewById(R.id.upload);
        imageView = (ImageView) findViewById(R.id.image);
        chooseButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);

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
                        String id = e.getId() +"";
                        Intent myIntent = new Intent(AddEventActivity.this, DetailActivity.class)
                                .putExtra("EventId" ,id);
                        startActivity(myIntent);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (ExecutionException e1) {
                        e1.printStackTrace();
                    }

                } catch (ParseException e1) {
                    e1.printStackTrace();
                    Log.d("error" , e1.getMessage());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile() {
        //if there is a file to upload
        Log.e("filePath", filePath.toString());
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = mStorageRef.child("images/pic.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
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
            case R.id.logout:
                LoginManager.getInstance().logOut();
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Event createEvent() throws ParseException, ExecutionException, InterruptedException, JSONException {
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
        ApiCaller caller = new ApiCaller();
        String username = getIntent().getStringExtra("Username");
        Log.d("username" , username +"");
        String result =caller.execute("http://goevent.azurewebsites.net/api/User/Name/"+username , "get").get();
        JsonParser parser = new JsonParser();
        event.setOrganisator(getUser());
        LatLng latlng = getLocationFromAddress(this, event.getStreet() + ", " + event.getHouseNumber() + ", " + event.getCity());
        event.setLongitude(latlng.longitude);
        event.setLatitude(latlng.latitude);
        event.setVenue(venue.getText().toString());
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

    @Override
    public void onClick(View view) {
        if (view == chooseButton) {
            showFileChooser();
        } else if (view == uploadButton) {
            Log.e("filePath", filePath.toString());
            uploadFile();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

