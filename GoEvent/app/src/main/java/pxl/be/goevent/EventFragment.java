package pxl.be.goevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kimprzybylski on 14/10/17.
 */

public class EventFragment extends Fragment {

    private ArrayAdapter<String> mEventAdapter;

    public EventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create some dummy data for the ListView.
        String[] data = {
                "Pukkelpop 25-08-2018 Kiewit",
                "Tabdans 24-12-2018 Beringen",
                "Grandma Baking 11-14-2018 Antwerpen",
                "Aankomst Sint 1-12-2017 Brussel",
                "Iron man 3-3-2018 Gent"
        };
        List<String> events = new ArrayList<String>(Arrays.asList(data));


        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mEventAdapter =
                new ArrayAdapter<String>(
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
                        .putExtra(Intent.EXTRA_TEXT, detail);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
