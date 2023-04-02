package com.example.digitalproject;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.digitalproject.databinding.ActivityMainBinding;
import com.example.digitalproject.models.MainModel;
import com.example.digitalproject.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        View view = activityMainBinding.getRoot();
//        setContentView(view);
//        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
//        activityMainBinding.setLifecycleOwner(this);
//        activityMainBinding.setMainViewModel(mainViewModel);
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setMainViewModel(mainViewModel);
        activityMainBinding.setLifecycleOwner(this);


        mainViewModel.mainModelMutableLiveData.observe(this, new Observer<MainModel>() {
            @Override
            public void onChanged(MainModel mainModel) {
                activityMainBinding.textError.setText(mainModel.getToken());
            }
        });
    }
}























