package com.icommunicate.bean;

/**
 * Created by system-15 on 30/5/20 Timing 16:25.
 */

public class DefultDialNumberBeans {
    public String name;
    public String number;
    public String id;
    public boolean isSelected;

    public DefultDialNumberBeans(String id, String name, String number, boolean isSelected) {
        this.name = name;
        this.number = number;
        this.id = id;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
