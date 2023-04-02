package com.example.digitalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.digitalproject.databinding.ActivitySingUpBinding;

public class SingUpActivity extends AppCompatActivity {

    private ActivitySingUpBinding activitySingUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySingUpBinding = ActivitySingUpBinding.inflate(getLayoutInflater());
        View view = activitySingUpBinding.getRoot();
        setContentView(view);

        activitySingUpBinding.buttonSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingUpActivity.this, LoginActivity.class);
                SingUpActivity.this.startActivity(intent);
            }
        });
    }
}