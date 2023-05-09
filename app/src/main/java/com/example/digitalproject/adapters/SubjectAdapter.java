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
import com.example.digitalproject.models.DocumentModel;

import java.util.ArrayList;
import java.util.List;

public class SubjectAdapter extends BaseAdapter {
    private List<String> subjects = new ArrayList<>();
    private Context context;

    public SubjectAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater subjectInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        String subject = subjects.get(i);

        convertView = subjectInflater.inflate(R.layout.subject, null);
        ((TextView) convertView.findViewById(R.id.subject_name)).setText(subject);
        return convertView;
    }

    public void add(String subject) {
        this.subjects.add(subject);
        notifyDataSetChanged();
    }

    public void clear() {
        subjects.clear();
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Object getItem(int i) {
        return subjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
