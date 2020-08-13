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

public class ConfirmationDialog extends BaseDialog {


    @BindView(R.id.dialog_title)
    AppCompatTextView dialogTitle;
    @BindView(R.id.dialog_no)
    Button dialogNo;
    @BindView(R.id.dialog_yes)
    Button dialogYes;

    public interface ConfirmationDialogCallback {
        public void onYes();

        public void onNo();
    }

    private ConfirmationDialogCallback callback;
    private String message;
    private String cancelButtonText = "";
    private String confirmButtonText = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_custom_okay_cancel_dialog, container, false);
        ButterKnife.bind(this, view);

        dialogTitle.setText(message);

        if (!TextUtils.isEmpty(cancelButtonText) && !TextUtils.isEmpty(confirmButtonText)) {
            dialogNo.setText(cancelButtonText);
            dialogYes.setText(confirmButtonText);
        }


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static void show(String message, FragmentManager fragmentManager, ConfirmationDialogCallback callback) {
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.message = message;
        dialog.callback = callback;
        dialog.show(fragmentManager, ConfirmationDialog.class.getSimpleName());
    }

    public static void show(String message, FragmentManager fragmentManager, String canceleButtonText, String confirmButtonText, ConfirmationDialogCallback callback) {
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.message = message;
        dialog.callback = callback;
        dialog.cancelButtonText = canceleButtonText;
        dialog.confirmButtonText = confirmButtonText;
        dialog.show(fragmentManager, ConfirmationDialog.class.getSimpleName());
    }

    @OnClick(R.id.dialog_yes)
    public void onYesButton() {
        callback.onYes();
        dismiss();
    }

    @OnClick(R.id.dialog_no)
    public void onNoButton() {
        callback.onNo();
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        callback.onNo();
    }

}