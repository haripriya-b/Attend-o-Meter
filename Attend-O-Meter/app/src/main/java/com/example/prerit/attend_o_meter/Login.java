package com.example.prerit.attend_o_meter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.MailTo;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;


public class Login extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.prerit.attend_o_meter.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Parse.initialize(this, "c1Tcja7oupsKmxy7qbkcyJWPHrpLvUI0vYXlvbtr", "hHSIS28AEgqL4zo2EiuMtjXv0wS3CVgxDlaDVRBD");
        ParseObject.registerSubclass(UserDetails.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


    public void authorizeUser(View view) {
        //final ProgressDialog progress = ProgressDialog.show(Login.this,"","Authorizing..",true,false);

        final ProgressDialog progress = ProgressDialog.show(Login.this, "Login", "Logging in...", true, false);
        //ProgressDialog pd = new ProgressDialog(Login.this);

        new Thread(new Runnable() {
            public void run() {
                try {
                    login();
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
                progress.cancel();
            }
        }).start();

    }

    public void forgotPassword(View view) {

        Intent intent = new Intent(this,ForgotPassword.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void register(View view){
        Intent intent = new Intent(this,Registration.class);
        startActivity(intent);
    }
/*
    private class LoginAsyncTask extends AsyncTask<String, Void, String>{


        LoginAsyncTask() {
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd.setMessage("Loading...");
            pd.show();



        }
*/

    public void login () throws com.parse.ParseException {
     /*   @Override
        protected String doInBackground(String... usernam) {
          String ran = "";

            try {
     */           EditText emailText = (EditText) findViewById(R.id.email);
                EditText passText = (EditText) findViewById(R.id.password);
                final String username = emailText.getText().toString();
                final String password = passText.getText().toString();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("UserDetails");
                query.whereEqualTo("emailId", username);

/*
                query.findInBackground(new FindCallback<ParseObject>() {

                    public void done(List<ParseObject> emailList, com.parse.ParseException e) {
                        if (e == null) {*/

                            List<ParseObject> emailList = query.find();
                            if (emailList.get(0).get("password").toString().equals(password)) {

                              //  Log.d("email", " found in parse " + emailList.get(0).get("password") + " Ids");

                                Bundle extras = new Bundle();
                                extras.putString("id", username);

                                if(emailList.get(0).get("userType").toString().equals("Student")){

                                    Intent intentStudent = new Intent(Login.this, StudentHome.class);
                                    intentStudent.putExtras(extras);
                                    startActivity(intentStudent);}

                                else if(emailList.get(0).get("userType").toString().equals("Professor")){
                                    Intent intentProfessor = new Intent(Login.this, ProfessorHome.class);
                                    intentProfessor.putExtras(extras);
                                    startActivity(intentProfessor);
                                }
                            }
                        /*} else {
                            Log.d("", "Error: " + e.getMessage());
                        }

                    }*/


                    //ParseObject userDetails = new ParseObject("UserDetails");
              //  });



/*
                return ran;

            } catch (Exception e) {
                Log.e("Login async task exception", e.getMessage(), e);
            }
            return "";
        }

*/
   /*     @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            pd.dismiss();
*/



      //  }
    }



}


