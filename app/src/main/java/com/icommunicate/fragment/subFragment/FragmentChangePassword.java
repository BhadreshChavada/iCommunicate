package com.icommunicate.fragment.subFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.icommunicate.R;
import com.icommunicate.activity.LoginActivity;
import com.icommunicate.activity.MasterActivity;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiResetPassword;
import com.icommunicate.apiCall.requestModels.ResetPasswordRequest;
import com.icommunicate.apiCall.responseModels.DefaultResponse;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.preferences.PreferenceUtil;
import com.icommunicate.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentChangePassword extends BaseFragment {

    final public static String TAG = FragmentChangePassword.class.getName();
    protected View root;
    @BindView(R.id.ic_action_back)
    AppCompatImageView icActionBack;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    @BindView(R.id.change_pw_old)
    AppCompatEditText changePwOld;
    @BindView(R.id.change_pw_new)
    AppCompatEditText changePwNew;
    @BindView(R.id.change_pw_confirm)
    AppCompatEditText changePwConfirm;
    @BindView(R.id.dialog_submit)
    Button dialogSubmit;


    public FragmentChangePassword() {
    }

    public static Fragment newInstance(Context context, String page_name, int count) {
        FragmentChangePassword f = new FragmentChangePassword();
        Bundle bundle = new Bundle();
        bundle.putString("page_name", page_name);
        bundle.putInt("count", count);
        f.setArguments(bundle);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_setting_change_password, container, false);
        ButterKnife.bind(this, root);
        getBundleArguments();
        setUpView();
        return root;
    }

    private void setUpView() {

    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getBundleArguments() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.containsKey("page_name")) {
                try {
                    icActionBack.setVisibility(View.VISIBLE);
                    actionBarTitle.setText(bundle.getString("page_name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick({R.id.ic_action_back, R.id.dialog_submit})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.ic_action_back:
                try {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.dialog_submit:
                validation();
                break;
        }
    }

    private void validation() {
        String OldPWD = CommonMethods.getValue(changePwOld.getText().toString());
        String NewPWD = CommonMethods.getValue(changePwNew.getText().toString());
        String ConformPWD = CommonMethods.getValue(changePwConfirm.getText().toString());

        if (TextUtils.isEmpty(OldPWD)) {
            displayMessage(getActivity().getResources().getString(R.string.VALEDATE_OLD_PASSWORD));
            return;
        }
        if (TextUtils.isEmpty(NewPWD)) {
            displayMessage(getActivity().getResources().getString(R.string.VALEDATE_NEW_PASSWORD));
            return;
        }
        if (TextUtils.isEmpty(ConformPWD)) {
            displayMessage(getActivity().getResources().getString(R.string.VALEDATE_CONFORM_PASSWORD));
            return;
        }

        if (!CommonMethods.getValue(ConformPWD).equalsIgnoreCase(NewPWD)) {
            displayMessage(getActivity().getResources().getString(R.string.VALEDATE_PASSWORD_NOT_MATCH));
            return;
        }

        if (CommonMethods.getValue(OldPWD).equalsIgnoreCase(NewPWD)) {
            displayMessage(getActivity().getResources().getString(R.string.VALEDATE_PASSWORD_OLD_NEW_NOT_MATCH));
            return;
        }
        /*if (!CommonMethods.getValue(PreferenceUtil.loginPassword().get()).equalsIgnoreCase(OldPWD)) {
            displayMessage(getActivity().getResources().getString(R.string.VALEDATE_PASSWORD_NOT_SAME));
            return;
        }*/
        callApichangePassword(NewPWD, OldPWD, PreferenceUtil.loginUserData().get().getId());

    }

    public void displayMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void callApichangePassword(String newPassword, String oldPassword, String userId) {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setNewPassword(newPassword);
        request.setOldPassword(oldPassword);
        request.setUserId(userId);
        ApiResetPassword apiResetPassword = new ApiResetPassword(getActivity(), new IResult() {
            @Override
            public void notifySuccess(String requestType, Object response) {
                DefaultResponse defaultResponse = (DefaultResponse) response;
                if (defaultResponse.isError()) {
                    Toast.makeText(getActivity(), defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    PreferenceUtil.deleteAllSharePrefs();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.right_in, R.anim.right_out);
                        }
                    }, 1000);
                }
            }

            @Override
            public void notifyNetworkSuccess(String requestType) {

            }
        });
        apiResetPassword.execute(request);
    }
}
