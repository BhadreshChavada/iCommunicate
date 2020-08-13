package com.icommunicate.apiCall.responseModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
@JsonObject
public class CallRecordResponse {

	@SerializedName("data")
	@JsonField(name = "data")
	private List<LogsItem> logs;

	public void setLogs(List<LogsItem> logs) {
		this.logs = logs;
	}

	public List<LogsItem> getLogs() {
		return logs;
	}

	@Override
	public String toString() {
		return "CallRecordResponse{" +
				"logs=" + logs +
				'}';
	}
}