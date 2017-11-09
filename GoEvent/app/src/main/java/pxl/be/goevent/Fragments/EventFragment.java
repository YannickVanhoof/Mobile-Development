package pxl.be.goevent.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import pxl.be.goevent.Activities.DetailActivity;
import pxl.be.goevent.ApiCaller;
import pxl.be.goevent.CustomListAdapter;
import pxl.be.goevent.Event;
import pxl.be.goevent.JsonParser;
import pxl.be.goevent.R;

/**
 * Created by kimprzybylski on 14/10/17.
 */

public class EventFragment extends Fragment {


    private List<Event> filteredList;
    public EventFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String type = bundle.getString("Type");
        ApiCaller caller = new ApiCaller();
        String[] name = new String[0];
        String[] date = new String[0];
        try {
            String json = caller.execute("http://goevent.azurewebsites.net/api/Event" ,"GET").get();
            JsonParser parser = new JsonParser();
            Event[] eventsFromJson = parser.JsonToEventArray(json);

            filteredList = filterEventArrayByCategory(type,eventsFromJson);
            name = new String[filteredList.size()];
            date = new String[filteredList.size()];

            for (int i = 0; i < filteredList.size(); i++) {

                name[i] = filteredList.get(i).getName();
                date[i] = filteredList.get(i).getDateAsString();
            }


        }catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        final CustomListAdapter adapter = new CustomListAdapter(getActivity(), name, date);
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);
        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_event);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            redirect(position);
            }
        });
        return rootView;
    }

    private List<Event> filterEventArrayByCategory(String category , Event[] events){
        List<Event>filtered = new ArrayList<>();

        for (Event event:events) {

           if (Objects.equals(event.getCategory(), category)){
                filtered.add(event);

            }
        }
        return filtered;
    }
    private void redirect(int position){

        Intent intent = new Intent(getActivity(), DetailActivity.class)
                .putExtra("EventId" , filteredList.get(position).getId() +"");
        startActivity(intent);
    }
}
