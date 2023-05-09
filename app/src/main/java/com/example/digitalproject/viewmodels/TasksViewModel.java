package com.example.digitalproject.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalproject.models.TaskModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TasksViewModel extends ViewModel {
    public MutableLiveData<List<TaskModel>> tasks = new MutableLiveData<>();
    private String token;
    private String subject;

    public void setToken(String token) {
        this.token = token;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public TasksViewModel() {
    }

    public void getTasksBySubject() {
        String url = "http://10.0.2.2:8080/api/tasks/tasks/by/subject/";
        OkHttpClient client = new OkHttpClient();
        String query = String.format("%s?subject=%s", url, this.subject);
        Log.i("REQUEST", query);
        Request request = new Request.Builder().addHeader("Authorization", "Bearer ".concat(token))
                .url(String.format("%s?subject=%s", url, this.subject)).build();
        Log.i("RUN", "START");
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
                        JSONArray jsonArray = new JSONArray(resp);
                        List<TaskModel> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            list.add(new TaskModel(jsonObject.getString("task"), jsonObject.getString("dateStart"), jsonObject.getString("dateEnd")));
                        }
                        tasks.postValue(list);
                    } catch (JSONException e) {
                    }
                }
            }
        });
    }
}
