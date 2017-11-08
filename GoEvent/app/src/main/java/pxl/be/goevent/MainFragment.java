package pxl.be.goevent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {

    private Button loginButton;
    private EditText nameEditText, passwordEditText;

    private LoginButton fbLoginButton;
    private CallbackManager callbackManager;

    public static final String PREFS_NAME = "LoginPrefsFile";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        loginButton = rootView.findViewById(R.id.login_button);
        nameEditText = rootView.findViewById(R.id.name_editText);
        passwordEditText = rootView.findViewById(R.id.password_editText);
        String username;
        String password ="";
        SharedPreferences pref = getActivity().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if (getActivity().getIntent().getStringExtra("Username") == null){
            username = pref.getString(PREF_USERNAME, null);
            password = pref.getString(PREF_PASSWORD, null);
        }else {
            username = getActivity().getIntent().getStringExtra("Username");

        }

        nameEditText.setText(username);
        passwordEditText.setText(password);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = null;
                try {
                    result = new ApiCaller().execute("http://goevent.azurewebsites.net/api/User/Name/"+nameEditText.getText(), "GET").get();
                    Log.d("result", result);
                    if(result.startsWith("{")) {
                        Log.e("string not empty", "not null");
                        AppUser user = new JsonParser().JsonToAppUser(result);

                        if(passwordEditText.getText().toString().equals(user.getPassword())) {
                            Toast.makeText(getActivity().getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();

                            // Save username and password
                            getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                                    .edit()
                                    .putString(PREF_USERNAME, nameEditText.getText().toString())
                                    .putString(PREF_PASSWORD, passwordEditText.getText().toString())
                                    .apply();

                            //Start your second activity
                            Intent intent = new Intent(getActivity(), HomeActivity.class)
                                    .putExtra("userName", user.getUserName());
                            startActivity(intent);
                            getActivity().finish();

                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button registerButton = rootView.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Facebook login
        fbLoginButton = rootView.findViewById(R.id.fb_login_button);
        fbLoginButton.setFragment(this);

        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Intent intent= createRegisterIntent(object);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));

            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));

            return bundle;
        }
        catch(JSONException e) {
            e.printStackTrace();
            Log.d("FB", e.getMessage());
        }
        return null;
    }

    private Intent createRegisterIntent(JSONObject object){
        // Get facebook data from login
        Bundle bFacebookData = getFacebookData(object);
        String userName = bFacebookData.getString("first_name")+""+bFacebookData.getString("last_name");
        String lastName = bFacebookData.getString("last_name");
        String firstName = bFacebookData.getString("first_name");
        if(IfExist(userName)){
            return new Intent(getActivity(), HomeActivity.class)
                    .putExtra("userName", userName);
        }
        Intent intent = new Intent(getActivity() , RegisterActivity.class);
        intent.putExtra("last_name" , lastName).putExtra("user_name" , userName).putExtra("first_name" , firstName);
        return intent;
    }
    private boolean IfExist(String userName){
        try {
            String result = new ApiCaller().execute("http://goevent.azurewebsites.net/api/User/Name/"+userName, "GET").get();
            AppUser u = new JsonParser().JsonToAppUser(result);
            return Objects.equals(userName, u.getUserName());
        } catch (InterruptedException | JSONException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }

    }
}
