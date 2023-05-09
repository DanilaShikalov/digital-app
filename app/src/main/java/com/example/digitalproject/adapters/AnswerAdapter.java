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
import com.example.digitalproject.models.AnswerModel;

import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends BaseAdapter {
    private List<AnswerModel> tasks = new ArrayList<>();
    private Context context;

    public AnswerAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater answerInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        AnswerModel answerModel = tasks.get(i);

        convertView = answerInflater.inflate(R.layout.answer, null);
        ((TextView) convertView.findViewById(R.id.name_answer)).setText(answerModel.getName());
        ((TextView) convertView.findViewById(R.id.date_answer)).setText(answerModel.getDate());

        return convertView;
    }

    public void add(AnswerModel answerModel) {
        this.tasks.add(answerModel);
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
