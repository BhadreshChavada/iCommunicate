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

public class OkayDialog extends BaseDialog {


    @BindView(R.id.dialog_title)
    AppCompatTextView dialogTitle;
    @BindView(R.id.dialog_yes)
    Button dialogYes;

    public interface OkayDialogCallback {
        public void onYes();
    }

    private OkayDialogCallback callback;
    private String message;
    private String confirmButtonText = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_custom_okay_dialog, container, false);
        ButterKnife.bind(this, view);

        dialogTitle.setText(message);

        if (!TextUtils.isEmpty(confirmButtonText)) {
            dialogYes.setText(confirmButtonText);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static void show(String message, FragmentManager fragmentManager, OkayDialogCallback callback) {
        OkayDialog dialog = new OkayDialog();
        dialog.message = message;
        dialog.callback = callback;
        dialog.show(fragmentManager, OkayDialog.class.getSimpleName());
    }

    public static void show(String message, FragmentManager fragmentManager, String confirmButtonText, OkayDialogCallback callback) {
        OkayDialog dialog = new OkayDialog();
        dialog.message = message;
        dialog.callback = callback;
        dialog.confirmButtonText = confirmButtonText;
        dialog.show(fragmentManager, OkayDialog.class.getSimpleName());
    }

    @OnClick(R.id.dialog_yes)
    public void onYesButton() {
        callback.onYes();
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        callback.onYes();
    }

}