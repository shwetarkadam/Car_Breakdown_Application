package com.parse.starter;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class CarDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    EditText carModelText;
    EditText phoneDetailsText;
    EditText vehicleNumberText;
    Boolean entry=false;






    public void submit(View view){

              details();
              if(entry){


                  Intent intent = new Intent(getApplicationContext(),RiderActivity.class);
                  startActivity(intent);


              }


          }



        public void details(){


            carModelText=(EditText)findViewById(R.id.carModelText);
            phoneDetailsText=(EditText)findViewById(R.id.phoneNumberText);
            vehicleNumberText=(EditText)findViewById(R.id.vehicleNumberText);

            String a = carModelText.getText().toString();
            String b = phoneDetailsText.getText().toString();
            String c = vehicleNumberText.getText().toString();


            if (a.matches("") || b.matches("") || c.matches("")) {

                Toast.makeText(this, "All Entries Required", Toast.LENGTH_SHORT).show();
                entry =false;
            }
            else{
                ParseObject carDetails = new ParseObject("CarDetails");
                carDetails.put("username", ParseUser.getCurrentUser().getUsername());
                carDetails.put("carModel", a);
                carDetails.put("phoneDetails",b);
                carDetails.put("number", c);

                boolean t =true;
                
                carDetails.saveInBackground();
                entry=true;


            }


        }










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);


        ConstraintLayout backgroundLayout = (ConstraintLayout) findViewById(R.id.backgroundRelLayout);

        backgroundLayout.setOnClickListener(this);


        ParseAnalytics.trackAppOpenedInBackground(getIntent());





    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.backgroundRelLayout) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


        }

    }
}
