package com.icommunicate.apiCall.responseModels;

import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;


@JsonObject
public class LogsItem{

	@SerializedName("groupSid")
	@JsonField(name ="groupSid")
	private Object groupSid;

	@SerializedName("priceUnit")
	@JsonField(name ="priceUnit")
	private String priceUnit;

	@SerializedName("answeredBy")
	@JsonField(name ="answeredBy")
	private Object answeredBy;

	@SerializedName("toFormatted")
	@JsonField(name ="toFormatted")
	private String toFormatted;

	@SerializedName("phoneNumberSid")
	@JsonField(name ="phoneNumberSid")
	private String phoneNumberSid;

	@SerializedName("parentCallSid")
	@JsonField(name ="parentCallSid")
	private String parentCallSid;

	@SerializedName("uri")
	@JsonField(name ="uri")
	private String uri;

	@SerializedName("callerName")
	@JsonField(name ="callerName")
	private String callerName;

	@SerializedName("dateUpdated")
	@JsonField(name ="dateUpdated")
	private DateUpdated dateUpdated;

	@SerializedName("sid")
	@JsonField(name ="sid")
	private String sid;

	@SerializedName("duration")
	@JsonField(name ="duration")
	private String duration;

	@SerializedName("apiVersion")
	@JsonField(name ="apiVersion")
	private String apiVersion;

	@SerializedName("dateCreated")
	@JsonField(name ="dateCreated")
	private DateCreated dateCreated;

	@SerializedName("price")
	@JsonField(name ="price")
	private String price;

	@SerializedName("subresourceUris")
	@JsonField(name ="subresourceUris")
	private SubresourceUris subresourceUris;

	@SerializedName("fromFormatted")
	@JsonField(name ="fromFormatted")
	private String fromFormatted;

	@SerializedName("from")
	@JsonField(name ="from")
	private String from;

	@SerializedName("startTime")
	@JsonField(name ="startTime")
	private StartTime startTime;

	@SerializedName("endTime")
	@JsonField(name ="endTime")
	private EndTime endTime;

	@SerializedName("forwardedFrom")
	@JsonField(name ="forwardedFrom")
	private String forwardedFrom;

	@SerializedName("to")
	@JsonField(name ="to")
	private String to;

	@SerializedName("accountSid")
	@JsonField(name ="accountSid")
	private String accountSid;

	@SerializedName("direction")
	@JsonField(name ="direction")
	private String direction;

	@SerializedName("status")
	@JsonField(name ="status")
	private String status;

	public void setGroupSid(Object groupSid){
		this.groupSid = groupSid;
	}

	public Object getGroupSid(){
		return groupSid;
	}

	public void setPriceUnit(String priceUnit){
		this.priceUnit = priceUnit;
	}

	public String getPriceUnit(){
		return priceUnit;
	}

	public void setAnsweredBy(Object answeredBy){
		this.answeredBy = answeredBy;
	}

	public Object getAnsweredBy(){
		return answeredBy;
	}

	public void setToFormatted(String toFormatted){
		this.toFormatted = toFormatted;
	}

	public String getToFormatted(){
		return toFormatted;
	}

	public void setPhoneNumberSid(String phoneNumberSid){
		this.phoneNumberSid = phoneNumberSid;
	}

	public String getPhoneNumberSid(){
		return phoneNumberSid;
	}

	public void setParentCallSid(String parentCallSid){
		this.parentCallSid = parentCallSid;
	}

	public String getParentCallSid(){
		return parentCallSid;
	}

	public void setUri(String uri){
		this.uri = uri;
	}

	public String getUri(){
		return uri;
	}

	public void setCallerName(String callerName){
		this.callerName = callerName;
	}

	public String getCallerName(){
		return callerName;
	}

	public void setDateUpdated(DateUpdated dateUpdated){
		this.dateUpdated = dateUpdated;
	}

	public DateUpdated getDateUpdated(){
		return dateUpdated;
	}

	public void setSid(String sid){
		this.sid = sid;
	}

	public String getSid(){
		return sid;
	}

	public void setDuration(String duration){
		this.duration = duration;
	}

	public String getDuration(){
		return duration;
	}

	public void setApiVersion(String apiVersion){
		this.apiVersion = apiVersion;
	}

	public String getApiVersion(){
		return apiVersion;
	}

	public void setDateCreated(DateCreated dateCreated){
		this.dateCreated = dateCreated;
	}

	public DateCreated getDateCreated(){
		return dateCreated;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setSubresourceUris(SubresourceUris subresourceUris){
		this.subresourceUris = subresourceUris;
	}

	public SubresourceUris getSubresourceUris(){
		return subresourceUris;
	}

	public void setFromFormatted(String fromFormatted){
		this.fromFormatted = fromFormatted;
	}

	public String getFromFormatted(){
		return fromFormatted;
	}

	public void setFrom(String from){
		this.from = from;
	}

	public String getFrom(){
		return from;
	}

	public void setStartTime(StartTime startTime){
		this.startTime = startTime;
	}

	public StartTime getStartTime(){
		return startTime;
	}

	public void setEndTime(EndTime endTime){
		this.endTime = endTime;
	}

	public EndTime getEndTime(){
		return endTime;
	}

	public void setForwardedFrom(String forwardedFrom){
		this.forwardedFrom = forwardedFrom;
	}

	public String getForwardedFrom(){
		return forwardedFrom;
	}

	public void setTo(String to){
		this.to = to;
	}

	public String getTo(){
		return to;
	}

	public void setAccountSid(String accountSid){
		this.accountSid = accountSid;
	}

	public String getAccountSid(){
		return accountSid;
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
			"LogsItem{" + 
			"groupSid = '" + groupSid + '\'' + 
			",priceUnit = '" + priceUnit + '\'' + 
			",answeredBy = '" + answeredBy + '\'' + 
			",toFormatted = '" + toFormatted + '\'' + 
			",phoneNumberSid = '" + phoneNumberSid + '\'' + 
			",parentCallSid = '" + parentCallSid + '\'' + 
			",uri = '" + uri + '\'' + 
			",callerName = '" + callerName + '\'' + 
			",dateUpdated = '" + dateUpdated + '\'' + 
			",sid = '" + sid + '\'' + 
			",duration = '" + duration + '\'' + 
			",apiVersion = '" + apiVersion + '\'' + 
			",dateCreated = '" + dateCreated + '\'' + 
			",price = '" + price + '\'' + 
			",subresourceUris = '" + subresourceUris + '\'' + 
			",fromFormatted = '" + fromFormatted + '\'' + 
			",from = '" + from + '\'' + 
			",startTime = '" + startTime + '\'' + 
			",endTime = '" + endTime + '\'' + 
			",forwardedFrom = '" + forwardedFrom + '\'' + 
			",to = '" + to + '\'' + 
			",accountSid = '" + accountSid + '\'' + 
			",direction = '" + direction + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}