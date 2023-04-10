package com.example.digitalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.digitalproject.databinding.ActivitySingUpBinding;
import com.example.digitalproject.viewmodels.MainViewModel;
import com.example.digitalproject.viewmodels.SingUpViewModel;

public class SingUpActivity extends AppCompatActivity {

    private ActivitySingUpBinding activitySingUpBinding;
    private SingUpViewModel setMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainViewModel = ViewModelProviders.of(this).get(SingUpViewModel.class);
        activitySingUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sing_up);
        activitySingUpBinding.setSingUpViewModel(setMainViewModel);
        activitySingUpBinding.setLifecycleOwner(this);

        activitySingUpBinding.buttonSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingUpActivity.this, LoginActivity.class);
                SingUpActivity.this.startActivity(intent);
            }
        });
    }
}