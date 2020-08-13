package com.icommunicate.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.icommunicate.R;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiForgotPassword;
import com.icommunicate.apiCall.requestModels.ForgotPasswordRequest;
import com.icommunicate.common.IntentUtils;
import com.icommunicate.common.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgotPasswordActivity extends AppCompatActivity {


    @BindView(R.id.forgot_password_email)
    AppCompatEditText forgotPasswordEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.finishActivity(ForgotPasswordActivity.this);
    }

    @OnClick(R.id.btn_forgot_password)
    public void onViewClicked() {
        String textForgotPasswordEmail = forgotPasswordEmail.getText().toString();

        if (TextUtils.isEmpty(textForgotPasswordEmail)) {
            Toast.makeText(this, getResources().getString(R.string.blank_email), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(Patterns.EMAIL_ADDRESS.matcher(textForgotPasswordEmail).matches())) {
            Toast.makeText(this, getResources().getString(R.string.valid_email), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            KeyboardUtils.hideKeyboard(ForgotPasswordActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail(textForgotPasswordEmail);
        callForgotPassword(forgotPasswordRequest);
    }

    //Call Forgot Password api
    private void callForgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        ApiForgotPassword apiForgotPassword = new ApiForgotPassword(ForgotPasswordActivity.this, new IResult() {
            @Override
            public void notifySuccess(String requestType, Object response) {
                if (response instanceof Boolean) {
                    IntentUtils.finishActivity(ForgotPasswordActivity.this);
                }
            }

            @Override
            public void notifyNetworkSuccess(String requestType) {

            }
        });
        apiForgotPassword.execute(forgotPasswordRequest);
    }
}
