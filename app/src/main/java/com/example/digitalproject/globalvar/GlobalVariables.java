package com.example.digitalproject.globalvar;

import android.app.Application;

public class GlobalVariables extends Application {
    private String token_access = "null";

    public String getToken_access() {
        return token_access;
    }

    public void setToken_access(String token_access) {
        this.token_access = token_access;
    }
}
