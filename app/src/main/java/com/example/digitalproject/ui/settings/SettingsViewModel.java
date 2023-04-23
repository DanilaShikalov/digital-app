package com.example.digitalproject.ui.settings;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalproject.globalvar.GlobalVariables;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public MutableLiveData<List<String>> listJobs = new MutableLiveData<>();
    private String token;
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> phone = new MutableLiveData<>();
    public MutableLiveData<String> emailChanged = new MutableLiveData<>();
    public MutableLiveData<String> passwordChanged = new MutableLiveData<>();
    public MutableLiveData<String> phoneChanged = new MutableLiveData<>();
    public MutableLiveData<String> jobChanged = new MutableLiveData<>();

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public void getJobs() {
        String token = this.getToken();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/api/jobs/job/")
                .addHeader("Authorization", "Bearer ".concat(token))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        for (int i = 0; i < array.length(); i++) {
                            list.add(array.getJSONObject(i).getString("title"));
                        }
                        Log.i("Jobs", list.toString());
                        listJobs.postValue(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void saveEmail() {
        String email = this.email.getValue();
        if (email != null) {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(null, new byte[0]);
            Request request = new Request.Builder()
                    .url(String.format("%s?email=%s", "http://10.0.2.2:8080/api/persons/person/email", email))
                    .put(requestBody)
                    .addHeader("Authorization", "Bearer ".concat(token))
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    emailChanged.postValue("changed");
                }
            });
        }
    }


    public void savePassword() {
        String password = this.password.getValue();
        if (password != null) {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(null, new byte[0]);
            Request request = new Request.Builder()
                    .url(String.format("%s?password=%s", "http://10.0.2.2:8080/api/persons/person/password", password))
                    .put(requestBody)
                    .addHeader("Authorization", "Bearer ".concat(token))
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    passwordChanged.postValue("changed");
                }
            });
        }
    }

    public void changeJob(String job) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(null, new byte[0]);
        Request request = new Request.Builder()
                .url(String.format("%s?job=%s", "http://10.0.2.2:8080/api/jobs/job/person", job))
                .put(requestBody)
                .addHeader("Authorization", "Bearer ".concat(token))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                jobChanged.postValue("changed");
            }
        });
    }

    public void savePhone() {
        String phone = this.phone.getValue();
        Log.i("Phone", phone);
        if (phone != null) {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(null, new byte[0]);
            Request request = new Request.Builder()
                    .url(String.format("%s?phone=%s", "http://10.0.2.2:8080/api/persons/person/phone", phone))
                    .put(requestBody)
                    .addHeader("Authorization", "Bearer ".concat(token))
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    phoneChanged.postValue("changed");
                }
            });
        }
    }

    public LiveData<String> getText() {
        return mText;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}