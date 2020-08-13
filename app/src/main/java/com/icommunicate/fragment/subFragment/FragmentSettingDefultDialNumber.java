package com.icommunicate.fragment.subFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.icommunicate.R;
import com.icommunicate.activity.LoginActivity;
import com.icommunicate.adapter.DefultDialNumberAdapter;
import com.icommunicate.adapterActions.SimpleListener;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiDefultNumber;
import com.icommunicate.apiCall.requestModels.DefultNumberRequest;
import com.icommunicate.apiCall.responseModels.DefaultResponse;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberData;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberResponse;
import com.icommunicate.bean.DefultDialNumberBeans;
import com.icommunicate.common.preferences.PreferenceUtil;
import com.icommunicate.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentSettingDefultDialNumber extends BaseFragment {

    final public static String TAG = FragmentSettingDefultDialNumber.class.getName();
    protected View root;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    @BindView(R.id.recyclerview_defult_dail_number)
    RecyclerView recyclerviewDefultDailNumber;
    DefultDialNumberAdapter adapter;
    @BindView(R.id.ic_action_back)
    AppCompatImageView icActionBack;

    public FragmentSettingDefultDialNumber() {
    }

    public static Fragment newInstance(Context context, String page_name, int count) {
        FragmentSettingDefultDialNumber f = new FragmentSettingDefultDialNumber();
        Bundle bundle = new Bundle();
        bundle.putString("page_name", page_name);
        bundle.putInt("count", count);
        f.setArguments(bundle);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_setting_defult_dial_number, container, false);
        ButterKnife.bind(this, root);
        getBundleArguments();
        setUpView();
        return root;
    }

    private void setUpView() {
        DefultNumberResponse response = new Gson().fromJson(PreferenceUtil.defultDailNumber().get(), DefultNumberResponse.class);
        setUpAdapter(response.getData());
        response.getData().forEach(defultNumberData -> defultNumberData.setSelected(false));
        for (int i = 0; i < response.getData().size(); i++) {
            if (response.getData().get(i).getNumber().equalsIgnoreCase(PreferenceUtil.byDefultDailNumber().get())) {
                response.getData().get(i).setSelected(true);
            }
        }
    }


    private void setUpAdapter(List<DefultNumberData> defultDialNumberBeans) {
        adapter = new DefultDialNumberAdapter(getContext(), defultDialNumberBeans, new SimpleListener() {
            @Override
            public void selected(View view, int position) {

            }
        });
        recyclerviewDefultDailNumber.setAdapter(adapter);
        recyclerviewDefultDailNumber.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
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

    @OnClick(R.id.ic_action_back)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.ic_action_back:
                try {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
