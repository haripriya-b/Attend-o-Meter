package com.example.prerit.attend_o_meter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SemesterDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.fragment_semester_dialog, null);

        builder.setTitle("Add Semester")
               .setView(dialoglayout)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        EditText editText_sname = (EditText) dialoglayout.findViewById(R.id.ed_sname);
                        String sname = editText_sname.getText().toString();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        EditText editText_sdate = (EditText) dialoglayout.findViewById(R.id.ed_sdate);

                        EditText editText_edate = (EditText) dialoglayout.findViewById(R.id.ed_edate);

                        Date date_start = new Date();
                        Date date_end = new Date();

                        try {
                            Calendar c1 = Calendar.getInstance();
                            c1.setTime(dateFormat.parse(editText_sdate.getText().toString()));
                            c1.add(Calendar.DATE, 1);  // number of days to add
                            date_start = dateFormat.parse(dateFormat.format(c1.getTime()));

                            Calendar c2 = Calendar.getInstance();
                            c2.setTime(dateFormat.parse(editText_edate.getText().toString()));
                            c2.add(Calendar.DATE, 1);  // number of days to add
                            date_end = dateFormat.parse(dateFormat.format(c2.getTime()));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Log.i("sdate",date_start.toString());
                        Log.i("edate",date_end.toString());


                        ParseObject semesterObject = ParseObject.create("Semester");
                        semesterObject.put("semesterName",sname);
                        semesterObject.put("startDate", date_start);
                        semesterObject.put("endDate", date_end);

                        semesterObject.saveInBackground();

                        mListener.addSem(sname, date_start, date_end);
                        mListener.onSemesterDialogPositiveClick(SemesterDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onSemesterDialogNegativeClick(SemesterDialogFragment.this);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface SemesterDialogListener {
        public void onSemesterDialogPositiveClick(DialogFragment dialog);
        public void addSem(String sem, Date start, Date end);
        public void onSemesterDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    SemesterDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SemesterDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}

