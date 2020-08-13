package com.icommunicate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.icommunicate.R;
import com.icommunicate.adapterActions.MoreOptionListener;
import com.icommunicate.bean.MoreOptionModal;
import com.icommunicate.common.CommonMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoreOptionAdapter extends RecyclerView.Adapter<MoreOptionAdapter.MyViewHolder> {


    private Context mContext;
    private List<MoreOptionModal> queriesList;
    private MoreOptionListener mListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_more_icon)
        ImageView rowMoreIcon;
        @BindView(R.id.row_more_title)
        AppCompatTextView rowMoreTitle;
        @BindView(R.id.row_more_option_lnr)
        LinearLayout rowMoreOptionLnr;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public MoreOptionAdapter(Context mContext, List<MoreOptionModal> albumList, MoreOptionListener mListener) {
        this.mContext = mContext;
        this.queriesList = albumList;
        this.mListener = mListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_more_option_cell, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MoreOptionModal moreOptionModal = queriesList.get(position);
        holder.rowMoreTitle.setText(CommonMethods.getValue(moreOptionModal.getModule_name()));
        int id = R.drawable.ic_icon_logout;
        switch (moreOptionModal.getModule_id()) {
            case 1:
                id = R.drawable.ic_icon_logout;
                break;
        }
        holder.rowMoreIcon.setImageDrawable(mContext.getResources().getDrawable(id));

        holder.rowMoreOptionLnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selected(moreOptionModal);
            }
        });

    }


    public void setData(List<MoreOptionModal> array) {
        this.queriesList = array;
    }


    @Override
    public int getItemCount() {
        return queriesList.size();
    }

}

