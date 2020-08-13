package com.icommunicate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.icommunicate.R;
import com.icommunicate.adapterActions.MessageAdapterListener;
import com.icommunicate.bean.MessageItem;
import com.icommunicate.common.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<MessageItem> queriesList;
    private MessageAdapterListener mListener;
    private adapterFilter mFilter;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_recent_contact_name)
        AppCompatTextView rowRecentContactName;
        @BindView(R.id.row_recent_last_msg)
        AppCompatTextView rowRecentLastMsg;
        @BindView(R.id.row_recent_msg_date)
        AppCompatTextView rowRecentMsgDate;
        @BindView(R.id.row_recent_msg_time)
        AppCompatTextView rowRecentMsgTime;
        @BindView(R.id.row_msg_lnr)
        LinearLayout rowMsgLnr;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public MessagesAdapter(Context mContext, List<MessageItem> albumList, MessageAdapterListener mListener) {
        this.mContext = mContext;
        this.queriesList = albumList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_messages_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        MessageItem question = queriesList.get(position);

        holder.rowRecentContactName.setText(question.getContactName());
        holder.rowRecentLastMsg.setText(question.getLastMsg().replace("Sent from your Twilio trial account - ", ""));
        holder.rowRecentMsgDate.setText(DateUtil.setUpDate(question.getCallCreated()));
        holder.rowRecentMsgTime.setText(DateUtil.setUpTime(question.getCallCreated()));

        holder.rowMsgLnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selected(question, position);
            }
        });

    }

    public void add(MessageItem item) {
        queriesList.add(item);
        notifyItemInserted(queriesList.size() - 1);
    }

    public void remove() {
        queriesList.remove(queriesList.size() - 1);
        notifyDataSetChanged();

    }

    public void replaceItem(int position, MessageItem item) {
        queriesList.set(position, item);
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        return queriesList.size();
    }


    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new adapterFilter(queriesList, MessagesAdapter.this);
        }
        return mFilter;
    }

    public class adapterFilter extends Filter {

        List<MessageItem> mFilterValues;
        MessagesAdapter selectableAdapter;

        public adapterFilter(List<MessageItem> mFilterValues, MessagesAdapter selectableAdapter) {
            this.mFilterValues = mFilterValues;
            this.selectableAdapter = selectableAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<MessageItem> tempSelectableItem = new ArrayList<>();
            if (constraint != null && !TextUtils.isEmpty(constraint)) {
                for (MessageItem selectableItem : mFilterValues) {
                    String fltString = constraint.toString().toLowerCase();

                    if (selectableItem.getContactName() != null && selectableItem.getContactName().toLowerCase().trim().contains(fltString)) {
                        tempSelectableItem.add(selectableItem);
                    }
                }
                filterResults.values = tempSelectableItem;
                filterResults.count = tempSelectableItem.size();
            } else {
                filterResults.values = mFilterValues;
                filterResults.count = mFilterValues.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            selectableAdapter.queriesList = (List<MessageItem>) results.values;
            selectableAdapter.notifyDataSetChanged();
        }
    }

}

