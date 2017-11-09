package pxl.be.goevent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.facebook.login.LoginManager;
import pxl.be.goevent.Application;
import pxl.be.goevent.Fragments.DetailsFragment;
import pxl.be.goevent.MapsActivity;
import pxl.be.goevent.R;

public class DetailActivity extends Application {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            String eventId = getIntent().getStringExtra("EventId");
            Log.d("My ID" , eventId +"");
            Bundle bundle = new Bundle();
            bundle.putString("EventId" , eventId);
            bundle.putString("Username" , getUser().getUserName());
            DetailsFragment fragment = new DetailsFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            case R.id.myevents:
                startActivity(new Intent(this, MyEventsActivity.class));
                return true;
            case R.id.logout:
                LoginManager.getInstance().logOut();
                setUser(null);
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.addEvent:
                startActivity(new Intent(this, AddEventActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
