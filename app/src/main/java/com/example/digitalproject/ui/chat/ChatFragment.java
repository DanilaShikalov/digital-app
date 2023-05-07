package com.example.digitalproject.ui.chat;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    @SuppressLint("StaticFieldLeak")
    private static MessageAdapter messageAdapter;
    private static String token;
    public ChatViewModel chatViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);
        chatViewModel.setProcess(true);
        binding = FragmentChatBinding.inflate(inflater, container, false);

        messageAdapter = new MessageAdapter(requireContext());
        binding.messagesView.setAdapter(messageAdapter);
        binding.setChatViewModel(chatViewModel);
        token = ((GlobalVariables) requireActivity().getApplication()).getToken_access();
        chatViewModel.setToken(token);
        chatViewModel.listMutableLiveData.observe(requireActivity(), new Observer<List<MessageModel>>() {
            @Override
            public void onChanged(List<MessageModel> messageModels) {
                messageAdapter.clear();
                messageAdapter.notifyDataSetChanged();
                messageModels.forEach(x -> messageAdapter.add(x));
                binding.messagesView.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();
            }
        });
        Thread thread = new Thread(chatViewModel);
        thread.start();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        chatViewModel.setProcess(false);
    }
}