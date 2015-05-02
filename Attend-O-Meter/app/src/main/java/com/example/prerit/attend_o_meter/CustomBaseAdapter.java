package com.example.prerit.attend_o_meter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by haripriya on 18/4/15.
 */
public class CustomBaseAdapter extends BaseAdapter {
    private static ArrayList<CoursePOJO> searchArrayList;

    private LayoutInflater mInflater;

    public CustomBaseAdapter(Context context, ArrayList<CoursePOJO> results) {
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
            convertView = mInflater.inflate(R.layout.course_info, null);
            holder = new ViewHolder();
            holder.txt_course_name = (TextView) convertView.findViewById(R.id.course_name);
            holder.txt_time = (TextView) convertView
                    .findViewById(R.id.Time);
            holder.txt_bunks_left = (TextView) convertView.findViewById(R.id.bunks_left);
            holder.txt_classes_left = (TextView) convertView.findViewById(R.id.classes_left);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_course_name.setText(searchArrayList.get(position).getCourseName());
        if (searchArrayList.get(position)
                .getTime() != null){
            String date;
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            date = dateFormat.format(searchArrayList.get(position)
                    .getTime()).toString();

            holder.txt_time.setText(date);
        }

        if(searchArrayList.get(position).getTotClasses() != 0) {
            int bunked = searchArrayList.get(position).getCompClasses() - searchArrayList.get(position).getAttendance();
            int bunks_allowed = (searchArrayList.get(position).getTotClasses()*(100-Integer.valueOf(searchArrayList.get(position).getMin_attdn()))/ 100);
            int bunks_left = bunks_allowed - bunked;
            holder.txt_bunks_left.setText("Bunks Left: " + String.valueOf(bunks_left));
            holder.txt_classes_left.setText("Lec. Left: " + String.valueOf(searchArrayList.get(position).getTotClasses() - searchArrayList.get(position).getCompClasses()));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView txt_course_name;
        TextView txt_time;
        TextView txt_bunks_left;
        TextView txt_classes_left;
    }
}