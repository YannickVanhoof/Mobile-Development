package pxl.be.goevent;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by 11500046 on 7/11/2017.
 */

public class Application extends AppCompatActivity {
    static AppUser user;

    public static AppUser getUser() {
        return user;
    }

    public static void setUser(AppUser u) {
        user = u;
    }
}
