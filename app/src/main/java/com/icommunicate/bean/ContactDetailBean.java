package com.icommunicate.bean;

public class ContactDetailBean {
    String title;
    String value;
    int head_image;
    int tail_image;

    public ContactDetailBean(String title, String value, int head_image, int tail_image) {
        this.title = title;
        this.value = value;
        this.head_image = head_image;
        this.tail_image = tail_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getHead_image() {
        return head_image;
    }

    public void setHead_image(int head_image) {
        this.head_image = head_image;
    }

    public int getTail_image() {
        return tail_image;
    }

    public void setTail_image(int tail_image) {
        this.tail_image = tail_image;
    }
}
