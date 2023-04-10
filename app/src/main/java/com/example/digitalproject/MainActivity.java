package com.example.digitalproject;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.digitalproject.databinding.ActivityMainBinding;
import com.example.digitalproject.globalvar.GlobalVariables;
import com.example.digitalproject.models.MainModel;
import com.example.digitalproject.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setMainViewModel(mainViewModel);
        activityMainBinding.setLifecycleOwner(this);


//        mainViewModel.info.observe(this, str -> activityMainBinding.textError.setText(str));
        mainViewModel.mainModelMutableLiveData.observe(this, model -> {
            ((GlobalVariables) this.getApplication()).setToken_access(model.getToken());
            Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intent);
        });

//        activityMainBinding.buttonSingUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SingUpActivity.class);
//                MainActivity.this.startActivity(intent);
//            }
//        });
    }
}























