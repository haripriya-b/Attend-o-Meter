package com.example.prerit.attend_o_meter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by haripriya on 22/4/15.
 */
public class AttendanceBaseAdapter extends BaseAdapter {
    private static ArrayList<StudentPOJO> searchArrayList;

    private LayoutInflater mInflater;

    public AttendanceBaseAdapter(Context context, ArrayList<StudentPOJO> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);

    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.attendance_student, null);
            holder = new ViewHolder();
            holder.txt_student_name = (TextView) convertView.findViewById(R.id.student_name);
            holder.cb_attended = (CheckBox) convertView.findViewById(R.id.cb_attended);

            holder.cb_attended.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i("chk", Integer.toString(position));
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    searchArrayList.get(getPosition).setAttended(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    Log.i("chk", Boolean.toString(searchArrayList.get(getPosition).isAttended()));
                }
            });

            convertView.setTag(holder);
            convertView.setTag(R.id.student_name, holder.txt_student_name);
            convertView.setTag(R.id.cb_attended, holder.cb_attended);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_student_name.setText(searchArrayList.get(position).getStudentName());
        holder.cb_attended.setTag(position);
        holder.cb_attended.setChecked(searchArrayList.get(position).isAttended());

        return convertView;
    }

    static class ViewHolder {
        TextView txt_student_name;
        CheckBox cb_attended;
    }
}
