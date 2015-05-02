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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StudentCourses extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.prerit.attend_o_meter.MESSAGE";
    private String username;
    private ArrayList<StudentCoursePojo> searchResults = new ArrayList<StudentCoursePojo>();
    private String courseId;
    private String courseName;
    private int attendedLectures;
    private int completedLectures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses);

        final ListView lv = (ListView) findViewById(R.id.scListView);


        Parse.initialize(this, "c1Tcja7oupsKmxy7qbkcyJWPHrpLvUI0vYXlvbtr", "hHSIS28AEgqL4zo2EiuMtjXv0wS3CVgxDlaDVRBD");

        Intent intent = getIntent();
        username= intent.getStringExtra(StudentHome.EXTRA_MESSAGE);
        //ParseObject.registerSubclass(Course.class);
        // Create the text view
        Toast.makeText(StudentCourses.this, "Welcome hi " + username, Toast.LENGTH_LONG).show();
        ParseObject.registerSubclass(Student_Course.class);
        ParseQuery<ParseObject> querySc = ParseQuery.getQuery("Student_Course");
        querySc.whereEqualTo("studentId", username);

        //ParseQuery<ParseObject> queryC = ParseQuery.getQuery("Course");
        //queryC.whereMatchesKeyInQuery("courseId", "courseId", querySc);
        querySc.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> courseList, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("Course size " + courseList.size(), "" + courseList.get(0).get("attendance") + " ");
                    for(int i=0;i<courseList.size();i++) {


                        StudentCoursePojo studentCoursePojo = new StudentCoursePojo();
                        String cid = courseList.get(i).get("courseId").toString();
                        studentCoursePojo.setCourse_id(cid);


                        //studentCoursePojo.setCourse_id(courseList.get(i).get("courseName").toString());

                        int currentAttendance = Integer.parseInt(courseList.get(i).get("attendance").toString());
                        //int completedLectures = Integer.parseInt(courseList.get(i).get("completedLectures").toString());
                        studentCoursePojo.setAttendance(currentAttendance);

                        searchResults.add(studentCoursePojo);
                    }

                    lv.setAdapter(new StudentCourseAdapter(StudentCourses.this, searchResults));
                    // Log.d("asdf",schedule.getCourseId());
                } else {
                    Log.d("", "Error: not working here");
                    Log.d("", "Error: " + e.getMessage());
                }
            }


        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {


                Object o = lv.getItemAtPosition(position);
                StudentCoursePojo fullObject = (StudentCoursePojo)o;
                Toast.makeText(StudentCourses.this, "You have chosen: " + position +" " +  fullObject.getAttendance(), Toast.LENGTH_LONG).show();
                fullObject.getCourse_id();
                Intent intent = new Intent(StudentCourses.this,CourseStudent.class);
                Bundle extras = new Bundle();
                extras.putString("userid",username);
                extras.putString("courseId",fullObject.getCourse_id());
                extras.putInt("attendance",fullObject.getAttendance());
                intent.putExtras(extras);
                startActivity(intent);

            }
        });



    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_courses, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putExtra(EXTRA_MESSAGE,username);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);


    }




}
