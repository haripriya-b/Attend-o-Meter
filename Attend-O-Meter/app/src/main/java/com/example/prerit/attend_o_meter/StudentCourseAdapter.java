package com.example.prerit.attend_o_meter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prerit on 23/4/15.
 */
public class StudentCourseAdapter extends BaseAdapter{
    private static ArrayList<StudentCoursePojo> searchArrayList;

    private LayoutInflater mInflater;

    public StudentCourseAdapter(Context context, ArrayList<StudentCoursePojo> results) {
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

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.student_course_row, null);
            holder = new ViewHolder();
            holder.txtCourseId = (TextView) convertView.findViewById(R.id.tv_courseId);
           // holder.txtCourseName = (TextView) convertView
           //         .findViewById(R.id.tv_courseName);
            holder.attendance = (TextView) convertView.findViewById(R.id.attendance_pc);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtCourseId.setText(searchArrayList.get(position).getCourse_id());
       // holder.txtCourseName.setText(searchArrayList.get(position)
            //    .getCourse_name());
        holder.attendance.setText("Attended: " + Integer.toString(searchArrayList.get(position).getAttendance()));

        return convertView;
    }

    static class ViewHolder {
        TextView txtCourseId;
        //TextView txtCourseName;
        TextView attendance;
    }
}
