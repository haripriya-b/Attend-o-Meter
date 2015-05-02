package com.example.prerit.attend_o_meter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TaskStackBuilder;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CourseProfessor extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.example.prerit.attend_o_meter.MESSAGE";
    private String courseId;
    private String courseName;
    private String proffId;
    private int totalLectures;
    private int completedLectures;
    private int maxMarks;
    private int minAttendance;
    private String semester;
    private ArrayList<SchedulePOJO> searchResults = new ArrayList<SchedulePOJO>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_professor);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Parse.initialize(this, "c1Tcja7oupsKmxy7qbkcyJWPHrpLvUI0vYXlvbtr", "hHSIS28AEgqL4zo2EiuMtjXv0wS3CVgxDlaDVRBD");
        //final ArrayList<SchedulePOJO> searchResults = new ArrayList<SchedulePOJO>();

        final ListView lv = (ListView) findViewById(R.id.schedule_listView);




        //CourseSchedule schedule = new CourseSchedule();
        //schedule.getCourseId();
        Intent intent = getIntent();
        courseId= intent.getStringExtra(ProfessorCourses.EXTRA_MESSAGE);
        // Create the text view
       // Toast.makeText(CourseProfessor.this, "Welcome " + courseId, Toast.LENGTH_SHORT).show();
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
                    Log.d("name","" + courseName);
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
                    String text2 = String.format("%-20s%s\n","Course progress:",Integer.toString(completedLectures) + "/" + Integer.toString(totalLectures));
                    tv_lectureNum.setText(text2);
                    TextView tv_semName = (TextView)findViewById(R.id.semester_name);
                    String text3 = String.format("%-25s%s\n","Semester:",semester);
                    // tv_semName.setText("Semester:               " + semester);
                    tv_semName.setText(text3);

                    TextView tv_minAttdn = (TextView)findViewById(R.id.tv_minAttdn);
                    String text4 = String.format("%-20s%s\n","Min Attendance:",minAttendance+"%");
                    //tv_minAttdn.setText("Min Attendance:                " + minAttendance + "%");
                    tv_minAttdn.setText(text4);

                    TextView tv_maxMarks = (TextView)findViewById(R.id.tv_maxMarks);
                    String text5 = String.format("%-22s%s\n","Max marks:", maxMarks);
                    //tv_minAttdn.setText("Min Attendance:                " + minAttendance + "%");
                    tv_maxMarks.setText(text5);
                    //TextView tv_maxMarks = (TextView)findViewById(R.id.tv_maxMarks);
                    //String text5 = String.format("%-20s%s\n","Max Marks:",+"%");
                    //tv_minAttdn.setText("Min Attendance:                " + minAttendance + "%");
                    //tv_minAttdn.setText(text4);
//                    Course course = new Course();
//                    course = (Course)courseList1.get(0);
//                    totalLectures = course.getTotalLectures();
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



        ParseObject.registerSubclass(CourseSchedule.class);
        ParseQuery<ParseObject> querySchedule = ParseQuery.getQuery("CourseSchedule");
        querySchedule.whereEqualTo("courseId", courseId);
        //query.whereEqualTo("courseId", courseId)

        querySchedule.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> courseList, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("Course size " + courseList.size(), "" + courseList.get(0).get("dayOfWeek").toString() + " "
                            + courseList.get(0).get("timeHrs").toString() + " "
                            + courseList.get(0).get("timeMins").toString());


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
                            Log.d("Time is : ",time.toString());
                            schedulePOJO.setDayOfWeek(courseList.get(i).get("dayOfWeek").toString()/*schedule.getDay()*/);
                            searchResults.add(schedulePOJO);

                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                    }

                    lv.setAdapter(new ScheduleCustomAdapter(CourseProfessor.this, searchResults));
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
                //Toast.makeText(CourseProfessor.this, "You have chosen: " + " " + fullObject.getDayOfWeek(), Toast.LENGTH_LONG).show();
            }
        });
    }



    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(courseId);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_professor, menu);
        restoreActionBar();
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
            case R.id.editCourseDetails:
                editCourse();
                break;
            case R.id.logout:
                finish();
                Intent intentLogin = new Intent(CourseProfessor.this,Login.class);
                startActivity(intentLogin);

                break;

            case R.id.deleteCourse:
                deleteCourse();
                break;
            case R.id.assignMarks:
                assignMarks();
                break;
            case R.id.all_students:
                viewStudents();
                break;

            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putExtra(EXTRA_MESSAGE,proffId);
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

    private void viewStudents() {

                Bundle extras = new Bundle();
                extras.putString("cid", courseId);
                extras.putInt("compLec", completedLectures);
                Intent intentStudentsInCourse = new Intent(CourseProfessor.this,StudentsInCourse.class);
                intentStudentsInCourse.putExtras(extras);
                startActivity(intentStudentsInCourse);

    }

    private void assignMarks() {


        /*Bundle currentData = new Bundle();
        currentData.putString("courseId",this.courseId);
        currentData.putString("courseName",this.courseName);
        currentData.putString("courseId",this.courseId);
        currentData.putString("semester",this.semester);
        currentData.putInt("minAttendance", this.minAttendance);
        currentData.putInt("totalLectures",this.totalLectures);
        currentData.putInt("completedLectures", this.completedLectures);*/





    }


    public void deleteCourse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this course?")
                .setTitle("Delete Course");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // deleteCourse();


