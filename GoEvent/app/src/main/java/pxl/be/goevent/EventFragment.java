package pxl.be.goevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by kimprzybylski on 14/10/17.
 */

public class EventFragment extends Fragment {

    private ArrayAdapter<String> mEventAdapter;
    private String type;
    private List<Event> filteredList;
    public EventFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
       String type = bundle.getString("Type");

        // Create some dummy data for the ListView.
        List<String> events =null;
        ApiCaller caller = new ApiCaller();
        try {
            String json = caller.execute("http://goevent.azurewebsites.net/api/Event").get();
            JsonParser parser = new JsonParser();
            Event[] eventsFromJson = parser.JsonToEventArray(json);

            filteredList = filterEventArrayByCategory(type,eventsFromJson);
            String[] data = new String[filteredList.size()];

            for (int i = 0; i < filteredList.size(); i++) {
                data[i] = filteredList.get(i).getName() + " Date: " + filteredList.get(i).getDateAsString();

            }
            events = new ArrayList<>(Arrays.asList(data));

        }catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mEventAdapter =
                new ArrayAdapter<>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_event, // The name of the layout ID.
                        R.id.list_item_event_textview, // The ID of the textview to populate.
                        events);

        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_event);
        listView.setAdapter(mEventAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String detail = mEventAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, detail)
                        .putExtra("EventId" , filteredList.get(position).getId() +"");
                startActivity(intent);
            }
        });

        return rootView;
    }

    private List<Event> filterEventArrayByCategory(String category , Event[] events){
        List<Event>filtered = new ArrayList<>();
        Log.d("bf filter events" , events.length +"");
        for (Event event:events) {

           if (Objects.equals(event.getCategory(), category)){
                filtered.add(event);

            }
        }
        return filtered;
    }
}
