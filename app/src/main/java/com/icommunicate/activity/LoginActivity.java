package com.icommunicate.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.icommunicate.R;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiLogin;
import com.icommunicate.apiCall.requestModels.LoginRequest;
import com.icommunicate.apiCall.responseModels.LoginResponse;
import com.icommunicate.common.IntentUtils;
import com.icommunicate.common.KeyboardUtils;
import com.icommunicate.common.dailog.PermisionAllowDialog;
import com.icommunicate.common.preferences.PreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_email)
    AppCompatEditText loginEmail;
    @BindView(R.id.login_password)
    AppCompatEditText loginPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.btn_forgot_password)
    AppCompatButton btnForgotPassword;
    @BindView(R.id.txtRegistration)
    TextView txtRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        String firstString = getColoredSpanned("Think Global Act Local - ", "#00023b");
        String secondString = getColoredSpanned("Create Account", "#c92800");

        txtRegistration.setText(Html.fromHtml(firstString + secondString));
    }


    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.finishActivity(LoginActivity.this);
    }

    private void logicLogin() {
        String textLoginEmail = loginEmail.getText().toString();
        String textLoginPassword = loginPassword.getText().toString();

        if (TextUtils.isEmpty(textLoginEmail)) {
            Toast.makeText(this, getResources().getString(R.string.blank_email), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!(Patterns.EMAIL_ADDRESS.matcher(textLoginEmail).matches())) {
            Toast.makeText(this, getResources().getString(R.string.valid_email), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(textLoginPassword)) {
            Toast.makeText(this, getResources().getString(R.string.blank_password), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            KeyboardUtils.hideKeyboard(LoginActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PermisionAllowDialog.show(getSupportFragmentManager(), new PermisionAllowDialog.ConfirmationDialogCallback() {
            @Override
            public void onAllow() {
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(textLoginEmail);
                loginRequest.setPassword(textLoginPassword);
                callLogin(loginRequest);

            }

            @Override
            public void onDeny() {

            }
        });
    }

    //Call login api

    private void callLogin(LoginRequest loginRequest) {
        ApiLogin apiLogin = new ApiLogin(LoginActivity.this, new IResult() {
            @Override
            public void notifySuccess(String requestType, Object response) {
                if (response instanceof LoginResponse) {
                    LoginResponse loginResponse = (LoginResponse) response;
                    if (loginResponse.isError()) {
                        Toast.makeText(LoginActivity.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        PreferenceUtil.loginPassword().set(loginRequest.getPassword());
                        PreferenceUtil.isUserLoggedIn().set(true);
                        PreferenceUtil.loginUserData().set(loginResponse.getData());
                        IntentUtils.redirectWithFinishTo(LoginActivity.this, MasterActivity.class);
                    }
                }
            }

            @Override
            public void notifyNetworkSuccess(String requestType) {

            }
        });
        apiLogin.execute(loginRequest);
    }

    @OnClick({R.id.btn_login, R.id.btn_forgot_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                logicLogin();
                break;
            case R.id.btn_forgot_password:
                IntentUtils.redirectTo(LoginActivity.this, ForgotPasswordActivity.class);
                break;
        }
    }
}
