package com.example.digitalproject.ui.settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.digitalproject.databinding.FragmentSettingsBinding;
import com.example.digitalproject.globalvar.GlobalVariables;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        settingsViewModel.setToken(((GlobalVariables) requireActivity().getApplication()).getToken_access());
        List<String> jobs = new ArrayList<>();
        settingsViewModel.listJobs.observe(requireActivity(), jobs::addAll);
        settingsViewModel.getJobs();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, jobs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);

        binding.chooseFileButton.setOnClickListener(view -> showFileChooser());

        return binding.getRoot();
    }

    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("*/*");

        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        startActivityForResult(intent, 2000);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String token = ((GlobalVariables) requireActivity().getApplication()).getToken_access();
        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri fileUri = data.getData();
                try {
                    InputStream in = requireActivity().getContentResolver().openInputStream(fileUri);
                    byte[] bytes = new byte[in.available()];
                    in.read(bytes);

                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("multipartFile", "file.txt", RequestBody.create(bytes,
                                    MediaType.parse("multipart/form-data")))
                            .build();
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url("http://10.0.2.2:8080/api/documents/mongo/document/")
                            .addHeader("Authorization", "Bearer ".concat(token))
                            .build();
                    client.newCall(request).execute();
                } catch (Exception e) {

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}