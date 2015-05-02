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
 * Created by haripriya on 28/4/15.
 */
public class StudentsInCourseAdapter extends BaseAdapter{

    private static ArrayList<StudentsInCoursePOJO> searchArrayList;

    private LayoutInflater mInflater;

    public StudentsInCourseAdapter(Context context, ArrayList<StudentsInCoursePOJO> results) {
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
            convertView = mInflater.inflate(R.layout.students_in_course, null);
            holder = new ViewHolder();
            holder.txt_student_name = (TextView) convertView.findViewById(R.id.student_name);
            holder.txt_attdn = (TextView) convertView.findViewById(R.id.student_attdn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_student_name.setText(searchArrayList.get(position).getStudentName());
        holder.txt_attdn.setText(Integer.toString(searchArrayList.get(position).getAttendance()) + "%");

        return convertView;
    }

    static class ViewHolder {
        TextView txt_student_name;
        TextView txt_attdn;
    }
}
