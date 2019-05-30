package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Boolean signUpModeActive = true;

    TextView changeSignupModeTextView;

    Switch userTypeSwitch ;
    EditText passwordEditText;

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

            signUp(view);

        }

        return false;
    }


    public void redirectActivity() {

        userTypeSwitch = (Switch) findViewById(R.id.userTypeSwitch);

        if (ParseUser.getCurrentUser().getString("riderOrDriver").equals("rider")) {

            if (!userTypeSwitch.isChecked() && signUpModeActive) {
                Intent intent = new Intent(getApplicationContext(), CarDetailsActivity.class);
                startActivity(intent);


            }
            else {
                Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
                startActivity(intent);
            }


        } else {

            Intent intent = new Intent(getApplicationContext(), ViewRequestsActivity.class);
            startActivity(intent);


        }
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.changeSignUpTextView) {

            Button signupButton = (Button) findViewById(R.id.button);
            userTypeSwitch = (Switch) findViewById(R.id.userTypeSwitch);
            TextView riderTextView = (TextView) findViewById(R.id.riderTextView);
            TextView driverTextView = (TextView) findViewById(R.id.driverTextView);


            if (signUpModeActive) {

                signUpModeActive = false;
                signupButton.setText("Login");
                changeSignupModeTextView.setText("Or, Signup");
                userTypeSwitch.setVisibility(view.INVISIBLE);
                riderTextView.setVisibility(view.INVISIBLE);
                driverTextView.setVisibility(view.INVISIBLE);


            } else {

                signUpModeActive = true;
                signupButton.setText("Signup");
                changeSignupModeTextView.setText("Or, Login");
                userTypeSwitch.setVisibility(view.VISIBLE);
                riderTextView.setVisibility(view.VISIBLE);
                driverTextView.setVisibility(view.VISIBLE);


            }

        }
        else if (view.getId() == R.id.backgroundRelativeLayout) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


        }


    }



    public void signUp(View view) {

        EditText usernameEditText = (EditText) findViewById(R.id.userNameEditText);

        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

       userTypeSwitch = (Switch)findViewById(R.id.userTypeSwitch);






        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();

        } else {

            if (signUpModeActive) {

               String userType = "rider";


                ParseUser user = new ParseUser();
                if (userTypeSwitch.isChecked()) {
                    Log.i("switch",userTypeSwitch.toString());

                    userType = "driver";

                }

                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                Log.i("switch",userTypeSwitch.toString());

                user.put("riderOrDriver", userType);


                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            Log.i("Signup", "Successful");
                             redirectActivity();



                        } else {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            } else {



                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user != null) {

                            Log.i("Signup", "Login successful");
                            redirectActivity();

                        } else {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                });


            }


        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeSignupModeTextView = (TextView) findViewById(R.id.changeSignUpTextView);

        changeSignupModeTextView.setOnClickListener(this);

        RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayout);

        backgroundRelativeLayout.setOnClickListener(this);




        setTitle("Vehicle Assistance");




        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

}