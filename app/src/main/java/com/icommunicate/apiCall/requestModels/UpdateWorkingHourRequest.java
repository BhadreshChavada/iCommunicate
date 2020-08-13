package com.icommunicate.apiCall.requestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by system-15 on 16/1/20 Timing 14:35.
 */
public class UpdateWorkingHourRequest {
    @SerializedName("user_id")
    @Expose
    private String userId;


    @SerializedName("working_hours")
    @Expose
    private String workingHours;


    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
