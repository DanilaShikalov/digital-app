package com.example.digitalproject.viewmodels;

import androidx.annotation.NonNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class MainViewModelTest {
    @Test
    void authenticate() {
        String url = "http://localhost:8080/api/auth/authenticate";
        OkHttpClient client = new OkHttpClient();
        String email = "string";
        String password = "string";
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        // TODO Непонятно почему POST запрос не имеет тела
        Request request = new Request.Builder().method("POST", requestBody).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                System.out.println(response);
            }
        });
    }
}