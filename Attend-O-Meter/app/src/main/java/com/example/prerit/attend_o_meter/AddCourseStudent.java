package com.example.prerit.attend_o_meter;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddCourseStudent extends ActionBarActivity implements StudentSemesterDialogFragment.StudentSemesterDialogListener {

    Map<String, String> coursesDetails = new HashMap<>();
    String semester;
    ArrayList<String> courses = new ArrayList<>();
    Map<String, Boolean> chk_box_course = new HashMap<>();
    String sid;
    ArrayAdapter<String> dataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_student);

        Parse.initialize(this, "c1Tcja7oupsKmxy7qbkcyJWPHrpLvUI0vYXlvbtr", "hHSIS28AEgqL4zo2EiuMtjXv0wS3CVgxDlaDVRBD");
	    ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseObject.registerSubclass(Semester.class);
        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(Student_Course.class);

        Bundle bundle = getIntent().getExtras();
        sid = bundle.getString("id");

        showStudentsSemesterDialog();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_course_student, menu);
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

    public void showStudentsSemesterDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new StudentSemesterDialogFragment();
        dialog.show(getSupportFragmentManager(), "StudentSemesterDialogFragment");
    }

    @Override
    public void setSemester(String sem) {
        this.semester = sem;
        Toast.makeText(this, semester, Toast.LENGTH_LONG).show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Course");
        query.selectKeys(Arrays.asList("courseId", "courseName"));
        query.whereEqualTo("semester", semester);


        try {
            List<ParseObject> result = query.find();

            for (int i = 0; i < result.size(); i++) {
                coursesDetails.put(result.get(i).getString("courseId"), result.get(i).getString("courseName"));
                courses.add(result.get(i).getString("courseId") + " " + result.get(i).getString("courseName"));
                chk_box_course.put(result.get(i).getString("courseId") + " " + result.get(i).getString("courseName"), false);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) findViewById(R.id.list);
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courses);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                String course = parent.getItemAtPosition(position).toString();

                ParseObject student_course = ParseObject.create("Student_Course");
                String[] cou = course.split(" ");
                String cid = cou[0];

                student_course.put("courseId",cid);
                student_course.put("studentId",sid);
                student_course.put("attendance",0);

                student_course.saveInBackground();
                //Log.i("cid",cid);
                //Log.i("sid",sid);

		ParsePush.subscribeInBackground(cid, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                   // Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    //Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

                Toast.makeText(getApplicationContext(),
                        cid+ " Added!",
                        Toast.LENGTH_LONG).show();

                Bundle extras = new Bundle();
                extras.putString("id", sid);

                Intent intentStudent = new Intent(AddCourseStudent.this, StudentHome.class);
                intentStudent.putExtras(extras);
                startActivity(intentStudent);


            }
        });

    }

}
