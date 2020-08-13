package com.icommunicate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.icommunicate.R;
import com.icommunicate.adapterActions.MessageAdapterListener;
import com.icommunicate.adapterActions.SimpleListener;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberData;
import com.icommunicate.bean.DefultDialNumberBeans;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.preferences.PreferenceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DefultDialNumberAdapter extends RecyclerView.Adapter<DefultDialNumberAdapter.MyViewHolder> {


    private Context mContext;
    private List<DefultNumberData> queriesList;
    private SimpleListener mListener;
    private int mCheckedPostion = -1;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dial_id)
        AppCompatTextView dialId;
        @BindView(R.id.dial_name)
        AppCompatTextView dialName;
        @BindView(R.id.dial_number)
        AppCompatTextView dialNumber;
        @BindView(R.id.dial_isSelected)
        AppCompatCheckBox dialIsSelected;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public DefultDialNumberAdapter(Context mContext, List<DefultNumberData> albumList, SimpleListener mListener) {
        this.mContext = mContext;
        this.queriesList = albumList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_defualt_dial_number_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DefultNumberData question = queriesList.get(position);
        holder.dialId.setText(CommonMethods.getValue(String.valueOf(position + 1)));
        holder.dialName.setText(CommonMethods.getValue(question.getCity()) + ", " + CommonMethods.getValue(question.getCountry()));
        holder.dialNumber.setText(CommonMethods.getValue(question.getNumber()));
        if (question.isSelected()) {
            holder.dialIsSelected.setChecked(true);
            mCheckedPostion = position;
            PreferenceUtil.byDefultDailNumber().set(CommonMethods.getValue(question.getNumber()));
        } else {
            holder.dialIsSelected.setChecked(false);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.selected(v, position);
            }
        });
        holder.dialIsSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    question.setSelected(true);
                    if (mCheckedPostion >= 0) {
                        queriesList.get(mCheckedPostion).setSelected(false);
                        notifyItemChanged(mCheckedPostion);
                    }
                    mCheckedPostion = position;
                } else {
                    question.setSelected(false);
                }
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

