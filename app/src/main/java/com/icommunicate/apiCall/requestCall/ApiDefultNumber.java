package com.icommunicate.apiCall.requestCall;

import android.app.ProgressDialog;
import android.content.Context;
import android.nfc.Tag;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icommunicate.R;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.RetroApi;
import com.icommunicate.apiCall.ServiceGeneratorRetro;
import com.icommunicate.apiCall.requestModels.DefultNumberRequest;
import com.icommunicate.apiCall.requestModels.ForgotPasswordRequest;
import com.icommunicate.apiCall.responseModels.CallRecordResponse;
import com.icommunicate.apiCall.responseModels.DefaultResponse;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberResponse;
import com.icommunicate.bean.DefultDialNumberBeans;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.KLog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiDefultNumber {
    private static final String TAG = "ApiDefultNumber";
    private RetroApi retroApi;
    private IResult responseInterface;
    private Context mContext;

    public ApiDefultNumber(Context mContext, IResult responseInterface) {
        retroApi = ServiceGeneratorRetro.createService(RetroApi.class, mContext, false);
        this.responseInterface = responseInterface;
        this.mContext = mContext;
    }

    public void execute(DefultNumberRequest request) {
        if (CommonMethods.isNetworkAvailable(mContext)) {

            final ProgressDialog dialog = CommonMethods.showProgressBar(mContext);
            dialog.show();

            final Gson gson = new GsonBuilder().create();

            Call<ResponseBody> responseBodyCall = retroApi.defaultNumber(request);
            responseBodyCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    try {
                        KLog.e("ResponseCode:" + response.code());
                        CommonMethods.closeDialog(dialog);

                        if (response.code() == 200) {
                            String responseJson = response.body().string();
                            try {
                                KLog.e(TAG + " " + responseJson);
                                responseInterface.notifySuccess(TAG, gson.fromJson(responseJson, DefultNumberResponse.class));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            String res = response.errorBody().string();
                            KLog.e(res);
                            try {
                                int responseCode = response.code();
                                CommonMethods.ErrorDialog(mContext, responseCode);
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
