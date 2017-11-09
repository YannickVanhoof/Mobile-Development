package pxl.be.goevent.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import pxl.be.goevent.Application;

import pxl.be.goevent.Fragments.MainFragment;
import pxl.be.goevent.R;

public class MainActivity extends Application {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getUser() != null){

            startActivity(new Intent(this, HomeActivity.class));
        }
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fm.findFragmentById(R.id.main_fragment);

        if (fragment == null) {
            fragment = new MainFragment();
                fm.beginTransaction()
                        .add(R.id.main_fragment, fragment)
                        .commit();

        }
    }
}

