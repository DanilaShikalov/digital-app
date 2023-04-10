package com.example.digitalproject.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalproject.models.MainModel;
import com.example.digitalproject.models.SingUpModel;

import java.util.Objects;

public class SingUpViewModel extends ViewModel {
    public MutableLiveData<MainModel> model = new MutableLiveData<>();
    public MutableLiveData<String> lastname = new MutableLiveData<>();
    public MutableLiveData<String> firstname = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public void register() {
        Log.i("QFQ", "OQEOFBQFR");
        Log.i("Data", Objects.requireNonNull(lastname.getValue()).toString());
        StringBuilder password = new StringBuilder("erhhth");
    }
}
