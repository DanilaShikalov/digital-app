package com.example.digitalproject.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonAnswerViewModel extends ViewModel {
    public MutableLiveData<String> answer = new MutableLiveData<>();
    public MutableLiveData<String> grade = new MutableLiveData<>();
    private String token;
    private String subject;
    private String task;
    private String name;
    private String surname;

    public void getAnswerWithGradeByNameAndSurname() {
        String url = "http://10.0.2.2:8080/api/answers/answer/by/name/";
        OkHttpClient client = new OkHttpClient();
        String query = String.format("%s?subject=%s&task=%s&name=%s&surname=%s", url, this.subject, this.task, this.name, this.surname).replaceAll("\\s", "%20");
        Log.i("REQUEST", query);
        Request request = new Request.Builder().addHeader("Authorization", "Bearer ".concat(token))
                .url(query).build();
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
                        JSONObject jsonObject = new JSONObject(resp);
                        String puk = jsonObject.getString("bytes");
                        byte[] array = new byte[puk.length()];
                        for (int i = 0; i < array.length; i++) {
                            array[i] = (byte) puk.charAt(i);
                        }
                        Log.i("BYTES", Arrays.toString(array));
                        String text = new String(Base64.getDecoder().decode(puk));
                        Log.i("TEXT", text);
                        answer.postValue(text);
                        grade.postValue(jsonObject.getString("grade"));
                    } catch (JSONException e) {
                    }
                }
            }
        });
    }

    public void setGrade(String grade) throws IOException {
        String url = "http://10.0.2.2:8080/api/grades/grade/by/name/";
        OkHttpClient client = new OkHttpClient();
        String query = String.format("%s?subject=%s&task=%s&name=%s&surname=%s&grade=%s", url, this.subject, this.task, this.name, this.surname, grade).replaceAll("\\s", "%20");
        Log.i("REQUEST", query);
        RequestBody reqbody = RequestBody.create(new byte[0], null);
        Request request = new Request.Builder().addHeader("Authorization", "Bearer ".concat(token))
                .url(query).post(reqbody).build();
        Log.i("RUN", "START");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
