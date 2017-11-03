package pxl.be.goevent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.login.LoginManager;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_home, new HomeFragment())
                    .commit();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
            case R.id.myevents:
                startActivity(new Intent(this, MyEventsActivity.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
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