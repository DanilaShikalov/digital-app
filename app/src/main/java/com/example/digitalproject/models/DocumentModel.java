package com.example.digitalproject.models;

public class DocumentModel {
    private String type;
    private String email;
    private String size;

    public DocumentModel(String type, String email, String size) {
        this.type = type;
        this.email = email;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
