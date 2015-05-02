package com.example.prerit.attend_o_meter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StudentSemesterDialogFragment extends DialogFragment {

    ArrayList<String> semesters = new ArrayList<>();
    ListAdapter adapter;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Semester");
        query.selectKeys(Arrays.asList("semesterName"));
      //  ArrayList<String> semesters = new ArrayList<>();

        try {
            List<ParseObject> result = query.find();

            for(int i=0;i<result.size();i++) {
                Log.i("sem", result.get(i).getString("semesterName"));
                semesters.add(new String(result.get(i).getString("semesterName").toString()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //semesters = mListener.getSemesters();

        // our adapter instance
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,semesters);


        builder.setTitle("Select Semester")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String sem = semesters.get(i);
                        mListener.setSemester(sem);

                    }
                });

        return builder.create();
    }

    public interface StudentSemesterDialogListener {
        public void setSemester(String sem);
    }

    // Use this instance of the interface to deliver action events
    StudentSemesterDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (StudentSemesterDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}