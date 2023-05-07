package com.example.digitalproject.ui.home;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {
    private String token;
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> surname = new MutableLiveData<>();
    public MutableLiveData<String> phone = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> salary = new MutableLiveData<>();
    public MutableLiveData<String> position = new MutableLiveData<>();
    public MutableLiveData<String> rating = new MutableLiveData<>();

    public void setToken(String token) {
        this.token = token;
    }

    public HomeViewModel() {
    }

    public void getInfo() {
        String url = "http://10.0.2.2:8080/api/persons/get/fullinfo";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("Authorization", "Bearer ".concat(token)).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resp = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject = new JSONObject(resp);
                        name.postValue(jsonObject.getString("name"));
                        surname.postValue(jsonObject.getString("surname"));
                        phone.postValue(jsonObject.getString("phone"));
                        email.postValue(jsonObject.getString("email"));
                        salary.postValue(Long.toString(jsonObject.getLong("salary")));
                        position.postValue(jsonObject.getString("position"));
                        rating.postValue(jsonObject.getString("rating"));
                    } catch (JSONException e) {
                    }
                }
            }
        });
    }
}