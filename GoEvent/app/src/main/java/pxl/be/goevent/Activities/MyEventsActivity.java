package pxl.be.goevent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.facebook.login.LoginManager;
import pxl.be.goevent.Application;
import pxl.be.goevent.MapsActivity;
import pxl.be.goevent.Fragments.MyEventsFragment;
import pxl.be.goevent.R;

/**
 * Created by 11500046 on 2/11/2017.
 */

public class MyEventsActivity extends Application {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        MyEventsFragment fragment = new MyEventsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("UserId" , getUser().getId());
               fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_event, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            case R.id.myevents:
                startActivity(new Intent(this, HomeActivity.class));
                return true;

            case R.id.logout:
                LoginManager.getInstance().logOut();
                setUser(null);
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
