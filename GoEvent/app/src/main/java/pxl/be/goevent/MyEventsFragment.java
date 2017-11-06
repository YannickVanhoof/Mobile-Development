package pxl.be.goevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.util.concurrent.ExecutionException;

/**
 * Created by 11500046 on 2/11/2017.
 */

public class MyEventsFragment  extends Fragment{
    private ArrayAdapter<String> mEventAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ApiCaller caller = new ApiCaller();
        String json = null;
        try {
            json = caller.execute("http://goevent.azurewebsites.net/Api/User/1/Events" ,"GET").get();
            Log.d("USER" ,json);
            JsonParser parser = new JsonParser();
            try {
               final Event[] events = parser.JsonToEventArray(json);
                List<String> eventList = new ArrayList<>();
                if (events != null || events.length > 0){

                        for (Event e : events){
                        eventList.add(e.getName() + " Date: " + e.getDateAsString());
                    }
                }
                mEventAdapter =
                        new ArrayAdapter<>(
                                getActivity(), // The current context (this activity)
                                R.layout.list_item_event, // The name of the layout ID.
                                R.id.list_item_event_textview, // The ID of the textview to populate.
                                eventList);

                View rootView = inflater.inflate(R.layout.fragment_event, container, false);

                ListView listView = (ListView) rootView.findViewById(R.id.listview_event);
                listView.setAdapter(mEventAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        String detail = mEventAdapter.getItem(position);
                        Intent intent = new Intent(getActivity(), DetailActivity.class)
                                .putExtra(Intent.EXTRA_TEXT, detail)
                                .putExtra("EventId" , events[position].getId() +"");

                        startActivity(intent);
                    }
                });

                return rootView;


            } catch (JSONException e) {
                e.printStackTrace();
                return null;

            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

    }
}
