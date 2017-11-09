package pxl.be.goevent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.facebook.login.LoginManager;
import org.json.JSONException;
import java.util.concurrent.ExecutionException;
import pxl.be.goevent.ApiCaller;
import pxl.be.goevent.AppUser;
import pxl.be.goevent.Application;
import pxl.be.goevent.Fragments.HomeFragment;
import pxl.be.goevent.JsonParser;
import pxl.be.goevent.MapsActivity;
import pxl.be.goevent.R;

public class HomeActivity extends Application {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            String userName = getIntent().getStringExtra("userName");
            try {
                String result = new ApiCaller().execute("http://goevent.azurewebsites.net/api/User/Name/"+userName, "GET").get();
                AppUser user = new JsonParser().JsonToAppUser(result);
                setUser(user);
            } catch (JSONException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

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