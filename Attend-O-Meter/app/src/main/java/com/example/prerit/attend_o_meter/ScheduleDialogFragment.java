package com.example.prerit.attend_o_meter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class ScheduleDialogFragment extends DialogFragment {

    ArrayList<Integer> days = new ArrayList();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.fragment_schedule_dialog, null);




        builder.setTitle("Add Schedule")
                .setView(dialoglayout)
                .setMultiChoiceItems(R.array.array_days, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    days.add(which);
                                } else if (days.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    days.remove(Integer.valueOf(which));
                                }
                            }
                        })

                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        EditText ed_hrs = (EditText) dialoglayout.findViewById(R.id.ed_hr);
                        String hrs = ed_hrs.getText().toString();

                        EditText ed_mins = (EditText) dialoglayout.findViewById(R.id.ed_mins);
                        String mins = ed_mins.getText().toString();

                        EditText ed_ampm = (EditText) dialoglayout.findViewById(R.id.ed_AMPM);
                        String ampm = ed_ampm.getText().toString();

                        mListener.setSchedule(hrs, mins, ampm, days);


                        mListener.onScheduleDialogPositiveClick(ScheduleDialogFragment.this);

                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onScheduleDialogNegativeClick(ScheduleDialogFragment.this);
                    }
                });



        // Create the AlertDialog object and return it
        return builder.create();
    }


    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface ScheduleDialogListener {
        public void onScheduleDialogPositiveClick(DialogFragment dialog);
        public void onScheduleDialogNegativeClick(DialogFragment dialog);
        public void setSchedule(String hrs, String mins, String ampm, ArrayList<Integer> days);

    }

    // Use this instance of the interface to deliver action events
    ScheduleDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ScheduleDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}