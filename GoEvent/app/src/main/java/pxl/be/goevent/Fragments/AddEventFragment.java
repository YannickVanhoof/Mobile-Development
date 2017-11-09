package pxl.be.goevent.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import pxl.be.goevent.Activities.AddEventActivity;
import pxl.be.goevent.Activities.EventsActivity;
import pxl.be.goevent.ApiCaller;
import pxl.be.goevent.Event;
import pxl.be.goevent.JsonParser;
import pxl.be.goevent.R;


public class AddEventFragment extends Fragment implements View.OnClickListener {

    Button addButton, chooseButton;

    private EditText name, street, housenumber, city, postcode, venue, date, start, end, description;

    private Spinner spinner;

    private Event event;

    private static final int PICK_IMAGE_REQUEST = 234;

    private ImageView imageView;

    private Uri filePath;

    private StorageReference mStorageRef;

    public AddEventFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_event, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        spinner = (Spinner) rootView.findViewById(R.id.type_spinner);
        addItemsOnSpinner();

        name = (EditText)rootView.findViewById(R.id.name_editText);
        street = (EditText)rootView.findViewById(R.id.street_editText);
        housenumber = (EditText)rootView.findViewById(R.id.houseNumber_editText);
        city = (EditText)rootView.findViewById(R.id.city_editText);
        postcode = (EditText)rootView.findViewById(R.id.postcode_editText);
        venue = (EditText)rootView.findViewById(R.id.venue_editText);
        date = (EditText)rootView.findViewById(R.id.date_editText);
        start = (EditText)rootView.findViewById(R.id.startTime_editText);
        end = (EditText)rootView.findViewById(R.id.endTime_editText);
        description = (EditText)rootView.findViewById(R.id.description_editText);
        imageView = (ImageView) rootView.findViewById(R.id.image);
        chooseButton = (Button)rootView.findViewById(R.id.chooseButton);
        addButton = (Button)rootView.findViewById(R.id.addButton);

        chooseButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        return rootView;
    }

    // Toont afbeelding als deze gekozen is
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AddEventActivity.RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = mStorageRef.child("images/"+ event.getName()+".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getActivity().getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(getActivity().getApplicationContext(), "No File To Upload", Toast.LENGTH_LONG).show();
        }
    }

    private Event createEvent() throws ParseException, ExecutionException, InterruptedException, JSONException {
        Event event = new Event();
        event.setId(100);
        event.setName(name.getText().toString());
        event.setStreet(street.getText().toString());
        event.setHouseNumber(Integer.parseInt(housenumber.getText().toString()));
        event.setCity(city.getText().toString());
        event.setPostalCode(Integer.parseInt(postcode.getText().toString()));
        event.setVenue(venue.getText().toString());
        event.setCategory(String.valueOf(spinner.getSelectedItem()));
        event.setDate(createDate(date.getText().toString()));
        event.setStartTime(createTime(start.getText().toString()));
        event.setEndTime(createTime(end.getText().toString()));
        event.setDescription(description.getText().toString());
        LatLng latlng = getLocationFromAddress(getActivity(), event.getStreet() + ", " + event.getHouseNumber() + ", " + event.getCity());
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
        LatLng latLng = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            latLng = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return latLng;
    }

    public void addItemsOnSpinner() {
        List<String> list = new ArrayList<>();
        list.add("Music");
        list.add("Dance");
        list.add("Party");
        list.add("Expo");
        list.add("Sport");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void addEvent() {
        try {
            event = createEvent();
            String json = new JsonParser().EventToJson(event);
            ApiCaller caller = new ApiCaller();
            caller.execute("http://goevent.azurewebsites.net/api/Event" , "POST" , json).get();

        } catch (ParseException | InterruptedException | ExecutionException | JSONException exception) {
            exception.printStackTrace();
        }

        uploadFile();

        Intent myIntent = new Intent(getActivity(), EventsActivity.class)
                .putExtra("Type", event.getCategory());
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chooseButton:
                showFileChooser();
                break;
            case R.id.addButton:
                addEvent();
                break;
        }
    }
}
