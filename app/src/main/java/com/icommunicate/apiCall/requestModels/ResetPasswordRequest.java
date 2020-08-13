package com.icommunicate.apiCall.requestModels;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordRequest{

	@SerializedName("old_password")
	private String oldPassword;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("new_password")
	private String newPassword;

	public void setOldPassword(String oldPassword){
		this.oldPassword = oldPassword;
	}

	public String getOldPassword(){
		return oldPassword;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setNewPassword(String newPassword){
		this.newPassword = newPassword;
	}

	public String getNewPassword(){
		return newPassword;
	}

	@Override
 	public String toString(){
		return 
			"ResetPasswordRequest{" + 
			"old_password = '" + oldPassword + '\'' + 
			",user_id = '" + userId + '\'' + 
			",new_password = '" + newPassword + '\'' + 
			"}";
		}
}