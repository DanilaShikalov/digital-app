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

import com.example.digitalproject.adapters.TasksAdapter;
import com.example.digitalproject.databinding.ActivityTasksBinding;
import com.example.digitalproject.databinding.FragmentSubjectBinding;
import com.example.digitalproject.globalvar.GlobalVariables;
import com.example.digitalproject.models.TaskModel;
import com.example.digitalproject.viewmodels.TasksViewModel;

import java.util.List;

public class TasksActivity extends AppCompatActivity {
    private ActivityTasksBinding binding;
    private TasksViewModel tasksViewModel;
    private TasksAdapter tasksAdapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tasks);
        binding.setTasksViewModel(tasksViewModel);
        binding.setLifecycleOwner(this);
        token = ((GlobalVariables) this.getApplication()).getToken_access();
        tasksViewModel = ViewModelProviders.of(this).get(TasksViewModel.class);
        tasksViewModel.setSubject(getIntent().getStringExtra("subject"));
        tasksViewModel.setToken(token);
        tasksAdapter = new TasksAdapter(this);
        binding.tasksView.setAdapter(tasksAdapter);
        tasksViewModel.tasks.observe(this, new Observer<List<TaskModel>>() {
            @Override
            public void onChanged(List<TaskModel> taskModels) {
                Log.i("list", taskModels.toString());
                taskModels.forEach(x -> tasksAdapter.add(x));
                tasksAdapter.notifyDataSetChanged();
            }
        });

        binding.tasksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskModel taskModel = (TaskModel) tasksAdapter.getItem(i);
                Intent intent = new Intent(TasksActivity.this, AnswerActivity.class);
                intent.putExtra("task", taskModel.getName());
                intent.putExtra("subject", getIntent().getStringExtra("subject"));
                TasksActivity.this.startActivity(intent);
            }
        });

        tasksViewModel.getTasksBySubject();
    }
}