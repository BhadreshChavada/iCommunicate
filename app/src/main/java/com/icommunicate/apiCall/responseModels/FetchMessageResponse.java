package com.icommunicate.apiCall.responseModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class FetchMessageResponse{

	@SerializedName("data")
	@JsonField(name ="data")
	private List<FetchMessageItem> data;

	@SerializedName("error")
	@JsonField(name ="error")
	private boolean error;

	@SerializedName("message")
	@JsonField(name ="message")
	private String message;

	public void setData(List<FetchMessageItem> data){
		this.data = data;
	}

	public List<FetchMessageItem> getData(){
		return data;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"FetchMessageResponse{" + 
			"data = '" + data + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}