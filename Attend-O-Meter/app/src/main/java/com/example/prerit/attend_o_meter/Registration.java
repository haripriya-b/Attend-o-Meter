package com.example.prerit.attend_o_meter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class Registration extends ActionBarActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Parse.initialize(this, "c1Tcja7oupsKmxy7qbkcyJWPHrpLvUI0vYXlvbtr", "hHSIS28AEgqL4zo2EiuMtjXv0wS3CVgxDlaDVRBD");
        ParseObject.registerSubclass(UserDetails.class);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);

        Spinner spinner = (Spinner) findViewById(R.id.userType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_userType, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void registerDetails(View view) {

        EditText editText_email = (EditText) findViewById(R.id.ed_email);
        String email = editText_email.getText().toString();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserDetails");
        query.whereEqualTo("emailId",email);

        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> emailList, com.parse.ParseException e) {
                if (e == null) {
                    if (emailList.size() != 0) {
                        Toast.makeText(Registration.this, "Email already exits.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Registration.this, Registration.class);
                        startActivity(intent);
                        return;
                    } else {
                        Spinner mySpinner = (Spinner) findViewById(R.id.userType);
                        String user = mySpinner.getSelectedItem().toString();

                        EditText editText_fname = (EditText) findViewById(R.id.ed_fname);
                        String fname = editText_fname.getText().toString();

                        EditText editText_lname = (EditText) findViewById(R.id.ed_lname);
                        String lname = editText_lname.getText().toString();

                        EditText editText_email = (EditText) findViewById(R.id.ed_email);
                        String email = editText_email.getText().toString();

                        EditText editText_password = (EditText) findViewById(R.id.ed_password);
                        String password = editText_password.getText().toString();

                        ParseObject registrationDetails = ParseObject.create("UserDetails");
                        registrationDetails.put("userType",user);
                        registrationDetails.put("emailId",email);
                        registrationDetails.put("firstName",fname);
                        registrationDetails.put("lastName",lname);
                        registrationDetails.put("password",password);

                        registrationDetails.saveInBackground();

                        Toast.makeText(Registration.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Registration.this, Login.class);
                        startActivity(intent);
                    }
                } else {
                    Log.d("", "Error: " + e.getMessage());
                    Toast.makeText(Registration.this, "Cannot connect to database", Toast.LENGTH_LONG).show();
                    return;

                }
            }
        });

    }
}