//                ParseObject.registerSubclass(Student_Course.class);
//                ParseQuery<ParseObject> queryStu_Course = ParseQuery.getQuery("CourseSchedule");
//                queryStu_Course.whereEqualTo("courseId", courseId);
//                //query.whereEqualTo("courseId", courseId)
//
//                queryStu_Course.findInBackground(new FindCallback<ParseObject>() {
//
//                    public void done(List<ParseObject> studentCourseList, com.parse.ParseException e) {
//                        if (e == null) {
//                            for (int i = 0; i < studentCourseList.size(); i++) {
//                                Student_Course student_coursec = (Student_Course)studentCourseList.get(i);
//                                student_coursec.deleteEventually();
//                            }
//                        }  else {
//                            Log.d("", "Error: not working here");
//                            Log.d("", "Error: " + e.getMessage());
//                        }
//                    }
//
//
//                });
//
//                ParseObject.registerSubclass(CourseSchedule.class);
//                ParseQuery<ParseObject> querySchedule = ParseQuery.getQuery("CourseSchedule");
//                querySchedule.whereEqualTo("courseId", courseId);
//                //query.whereEqualTo("courseId", courseId)
//
//                querySchedule.findInBackground(new FindCallback<ParseObject>() {
//
//                    public void done(List<ParseObject> courseList, com.parse.ParseException e) {
//                        if (e == null) {
//                            CourseSchedule schedule;
//
//                            for (int i = 0; i < courseList.size(); i++) {
//
//                                schedule = (CourseSchedule) courseList.get(i);
//                                schedule.deleteEventually();
//                            }
//                        }  else {
//                            Log.d("", "Error: not working here");
//                            Log.d("", "Error: " + e.getMessage());
//                        }
//                    }
//
//
//                });

                ParseObject.registerSubclass(Course.class);
                ParseQuery<ParseObject> queryCourse = ParseQuery.getQuery("Course");
                queryCourse.whereEqualTo("courseId", courseId);

                try {
                    List<ParseObject> result = queryCourse.find();
                    ParseObject deletingCourse =  result.get(0);
                    proffId =  deletingCourse.get("professor").toString();
                    deletingCourse.deleteEventually();

                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
/*
                queryCourse.findInBackground(new FindCallback<ParseObject>() {

                    public void done(List<ParseObject> courseList2, com.parse.ParseException e) {
                        if (e == null) {
                            Course deletingCourse = (Course) courseList2.get(0);
                            proffId =  deletingCourse.get("professor").toString();
                            deletingCourse.deleteEventually();



                        } else {
                            Log.d("", "Error: not working here");
                            Log.d("", "Error: " + e.getMessage());
                        }

                    }


                });

*/

                Intent intentProfessor = new Intent(CourseProfessor.this, ProfessorHome.class);
                Bundle extra = new Bundle();
                extra.putString("id", proffId);

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

    private void editCourse() {
        Bundle currentData = new Bundle();
        currentData.putString("courseId",this.courseId);
        currentData.putString("courseName",this.courseName);
        currentData.putString("courseId",this.courseId);
        currentData.putString("semester",this.semester);
        currentData.putInt("minAttendance", this.minAttendance);
        currentData.putInt("totalLectures",this.totalLectures);

       // Intent intentEdit = new Intent(this, EditCourse.class);
       // intentEdit.putExtras(currentData);
       // startActivity(intentEdit);
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(this,ProfessorCourses.class);
        intent1.putExtra(EXTRA_MESSAGE, this.proffId);
        startActivity(intent1);
        //finish();
    }



}


