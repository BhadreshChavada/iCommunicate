package com.icommunicate.bean;

public class MessageItem {
    String contactName;
    String lastMsg;
    String callCreated;

    public MessageItem() {
    }

    public MessageItem(String contactName, String lastMsg, String callCreated) {
        this.contactName = contactName;
        this.lastMsg = lastMsg;
        this.callCreated = callCreated;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getCallCreated() {
        return callCreated;
    }

    public void setCallCreated(String callCreated) {
        this.callCreated = callCreated;
    }
}
