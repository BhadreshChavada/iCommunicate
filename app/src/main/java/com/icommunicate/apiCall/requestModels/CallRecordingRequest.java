package com.icommunicate.apiCall.requestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by system-15 on 16/1/20 Timing 14:35.
 */
public class CallRecordingRequest {
    @SerializedName("call_id")
    @Expose
    private String callId;

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }
}
