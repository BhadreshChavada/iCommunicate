package com.icommunicate.common.dailog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icommunicate.R;
import com.icommunicate.adapter.SelectContactNumberAdapter;
import com.icommunicate.adapterActions.SelectNumberCallbackCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by system-15 on 14/6/20 Timing 17:38.
 */

public class SelectContactNumberDailog extends BaseDialog {


    SelectContactNumberAdapter mAdapter;
    List<String> defultNumberData;
    String title;
    SelectNumberCallbackCallback callback;
    @BindView(R.id.recyclerview_number)
    RecyclerView recyclerviewNumber;
    @BindView(R.id.ivTitle)
    TextView tvTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_number, container, false);
        ButterKnife.bind(this, view);
        bindData();
        return view;
    }

    private void bindData() {
        tvTitle.setText(title);
        if (defultNumberData.size() > 0) {
            mAdapter = new SelectContactNumberAdapter(getActivity(), defultNumberData, new SelectNumberCallbackCallback() {
                @Override
                public void onClick(View view, int position) {
                    callback.onClick(view, position);
                    dismiss();
                }

                @Override
                public void onNo() {
                    dismiss();
                }
            });
            recyclerviewNumber.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerviewNumber.setItemAnimator(new DefaultItemAnimator());
            recyclerviewNumber.setNestedScrollingEnabled(false);
            recyclerviewNumber.setAdapter(mAdapter);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static void show(String title, List<String> weekData, FragmentManager fragmentManager, SelectNumberCallbackCallback _callback) {
        SelectContactNumberDailog dialog = new SelectContactNumberDailog();
        dialog.callback = _callback;
        dialog.title = title;
        dialog.defultNumberData = weekData;
        dialog.show(fragmentManager, SelectContactNumberDailog.class.getSimpleName());

    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        callback.onNo();
    }

    @OnClick(R.id.dialog_week_view)
    public void onViewClicked() {
        dismiss();
    }
}