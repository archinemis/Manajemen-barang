package com.example.retrofit.Model;

import com.google.gson.annotations.SerializedName;

public class UploadImage {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;
    String getMessage() {
        return message;
    }
    boolean getSuccess() {
        return success;
    }
}
