package com.icommunicate.apiCall.requestCall;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icommunicate.R;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.RetroApi;
import com.icommunicate.apiCall.ServiceGeneratorRetro;
import com.icommunicate.apiCall.responseModels.CallTokenModel;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.KLog;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiRetriveToken {
    private static final String TAG = "CallTokenRetrive";
    private RetroApi retroApi;
    private IResult responseInterface;
    private Context mContext;

    public ApiRetriveToken(Context mContext, IResult responseInterface) {
        retroApi = ServiceGeneratorRetro.createService(RetroApi.class, mContext, false);
        this.responseInterface = responseInterface;
        this.mContext = mContext;
        KLog.setTAG("ApiLogin");
    }

    public void execute(String loginRequest) {
        if (CommonMethods.isNetworkAvailable(mContext)) {

            final ProgressDialog dialog = CommonMethods.showProgressBar(mContext);
            dialog.show();

            final Gson gson = new GsonBuilder().create();

            Call<CallTokenModel> responseBodyCall = retroApi.retriveToken(loginRequest);
            responseBodyCall.enqueue(new Callback<CallTokenModel>() {
                @Override
                public void onResponse(@NotNull Call<CallTokenModel> call, @NotNull Response<CallTokenModel> response) {

                    Log.d("TOKENNNNN", response.body().getToken());

                    try {
                        KLog.e("ResponseCode:" + response.code());
                        CommonMethods.closeDialog(dialog);

                        if (response.code() == 200) {


                            String responseJson = response.body().getToken();
                            try {
                                responseInterface.notifySuccess(TAG, responseJson);
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
                public void onFailure(@NotNull Call<CallTokenModel> call, @NotNull Throwable error) {
                    CommonMethods.closeDialog(dialog);
                    error.printStackTrace();

                }
            });
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.network_not_available), Toast.LENGTH_SHORT).show();
        }
    }

}
