package com.icommunicate.apiCall.requestCall;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icommunicate.R;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.RetroApi;
import com.icommunicate.apiCall.ServiceGeneratorRetro;
import com.icommunicate.apiCall.requestModels.DeleteCallLogRequest;
import com.icommunicate.apiCall.responseModels.SuccessResponse;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.KLog;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiDeleteCallLogs {
    private static final String TAG = "ApiDeleteCallLogs";
    private RetroApi retroApi;
    private IResult responseInterface;
    private Context mContext;

    public ApiDeleteCallLogs(Context mContext, IResult responseInterface) {
        retroApi = ServiceGeneratorRetro.createService(RetroApi.class, mContext, false);
        this.responseInterface = responseInterface;
        this.mContext = mContext;
        KLog.setTAG("ApiDeleteCallLogs");
    }


    public void execute(DeleteCallLogRequest deleteCallLogRequest) {
        if (CommonMethods.isNetworkAvailable(mContext)) {

            final ProgressDialog dialog = CommonMethods.showProgressBar(mContext);
            dialog.show();

            final Gson gson = new GsonBuilder().create();

            Call<ResponseBody> responseBodyCall = retroApi.deleteCallRecord(deleteCallLogRequest);
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    try {
                        KLog.e("ResponseCode:" + response.code());
                        CommonMethods.closeDialog(dialog);
                        if (response.code() == 200) {
                            String responseJson = response.body().string();
                            try {
                                responseInterface.notifySuccess(TAG, gson.fromJson(responseJson, SuccessResponse.class));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable error) {
                    CommonMethods.closeDialog(dialog);
                    error.printStackTrace();
                }
            });
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.network_not_available), Toast.LENGTH_SHORT).show();
        }

    }


}
