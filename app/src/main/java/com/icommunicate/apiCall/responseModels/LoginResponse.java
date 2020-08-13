package com.icommunicate.apiCall.responseModels;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("data")
    private LoginData data;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setData(LoginData data) {
        this.data = data;
    }

    public LoginData getData() {
        return data;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "LoginResponse{" +
                        "data = '" + data + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}