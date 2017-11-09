package pxl.be.goevent.Fragments;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import pxl.be.goevent.ApiCaller;
import pxl.be.goevent.AppUser;
import pxl.be.goevent.Event;
import pxl.be.goevent.JsonParser;
import pxl.be.goevent.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by 11500046 on 31/10/2017.
 */

public class DetailsFragment extends Fragment{
    private Event event;
    //private StorageReference mStorageRef;
    private String logedInUserName;

    public DetailsFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstances){
        final View rootView = inflater.inflate(R.layout.fragment_detail , container , false);
        String id = getArguments().getString("EventId");
        logedInUserName = getArguments().getString("Username");

        ApiCaller caller = new ApiCaller();
        try {
           String json = caller.execute("http://goevent.azurewebsites.net/api/Event/"+id , "GET").get();
            Log.d("json detail" , json);
            JsonParser parser = new JsonParser();
            event = parser.JsonToEvent(json);
            TextView name = rootView.findViewById(R.id.eventname);
            name.setText(event.getName());
            TextView description = rootView.findViewById(R.id.description);
            description.setText(event.getDescription());
            TextView venue = rootView.findViewById(R.id.venue);
            venue.setText(event.getVenue());
            TextView address = rootView.findViewById(R.id.address);
            address.setText(event.getAddress());
            TextView date = rootView.findViewById(R.id.date);
            date.setText(event.getDateAsString());
            TextView start = rootView.findViewById(R.id.startTime);
            start.setText(event.getStartTime());
            TextView end = rootView.findViewById(R.id.endTime);
            end.setText(event.getEndTime());

            // add image
            final ImageView imageView;
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                imageView = rootView.findViewById(R.id.detail_image);

            storageReference.child("images/"+event.getName()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getApplicationContext()).load(uri.toString()).into(imageView);
                }
            });

            final Button button = rootView.findViewById(R.id.going);
            List<String>users = new ArrayList<>();
            if(event.getAttendees() == null){
                users.add("No one is going");
            }else {
            for (AppUser user: event.getAttendees()) {
                if (Objects.equals(user.getUserName(), logedInUserName)){
                    Log.d("logedin" ,logedInUserName);
                    button.setVisibility(View.INVISIBLE);
                    TextView view = rootView.findViewById(R.id.alreadyGoing);
                    view.setVisibility(View.VISIBLE);
                }
                users.add(user.getFirstname() +" " + user.getLastName());
            }}
            ArrayAdapter<String> userAdapter = new ArrayAdapter<>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_item_user, // The name of the layout ID.
                            R.id.list_item_user_textview, // The ID of the textview to populate.
                            users);
            ListView attendees = rootView.findViewById(R.id.attendees);
            attendees.setAdapter(userAdapter);

            button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    AddUserToAttendees();
                    button.setVisibility(View.INVISIBLE);
                    rootView.findViewById(R.id.alreadyGoing).setVisibility(View.VISIBLE);

                }
            });


        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return rootView;
    }
    private void AddUserToAttendees(){
        ApiCaller caller = new ApiCaller();
        try {

            String user = caller.execute("http://goevent.azurewebsites.net/api/User/Name/"+logedInUserName , "GET").get();
            caller = new ApiCaller();

            String result = caller.execute("http://goevent.azurewebsites.net/api/Event/"+  event.getId() +"/addUser" , "POST" ,user).get();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(DetailsFragment.this).attach(DetailsFragment.this).commit();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
