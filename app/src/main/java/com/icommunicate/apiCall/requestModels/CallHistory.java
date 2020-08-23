package com.icommunicate.apiCall.requestModels;

/**
 * Created by Bhadresh on 23,August,2020
 */

public class CallHistory {


    String from_number, to_number;

    public CallHistory(String from_number, String to_number) {
        this.from_number = from_number;
        this.to_number = to_number;
    }

    public String getFrom_number() {
        return from_number;
    }

    public void setFrom_number(String from_number) {
        this.from_number = from_number;
    }

    public String getTo_number() {
        return to_number;
    }

    public void setTo_number(String to_number) {
        this.to_number = to_number;
    }
}
