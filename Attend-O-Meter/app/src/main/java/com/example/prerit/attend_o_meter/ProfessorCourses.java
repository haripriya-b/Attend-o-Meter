package com.example.prerit.attend_o_meter;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ProfessorCourses extends ListActivity {
    public final static String EXTRA_MESSAGE = "com.example.prerit.attend_o_meter.MESSAGE";
    //private static final int PROGRESS = 0x1;
    private String username;
    private String name;
    private String id;
    // ArrayAdapter<String> adapter = null;
    //ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_courses);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //ProgressDialog progress = new ProgressDialog(this);

        Parse.initialize(this, "c1Tcja7oupsKmxy7qbkcyJWPHrpLvUI0vYXlvbtr", "hHSIS28AEgqL4zo2EiuMtjXv0wS3CVgxDlaDVRBD");
        ParseObject.registerSubclass(Course.class);
        Intent intent = getIntent();
        username= intent.getStringExtra(ProfessorHome.EXTRA_MESSAGE);
        // Create the text view
      //  Toast.makeText(ProfessorCourses.this, "Welcome " + username, Toast.LENGTH_LONG).show();
        //creating the adapter


        // values.add("Android");
        //  values.add("iPhone");


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Course");
        query.whereEqualTo("professor", username);

        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> courseList, com.parse.ParseException e) {
                if (e == null) {
                    ArrayList<String> values = new ArrayList<String>();
                    for (int i = 0; i < courseList.size(); i++) {
                        id = courseList.get(i).get("courseId").toString();
                        name = courseList.get(i).get("courseName").toString();

                        values.add(id+"-"+name);

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfessorCourses.this, android.R.layout.simple_list_item_1, values);
                    setListAdapter(adapter);

                    // Log.d("email - ", username);
                    // Log.d("email", " found in parse " + courseList.get(1).get("courseId").toString() + " Ids");

                    // ArrayAdapter<String> adapter;

                    //listView = (ListView)findViewById(R.id.list);


                    //    listView.setAdapter(adapter);
                    //   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    //  @Override
                    //public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                }

                else {
                    Log.d("", "Error: not working here");
                    Log.d("", "Error: " + e.getMessage());
                }
            }


        });


    }
//    @Override
//    public void onBackPressed() {
//        Intent intentProfessor = new Intent(this, ProfessorHome.class);
//        intentProfessor.putExtra(EXTRA_MESSAGE, username);
//        startActivity(intentProfessor);
//    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_professor_courses, menu);
        //return true;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_professor_courses, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                Intent upIntent = NavUtils.getParentActivityIntent(this);
//                upIntent.putExtra(EXTRA_MESSAGE,username);
//                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
//                    // This activity is NOT part of this app's task, so create a new task
//                    // when navigating up, with a synthesized back stack.
//                    TaskStackBuilder.create(this)
//                            // Add all of this activity's parents to the back stack
//                            .addNextIntentWithParentStack(upIntent)
//                                    // Navigate up to the closest parent
//                            .startActivities();
//                } else {
//                    // This activity is part of this app's task, so simply
//                    // navigate up to the logical parent activity.
//                    NavUtils.navigateUpTo(this, upIntent);
//                }
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String clickedDetail = (String)l.getItemAtPosition(position);
        //Log.i("click:", clickedDetail);
        String[] temp = clickedDetail.split(new String("-"));
        Log.i("splited",temp[0]);
        Intent intent = new Intent(this,CourseProfessor.class);
        intent.putExtra(EXTRA_MESSAGE, temp[0]/*courseId */);
        startActivity(intent);
        //Toast.makeText(ProfessorCourses.this, "Welcome " + clickedDetail, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(this,ProfessorHome.class);
        intent1.putExtra(EXTRA_MESSAGE, this.username);
        startActivity(intent1);
        //finish();
    }

}

