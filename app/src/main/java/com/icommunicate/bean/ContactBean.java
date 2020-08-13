package com.icommunicate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContactBean implements Parcelable {
    String id;
    String name;
    String phone_number;
    String profile;
    String user_id;
    boolean isMemberbean = false;
    boolean isSelectedbean = false;
    List<String> email;
    List<String> Numbers;

    String contactId;
    String lookupId;


    public ContactBean(String senderUid, String senderName, String date) {
        this.id = senderUid;
        this.phone_number = senderName;
        this.name = date;
    }


    public ContactBean(String id, String name, String phone_number, String profile, List<String> email, String contactId, String lookupId) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.profile = profile;
        this.email = email;
        this.contactId = contactId;
        this.lookupId = lookupId;
    }

    protected ContactBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        contactId = in.readString();
        lookupId = in.readString();
        phone_number = in.readString();
        profile = in.readString();
        user_id = in.readString();
        isMemberbean = in.readByte() != 0;
        isSelectedbean = in.readByte() != 0;
        email = in.createStringArrayList();
        Numbers = in.createStringArrayList();
    }

    public static final Creator<ContactBean> CREATOR = new Creator<ContactBean>() {
        @Override
        public ContactBean createFromParcel(Parcel in) {
            return new ContactBean(in);
        }

        @Override
        public ContactBean[] newArray(int size) {
            return new ContactBean[size];
        }
    };

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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isMemberbean() {
        return isMemberbean;
    }

    public void setMemberbean(boolean memberbean) {
        isMemberbean = memberbean;
    }

    public boolean isSelectedbean() {
        return isSelectedbean;
    }

    public void setSelectedbean(boolean selectedbean) {
        isSelectedbean = selectedbean;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getNumbers() {
        return Numbers;
    }

    public void setNumbers(List<String> numbers) {
        Numbers = numbers;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getLookupId() {
        return lookupId;
    }

    public void setLookupId(String lookupId) {
        this.lookupId = lookupId;
    }

    public static Creator<ContactBean> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(contactId);
        dest.writeString(lookupId);
        dest.writeString(phone_number);
        dest.writeString(profile);
        dest.writeString(user_id);
        dest.writeByte((byte) (isMemberbean ? 1 : 0));
        dest.writeByte((byte) (isSelectedbean ? 1 : 0));
        dest.writeStringList(email);
        dest.writeStringList(Numbers);
    }
}
