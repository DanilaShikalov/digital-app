package com.example.digitalproject.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalproject.models.AnswerModel;
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

public class AnswerViewModel extends ViewModel {
    public MutableLiveData<List<AnswerModel>> tasks = new MutableLiveData<>();
    private String token;
    private String subject;
    private String task;

    public void setTask(String task) {
        this.task = task;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public AnswerViewModel() {
    }

    public void getAnswersWithPerson() {
        String url = "http://10.0.2.2:8080/api/answers/answer/with/person/";
        OkHttpClient client = new OkHttpClient();
        Log.i("Query", String.format("%s?subject=%s&task=%s", url, subject, task).replaceAll("\\s", "%20"));
        Request request = new Request.Builder().addHeader("Authorization", "Bearer ".concat(token))
                .url(String.format("%s?subject=%s&task=%s", url, subject, task).replaceAll("\\s", "%20")).build();
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
                        List<AnswerModel> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.i("JSON", jsonObject.toString());
                            list.add(new AnswerModel(
                                    jsonObject.getJSONObject("person").getString("name").concat(" ").concat(jsonObject.getJSONObject("person").getString("surname")),
                                    jsonObject.getString("date")
                            ));
                        }
                        tasks.postValue(list);
                    } catch (JSONException e) {
                    }
                }
            }
        });
    }
}
