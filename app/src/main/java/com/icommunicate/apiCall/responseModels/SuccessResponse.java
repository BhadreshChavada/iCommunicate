package com.icommunicate.apiCall.responseModels;
import com.google.gson.annotations.SerializedName;

public class SuccessResponse {



    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;



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


}