package com.example.digitalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.digitalproject.adapters.AnswerAdapter;
import com.example.digitalproject.adapters.TasksAdapter;
import com.example.digitalproject.databinding.ActivityAnswerBinding;
import com.example.digitalproject.databinding.ActivityTasksBinding;
import com.example.digitalproject.globalvar.GlobalVariables;
import com.example.digitalproject.models.AnswerModel;
import com.example.digitalproject.models.TaskModel;
import com.example.digitalproject.viewmodels.AnswerViewModel;
import com.example.digitalproject.viewmodels.TasksViewModel;

import java.util.List;

public class AnswerActivity extends AppCompatActivity {
    private ActivityAnswerBinding binding;
    private AnswerViewModel answerViewModel;
    private AnswerAdapter answerAdapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}
        binding = DataBindingUtil.setContentView(this, R.layout.activity_answer);
        binding.setLifecycleOwner(this);
        token = ((GlobalVariables) this.getApplication()).getToken_access();
        answerViewModel = ViewModelProviders.of(this).get(AnswerViewModel.class);
        answerViewModel.setSubject(getIntent().getStringExtra("subject"));
        answerViewModel.setTask(getIntent().getStringExtra("task"));
        answerViewModel.setToken(token);
        answerAdapter = new AnswerAdapter(this);
        binding.answersView.setAdapter(answerAdapter);
        answerViewModel.tasks.observe(this, new Observer<List<AnswerModel>>() {
            @Override
            public void onChanged(List<AnswerModel> taskModels) {
                Log.i("list", taskModels.toString());
                taskModels.forEach(x -> answerAdapter.add(x));
                answerAdapter.notifyDataSetChanged();
            }
        });

        binding.answersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AnswerModel answerModel = (AnswerModel) answerAdapter.getItem(i);
                Intent intent = new Intent(AnswerActivity.this, PersonAnswerActivity.class);
                intent.putExtra("task", getIntent().getStringExtra("task"));
                intent.putExtra("subject", getIntent().getStringExtra("subject"));
                intent.putExtra("name", answerModel.getName());
                AnswerActivity.this.startActivity(intent);
            }
        });

        answerViewModel.getAnswersWithPerson();
    }
}