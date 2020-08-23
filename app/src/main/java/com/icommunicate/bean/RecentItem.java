package com.icommunicate.bean;

import java.io.Serializable;

public class RecentItem implements Serializable {
    String contactName;
    String toNumber;
    int callStatus;
    String callDuration;
    String callCreated;
    String sID;
    String fromNumber;
    String displayNumber;

    public RecentItem() {
    }

    public RecentItem(String contactName, String displayNumber, String toNumber, String fromNumber, int callStatus, String callDuration, String callCreated, String sID) {
        this.contactName = contactName;
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.callStatus = callStatus;
        this.callDuration = callDuration;
        this.callCreated = callCreated;
        this.displayNumber = displayNumber;
        this.sID = sID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
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

    public String getDisplayNumber() {
        return displayNumber;
    }

    public void setDisplayNumber(String displayNumber) {
        this.displayNumber = displayNumber;
    }
}
