package com.example.digitalproject.ui.chat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.digitalproject.LoginActivity;
import com.example.digitalproject.R;
import com.example.digitalproject.adapters.MessageAdapter;
import com.example.digitalproject.databinding.FragmentChatBinding;
import com.example.digitalproject.globalvar.GlobalVariables;
import com.example.digitalproject.models.MemberData;
import com.example.digitalproject.models.MessageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private MessageAdapter messageAdapter;
    private Thread runnable;
    private String token;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChatViewModel chatViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);

        messageAdapter = new MessageAdapter(requireContext());
        binding.messagesView.setAdapter(messageAdapter);

        token = ((GlobalVariables) requireActivity().getApplication()).getToken_access();

        runnable = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://10.0.2.2:8080/api/messages/message/";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().addHeader("Authorization", "Bearer ".concat(token)).url(url).build();
                while (Thread.currentThread().isInterrupted()) {
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
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        /// TODO Доделать чат
//                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
//                                        messageAdapter.add(new MessageModel(jsonObject.getString("message"),
//                                                new MemberData(jsonObject.getJSONObject("person").getString("name"), jsonObject.getString("color")),
//                                                ));
                                    }
                                } catch (JSONException e) {}
                            }
                        }
                    });
                }
            }
        });

        messageAdapter.add(new MessageModel("hello", new MemberData("black", "black"), true));
        messageAdapter.add(new MessageModel("213", new MemberData("blue", "blue"), true));
        messageAdapter.add(new MessageModel("fgfgfg", new MemberData("red", "red"), false));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        runnable.interrupt();
        binding = null;
    }
}