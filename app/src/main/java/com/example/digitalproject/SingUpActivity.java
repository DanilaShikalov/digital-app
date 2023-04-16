package com.example.digitalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.digitalproject.databinding.ActivitySingUpBinding;
import com.example.digitalproject.globalvar.GlobalVariables;
import com.example.digitalproject.models.MainModel;
import com.example.digitalproject.viewmodels.MainViewModel;
import com.example.digitalproject.viewmodels.SingUpViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class SingUpActivity extends AppCompatActivity {

    private ActivitySingUpBinding activitySingUpBinding;
    private SingUpViewModel singUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        singUpViewModel = ViewModelProviders.of(this).get(SingUpViewModel.class);
        activitySingUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sing_up);
        activitySingUpBinding.setSingUpViewModel(singUpViewModel);
        activitySingUpBinding.setLifecycleOwner(this);
//        activitySingUpBinding.singUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SingUpActivity.this, LoginActivity.class);
//                SingUpActivity.this.startActivity(intent);
//            }
//        });

        singUpViewModel.model.observe(this, model -> {
            ((GlobalVariables) this.getApplication()).setToken_access(model.getToken());
            Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SingUpActivity.this, LoginActivity.class);
            SingUpActivity.this.startActivity(intent);
        });

        singUpViewModel.info.observe(this, s -> {
            try {
                JSONObject jsonObject = new JSONObject(s);
                activitySingUpBinding.info.setText(jsonObject.getString("description"));
            } catch (JSONException e) {
                activitySingUpBinding.info.setText("Неизвестная ошибка!");
            }
        });
    }
}