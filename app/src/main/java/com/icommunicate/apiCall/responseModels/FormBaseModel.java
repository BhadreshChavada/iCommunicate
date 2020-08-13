package com.icommunicate.apiCall.responseModels;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class FormBaseModel {

   @SerializedName("id")
   private int id;

   @SerializedName("status")
   private boolean status;

   @SerializedName("message")
   private String message;

    public FormBaseModel() {
    }

    public FormBaseModel(int id, boolean status, String message) {
        this.id = id;
        this.status = status;
        this.message = message;
    }

    public int getId() {
       return id;
   }

   public void setId(int id) {
       this.id = id;
   }

   public boolean isStatus() {
       return status;
   }

   public void setStatus(boolean status) {
       this.status = status;
   }

   public String getMessage() {
       return message;
   }

   public void setMessage(String message) {
       this.message = message;
   }
}
