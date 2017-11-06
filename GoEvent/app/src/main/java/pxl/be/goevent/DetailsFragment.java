package pxl.be.goevent;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by 11500046 on 31/10/2017.
 */

public class DetailsFragment extends Fragment{
    private Event event;
    public DetailsFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstances){
        final View rootView = inflater.inflate(R.layout.fragment_detail , container , false);
        String id = getArguments().getString("EventId");

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
            //Log.d("iets" , event.getOrganisator() +"");
            List<String>users = new ArrayList<>();
            if(event.getAttendees() == null){
                users.add("No one is going");
            }else {
            for (AppUser user: event.getAttendees()) {
                users.add(user.getFirstname() +" " + user.getLastName());
            }}
            ArrayAdapter<String> userAdapter = new ArrayAdapter<>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_item_user, // The name of the layout ID.
                            R.id.list_item_user_textview, // The ID of the textview to populate.
                            users);
            ListView attendees = rootView.findViewById(R.id.attendees);
            attendees.setAdapter(userAdapter);
            final Button button = rootView.findViewById(R.id.going);
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

            String user = caller.execute("http://goevent.azurewebsites.net/api/User/1" , "GET").get();
            caller = new ApiCaller();
            String result = caller.execute("http://goevent.azurewebsites.net/api/Event/"+  event.getId() +"/addUser" , "POST" ,user).get();




        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
