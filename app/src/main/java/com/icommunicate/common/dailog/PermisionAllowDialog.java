package com.icommunicate.common.dailog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;

import com.icommunicate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PermisionAllowDialog extends BaseDialog {


    @BindView(R.id.dialog_title)
    AppCompatTextView dialogTitle;
    @BindView(R.id.dialog_deny)
    Button dialogDeny;
    @BindView(R.id.dialog_allow)
    Button dialogAllow;

    public interface ConfirmationDialogCallback {
        public void onAllow();

        public void onDeny();
    }

    private ConfirmationDialogCallback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_permision, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static void show(FragmentManager fragmentManager,ConfirmationDialogCallback callback) {
        PermisionAllowDialog dialog = new PermisionAllowDialog();
        dialog.callback = callback;
        dialog.show(fragmentManager, PermisionAllowDialog.class.getSimpleName());
    }

    @OnClick(R.id.dialog_allow)
    public void onAllowButton() {
        callback.onAllow();
        dismiss();
    }

    @OnClick(R.id.dialog_deny)
    public void onDenyButton() {
        callback.onDeny();
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        callback.onDeny();
    }

}