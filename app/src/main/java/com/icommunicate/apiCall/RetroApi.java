package com.icommunicate.apiCall;

import com.icommunicate.apiCall.requestModels.CallHistory;
import com.icommunicate.apiCall.requestModels.CallRecordingRequest;
import com.icommunicate.apiCall.requestModels.DefultNumberRequest;
import com.icommunicate.apiCall.requestModels.DeleteCallLogRequest;
import com.icommunicate.apiCall.requestModels.FetchMessageRequest;
import com.icommunicate.apiCall.requestModels.ForgotPasswordRequest;
import com.icommunicate.apiCall.requestModels.LoginRequest;
import com.icommunicate.apiCall.requestModels.ResetPasswordRequest;
import com.icommunicate.apiCall.requestModels.SendMessageRequest;
import com.icommunicate.apiCall.requestModels.UpdateWorkingHourRequest;
import com.icommunicate.apiCall.responseModels.CallTokenModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Ashish parmar
 * Api call
 */
public interface RetroApi {

    @POST("/icoomunicate/users/login")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> login(@Body LoginRequest loginRequest);

    @POST("/icoomunicate/users/forgot_password")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> forgotPassword(@Body ForgotPasswordRequest loginRequest);

    @POST("/icoomunicate/users/resetPassword")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> resetPassword(@Body ResetPasswordRequest loginRequest);

    @GET("/callRecord.php")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> getCallRecord();

    @GET("/accessToken1.php?")
    Call<CallTokenModel> retriveToken(@Query("identity") String name);


    @POST("/icoomunicate/users/callRecording")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> callRecording(@Body CallRecordingRequest callRecordingRequest);

    @POST("/icoomunicate/users/deleteCallResource")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> deleteCallRecord(@Body DeleteCallLogRequest deleteCallLogRequest);

    @POST("/icoomunicate/users/sendMessage")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> sendMessage(@Body SendMessageRequest sendMessageRequest);


    @POST("/icoomunicate/users/fetchMessages")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> fetchMessage(@Body FetchMessageRequest fetchMessageRequest);

    @POST("icoomunicate/users/defaultNumber")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> defaultNumber(@Body DefultNumberRequest defaultNumberRequest);

    @POST("icoomunicate/users/updateWorkingHours")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> updateWorkingHours(@Body UpdateWorkingHourRequest defaultNumberRequest);


    @POST("icoomunicate/users/callLogs")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> getCallLogs(@Body DefultNumberRequest defaultNumberRequest);

    @POST("icoomunicate/users//callLogsTwoNumber")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> getCallHistory(@Body CallHistory callHistory);
}
