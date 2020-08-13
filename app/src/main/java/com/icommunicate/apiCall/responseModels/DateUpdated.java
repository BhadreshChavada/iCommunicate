package com.icommunicate.apiCall.responseModels;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject
public class DateUpdated{

	@SerializedName("date")
	@JsonField(name ="date")
	private String date;

	@SerializedName("timezone")
	@JsonField(name ="timezone")
	private String timezone;

	@SerializedName("timezone_type")
	@JsonField(name ="timezone_type")
	private int timezoneType;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setTimezone(String timezone){
		this.timezone = timezone;
	}

	public String getTimezone(){
		return timezone;
	}

	public void setTimezoneType(int timezoneType){
		this.timezoneType = timezoneType;
	}

	public int getTimezoneType(){
		return timezoneType;
	}

	@Override
 	public String toString(){
		return 
			"DateUpdated{" + 
			"date = '" + date + '\'' + 
			",timezone = '" + timezone + '\'' + 
			",timezone_type = '" + timezoneType + '\'' + 
			"}";
		}
}