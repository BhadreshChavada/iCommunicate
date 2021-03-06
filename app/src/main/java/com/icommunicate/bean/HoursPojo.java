package com.icommunicate.bean;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by system-15 on 28/6/20 Timing 19:18.
 */

public class HoursPojo {
    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("from")
    @Expose
    private String from;

    @SerializedName("to")
    @Expose
    private String to;

    @SerializedName("isDayoff")
    @Expose
    private boolean isDayoff;




    public HoursPojo(String day, String from, String to, boolean isDayoff) {
        this.day = day;
        this.from = from;
        this.to = to;
        this.isDayoff = isDayoff;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public boolean getIsDayoff() {
        return isDayoff;

    }

    public void setIsDayoff(boolean isDayoff) {
        this.isDayoff = isDayoff;
    }
}
