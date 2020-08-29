package com.icommunicate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.icommunicate.R;
import com.icommunicate.adapterActions.SelectNumberCallbackCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SelectContactNumberAdapter extends RecyclerView.Adapter<SelectContactNumberAdapter.MyViewHolder> {


    private Context mContext;
    private List<String> queriesList;
    private SelectNumberCallbackCallback mListener;
    private int mCheckedPostion = -1;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dial_number)
        AppCompatTextView dialNumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public SelectContactNumberAdapter(Context mContext, List<String> albumList, SelectNumberCallbackCallback mListener) {
        this.mContext = mContext;
        this.queriesList = albumList;
        this.mListener = mListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_select_number_dialog, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String value = queriesList.get(position);
        holder.dialNumber.setText(value);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, position);
            }
        });
    }

    public void add(String item) {
        queriesList.add(item);
        notifyItemInserted(queriesList.size() - 1);
    }




    @Override
    public int getItemCount() {
        return queriesList.size();
    }


}

