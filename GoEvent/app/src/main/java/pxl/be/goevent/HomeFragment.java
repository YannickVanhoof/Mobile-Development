package pxl.be.goevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kimprzybylski on 14/10/17.
 */

public class HomeFragment extends Fragment {

    private SimpleAdapter mHomeAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map;

        map = new HashMap<String, String>();
        map.put("icon", String.valueOf(R.mipmap.music));
        map.put("type", "Music");
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("icon", String.valueOf(R.mipmap.dance));
        map.put("type", "Dance");
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("icon", String.valueOf(R.mipmap.party));
        map.put("type", "Party");
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("icon", String.valueOf(R.mipmap.expo));
        map.put("type", "Expo");
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("icon", String.valueOf(R.mipmap.sport));
        map.put("type", "Sport");
        listItem.add(map);


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        mHomeAdapter = new SimpleAdapter (
                getActivity(),
                listItem,
                R.layout.list_item_home,
                new String[] {"icon", "type"},
                new int[] {R.id.icon, R.id.list_item_home_textview});

        ListView listView = (ListView) rootView.findViewById(R.id.listview_home);
        listView.setAdapter(mHomeAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                HashMap<String, String> map = (HashMap<String, String>) mHomeAdapter.getItem(position);

            Intent intent = new Intent(getActivity(), EventsActivity.class)
                        .putExtra("Type", map.get("type"));
                startActivity(intent);

            }
        });

        return rootView;
    }
}
