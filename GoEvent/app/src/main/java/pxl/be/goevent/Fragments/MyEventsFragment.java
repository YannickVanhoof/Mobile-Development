package pxl.be.goevent.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import pxl.be.goevent.Activities.DetailActivity;
import pxl.be.goevent.ApiCaller;
import pxl.be.goevent.CustomListAdapter;
import pxl.be.goevent.Event;
import pxl.be.goevent.JsonParser;
import pxl.be.goevent.R;

/**
 * Created by 11500046 on 2/11/2017.
 */

public class MyEventsFragment  extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int userid = getArguments().getInt("UserId");
        ApiCaller caller = new ApiCaller();
        String json = null;
        try {
            json = caller.execute("http://goevent.azurewebsites.net/Api/User/"+userid+"/Events" ,"GET").get();
            JsonParser parser = new JsonParser();
            String[] name;
            String[] date;
            final Event[] events = parser.JsonToEventArray(json);
            name = new String[events.length];
             date = new String[events.length];
                if (events != null || events.length > 0){

                    for (int i = 0; i < events.length; i++) {
                        name[i] = events[i].getName();
                        date[i] = events[i].getDateAsString();
                    }
                }
                final CustomListAdapter adapter = new CustomListAdapter(getActivity(), name, date);
                View rootView = inflater.inflate(R.layout.fragment_event, container, false);

                // Get a reference to the ListView, and attach this adapter to it.
                ListView listView = (ListView) rootView.findViewById(R.id.listview_event);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                      redirect(events[position].getId());

                    }


                });

                return rootView;

        } catch (InterruptedException | ExecutionException|JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    private void redirect(int id){
        Intent intent = new Intent(getActivity(), DetailActivity.class)
                .putExtra("EventId" , id+"");
        startActivity(intent);
    }
}