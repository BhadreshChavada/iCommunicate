package com.icommunicate.fragment.subFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icommunicate.R;
import com.icommunicate.activity.MessageActivity;
import com.icommunicate.adapter.RecentAdapter;
import com.icommunicate.adapterActions.RecentAdapterListener;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiCallLogs;
import com.icommunicate.apiCall.responseModels.CallRecordResponse;
import com.icommunicate.apiCall.responseModels.LogsItem;
import com.icommunicate.bean.RecentItem;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.swipeRecyclerview.SwipeHelper;
import com.icommunicate.fragment.BaseFragment;
import com.icommunicate.twillio.VoiceActivityDuplicate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;
import segmented_control.widget.custom.android.com.segmentedcontrol.item_row_column.SegmentViewHolder;
import segmented_control.widget.custom.android.com.segmentedcontrol.listeners.OnSegmentClickListener;

public class FragmentRecent extends BaseFragment {

    final public static String TAG = FragmentRecent.class.getName();
    protected View root;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    @BindView(R.id.recent_recycle)
    RecyclerView recentRecycle;
    @BindView(R.id.segmented_control)
    SegmentedControl segmentedControl;
    private RecentAdapter mAdapter;

    public FragmentRecent() {
    }

    public static Fragment newInstance(Context context, String page_name, int count) {
        FragmentRecent f = new FragmentRecent();
        Bundle bundle = new Bundle();
        bundle.putString("page_name", page_name);
        bundle.putInt("count", count);
        f.setArguments(bundle);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_recent, container, false);
        ButterKnife.bind(this, root);
        getBundleArguments();
        segmentedControl.addOnSegmentClickListener(new OnSegmentClickListener() {
            @Override
            public void onSegmentClick(SegmentViewHolder segmentViewHolder) {
                if (segmentViewHolder.getColumn() == 0) {
                    callCallRecordApi();
                } else if (segmentViewHolder.getColumn() == 1) {
                    getMiscallList();
                }
            }
        });
        segmentedControl.setSelectedSegment(0);
        return root;
    }

    private void getMiscallList() {

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
                    actionBarTitle.setText(bundle.getString("page_name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setUpListing(ArrayList<RecentItem> recentItemArrayList) {
        recentRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecentAdapter(getContext(), recentItemArrayList, new RecentAdapterListener() {
            @Override
            public void selected(View v, int position) {

                // TODO: 13/8/20 Redirect to the Call history screen
            }
        });
        recentRecycle.setAdapter(mAdapter);
        setUpSwipeFunctionality(recentItemArrayList);

    }

    private void setUpSwipeFunctionality(ArrayList<RecentItem> recentItemArrayList) {
        SwipeHelper swipeHelper = new SwipeHelper(getActivity()) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF9502"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                mAdapter.remove(pos);
                                Toast.makeText(getActivity(), "Remove Successfully", Toast.LENGTH_LONG).show();
                            }
                        }
                ));
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Message",
                        0,
                        Color.parseColor("#C7C7CB"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent intent = new Intent(getActivity(), MessageActivity.class);
                                intent.putExtra("phoneNumber", recentItemArrayList.get(pos).getContactNumber());

                                startActivity(intent);
                            }
                        }
                ));
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Call",
                        0,
                        Color.parseColor("#FF9502"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent calling = new Intent(getContext(), VoiceActivityDuplicate.class);
                                calling.putExtra("Name", recentItemArrayList.get(pos).getContactName());
                                calling.putExtra("phoneNumber", recentItemArrayList.get(pos).getContactNumber());
                                startActivity(calling);
                            }
                        }
                ));
            }
        };
        swipeHelper.attachToRecyclerView(recentRecycle);
    }


    private void callCallRecordApi() {
        ArrayList<RecentItem> recentItemArrayList = new ArrayList<>();
        ApiCallLogs apiGetCallRecords = new ApiCallLogs(getContext(), new IResult() {
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
}