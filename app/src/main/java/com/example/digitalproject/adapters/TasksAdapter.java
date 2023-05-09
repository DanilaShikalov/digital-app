package com.example.digitalproject.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.digitalproject.R;
import com.example.digitalproject.models.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends BaseAdapter {
    private List<TaskModel> tasks = new ArrayList<>();
    private Context context;

    public TasksAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater taskInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TaskModel task = tasks.get(i);

        convertView = taskInflater.inflate(R.layout.task, null);
        ((TextView) convertView.findViewById(R.id.task_name)).setText(task.getName());
        ((TextView) convertView.findViewById(R.id.date_start)).setText(task.getDate_start());
        ((TextView) convertView.findViewById(R.id.date_end)).setText(task.getDate_end());

        return convertView;
    }

    public void add(TaskModel subject) {
        this.tasks.add(subject);
        notifyDataSetChanged();
    }

    public void clear() {
        tasks.clear();
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
