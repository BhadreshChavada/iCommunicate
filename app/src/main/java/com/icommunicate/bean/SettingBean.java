package com.icommunicate.bean;

/**
 * Created by system-15 on 18/4/20 Timing 10:54.
 */
public class SettingBean {
    private String id;
    private String name;
    private String headerTitle;
private int imageResource;

    public SettingBean(String id, String name, String headerTitle) {
        this.id = id;
        this.name = name;
        this.headerTitle = headerTitle;
    }

    public SettingBean(String id, String name, String headerTitle, int imageResource) {
        this.id = id;
        this.name = name;
        this.headerTitle = headerTitle;
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }
}
