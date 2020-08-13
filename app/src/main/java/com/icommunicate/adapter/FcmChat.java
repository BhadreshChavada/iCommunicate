package com.icommunicate.adapter;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.icommunicate.apiCall.responseModels.FetchMessageItem;

import java.util.Comparator;

@JsonObject

 public class FcmChat {

    @SerializedName("type")
    @JsonField(name = "type")
    private int type;

    @SerializedName("to")
    @JsonField(name = "to")
    private String to;

    @SerializedName("from")
    @JsonField(name = "from")
    private String from;

    @SerializedName("sender_name")
    @JsonField(name = "sender_name")
    private String senderName;

    @SerializedName("message")
    @JsonField(name = "message")
    private String message;

    @SerializedName("timestamp")
    @JsonField(name = "timestamp")
    private long timestamp;

    public FcmChat(int type,String to, String from, String senderName, String message, long timestamp) {
        this.type = type;
        this.to = to;
        this.from = from;
        this.senderName = senderName;
        this.message = message;
        this.timestamp = timestamp;
    }

    public FcmChat() {
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


}