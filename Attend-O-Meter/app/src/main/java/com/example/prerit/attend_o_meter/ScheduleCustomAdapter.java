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
 * Created by prerit on 19/4/15.
 */
public class ScheduleCustomAdapter extends BaseAdapter {

    private static ArrayList<SchedulePOJO> schedulePOJOArrayList;

    private LayoutInflater mInflater;

    public ScheduleCustomAdapter(Context context, ArrayList<SchedulePOJO> results) {
        schedulePOJOArrayList = results;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return schedulePOJOArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return schedulePOJOArrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.schedule_row_view, null);
            holder = new ViewHolder();
            holder.tv_scheduleDay = (TextView) convertView.findViewById(R.id.day);
            holder.tv_scheduleTime = (TextView) convertView.findViewById(R.id.timeOfCourse);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_scheduleDay.setText(schedulePOJOArrayList.get(position).getDayOfWeek().toString());

        if (schedulePOJOArrayList.get(position).getTimeOfCourse() != null) {
            String time;
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");


            time = dateFormat.format(schedulePOJOArrayList.get(position).getTimeOfCourse());
            Log.d("in adapter "+time,"");
            holder.tv_scheduleTime.setText(time.toString());
        }
        return convertView;

    }

    static class ViewHolder {
        TextView tv_scheduleDay;
        TextView tv_scheduleTime;

    }
}
