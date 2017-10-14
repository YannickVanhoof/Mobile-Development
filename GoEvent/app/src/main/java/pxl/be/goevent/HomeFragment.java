package pxl.be.goevent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kimprzybylski on 14/10/17.
 */

public class HomeFragment extends Fragment {

    private ArrayAdapter<String> mHomeAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create some dummy data for the ListView.
        String[] data = {
                "Music",
                "Dance",
                "Food",
                "Children",
                "Movie"
        };
        List<String> type = new ArrayList<String>(Arrays.asList(data));


        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mHomeAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_home, // The name of the layout ID.
                        R.id.list_item_home_textview, // The ID of the textview to populate.
                        type);

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_home);
        listView.setAdapter(mHomeAdapter);



        return rootView;
    }
}
