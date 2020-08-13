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
import com.icommunicate.adapterActions.RecentAdapterListener;
import com.icommunicate.bean.RecentItem;
import com.icommunicate.common.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.MyViewHolder> {


    private Context mContext;
    private List<RecentItem> queriesList;
    private RecentAdapterListener mListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_recent_call_type)
        AppCompatImageView rowRecentCallType;
        @BindView(R.id.row_recent_contact_name)
        AppCompatTextView rowRecentContactName;
        @BindView(R.id.row_recent_contact_number)
        AppCompatTextView rowRecentContactNumber;
        @BindView(R.id.row_recent_call_duration)
        AppCompatTextView rowRecentCallDuration;
        @BindView(R.id.row_recent_call_info)
        AppCompatImageView rowRecentCallInfo;
        @BindView(R.id.row_recent_call_date)
        AppCompatTextView rowRecentCallDate;
        @BindView(R.id.row_recent_call_time)
        AppCompatTextView rowRecentCallTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public RecentAdapter(Context mContext, List<RecentItem> albumList, RecentAdapterListener mListener) {
        this.mContext = mContext;
        this.queriesList = albumList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recent_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        RecentItem question = queriesList.get(position);
        switch (question.getCallStatus()) {
            case 1:
                holder.rowRecentCallType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_outgoing_call));
                break;
            case 2:
                holder.rowRecentCallType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_incoming_call));
                break;
            case 3:
                holder.rowRecentCallType.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_call_missed_outgoing));
                break;
        }
        holder.rowRecentContactName.setText(question.getContactName());
        holder.rowRecentContactNumber.setText(question.getContactNumber());

        holder.rowRecentCallDate.setText(DateUtil.setUpDate(question.getCallCreated()));
        holder.rowRecentCallTime.setText(DateUtil.setUpTime(question.getCallCreated()));
        holder.rowRecentCallDuration.setText(DateUtil.setUpDate(question.getCallCreated()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selected(v, position);
            }
        });

     /*   long minilisecond = Long.parseLong(question.getCallDuration()) *1000;
        String duration = DurationFormatUtils.formatDuration(minilisecond,"HH:mm:ss");
        holder.rowRecentCallDuration.setText(duration);*/

    }

    public void add(RecentItem item) {
        queriesList.add(item);
        notifyItemInserted(queriesList.size() - 1);
    }

    public void remove(int position) {
        queriesList.remove(position);
        notifyDataSetChanged();

    }

    public void replaceItem(int position, RecentItem item) {
        queriesList.set(position, item);
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        return queriesList.size();
    }


}

