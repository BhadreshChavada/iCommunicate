package com.icommunicate.apiCall.responseModels.defult_number;

import com.google.gson.annotations.SerializedName;

public class DefultNumberData {

    @SerializedName("number")
    private String number;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("country")
    private String country;

    @SerializedName("state")
    private String state;

    @SerializedName("default")
    private String jsonMemberDefault;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("id")
    private String id;

    @SerializedName("city")
    private String city;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("updated_date")
    private String updatedDate;

    boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setJsonMemberDefault(String jsonMemberDefault) {
        this.jsonMemberDefault = jsonMemberDefault;
    }

    public String getJsonMemberDefault() {
        return jsonMemberDefault;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "DefultNumberData{" +
                "number='" + number + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", jsonMemberDefault='" + jsonMemberDefault + '\'' +
                ", userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", city='" + city + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
