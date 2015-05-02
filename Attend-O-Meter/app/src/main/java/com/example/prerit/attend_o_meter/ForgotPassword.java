package com.example.prerit.attend_o_meter;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class ForgotPassword extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forgot_password, menu);
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



    public void resetPassword(View view) {

        EditText emailText = (EditText) findViewById(R.id.email);
        final String username = emailText.getText().toString();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserDetails");
        query.whereEqualTo("emailId", username);


        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> emailList, com.parse.ParseException e) {
                if (e == null) {
                    if (emailList.size() > 0) {

                        Log.d("email", " found in parse " + emailList.get(0).get("password") + " Ids");
                       AsyncTask<String, Void, String> newPass = new SendEmailAsyncTask().execute(username);
                        try {

                            emailList.get(0).put("password",newPass.get());
                            Log.d("email", " found in parse " + newPass.get() + " Ids");
                            Toast.makeText(ForgotPassword.this,"New password sent to registered email",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPassword.this,Login.class);
                            startActivity(intent);


                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        } catch (ExecutionException e1) {
                            e1.printStackTrace();
                        }

                    }
                } else {
                    Log.d("", "Error: " + e.getMessage());
                    Toast.makeText(ForgotPassword.this,"This email is not registered, please register first!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPassword.this,Login.class);
                    startActivity(intent);
                    //

                }
            }
        });


/*
    String randomString( int len )
    {
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
*/

    }

    private class SendEmailAsyncTask extends AsyncTask<String, Void, String>{
        SendEmailAsyncTask() {
        }


        @Override
        protected String doInBackground(String... username) {
            String ran = Long.toHexString(Double.doubleToLongBits(Math.random()));
            ran = ran.substring(1, 8);

            try {


                GMailSender sender = new GMailSender("submityourcviiitb@gmail.com", "godspeed123");
                sender.sendMail("Attend-O-Meter password reset",
                        "Your new password is " + ran,
                        username[0],
                        "hari2512@gmail.com");
                return ran;

            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }
        return "";
        }
    }
    }
