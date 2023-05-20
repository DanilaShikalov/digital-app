package com.example.digitalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.digitalproject.adapters.AnswerAdapter;
import com.example.digitalproject.adapters.TasksAdapter;
import com.example.digitalproject.databinding.ActivityAnswerBinding;
import com.example.digitalproject.databinding.ActivityTasksBinding;
import com.example.digitalproject.globalvar.GlobalVariables;
import com.example.digitalproject.models.AnswerModel;
import com.example.digitalproject.models.TaskModel;
import com.example.digitalproject.viewmodels.AnswerViewModel;
import com.example.digitalproject.viewmodels.TasksViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AnswerActivity extends AppCompatActivity {
    private ActivityAnswerBinding binding;
    private AnswerViewModel answerViewModel;
    private AnswerAdapter answerAdapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}
        binding = DataBindingUtil.setContentView(this, R.layout.activity_answer);
        binding.setLifecycleOwner(this);
        token = ((GlobalVariables) this.getApplication()).getToken_access();
        answerViewModel = ViewModelProviders.of(this).get(AnswerViewModel.class);
        answerViewModel.setSubject(getIntent().getStringExtra("subject"));
        answerViewModel.setTask(getIntent().getStringExtra("task"));
        answerViewModel.setToken(token);
        answerAdapter = new AnswerAdapter(this);
        binding.answersView.setAdapter(answerAdapter);
        answerViewModel.tasks.observe(this, new Observer<List<AnswerModel>>() {
            @Override
            public void onChanged(List<AnswerModel> taskModels) {
                Log.i("list", taskModels.toString());
                taskModels.forEach(x -> answerAdapter.add(x));
                answerAdapter.notifyDataSetChanged();
            }
        });

        binding.answersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AnswerModel answerModel = (AnswerModel) answerAdapter.getItem(i);
                Intent intent = new Intent(AnswerActivity.this, PersonAnswerActivity.class);
                intent.putExtra("task", getIntent().getStringExtra("task"));
                intent.putExtra("subject", getIntent().getStringExtra("subject"));
                intent.putExtra("name", answerModel.getName());
                AnswerActivity.this.startActivity(intent);
            }
        });

        answerViewModel.getAnswersWithPerson();

        binding.saveAnswer.setOnClickListener(view -> showFileChooser());;
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
        String token = ((GlobalVariables) this.getApplication()).getToken_access();
        String subject = getIntent().getStringExtra("subject");
        String task = getIntent().getStringExtra("task");
        String url = String.format("http://10.0.2.2:8080/api/answers/mongo/answer-by-token/?subject=%s&task=%s", subject, task).replaceAll(" ", "%20");
        if (requestCode == 2000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri fileUri = data.getData();
                try {
                    InputStream in = this.getContentResolver().openInputStream(fileUri);
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
                            .url(url)
                            .addHeader("Authorization", "Bearer ".concat(token))
                            .build();
                    Log.i("URL", url);
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}