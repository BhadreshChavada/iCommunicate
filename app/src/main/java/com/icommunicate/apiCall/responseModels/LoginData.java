package com.icommunicate.apiCall.responseModels;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class LoginData{

	@SerializedName("password")
	@JsonField(name ="password")
	private String password;

	@SerializedName("id")
	@JsonField(name ="id")
	private String id;

	@SerializedName("parent_user_id")
	@JsonField(name ="parent_user_id")
	private String parentUserId;

	@SerializedName("user_email")
	@JsonField(name ="user_email")
	private String userEmail;

	@SerializedName("first_name")
	@JsonField(name ="first_name")
	private String firstName;

	@SerializedName("last_name")
	@JsonField(name ="last_name")
	private String lastName;



	@SerializedName("user_status")
	@JsonField(name ="user_status")
	private String userStatus;

	@SerializedName("working_hours")
	@JsonField(name ="working_hours")
	private Integer working_hours;


	public Integer getWorking_hours() {
		return Integer.valueOf(working_hours);
	}

	public void setWorking_hours(Integer working_hours) {
		this.working_hours = working_hours;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentUserId() {
		return parentUserId;
	}

	public void setParentUserId(String parentUserId) {
		this.parentUserId = parentUserId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	@Override
	public String toString() {
		return "LoginData{" +
				"password='" + password + '\'' +
				", id='" + id + '\'' +
				", parentUserId='" + parentUserId + '\'' +
				", userEmail='" + userEmail + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", userStatus='" + userStatus + '\'' +
				'}';
	}
}