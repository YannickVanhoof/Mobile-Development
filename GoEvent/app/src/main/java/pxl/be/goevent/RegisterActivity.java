package pxl.be.goevent;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by 11500046 on 4/11/2017.
 */

public class RegisterActivity extends Application{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final TextView   userNameText = (TextView) findViewById(R.id.userName);
       String last_name = getIntent().getStringExtra("last_name");
        final String user_name = getIntent().getStringExtra("user_name");
        String first_name = getIntent().getStringExtra("first_name");
        if (last_name != null){
            TextView lastNameText = (TextView) findViewById(R.id.lastname);
            Log.d("last" ,lastNameText +"");
            lastNameText.setText(last_name);
        }
        if (last_name != null && user_name != null){
            Log.d("USERNAME" , user_name);
            userNameText.setEnabled(false);
            userNameText.setText(user_name);
        }
        if (first_name != null){
            TextView firstNameText = (TextView) findViewById(R.id.firstname);
            firstNameText.setText(first_name);
        }

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (CheckIfUserNameAlreadyExist(userNameText.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Username already exist",Toast.LENGTH_SHORT).show();
                    }else {
                        Register();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Not registered",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void Register(){
        AppUser user = CreateUser();
        user.setId(10);
        ApiCaller caller = new ApiCaller();
        JsonParser parser = new JsonParser();
        String json = parser.AppUserToJson(user);
        Log.d("json" , json);
        String result = null;
        try {
            result = caller.execute("http://goevent.azurewebsites.net/api/User" , "POST" ,json).get();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.putExtra("Username" , user.getUserName());
            startActivity(intent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("result" , result);

    }
    private AppUser CreateUser(){
        AppUser user = new AppUser();
        TextView username = (TextView) findViewById(R.id.userName);
        TextView password = (TextView) findViewById(R.id.password);
        TextView email = (TextView) findViewById(R.id.email);
        TextView firstname = (TextView) findViewById(R.id.firstname);
        TextView lastName = (TextView) findViewById(R.id.lastname);
        TextView address = (TextView) findViewById(R.id.address);
        TextView city = (TextView) findViewById(R.id.city);
        TextView postalCode = (TextView) findViewById(R.id.postalcode);
        user.setUserName(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(email.getText().toString());
        user.setFirstname(firstname.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setAddress(address.getText().toString());
        user.setCity(city.getText().toString());
        user.setPostalCode(Integer.parseInt(postalCode.getText().toString()));
        return user;
    }
    private boolean CheckIfUserNameAlreadyExist(String username) throws ExecutionException, InterruptedException {
        ApiCaller caller = new ApiCaller();
        String result = caller.execute("http://goevent.azurewebsites.net/api/User/Name/"+username , "Get").get();
        Log.d("aa" , username);
        Log.d("aa" , result);
        Log.d("result" ,(result.contains(username)) +"");
        return result.contains(username);
    }
}
