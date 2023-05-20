package com.example.digitalproject.viewmodels;

import static org.junit.jupiter.api.Assertions.assertEquals;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;
import androidx.lifecycle.MutableLiveData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class MainViewModelTest {

    @BeforeEach
    public void beforeEach() {
        ArchTaskExecutor.getInstance().setDelegate(new TaskExecutor() {
            @Override
            public void executeOnDiskIO(@NonNull Runnable runnable) {
                runnable.run();
            }

            @Override
            public void postToMainThread(@NonNull Runnable runnable) {
                runnable.run();
            }

            @Override
            public boolean isMainThread() {
                return true;
            }
        });
    }

    @Test
    void authenticate() {
        MainViewModel mainViewModel = new MainViewModel();
        mainViewModel.email.setValue("test");
        mainViewModel.password.setValue("test");
        mainViewModel.info.setValue("");

        mainViewModel.authenticate();

        assertEquals(mainViewModel.info.getValue(), "");
    }

    static int code = 100;

    @Test
    public void testOkHttpClient() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://google.com")
                .build();
        final CountDownLatch latch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                code = 400;
                latch.countDown();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                code = 200;
                latch.countDown();
            }
        });
        latch.await();
        assertEquals(200, code);
    }
}