package com.example.digitalproject.ui.documents;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalproject.models.DocumentModel;
import com.example.digitalproject.models.MemberData;
import com.example.digitalproject.models.MessageModel;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DocumentViewModel extends ViewModel {
    private String token;
    public MutableLiveData<List<DocumentModel>> documents = new MutableLiveData<>();

    public void setToken(String token) {
        this.token = token;
    }

    public DocumentViewModel() {
    }

    public void getDocuments() {
        String url = "http://10.0.2.2:8080/api/documents/mongo/getall/";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("Authorization", "Bearer ".concat(token)).url(url).build();
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
                        List<DocumentModel> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            list.add(new DocumentModel(jsonObject.getString("nameFile"), jsonObject.getString("email"),
                                    Integer.toString(jsonObject.getString("bytes").length()) + " B"));
                        }
                        documents.postValue(list);
                    } catch (JSONException e) {
                    }
                }
            }
        });
    }
}