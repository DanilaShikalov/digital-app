package com.example.digitalproject.models;

public class TaskModel {
    private String name;
    private String date_start;
    private String date_end;

    public TaskModel(String name, String date_start, String date_end) {
        this.name = name;
        this.date_start = date_start;
        this.date_end = date_end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }
}
