package com.example.digitalproject.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalproject.models.MainModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SingUpViewModel extends ViewModel {
    public MutableLiveData<MainModel> model = new MutableLiveData<>();
    public MutableLiveData<String> lastname = new MutableLiveData<>();
    public MutableLiveData<String> firstname = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> info = new MutableLiveData<>();

    public void register() {
        String url = "http://10.0.2.2:8080/api/auth/register";
        OkHttpClient client = new OkHttpClient();
        String email = this.email.getValue();
        String password = this.password.getValue();
        String lastname = this.lastname.getValue();
        String firstname = this.firstname.getValue();
        if (email != null && password != null && lastname != null && firstname != null) {
            JSONObject json = new JSONObject();
            try {
                json.put("firstname", firstname);
                json.put("lastname", lastname);
                json.put("email", email);
                json.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Request request = new Request.Builder().post(RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"))).url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String resp = Objects.requireNonNull(response.body()).string();
                        try {
                            JSONObject jsonObject = new JSONObject(resp);
                            model.postValue(new MainModel(jsonObject.getString("token")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (response.code() >= 400 && response.code() < 500) {
                        info.postValue(response.body().string());
                    }
                }
            });
        }
    }
}
