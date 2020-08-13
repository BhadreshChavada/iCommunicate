package com.icommunicate.apiCall.responseModels;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.Comparator;

@JsonObject
public class FetchMessageItem {

	@SerializedName("from")
	@JsonField(name ="from")
	private String from;

	@SerializedName("to")
	@JsonField(name ="to")
	private String to;

	@SerializedName("body")
	@JsonField(name ="body")
	private String body;

	@SerializedName("dateSent")
	@JsonField(name ="dateSent")
	private DateSent dateSent;

	@SerializedName("direction")
	@JsonField(name ="direction")
	private String direction;

	@SerializedName("status")
	@JsonField(name ="status")
	private String status;

	public void setFrom(String from){
		this.from = from;
	}

	public String getFrom(){
		return from;
	}

	public void setTo(String to){
		this.to = to;
	}

	public String getTo(){
		return to;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getBody(){
		return body;
	}

	public void setDateSent(DateSent dateSent){
		this.dateSent = dateSent;
	}

	public DateSent getDateSent(){
		return dateSent;
	}

	public void setDirection(String direction){
		this.direction = direction;
	}

	public String getDirection(){
		return direction;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}


	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"from = '" + from + '\'' + 
			",to = '" + to + '\'' + 
			",body = '" + body + '\'' + 
			",dateSent = '" + dateSent + '\'' + 
			",direction = '" + direction + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}