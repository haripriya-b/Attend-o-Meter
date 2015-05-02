package com.example.prerit.attend_o_meter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentsInCourse extends ActionBarActivity {

    String courseID = "";
    int compLec = 0;
    ArrayList<StudentsInCoursePOJO> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_in_course);

        Parse.initialize(this, "c1Tcja7oupsKmxy7qbkcyJWPHrpLvUI0vYXlvbtr", "hHSIS28AEgqL4zo2EiuMtjXv0wS3CVgxDlaDVRBD");
        ParseObject.registerSubclass(UserDetails.class);
        ParseObject.registerSubclass(Student_Course.class);
        ParseObject.registerSubclass(Course.class);

        Bundle extras = getIntent().getExtras();
        courseID = extras.getString("cid");
        compLec = extras.getInt("compLec");

        searchResults = new ArrayList<StudentsInCoursePOJO>();
        try {
            searchResults = GetSearchResults();
            Log.i("result", searchResults.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final ListView lv = (ListView) findViewById(R.id.listview2);
        lv.setAdapter(new StudentsInCourseAdapter(StudentsInCourse.this, searchResults));


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                //Toast.makeText(ProfessorHome.this, "You have chosen: " + " " + fullObject.getCourseID(), Toast.LENGTH_LONG).show();
            }

        });

    }


    private ArrayList<StudentsInCoursePOJO> GetSearchResults() throws ParseException {

        ArrayList<StudentsInCoursePOJO> results = new ArrayList<StudentsInCoursePOJO>();
        Map<String, Integer> stud_attd = new HashMap<>();
        Map<String, String> stud_name = new HashMap<>();
        ArrayList<String> studentIds = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Student_Course");
        query.whereEqualTo("courseId", courseID);

        try {
            List<ParseObject> result = query.find();
            Log.i("size", Integer.toString(result.size()));
            for(int i=0;i<result.size();i++) {
                int attnd = result.get(i).getInt("attendance");
                attnd = (attnd*100)/compLec;
                stud_attd.put(result.get(i).getString("studentId"), attnd);
                studentIds.add(result.get(i).getString("studentId"));
            }
            Log.i("sid", studentIds.toString());
            //Log.i("s_a", stud_aatnd.toString());
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        if (studentIds.size() == 0) {
            StudentsInCoursePOJO student = new StudentsInCoursePOJO();
            student.setStudentName(new String("No enrolled students"));
        }else {

            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("UserDetails");
            query1.whereContainedIn("emailId",studentIds);
            query1.selectKeys(Arrays.asList("emailId", "firstName", "lastName"));

            try {
                List<ParseObject> result = query1.find();
                Log.i("size1", Integer.toString(result.size()));
                for(int i=0;i<result.size();i++) {
                    String stu_id = result.get(i).getString("emailId").toString();
                    String stu_name = result.get(i).getString("firstName") + " " + result.get(i).getString("lastName");
                    stud_name.put(stu_id, stu_name);
                }
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            StudentsInCoursePOJO stud;
            for(int i=0;i<studentIds.size();i++) {
                stud = new StudentsInCoursePOJO();
                stud.setStudentName(stud_name.get(studentIds.get(i)));
                stud.setAttendance(stud_attd.get(studentIds.get(i)));

                results.add(stud);

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
