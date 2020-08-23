package com.icommunicate.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icommunicate.R;
import com.icommunicate.adapter.CallHistoryAdapter;
import com.icommunicate.apiCall.ApiConstant;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiCallDetails;
import com.icommunicate.apiCall.requestModels.CallHistory;
import com.icommunicate.apiCall.responseModels.CallRecordResponse;
import com.icommunicate.apiCall.responseModels.LogsItem;
import com.icommunicate.bean.RecentItem;
import com.icommunicate.common.CommonMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bhadresh on 23,August,2020
 */

public class CallDetailsActivity extends AppCompatActivity {

    CallHistoryAdapter mAdapter;
    @BindView(R.id.recyclerview_call_log)
    RecyclerView recyclerviewListenToRecord;
    @BindView(R.id.contactName)
    TextView contactName;
    @BindView(R.id.contactNumber)
    TextView contactNumber;
    @BindView(R.id.action_bar_edit)
    LinearLayout actionBarEdit;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    @BindView(R.id.action_bar_back)
    LinearLayout actionBarBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_detail);
        ButterKnife.bind(this);
        actionBarEdit.setVisibility(View.GONE);
        actionBarTitle.setText("Call Detail");
        getCallDetails();
    }

    RecentItem callItem;

    void getCallDetails() {
        if (getIntent().getSerializableExtra(ApiConstant.CALLLOG) != null) {

            callItem = (RecentItem) getIntent().getSerializableExtra(ApiConstant.CALLLOG);
            Log.d("From", callItem.getFromNumber());
            Log.d("To", callItem.getToNumber());

            if (callItem.getContactName() != null)
                contactName.setText(callItem.getContactName());
            contactNumber.setText(callItem.getDisplayNumber());

            CallHistory history = new CallHistory(callItem.getFromNumber(), callItem.getToNumber());
            ArrayList<RecentItem> recentItemArrayList = new ArrayList<>();
            ApiCallDetails apiCallDetails = new ApiCallDetails(this, new IResult() {
                @Override
                public void notifySuccess(String requestType, Object response) {
                    if (response instanceof CallRecordResponse) {
                        CallRecordResponse callRecordResponse = (CallRecordResponse) response;
                        if (callRecordResponse != null) {
                            if (callRecordResponse.getLogs() != null) {

                                Log.d("Date", callRecordResponse.getLogs().get(0).getStartTime().getDate());

                                List<LogsItem> logsItemList = callRecordResponse.getLogs();
                                if (logsItemList.size() > 0) {
                                    for (int i = 0; i < logsItemList.size(); i++) {
                                        LogsItem logsItem = logsItemList.get(i);
                                        recentItemArrayList.add(new RecentItem(
                                                logsItem.getCallerName(),
                                                CommonMethods.getValue(logsItem.getFromFormatted()),
                                                logsItem.getFrom(), logsItem.getTo(),
                                                logsItem.getDirection().contains("inbound") ? 2 : 1,
                                                CommonMethods.getValue(logsItem.getDuration()),
                                                logsItem.getDateCreated().getDate(), logsItem.getSid()
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
            apiCallDetails.execute(history);


        }
    }

    private void setUpListing(ArrayList<RecentItem> recentItemArrayList) {
        mAdapter = new CallHistoryAdapter(this, recentItemArrayList);
        recyclerviewListenToRecord.setAdapter(mAdapter);
        recyclerviewListenToRecord.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.action_bar_back)
    public void onViewClicked() {
        onBackPressed();
    }
}


