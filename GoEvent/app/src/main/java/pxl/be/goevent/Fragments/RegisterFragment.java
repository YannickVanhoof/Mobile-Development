package pxl.be.goevent.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import pxl.be.goevent.Activities.HomeActivity;
import pxl.be.goevent.ApiCaller;
import pxl.be.goevent.AppUser;
import pxl.be.goevent.JsonParser;
import pxl.be.goevent.R;

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        final TextView userNameText = rootView.findViewById(R.id.userName);
        String last_name = getActivity().getIntent().getStringExtra("last_name");
        final String user_name = getActivity().getIntent().getStringExtra("user_name");
        String first_name = getActivity().getIntent().getStringExtra("first_name");
        if (last_name != null){
            TextView lastNameText = rootView.findViewById(R.id.lastname);
            lastNameText.setText(last_name);
        }
        if (last_name != null && user_name != null){
            userNameText.setEnabled(false);
            userNameText.setText(user_name);
        }
        if (first_name != null){
            TextView firstNameText = rootView.findViewById(R.id.firstname);
            firstNameText.setText(first_name);
        }

        Button register = rootView.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (CheckIfUserNameAlreadyExist(userNameText.getText().toString())){
                        Toast.makeText(getActivity().getApplicationContext(), "Username already exist",Toast.LENGTH_SHORT).show();
                    }else {
                        Register(rootView);
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Not registered",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void Register(View view){
        AppUser user = CreateUser(view);
        user.setId(10);
        ApiCaller caller = new ApiCaller();
        JsonParser parser = new JsonParser();
        String json = parser.AppUserToJson(user);
            String result = null;
        try {
            result = caller.execute("http://goevent.azurewebsites.net/api/User" , "POST" ,json).get();
            Intent intent = new Intent(getActivity(), HomeActivity.class)
                    .putExtra("userName", user.getUserName());
            startActivity(intent);
            getActivity().finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
    private AppUser CreateUser(View view){
        AppUser user = new AppUser();
        TextView username = view.findViewById(R.id.userName);
        TextView password = view.findViewById(R.id.password);
        TextView email = view.findViewById(R.id.email);
        TextView firstname = view.findViewById(R.id.firstname);
        TextView lastName = view.findViewById(R.id.lastname);
        TextView address = view.findViewById(R.id.address);
        TextView city = view.findViewById(R.id.city);
        TextView postalCode = view.findViewById(R.id.postalcode);
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
        return result.contains(username);
    }
}
