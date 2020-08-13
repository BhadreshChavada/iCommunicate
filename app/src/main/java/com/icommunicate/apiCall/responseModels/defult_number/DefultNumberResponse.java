package com.icommunicate.apiCall.responseModels.defult_number;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DefultNumberResponse{

	@SerializedName("data")
	private List<DefultNumberData> data;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setData(List<DefultNumberData> data){
		this.data = data;
	}

	public List<DefultNumberData> getData(){
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
			"DefultNumberResponse{" + 
			"data = '" + data + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}