package com.example.digitalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.digitalproject.databinding.ActivityPersonAnswerBinding;
import com.example.digitalproject.globalvar.GlobalVariables;
import com.example.digitalproject.viewmodels.AnswerViewModel;
import com.example.digitalproject.viewmodels.PersonAnswerViewModel;

import java.io.IOException;
import java.util.StringTokenizer;

public class PersonAnswerActivity extends AppCompatActivity {
    private PersonAnswerViewModel personAnswerViewModel;
    private String token;
    private ActivityPersonAnswerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person_answer);
        binding.setLifecycleOwner(this);
        token = ((GlobalVariables) this.getApplication()).getToken_access();
        personAnswerViewModel = ViewModelProviders.of(this).get(PersonAnswerViewModel.class);
        personAnswerViewModel.setSubject(getIntent().getStringExtra("subject"));
        personAnswerViewModel.setTask(getIntent().getStringExtra("task"));
        personAnswerViewModel.setToken(token);
        StringTokenizer stringTokenizer = new StringTokenizer(getIntent().getStringExtra("name"));
        personAnswerViewModel.setName(stringTokenizer.nextToken());
        personAnswerViewModel.setSurname(stringTokenizer.nextToken());

        personAnswerViewModel.answer.observe(this, s -> binding.answerText.setText(s));
        personAnswerViewModel.grade.observe(this, s -> binding.grade.setText("Grade: " + s));

        personAnswerViewModel.getAnswerWithGradeByNameAndSurname();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"5", "4", "3", "2"});
        final Spinner sp = new Spinner(this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(arrayAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select grade for this student:");
        builder.setCancelable(true);
        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String select = sp.getSelectedItem().toString();
                        Log.i("ITEM", select);
                        dialog.cancel();
                    }
                });
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String select = sp.getSelectedItem().toString();
                        Log.i("ITEM", select);
                        try {
                            personAnswerViewModel.setGrade(select);
                            Thread.sleep(500);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        personAnswerViewModel.getAnswerWithGradeByNameAndSurname();
                        dialog.dismiss();
                        Intent intent = new Intent(PersonAnswerActivity.this, LoginActivity.class);
                        PersonAnswerActivity.this.startActivity(intent);
                    }
                });
        builder.setView(sp);
//        builder.create();
        binding.saveGrade.setOnClickListener(view -> builder.show());

    }
}