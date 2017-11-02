package pxl.be.goevent;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Document;

import java.util.concurrent.ExecutionException;

/**
 * Created by 11500046 on 31/10/2017.
 */

public class DetailsFragment extends Fragment{
    public DetailsFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstances){
        View rootView = inflater.inflate(R.layout.fragment_detail , container , false);
        String id = getArguments().getString("EventId");
        ApiCaller caller = new ApiCaller();
        try {
            String json = caller.execute("http://goevent.azurewebsites.net/api/Event/"+id).get();
            JsonParser parser = new JsonParser();
            Event event = parser.JsonToEvent(json);
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
            Log.d("iets" , event.getOrganisator() +"");
            Log.d("iets" , event.getAttendees() +"");


        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return rootView;
    }
}
