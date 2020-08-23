package com.icommunicate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.icommunicate.R;
import com.icommunicate.bean.RecentItem;
import com.icommunicate.common.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CallHistoryAdapter extends RecyclerView.Adapter<CallHistoryAdapter.MyViewHolder> {


    private Context mContext;
    private List<RecentItem> queriesList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_recent_call_type)
        AppCompatImageView rowRecentCallType;
        @BindView(R.id.row_recent_call_type_txt)
        AppCompatTextView callType;
        @BindView(R.id.row_recent_call_date)
        AppCompatTextView callDate;
        @BindView(R.id.row_recent_call_duration)
        AppCompatTextView rowRecentCallDuration;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public CallHistoryAdapter(Context mContext, List<RecentItem> albumList) {
        this.mContext = mContext;
        this.queriesList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_call_history_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        RecentItem question = queriesList.get(position);
        switch (question.getCallStatus()) {
            case 1:
                holder.rowRecentCallType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_outgoing_call));
                holder.callType.setText("Outgoing Call");
                break;
            case 2:
                holder.rowRecentCallType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_incoming_call));
                holder.callType.setText("Incoming Call");
                break;
            case 3:
                holder.rowRecentCallType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_call_missed_outgoing));
                holder.callType.setText("Missed Call");
                break;
        }


        holder.callDate.setText(DateUtil.setUpDate(question.getCallCreated()) + " : " + DateUtil.setUpTime(question.getCallCreated()));
        if (question.getCallDuration() != null)
            holder.rowRecentCallDuration.setText(getDurationString(Integer.parseInt(question.getCallDuration())));


    }


    @Override
    public int getItemCount() {
        return queriesList.size();
    }


    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }
}

