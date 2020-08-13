package com.icommunicate.bean;

public class RecentItem {
    String contactName;
    String contactNumber;
    int callStatus;
    String callDuration;
    String callCreated;

    public RecentItem() {
    }

    public RecentItem(String contactName, String contactNumber, int callStatus, String callDuration,String callCreated) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.callStatus = callStatus;
        this.callDuration = callDuration;
        this.callCreated = callCreated;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(int callStatus) {
        this.callStatus = callStatus;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getCallCreated() {
        return callCreated;
    }

    public void setCallCreated(String callCreated) {
        this.callCreated = callCreated;
    }
}
