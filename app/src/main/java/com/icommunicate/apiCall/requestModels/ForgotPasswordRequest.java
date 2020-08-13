package com.icommunicate.apiCall.requestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by system-15 on 16/1/20 Timing 14:35.
 */
public class ForgotPasswordRequest {
    @SerializedName("email")
    @Expose
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
