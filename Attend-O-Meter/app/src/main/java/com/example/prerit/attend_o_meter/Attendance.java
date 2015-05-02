package com.example.prerit.attend_o_meter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Attendance extends ActionBarActivity {

    String courseID = "";
    String courseObjID = "";
    String profID = "";
    ArrayList<StudentPOJO> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Parse.initialize(this, "c1Tcja7oupsKmxy7qbkcyJWPHrpLvUI0vYXlvbtr", "hHSIS28AEgqL4zo2EiuMtjXv0wS3CVgxDlaDVRBD");
        ParseObject.registerSubclass(UserDetails.class);
        ParseObject.registerSubclass(Student_Course.class);
        ParseObject.registerSubclass(Course.class);

        Bundle extras = getIntent().getExtras();
        courseID = extras.getString("courseID");
        courseObjID = extras.getString("courseObjID");
        profID = extras.getString("profID");
        //Log.i("objID", courseObjID);

        searchResults = new ArrayList<StudentPOJO>();
        try {
            searchResults = GetSearchResults();
           // Log.i("result", searchResults.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final ListView lv = (ListView) findViewById(R.id.listview2);
        lv.setAdapter(new AttendanceBaseAdapter(this, searchResults));


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                //Toast.makeText(ProfessorHome.this, "You have chosen: " + " " + fullObject.getCourseID(), Toast.LENGTH_LONG).show();
            }

        });

        }

    public void markAttendance(View view) {

        final ProgressDialog progress = ProgressDialog.show(Attendance.this, "Marking Attendance", "Marking Attendance", true, false);

        new Thread(new Runnable() {
            public void run() {
                mark();
                progress.cancel();
            }
        }).start();


    }

    public void mark() {
        for(int i=0;i<searchResults.size();i++) {
            if(searchResults.get(i).isAttended() == true) {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Student_Course");

                try {
                    ParseObject result = query.get(searchResults.get(i).getObjectID());
                    result.increment("attendance");
                    result.save();
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Course");

        // Retrieve the object by id
        try {
            ParseObject result = query.get(courseObjID);
            result.increment("completedLectures");
            result.save();
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putString("id",profID );

        Intent intentProfessorHome = new Intent(Attendance.this, ProfessorHome.class);
        intentProfessorHome.putExtras(extras);
        startActivity(intentProfessorHome);
    }


    private ArrayList<StudentPOJO> GetSearchResults() throws ParseException {

        ArrayList<StudentPOJO> results = new ArrayList<StudentPOJO>();
        Map<String, String> stud_objID = new HashMap<>();
        ArrayList<String> studentIds = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Student_Course");
        query.whereEqualTo("courseId", courseID);

        try {
            List<ParseObject> result = query.find();
           // Log.i("size", Integer.toString(result.size()));
            for(int i=0;i<result.size();i++) {
                stud_objID.put(result.get(i).getString("studentId"), result.get(i).getObjectId());
                studentIds.add(result.get(i).getString("studentId"));
                }
            //Log.i("sid", studentIds.toString());
            //Log.i("s_a", stud_aatnd.toString());
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        if (studentIds.size() == 0) {
            StudentPOJO student = new StudentPOJO();
            student.setStudentName(new String("No enrolled students"));
        }else {

            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("UserDetails");
            query1.whereContainedIn("emailId",studentIds);
            query1.selectKeys(Arrays.asList("emailId", "firstName", "lastName"));

            try {
                List<ParseObject> result = query1.find();
               // Log.i("size1", Integer.toString(result.size()));
                StudentPOJO stud;
                for(int i=0;i<result.size();i++) {
                    stud = new StudentPOJO();
                    stud.setStudentName(result.get(i).getString("firstName") + " " + result.get(i).getString("lastName"));
                    String studentID = result.get(i).getString("emailId");
                    stud.setStudentID(studentID);
                    stud.setObjectID(stud_objID.get(studentID));

                    results.add(stud);


                }
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

        }


        return results;

    }



        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attendance, menu);
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
}
