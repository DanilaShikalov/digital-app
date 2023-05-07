package com.example.digitalproject.ui.chat;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalproject.models.MainModel;
import com.example.digitalproject.models.MemberData;
import com.example.digitalproject.models.MessageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatViewModel extends ViewModel implements Runnable {
    private boolean process = true;
    public MutableLiveData<List<MessageModel>> listMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> messageLiveData = new MutableLiveData<>();
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void run() {
        String url = "http://10.0.2.2:8080/api/messages/message/";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("Authorization", "Bearer ".concat(token)).url(url).build();
        while (process) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("RUN", "START");
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (listMutableLiveData.getValue() != null) {
                        listMutableLiveData.getValue().clear();
                    }
                    if (response.isSuccessful()) {
                        String resp = Objects.requireNonNull(response.body()).string();
                        try {
                            JSONArray jsonArray = new JSONArray(resp);
                            List<MessageModel> list = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.i("JSON", jsonObject.toString());
                                list.add(new MessageModel(jsonObject.getString("message"),
                                        new MemberData(jsonObject.getJSONObject("person").getString("name"), "red"),//jsonObject.getString("color")),
                                        jsonObject.getBoolean("statusMember")));
                            }
                            listMutableLiveData.postValue(list);
                        } catch (JSONException e) {
                        }
                    }
                }
            });
        }
    }

    public void postMessage() {
        Log.i("CLICK", messageLiveData.getValue() == null ? "NULL" : messageLiveData.getValue());
        String url = "http://10.0.2.2:8080/api/messages/message/";
        String message = messageLiveData.getValue();
        OkHttpClient client = new OkHttpClient();
        if (message != null) {
            JSONObject json = new JSONObject();
            try {
                json.put("color", "red");
                json.put("message", message);
                json.put("date", LocalDateTime.now());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Request request = new Request.Builder().addHeader("Authorization", "Bearer ".concat(token))
                    .post(RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"))).url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                }
            });
        }
    }

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }
}