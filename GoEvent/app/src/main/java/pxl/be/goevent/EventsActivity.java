package pxl.be.goevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.login.LoginManager;

public class EventsActivity extends Application {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        if (savedInstanceState == null) {
            String type = getIntent().getStringExtra("Type");
            Bundle bundle = new Bundle();
            bundle.putString("Type" , type);
            EventFragment fragment = new EventFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_event, fragment)
                    .commit();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    //TODO::menu items nog laten werken
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
