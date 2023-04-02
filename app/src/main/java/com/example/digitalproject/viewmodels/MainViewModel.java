package com.example.digitalproject.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalproject.models.MainModel;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<MainModel> mainModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> info = new MutableLiveData<>();

    public void authenticate() {
        Log.i("AAAAA", "etu6eru");
        Log.i("email", email.getValue());
        Log.i("pass", password.getValue());
        String url = "http://10.0.2.2:8080/api/auth/authenticate";
        OkHttpClient client = new OkHttpClient();
        String email = this.email.getValue();
        String password = this.password.getValue();
        if (email != null && password != null) {
            RequestBody requestBody = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .build();
            Log.i("request", requestBody.toString());
            // TODO Непонятно почему POST запрос не имеет тела
            Request request = new Request.Builder().header("accept", "*/*").header("Content-Type", "application/json").method("POST", requestBody).url(url).build();
            Log.i("req", request.toString());
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String resp = Objects.requireNonNull(response.body()).string();
                        mainModelMutableLiveData.postValue(new MainModel(resp));
                    } else if (response.code() >= 400 && response.code() < 500) {
                        info.postValue(response.toString());
                        Log.i("INFO", response.toString());
                    }
                }
            });
        }
    }
}
