package com.icommunicate.apiCall.responseModels;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject
public class SubresourceUris{

	@SerializedName("recordings")
	@JsonField(name ="recordings")
	private String recordings;

	@SerializedName("notifications")
	@JsonField(name ="notifications")
	private String notifications;

	public void setRecordings(String recordings){
		this.recordings = recordings;
	}

	public String getRecordings(){
		return recordings;
	}

	public void setNotifications(String notifications){
		this.notifications = notifications;
	}

	public String getNotifications(){
		return notifications;
	}

	@Override
 	public String toString(){
		return 
			"SubresourceUris{" + 
			"recordings = '" + recordings + '\'' + 
			",notifications = '" + notifications + '\'' + 
			"}";
		}
}