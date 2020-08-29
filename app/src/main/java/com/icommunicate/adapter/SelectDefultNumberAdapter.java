package com.icommunicate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.icommunicate.R;
import com.icommunicate.adapterActions.SelectNumberCallbackCallback;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberData;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.preferences.PreferenceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SelectDefultNumberAdapter extends RecyclerView.Adapter<SelectDefultNumberAdapter.MyViewHolder> {


    private Context mContext;
    private List<DefultNumberData> queriesList;
    private SelectNumberCallbackCallback mListener;
    private int mCheckedPostion = -1;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.dial_name)
        AppCompatTextView dialName;
        @BindView(R.id.dial_number)
        AppCompatTextView dialNumber;
        @BindView(R.id.dial_isSelected)
        CheckBox isSelected;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public SelectDefultNumberAdapter(Context mContext, List<DefultNumberData> albumList, SelectNumberCallbackCallback mListener) {
        this.mContext = mContext;
        this.queriesList = albumList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_defualt_dial_number_dialog, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DefultNumberData question = queriesList.get(position);
        holder.dialName.setText(CommonMethods.getValue(question.getCity()) + ", " + CommonMethods.getValue(question.getCountry()));
        holder.dialNumber.setText(CommonMethods.getValue(question.getNumber()));
        holder.isSelected.setVisibility(View.VISIBLE);


        if (CommonMethods.getValue(question.getNumber()).equals(PreferenceUtil.byDefultDailNumber().get())) {
            holder.isSelected.setChecked(true);
        } else {
            holder.isSelected.setChecked(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, position);
            }
        });
    }

    public void add(DefultNumberData item) {
        queriesList.add(item);
        notifyItemInserted(queriesList.size() - 1);
    }

    public void remove() {
        queriesList.remove(queriesList.size() - 1);
        notifyDataSetChanged();

    }

    public void replaceItem(int position, DefultNumberData item) {
        queriesList.set(position, item);
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        return queriesList.size();
    }


}

