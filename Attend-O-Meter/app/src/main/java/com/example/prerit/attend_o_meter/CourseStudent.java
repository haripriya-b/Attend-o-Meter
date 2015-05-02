package com.example.prerit.attend_o_meter;

import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CourseStudent extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.prerit.attend_o_meter.MESSAGE";
    private String username;
    private String courseId;
    private String courseName;
    private String semester;
    private String proffId;
    private int totalLectures;
    private int completedLectures;
    private int minAttendance;
    private int currentAttendance;
    private int maxMarks;
    private ArrayList<SchedulePOJO> searchResults = new ArrayList<SchedulePOJO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_student);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("userid");
        courseId = bundle.getString("courseId");
        currentAttendance = bundle.getInt("attendance");
        AsyncTask<String, Void, String> aString = new CourseStudentAsyncTask().execute(username);
        TextView tv_courseId = (TextView)findViewById(R.id.tv_courseId);
        tv_courseId.setText(String.format("%-25s%s\n","Course ID:",this.courseId));
        ParseObject.registerSubclass(Course.class);
        ParseQuery<ParseObject> queryCourse = ParseQuery.getQuery("Course");
        queryCourse.whereEqualTo("courseId", courseId);

        queryCourse.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> courseList1, com.parse.ParseException e) {
                if (e == null) {
                    //  Log.d("Course name "    ,""
                    //            +  courseList.get(0).get("semester").toString());

                    courseName = courseList1.get(0).get("courseName").toString();
                   // Log.d("name","" + courseName);
                    minAttendance = Integer.parseInt(courseList1.get(0).get("minAttendance").toString());
                    maxMarks = Integer.parseInt(courseList1.get(0).get("maxMarks").toString());
                    totalLectures = Integer.parseInt(courseList1.get(0).get("totalLectures").toString());
                    completedLectures =  Integer.parseInt(courseList1.get(0).get("completedLectures").toString());
                    proffId = courseList1.get(0).get("professor").toString();

                    semester = courseList1.get(0).get("semester").toString();


                    TextView tv_courseName = (TextView)findViewById(R.id.tv_courseName);
                    String text = String.format("%-20s%s\n","Course Name:",courseName);
                    tv_courseName.setText(text);



                    TextView tv_lectureNum = (TextView)findViewById(R.id.tv_lectureNum);
                    //tv_lectureNum.setText("Course progress:             " + Integer.toString(completedLectures) + "/" + Integer.toString(totalLectures)) ;
                    //String text2 = String.format("Course progress :   ",Integer.toString(completedLectures) + "/" + Integer.toString(totalLectures));
                    String text2 = String.format("%-20s%s\n","Course progress:",Integer.toString(completedLectures) + "/" + Integer.toString(totalLectures));
                    tv_lectureNum.setText(text2);
                    TextView tv_semName = (TextView)findViewById(R.id.semester_name);
                    String text3 = String.format("%-25s%s\n","Semester:",semester);
                    //String text3 = String.format("Semester        :   ",semester);
                    // tv_semName.setText("Semester:               " + semester);
                    tv_semName.setText(text3);

                    TextView tv_minAttdn = (TextView)findViewById(R.id.tv_minAttdn);
                    //String text4 = String.format("Min Attendance :   ",minAttendance+"%");
                    String text4 = String.format("%-20s%s\n","Min Attendance:",minAttendance+"%");
                    //tv_minAttdn.setText("Min Attendance:                " + minAttendance + "%");
                    tv_minAttdn.setText(text4);
//                    Course course = new Course();
//                    course = (Course)courseList1.get(0);

                    int attendance = (currentAttendance * 100)/completedLectures;
                    //Log.d("attendance " + currentAttendance + " " + completedLectures,"" + attendance);
                    TextView tv_myAttendance = (TextView)findViewById(R.id.tv_myAttendance);
                    //String text5 = String.format("My Attendance  :   ",attendance+"%");
                    //tv_minAttdn.setText("Min Attendance:                " + minAttendance + "%");
                    String text5 = String.format("%-21s%s\n","My Attendance:",attendance+"%");
                    tv_myAttendance.setText(text5);

                    int marks = (currentAttendance*maxMarks)/completedLectures;
                    TextView tv_myMarks = (TextView)findViewById(R.id.tv_myMarks);
                    String text6 = String.format("%-25s%s\n","My Marks:",marks);
                    //String text6 = String.format("My Marks       :   ",marks);
                    //tv_minAttdn.setText("Min Attendance:                " + minAttendance + "%");
                    tv_myMarks.setText(text6);

//
// totalLectures = course.getTotalLectures();
//
//                    completedLectures = course.getCompletedLectures();
//                   minAttendance = course.getMinAttendance();
//                   courseName = course.getCourseName();
                }

                else {
                    Log.d("", "Error: not working here");
                    Log.d("", "Error: " + e.getMessage());
                }
            }


        });









        /*
        For the course schedule using custom adaptor, schedulePOJO
         */





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



        switch(id){
            case R.id.deleteCourse:
                deleteCourse();
                break;
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
    private class CourseStudentAsyncTask extends AsyncTask<String, Void, String> {
        CourseStudentAsyncTask() {
        }
        @Override
        protected String doInBackground(String... username) {
            final ListView lv = (ListView) findViewById(R.id.schedule_listView);

            try {


                ParseObject.registerSubclass(CourseSchedule.class);
                ParseQuery<ParseObject> querySchedule = ParseQuery.getQuery("CourseSchedule");
                querySchedule.whereEqualTo("courseId", courseId);
                //query.whereEqualTo("courseId", courseId)

                querySchedule.findInBackground(new FindCallback<ParseObject>() {

                    public void done(List<ParseObject> courseList, com.parse.ParseException e) {
                        if (e == null) {
                           // Log.d("Course size " + courseList.size(), "" + courseList.get(0).get("dayOfWeek").toString() + " "
                               //     + courseList.get(0).get("timeHrs").toString() + " "
                                 //   + courseList.get(0).get("timeMins").toString());


                            for(int i=0;i<courseList.size();i++){
                                //dayOfWeek.add(courseList.get(i).get("dayOfWeek").toString());
                                //timeHrs.add(courseList.get(i).get("timeHrs").toString());
                                // CourseSchedule schedule = new CourseSchedule();
                                //schedule = (CourseSchedule)courseList.get(i);
                                Date time;
                                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");

                                try {
                                    SchedulePOJO schedulePOJO = new SchedulePOJO();

                                    time = dateFormat.parse(courseList.get(i).get("timeHrs").toString()/*schedule.getTimeHrs().toString()*/
                                            + ":" + courseList.get(i).get("timeMins").toString()/*schedule.getTimeMins().toString()*/
                                            + " " + courseList.get(i).get("AM_PM").toString()/*schedule.getAM_PM()*/);
                                    // Log.d("time is " + time,"" );

                                    schedulePOJO.setTimeOfCourse(time);
                                  //  Log.d("Time is : ",time.toString());
                                    schedulePOJO.setDayOfWeek(courseList.get(i).get("dayOfWeek").toString()/*schedule.getDay()*/);
                                    searchResults.add(schedulePOJO);

                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }

                            }

                            lv.setAdapter(new ScheduleCustomAdapter(CourseStudent.this, searchResults));
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
                        SchedulePOJO fullObject = (SchedulePOJO)o;
                        Toast.makeText(CourseStudent.this, "You have chosen: " + " " + fullObject.getDayOfWeek(), Toast.LENGTH_LONG).show();
                    }
                });




                return "";

            } catch (Exception e) {
                Log.e(" exception", e.getMessage(), e);
            }
            return "";
        }
    }

    public void deleteCourse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this course?")
                .setTitle("Delete Course");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // deleteCourse();


                ParseObject.registerSubclass(Student_Course.class);
                ParseQuery<ParseObject> queryStu_Course = ParseQuery.getQuery("Student_Course");
                queryStu_Course.whereEqualTo("courseId", courseId);
                //query.whereEqualTo("courseId", courseId)

                //queryStu_Course.findInBackground(new FindCallback<ParseObject>() {

                List<ParseObject> studentCourseList = null;
                try {
                    studentCourseList = queryStu_Course.find();
                    for (int i = 0; i < studentCourseList.size(); i++) {
                        ParseObject student_coursec = studentCourseList.get(i);
                        student_coursec.deleteEventually();
                    }
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }




                Intent intentProfessor = new Intent(CourseStudent.this, StudentHome.class);
                Bundle extra = new Bundle();
                extra.putString("id",username);

                intentProfessor.putExtras(extra);
                //intentProfessor.putExtra(EXTRA_MESSAGE,proffId );
                startActivity(intentProfessor);            }

        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
