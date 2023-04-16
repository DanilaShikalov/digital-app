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

public class MainViewModel extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<MainModel> mainModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> info = new MutableLiveData<>();

    public void authenticate() {
        String url = "http://10.0.2.2:8080/api/auth/authenticate";
        OkHttpClient client = new OkHttpClient();
//        String email = this.email.getValue();
//        String password = this.password.getValue();
//        if (email != null && password != null) {
            JSONObject json = new JSONObject();
            try {
                json.put("email", "a");
                json.put("password", "a");
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
                        Log.i("Token", resp);
                        try {
                            JSONObject jsonObject = new JSONObject(resp);
                            mainModelMutableLiveData.postValue(new MainModel(jsonObject.getString("token")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (response.code() >= 400 && response.code() < 500) {
                        info.postValue("Ошибка");
                    }
                }
            });
//        }
    }
}
