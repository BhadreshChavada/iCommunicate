package com.icommunicate.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;
import com.icommunicate.R;
import com.icommunicate.bean.ContactBean;
import com.icommunicate.common.preferences.PreferenceUtil;
import com.icommunicate.twillio.Constants;

import java.util.ArrayList;
import java.util.List;


public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderHandler, Filterable {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;
    private static final int VIEW_TYPE_HEADER = 3;
    private List<FcmChat> mFcmChats;
    private Context cntx;
    String senderUidValue = PreferenceUtil.byDefultDailNumber().get();
    private adapterFilter mFilter;


    public ChatRecyclerAdapter(Context cntx, List<FcmChat> fcmChats) {
        mFcmChats = fcmChats;
        this.cntx = cntx;
    }

    public void add(FcmChat fcmChat) {
        mFcmChats.add(fcmChat);
        notifyItemInserted(mFcmChats.size() - 1);
    }

    public void remove() {
        mFcmChats.remove(mFcmChats.size() - 1);
        notifyDataSetChanged();

    }

    public void removePosition(int position) {
        mFcmChats.remove(position);
        notifyDataSetChanged();

    }

    public void replaceItem(int position, FcmChat newChat) {
        mFcmChats.set(position, newChat);
        notifyItemChanged(position);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.row_chat_sender_item, parent, false);
                viewHolder = new MyChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.row_chat_receiver_item, parent, false);
                viewHolder = new OtherChatViewHolder(viewChatOther);
                break;
            case VIEW_TYPE_HEADER:
                View viewChatHeader = layoutInflater.inflate(R.layout.row_chat_header, parent, false);
                viewHolder = new HeaderChatViewHolder(viewChatHeader);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FcmChat item = mFcmChats.get(position);
        if (mFcmChats.get(position).getType() == Constants.MESSAGE_TYPE_HEADER) {
            configureHeaderChatViewHolder((HeaderChatViewHolder) holder, position);

        } else {
            if (TextUtils.equals(mFcmChats.get(position).getFrom(), senderUidValue)) {
                configureOtherChatViewHolder((OtherChatViewHolder) holder, position);
            } else {
                configureMyChatViewHolder((MyChatViewHolder) holder, position);

            }

        }

    }

    private void configureMyChatViewHolder(MyChatViewHolder myChatViewHolder, int position) {
        FcmChat fcmChat = mFcmChats.get(position);
        String time = DateUtils.formatDateTime(cntx, fcmChat.getTimestamp() * 1000, DateUtils.FORMAT_SHOW_TIME);
        String date = DateUtils.formatDateTime(cntx, fcmChat.getTimestamp() * 1000, DateUtils.FORMAT_SHOW_DATE);
        myChatViewHolder.textview_message.setText(fcmChat.getMessage().replace("Sent from your Twilio trial account - ", ""));
        myChatViewHolder.textview_time.setText(time);
        myChatViewHolder.main_chat_recycleview.setBackgroundColor(Color.TRANSPARENT);

    }

    private void configureOtherChatViewHolder(OtherChatViewHolder otherChatViewHolder, int position) {
        FcmChat fcmChat = mFcmChats.get(position);
        String time = DateUtils.formatDateTime(cntx, fcmChat.getTimestamp(), DateUtils.FORMAT_SHOW_TIME);
        otherChatViewHolder.textview_message.setText(fcmChat.getMessage().replace("Sent from your Twilio trial account - ", ""));
        otherChatViewHolder.textview_time.setText(time);
        otherChatViewHolder.main_chat_recycleview.setBackgroundColor(Color.TRANSPARENT);

    }

    private void configureHeaderChatViewHolder(HeaderChatViewHolder otherChatViewHolder, int position) {
        FcmChat fcmChat = mFcmChats.get(position);
        otherChatViewHolder.headerTitle.setText(fcmChat.getMessage());
    }

    @Override
    public int getItemCount() {
        if (mFcmChats != null) {
            return mFcmChats.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mFcmChats.get(position).getType() == Constants.MESSAGE_TYPE_HEADER) {
            return VIEW_TYPE_HEADER;

        } else {
            if (TextUtils.equals(mFcmChats.get(position).getFrom(), senderUidValue)) {
                return VIEW_TYPE_OTHER;
            } else {
                return VIEW_TYPE_ME;
            }

        }

    }

    @Override
    public List<?> getAdapterData() {
        return mFcmChats;
    }

    public FcmChat getItem(int position) {
        return mFcmChats.get(position);
    }

    private static class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_message, textview_time;
        private RelativeLayout main_chat_recycleview;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            textview_message = (TextView) itemView.findViewById(R.id.textview_message);
            textview_time = (TextView) itemView.findViewById(R.id.textview_time);
            main_chat_recycleview = (RelativeLayout) itemView.findViewById(R.id.main_chat_recycleview);
        }
    }

    private static class OtherChatViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_message, textview_time;
        private RelativeLayout main_chat_recycleview;

        public OtherChatViewHolder(View itemView) {
            super(itemView);
            textview_message = (TextView) itemView.findViewById(R.id.textview_message);
            textview_time = (TextView) itemView.findViewById(R.id.textview_time);
            main_chat_recycleview = (RelativeLayout) itemView.findViewById(R.id.main_chat_recycleview);
        }
    }

    private static class HeaderChatViewHolder extends RecyclerView.ViewHolder {
        private TextView headerTitle;

        public HeaderChatViewHolder(View itemView) {
            super(itemView);
            headerTitle = (TextView) itemView.findViewById(R.id.headerTitle);
        }
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new adapterFilter(mFcmChats, ChatRecyclerAdapter.this);
        }
        return mFilter;
    }

    public class adapterFilter extends Filter {

        List<FcmChat> mFilterValues;
        ChatRecyclerAdapter selectableAdapter;

        public adapterFilter(List<FcmChat> mFilterValues, ChatRecyclerAdapter selectableAdapter) {
            this.mFilterValues = mFilterValues;
            this.selectableAdapter = selectableAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<FcmChat> tempSelectableItem = new ArrayList<>();
            if (constraint != null && !TextUtils.isEmpty(constraint)) {
                for (FcmChat selectableItem : mFilterValues) {
                    String fltString = constraint.toString().toLowerCase();

                    if (selectableItem.getMessage() != null && selectableItem.getMessage().toLowerCase().trim().contains(fltString)) {
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
            selectableAdapter.mFcmChats = (List<FcmChat>) results.values;
            selectableAdapter.notifyDataSetChanged();
        }
    }
}
