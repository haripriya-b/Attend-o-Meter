package com.example.prerit.attend_o_meter;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class StudentHome extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.prerit.attend_o_meter.MESSAGE";
    String id;
    String semester = "";
    private int currentAttendance=0;
    private String courseName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
//
//        ParseObject.registerSubclass(Semester.class);
//        ParseObject.registerSubclass(CourseSchedule.class);
//        ParseObject.registerSubclass(Course.class);
//        ParseObject.registerSubclass(Student_Course.class);

        final Bundle extras = getIntent().getExtras();
        id = extras.getString("id").toString();
        AsyncTask<String, Void, ArrayList<CoursePOJO>> searchResult = new StudentHomeAsyncTask().execute(id);
        ArrayList<CoursePOJO> searchResults = null;
        try {
            searchResults = searchResult.get();
            Log.d("sdf"+searchResults.get(0).getCourseName(),"");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        final ListView lv = (ListView) findViewById(R.id.listview1);
        lv.setAdapter(new CustomBaseAdapter(StudentHome.this, searchResults));


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                CoursePOJO fullObject = (CoursePOJO)o;
                Bundle extra = new Bundle();
                extra.putString("userid",Long.toString(id) );
                extra.putString("courseId",((CoursePOJO) o).getCourseID());
                extra.putInt("attendance", ((CoursePOJO) o).getAttendance());
                Intent intent = new Intent(StudentHome.this,CourseStudent.class);
                intent.putExtras(extra);
                startActivity(intent);
                //Toast.makeText(StudentHome.this, "You have chosen: " + " " + fullObject.getCourseID(), Toast.LENGTH_LONG).show();
            }
        });

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:


            case R.id.action_add_course:
                goToAdd();
                break;

            case R.id.myCourses:
                viewMyCourses();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void viewMyCourses() {
        Intent intent1 = new Intent(this,StudentCourses.class);
        intent1.putExtra(EXTRA_MESSAGE, this.id);
        startActivity(intent1);

    }

    public void goToAdd() {
        Bundle extras = new Bundle();
        extras.putString("id",id);
        Intent intentAdd = new Intent(this, AddCourseStudent.class);
        intentAdd.putExtras(extras);
        startActivity(intentAdd);
    }

    private class StudentHomeAsyncTask extends AsyncTask<String, Void, ArrayList<CoursePOJO>>{
        StudentHomeAsyncTask(){

        }
        @Override
        protected ArrayList<CoursePOJO> doInBackground(String... id) {
            ArrayList<CoursePOJO> searchResults = new ArrayList<CoursePOJO>();
            try {
                searchResults = getResults();
            } catch (ParseException e) {
                e.printStackTrace();
            }



            return searchResults;
        }
        public ArrayList<CoursePOJO> GetSearchResults() throws ParseException {

            Parse.initialize(StudentHome.this, "c1Tcja7oupsKmxy7qbkcyJWPHrpLvUI0vYXlvbtr", "hHSIS28AEgqL4zo2EiuMtjXv0wS3CVgxDlaDVRBD");
            ParseObject.registerSubclass(Semester.class);

            ParseObject.registerSubclass(Course.class);
            ParseObject.registerSubclass(Student_Course.class);

            ArrayList<CoursePOJO> results = new ArrayList<CoursePOJO>();

            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEEE");
            Date currentDate;
            Calendar calendar = Calendar.getInstance();
            String dt = dateFormat1.format(calendar.getTime());
            currentDate = dateFormat1.parse(dt);
            String day = dateFormat2.format(calendar.getTime());
            //  Log.i("date:", currentDate.toString());

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Semester");
            try {
                List<ParseObject> result = query.find();
                Date sdate, edate;
                for(int i=0;i<result.size();i++) {
                    sdate = result.get(i).getDate("startDate");
                    edate = result.get(i).getDate("endDate");
                    if(currentDate.after(sdate) && currentDate.before(edate)) {
                        semester = result.get(i).getString("semesterName");
                        break;
                    }
                }
                //Log.i("semester:", semester);

            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

            if(semester.equals("")) {
                CoursePOJO course = new CoursePOJO();
                course.setCourseName("No ongoing Semster");
                results.add(course);
                return results;
            }else {

                CoursePOJO coursePOJO;
                ArrayList<String> course_ids = new ArrayList<>();
                Map<String, Integer> attendance = new HashMap<String, Integer>();

                ParseQuery<ParseObject> query_stud_cou = ParseQuery.getQuery("Student_Course");
                query_stud_cou.whereEqualTo("studentId", id);
                try{
                    List<ParseObject> result_stud_cou = query_stud_cou.find();
                    if(result_stud_cou.size() == 0) {
                        CoursePOJO course = new CoursePOJO();
                        course.setCourseName("No Courses added for this Semester!");
                        results.add(course);
                        return results;
                    }else {
                        for(int i=0;i<result_stud_cou.size();i++) {
                            course_ids.add(result_stud_cou.get(i).getString("courseId").toString());
                            attendance.put(result_stud_cou.get(i).getString("courseId").toString(), result_stud_cou.get(i).getInt("attendance"));
                            currentAttendance = result_stud_cou.get(i).getInt("attendance");
                            Log.i("courses ", result_stud_cou.get(i).getString("courseId").toString());
                        }
                    }
                }catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                ParseQuery<ParseObject> query_courses = ParseQuery.getQuery("Course");
                query_courses.whereEqualTo("semester", semester);
                query_courses.whereContainedIn("courseId", course_ids);

                try {
                    List<ParseObject> result_courses = query_courses.find();
                    String id, name, time="",  minAttdn;
                    int totLec, compLec;
                    if (result_courses.size() == 0) {
                        CoursePOJO course = new CoursePOJO();
                        course.setCourseName("No Courses added for this Semester!");
                        results.add(course);
                        return results;
                    }else {

                        for(int i=0;i<result_courses.size();i++) {

                            id = result_courses.get(i).getString("courseId");
                            name = result_courses.get(i).getString("courseName");

                            totLec = result_courses.get(i).getInt("totalLectures");
                            compLec = result_courses.get(i).getInt("completedLectures");
                            minAttdn = result_courses.get(i).getString("minAttendance");
                            ParseObject.registerSubclass(CourseSchedule.class);
                            ParseQuery<ParseObject> query_time = ParseQuery.getQuery("CourseSchedule");
                            query_time.whereEqualTo("courseId", id);
                            query_time.whereEqualTo("dayOfWeek", day);

                            Log.i("day:", day);
                            Log.i("id:", id);
                            try {
                                List<ParseObject> result_schd = query_time.find();

                                if (result_schd.size() == 0) {
                                    CoursePOJO course = new CoursePOJO();
                                    course.setCourseName("No Classes Today!");
                                    results.add(course);
                                    break;
                                } else {
                                    //Log.i("size:", Integer.toString(result_schd.size()));
                                    time = result_schd.get(0).getNumber("timeHrs").toString() + ":" + result_schd.get(0).getNumber("timeMins").toString()
                                            + " " + result_schd.get(0).getString("AM_PM");
                                }
                            } catch (com.parse.ParseException e) {
                                e.printStackTrace();
                            }

                            // Log.i("course:", name);
                            //Log.i("time:", time);
                            if (!time.equals("")) {
                                Date d1;
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                                d1 = dateFormat.parse(time);

                                coursePOJO = new CoursePOJO();
                                coursePOJO.setCourseID(id);
                                coursePOJO.setCourseName(name);
                                coursePOJO.setTime(d1);
                                coursePOJO.setCompClasses(compLec);
                                coursePOJO.setMin_attdn(minAttdn);
                                coursePOJO.setTotClasses(totLec);
                                coursePOJO.setAttendance(attendance.get(id));
                                results.add(coursePOJO);
                            }
                        }
                    }


                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

            }
            return results;

        }

        public ArrayList<CoursePOJO> getResults() throws ParseException {
            ArrayList<CoursePOJO> results = new ArrayList<CoursePOJO>();
            Date d1;
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            String time;
            CoursePOJO coursePOJO;


            time= "9:30 AM";
            d1 = dateFormat.parse(time);
            coursePOJO = new CoursePOJO();
            coursePOJO.setCourseID("DS101");
            coursePOJO.setCourseName("Data Modelling");
            coursePOJO.setTime(d1);
            coursePOJO.setCompClasses(29);
            coursePOJO.setMin_attdn("55");
            coursePOJO.setTotClasses(32);
            coursePOJO.setAttendance(12);
            results.add(coursePOJO);

            time= "12:20 PM";
            d1 = dateFormat.parse(time);
            coursePOJO = new CoursePOJO();
            coursePOJO.setCourseID("NS101");
            coursePOJO.setCourseName("WAN");
            coursePOJO.setTime(d1);
            coursePOJO.setCompClasses(26);
            coursePOJO.setMin_attdn("50");
            coursePOJO.setTotClasses(30);
            coursePOJO.setAttendance(17);
            results.add(coursePOJO);




            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<CoursePOJO> coursePOJOs) {
            super.onPostExecute(coursePOJOs);
        }
    }
}
