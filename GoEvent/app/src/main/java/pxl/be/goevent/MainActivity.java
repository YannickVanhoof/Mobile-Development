package pxl.be.goevent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {

    Button loginButton, cancelButton;
    EditText nameEditText, passwordEditText;

    TextView errortextview;

    LoginButton fbLoginButton;
    CallbackManager callbackManager;

    public static final String PREFS_NAME = "LoginPrefsFile";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        loginButton = (Button)findViewById(R.id.login_button);
        nameEditText = (EditText)findViewById(R.id.name_editText);
        passwordEditText = (EditText)findViewById(R.id.password_editText);
        cancelButton = (Button)findViewById(R.id.cancel_button);

        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        EditText usernameEditText = (EditText)findViewById(R.id.name_editText);
        usernameEditText.setText(username);
        passwordEditText.setText(password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEditText.getText().toString().equals("admin") &&
                        passwordEditText.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();

                    // Save username and password
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                            .edit()
                            .putString(PREF_USERNAME, nameEditText.getText().toString())
                            .putString(PREF_PASSWORD, passwordEditText.getText().toString())
                            .commit();

                    //Start your second activity
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        errortextview = (TextView) findViewById(R.id.error);
        fbLoginButton = (LoginButton)findViewById(R.id.fb_login_button);
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();
                //Start your second activity
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                errortextview.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
