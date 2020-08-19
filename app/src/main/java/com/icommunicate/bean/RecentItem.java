package com.icommunicate.bean;

public class RecentItem {
    String contactName;
    String contactNumber;
    int callStatus;
    String callDuration;
    String callCreated;
    String sID;

    public RecentItem() {
    }

    public RecentItem(String contactName, String contactNumber, int callStatus, String callDuration,String callCreated,String sID) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.callStatus = callStatus;
        this.callDuration = callDuration;
        this.callCreated = callCreated;
        this.sID = sID;
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

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }
}
