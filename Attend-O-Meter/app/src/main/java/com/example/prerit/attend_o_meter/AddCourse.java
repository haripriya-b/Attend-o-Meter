package com.example.prerit.attend_o_meter;


import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class AddCourse extends ActionBarActivity implements SemesterDialogFragment.SemesterDialogListener, ScheduleDialogFragment.ScheduleDialogListener{

    final List<String> semesters = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Spinner sem_spinner;
    TextView tv_schedule, tv_days;
    String id;
    Date sdate, edate;
    ArrayList<Integer> daysArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        Parse.initialize(this, "c1Tcja7oupsKmxy7qbkcyJWPHrpLvUI0vYXlvbtr", "hHSIS28AEgqL4zo2EiuMtjXv0wS3CVgxDlaDVRBD");
        ParseObject.registerSubclass(Semester.class);
        ParseObject.registerSubclass(CourseSchedule.class);
        ParseObject.registerSubclass(Course.class);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Semester");
        query.selectKeys(Arrays.asList("semesterName"));

        try {
            List<ParseObject> result = query.find();

            for(int i=0;i<result.size();i++) {
                semesters.add(result.get(i).getString("semesterName").toString());
            }
            String addNew = new String("Add New");
            semesters.add(addNew);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        sem_spinner = (Spinner) findViewById(R.id.spinner_semester);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,semesters);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sem_spinner.setAdapter(adapter);

        sem_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        String sel = arg0.getItemAtPosition(arg2).toString();
                        if (sel.equals("Add New") ) {

                            showSemesterDialog();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                }
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_course, menu);
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

    public void showSemesterDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new SemesterDialogFragment();
        dialog.show(getSupportFragmentManager(), "SemesterDialogFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onSemesterDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void addSem(String sem, Date start, Date end) {
        semesters.remove(semesters.size()-1);
        semesters.add(sem);
        semesters.add(new String("Add New"));

        runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        sem_spinner.setSelection(semesters.size()-2);


    }

    @Override
    public void onSemesterDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

    }

    public void showScheduleDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new ScheduleDialogFragment();
        dialog.show(getSupportFragmentManager(), "ScheduleDialogFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onScheduleDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onScheduleDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

    }

    @Override
    public void setSchedule(String hrs, String mins, String ampm, ArrayList<Integer> days) {



        Collections.sort(days);
        LinearLayout schedule_main_ll = (LinearLayout) findViewById(R.id.ll_schedule);

        LinearLayout schedule_ll = new LinearLayout(this);
        schedule_ll.setOrientation(LinearLayout.VERTICAL);
        schedule_ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        schedule_main_ll.addView(schedule_ll);

        tv_schedule = new TextView(AddCourse.this);
        tv_schedule.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        tv_schedule.setText(hrs + ":" + mins + " " + ampm);
        tv_schedule.setTextSize(20);



        schedule_ll.addView(tv_schedule);

        tv_days = new TextView(this);
        LinearLayout.LayoutParams params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        params.setMargins(0,0,0,16);
        tv_days.setLayoutParams(params);
        tv_days.setTextSize(13);

        EditText ed_cid = (EditText) findViewById(R.id.ed_courseid);
        String cid = ed_cid.getText().toString();


       // Log.i("cid",cid);



        for(int i=0;i<days.size();i++) {
            this.daysArray.add(days.get(i));
            switch (days.get(i)) {

                case 0:
                    ParseObject courseSchedule0 = ParseObject.create("CourseSchedule");
                    courseSchedule0.put("courseId",cid);
                    courseSchedule0.put("timeHrs",Integer.valueOf(hrs));
                    courseSchedule0.put("timeMins",Integer.valueOf(mins));
                    courseSchedule0.put("AM_PM",ampm);
                    courseSchedule0.put("dayOfWeek","Monday");

                    courseSchedule0.saveInBackground();
                    break;

                case 1:
                    ParseObject courseSchedule1 = ParseObject.create("CourseSchedule");
                    courseSchedule1.put("courseId",cid);
                    courseSchedule1.put("timeHrs",Integer.valueOf(hrs));
                    courseSchedule1.put("timeMins",Integer.valueOf(mins));
                    courseSchedule1.put("AM_PM",ampm);
                    courseSchedule1.put("dayOfWeek","Tuesday");

                    courseSchedule1.saveInBackground();
                    break;

                case 2:
                    ParseObject courseSchedule2 = ParseObject.create("CourseSchedule");
                    courseSchedule2.put("courseId",cid);
                    courseSchedule2.put("timeHrs",Integer.valueOf(hrs));
                    courseSchedule2.put("timeMins",Integer.valueOf(mins));
                    courseSchedule2.put("AM_PM",ampm);
                    courseSchedule2.put("dayOfWeek","Wednesday");

                    courseSchedule2.saveInBackground();
                    break;

                case 3:
                    ParseObject courseSchedule3 = ParseObject.create("CourseSchedule");
                    courseSchedule3.put("courseId",cid);
                    courseSchedule3.put("timeHrs",Integer.valueOf(hrs));
                    courseSchedule3.put("timeMins",Integer.valueOf(mins));
                    courseSchedule3.put("AM_PM",ampm);
                    courseSchedule3.put("dayOfWeek","Thursday");

                    courseSchedule3.saveInBackground();
                    break;

                case 4:
                    ParseObject courseSchedule4 = ParseObject.create("CourseSchedule");
                    courseSchedule4.put("courseId",cid);
                    courseSchedule4.put("timeHrs",Integer.valueOf(hrs));
                    courseSchedule4.put("timeMins",Integer.valueOf(mins));
                    courseSchedule4.put("AM_PM",ampm);
                    courseSchedule4.put("dayOfWeek","Friday");

                    courseSchedule4.saveInBackground();
                    break;

                case 5:
                    ParseObject courseSchedule5 = ParseObject.create("CourseSchedule");
                    courseSchedule5.put("courseId",cid);
                    courseSchedule5.put("timeHrs",Integer.valueOf(hrs));
                    courseSchedule5.put("timeMins",Integer.valueOf(mins));
                    courseSchedule5.put("AM_PM",ampm);
                    courseSchedule5.put("dayOfWeek","Saturday");

                    courseSchedule5.saveInBackground();
                    break;

                case 6:
                    ParseObject courseSchedule6 = ParseObject.create("CourseSchedule");
                    courseSchedule6.put("courseId",cid);
                    courseSchedule6.put("timeHrs",Integer.valueOf(hrs));
                    courseSchedule6.put("timeMins",Integer.valueOf(mins));
                    courseSchedule6.put("AM_PM",ampm);
                    courseSchedule6.put("dayOfWeek","Sunday");

                    courseSchedule6.saveInBackground();
                    break;
            }

        }



        String str_days = "";

        for(int i=0;i<days.size();i++) {
            this.daysArray.add(days.get(i));
            switch (days.get(i)) {

                case 0:
                    str_days = str_days + "Mo";
                    break;

                case 1:
                    str_days = str_days + "Tu";
                    break;

                case 2:
                    str_days = str_days + "We";
                    break;

                case 3:
                    str_days = str_days + "Th";
                    break;

                case 4:
                    str_days = str_days + "Fr";
                    break;

                case 5:
                    str_days = str_days + "Sa";
                    break;

                case 6:
                    str_days = str_days + "Su";
                    break;
            }

            if(days.size()>1 && i< days.size()-1) {
                str_days = str_days + ", ";
            }

        }


        tv_days.setText(str_days);
        schedule_ll.addView(tv_days);

    }


    public void addSchedule(View view) {

        EditText ed_cid = (EditText) findViewById(R.id.ed_courseid);
        String cid = ed_cid.getText().toString();

        if (cid.equals("")) {
            Toast.makeText(AddCourse.this, "Enter CourseID!",Toast.LENGTH_LONG).show();
        }else {
            showScheduleDialog();
        }
    }

    public void addCourse(View view) {

        Spinner spinner = (Spinner) findViewById(R.id.spinner_semester);
        String sem = spinner.getSelectedItem().toString();


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Semester");
        query.whereEqualTo("semesterName", sem);

        try {
            List<ParseObject> result = query.find();

            sdate = result.get(0).getDate("startDate");
            edate = result.get(0).getDate("endDate");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        EditText ed_cid = (EditText) findViewById(R.id.ed_courseid);
        String cid = ed_cid.getText().toString();

        EditText ed_cname = (EditText) findViewById(R.id.ed_courseName);
        String cname = ed_cname.getText().toString();

        EditText ed_minAttdn = (EditText) findViewById(R.id.ed_minAttdn);
        String minAttdn = ed_minAttdn.getText().toString();

        EditText ed_marks = (EditText) findViewById(R.id.ed_marks);
        String marks = ed_marks.getText().toString();

        Calendar c1 = Calendar.getInstance();
        c1.setTime(sdate);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(edate);

        Date d1 = c1.getTime();
       // Log.i("date",d1.toString());

        Date d2 = c2.getTime();
       // Log.i("date",d2.toString());


        int mon=0, tue=0, wed=0, thu=0, fri=0, sat=0, sun=0;

        while(c2.after(c1)) {
            if(c1.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY)
                mon++;
            else if (c1.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY)
                tue++;
            else if(c1.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY)
                wed++;
            else if(c1.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY)
                thu++;
            else if(c1.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY)
                fri++;
            else if (c1.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
                sat++;
            else if(c1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
                sun++;
            c1.add(Calendar.DATE,1);
        }

        Log.i("mon",Integer.toString(mon));
        Log.i("tue",Integer.toString(tue));
        Log.i("wed",Integer.toString(wed));
        Log.i("thu",Integer.toString(thu));
        Log.i("fri",Integer.toString(fri));
        Log.i("sat",Integer.toString(sat));
        Log.i("sun",Integer.toString(sun));


        int totClasses=0;

        for(int i=0;i<daysArray.size();i++) {
            switch (daysArray.get(i)){
                case 1:
                    totClasses = totClasses + mon;
                    break;
                case 2:
                    totClasses = totClasses + tue;
                    break;
                case 3:
                    totClasses = totClasses + wed;
                    break;
                case 4:
                    totClasses = totClasses + thu;
                    break;
                case 5:
                    totClasses = totClasses + fri;
                    break;
                case 6:
                    totClasses = totClasses + sat;
                    break;
                case 7:
                    totClasses = totClasses + sun;
                    break;

            }
        }

       // Log.i("tot",Integer.toString(totClasses));

        ParseObject course = ParseObject.create("Course");
        course.put("courseId",cid);
        course.put("courseName",cname);
        course.put("semester", sem);
        course.put("professor", id);
        course.put("totalLectures",totClasses);
        course.put("completedLectures",0);
        course.put("minAttendance",minAttdn);
        course.put("maxMarks",Integer.valueOf(marks));

        try {
            course.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Bundle extras = new Bundle();
        extras.putString("id", id);

	/*ParsePush.subscribeInBackground(cid, new SaveCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null) {
                //Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
            } else {
                //Log.e("com.parse.push", "failed to subscribe for push", e);
            }
        }
    });
*/

        Toast.makeText(AddCourse.this, "Course Added!", Toast.LENGTH_LONG).show();

        Intent intentProfessor = new Intent(AddCourse.this, ProfessorHome.class);
        intentProfessor.putExtras(extras);
        startActivity(intentProfessor);


    }
}
