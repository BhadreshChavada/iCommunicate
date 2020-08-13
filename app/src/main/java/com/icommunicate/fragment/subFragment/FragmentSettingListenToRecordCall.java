package com.icommunicate.fragment.subFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icommunicate.R;
import com.icommunicate.adapter.RecentAdapter;
import com.icommunicate.adapterActions.RecentAdapterListener;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiGetCallRecords;
import com.icommunicate.apiCall.responseModels.CallRecordResponse;
import com.icommunicate.apiCall.responseModels.LogsItem;
import com.icommunicate.bean.RecentItem;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentSettingListenToRecordCall extends BaseFragment {

    final public static String TAG = FragmentSettingListenToRecordCall.class.getName();
    protected View root;
    RecentAdapter mAdapter;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    @BindView(R.id.recyclerview_listen_to_record)
    RecyclerView recyclerviewListenToRecord;
    @BindView(R.id.ic_action_back)
    AppCompatImageView icActionBack;

    public FragmentSettingListenToRecordCall() {
    }

    public static Fragment newInstance(Context context, String page_name, int count) {
        FragmentSettingListenToRecordCall f = new FragmentSettingListenToRecordCall();
        Bundle bundle = new Bundle();
        bundle.putString("page_name", page_name);
        bundle.putInt("count", count);
        f.setArguments(bundle);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_setting_listen_to_record, container, false);
        ButterKnife.bind(this, root);
        getBundleArguments();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        callCallRecordApi();
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

    private void setUpListing(ArrayList<RecentItem> recentItemArrayList) {
        mAdapter = new RecentAdapter(getContext(), recentItemArrayList, new RecentAdapterListener() {
            @Override
            public void selected(View v, int position) {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.parse("/*https://demo.twilio.com/welcome/voice/"*/), "audio/*");
                startActivity(intent);
//                navigation.showNewCounterFragment(FragmentPlayAudio.newInstance(getActivity(), recentItemArrayList.get(position).getContactNumber(), counter + 1));
            }
        });
        recyclerviewListenToRecord.setAdapter(mAdapter);
        recyclerviewListenToRecord.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.notifyDataSetChanged();
    }

    private void callCallRecordApi() {
        ArrayList<RecentItem> recentItemArrayList = new ArrayList<>();
        ApiGetCallRecords apiGetCallRecords = new ApiGetCallRecords(getContext(), new IResult() {
            @Override
            public void notifySuccess(String requestType, Object response) {
                if (response instanceof CallRecordResponse) {
                    CallRecordResponse callRecordResponse = (CallRecordResponse) response;
                    if (callRecordResponse != null) {
                        if (callRecordResponse.getLogs() != null) {
                            List<LogsItem> logsItemList = callRecordResponse.getLogs();
                            if (logsItemList.size() > 0) {
                                for (int i = 0; i < logsItemList.size(); i++) {
                                    LogsItem logsItem = logsItemList.get(i);
                                    recentItemArrayList.add(new RecentItem(
                                            "Twillio-Number",
                                            CommonMethods.getValue(logsItem.getFromFormatted()),
                                            logsItem.getDirection().contains("inbound") ? 2 : 1,
                                            CommonMethods.getValue(logsItem.getDuration()),
                                            logsItem.getDateCreated().getDate()
                                    ));
                                }
                            }
                        }
                    }
                    setUpListing(recentItemArrayList);
                }

            }


            @Override
            public void notifyNetworkSuccess(String requestType) {

            }
        });
        apiGetCallRecords.execute();
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
