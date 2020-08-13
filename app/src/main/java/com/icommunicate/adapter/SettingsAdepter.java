package com.icommunicate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;
import com.icommunicate.R;
import com.icommunicate.adapterActions.SettingListner;
import com.icommunicate.bean.SettingBean;

import java.util.List;

/**
 * Created by system-15 on 17/4/20 Timing 15:36.
 */
public class SettingsAdepter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderHandler {
    private static final int VIEW_TYPE_OTHER = 2;
    private static final int VIEW_TYPE_HEADER = 1;

    private Context mContext;
    private List<SettingBean> queriesList;
    private SettingListner mListener;

    public SettingsAdepter(Context mContext, List<SettingBean> contactBeans, SettingListner mListener) {
        this.mContext = mContext;
        this.queriesList = contactBeans;
        this.mListener = mListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.row_settings, parent, false);
                viewHolder = new OtherViewHolder(viewChatOther);
                break;
            case VIEW_TYPE_HEADER:
                View viewChatHeader = layoutInflater.inflate(R.layout.row_header, parent, false);
                viewHolder = new HeaderViewHolder(viewChatHeader);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SettingBean bean = queriesList.get(position);
        if (TextUtils.equals(queriesList.get(position).getId(), "100022")) {
            configureHeader((HeaderViewHolder) holder, position);
        } else {
            configureOther((OtherViewHolder) holder, position);
        }
    }

    private void configureOther(OtherViewHolder holder, int position) {
        SettingBean settingBean = queriesList.get(position);
        holder.txtName.setText(settingBean.getName());
        holder.imgImage.setImageResource(settingBean.getImageResource());
        if (position != queriesList.size() - 1) {
            if (TextUtils.equals(queriesList.get(position + 1).getId(), "100022")) {
                holder.devider.setVisibility(View.GONE);
            } else {
                holder.devider.setVisibility(View.VISIBLE);
            }
        }
        //visible invisible spinner
        if (settingBean.getName() != null && settingBean.getName().equalsIgnoreCase("Allow Notifications")) {
            holder.btnSwitch.setVisibility(View.VISIBLE);
        } else {
            holder.btnSwitch.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.settingViewClick(queriesList.get(position));
            }
        });
    }

    private void configureHeader(HeaderViewHolder holder, int position) {
        holder.headerTag.setText(queriesList.get(position).getHeaderTitle());
    }


    public void add(SettingBean item) {
        queriesList.add(item);
        notifyItemInserted(queriesList.size() - 1);
    }

    public void remove() {
        // Remove last index of recycle view
        queriesList.remove(queriesList.size() - 1);
        notifyDataSetChanged();

    }

    public void replaceItem(int position, SettingBean item) {
        // Replace index of recycle view
        queriesList.set(position, item);
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        if (queriesList != null) {
            return queriesList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(queriesList.get(position).getId(), "100022")) {
            return VIEW_TYPE_HEADER;

        } else {
            return VIEW_TYPE_OTHER;
        }

    }

    @Override
    public List<?> getAdapterData() {
        return queriesList;
    }


    private static class OtherViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgImage;
        AppCompatTextView txtName;
        SwitchCompat btnSwitch;
        View devider;


        public OtherViewHolder(View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.img_image);
            txtName = itemView.findViewById(R.id.txt_name);
            devider = itemView.findViewById(R.id.devider);
            btnSwitch = itemView.findViewById(R.id.btn_switch);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView headerTag;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTag = itemView.findViewById(R.id.header_tag);
        }
    }
}
