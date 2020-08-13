package com.icommunicate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;
import com.icommunicate.R;
import com.icommunicate.adapterActions.ContactListner;
import com.icommunicate.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by system-15 on 17/4/20 Timing 15:36.
 */
public class ContactAdepter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderHandler, SectionIndexer, Filterable {
    private static final int VIEW_TYPE_OTHER = 2;
    private static final int VIEW_TYPE_HEADER = 1;

    private Context mContext;
    private List<ContactBean> queriesList;
    private ContactListner mListener;
    private ArrayList<Integer> mSectionPositions;
    private adapterFilter mFilter;

    public ContactAdepter(Context mContext, List<ContactBean> contactBeans, ContactListner mListener) {
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
                View viewChatOther = layoutInflater.inflate(R.layout.row_contact, parent, false);
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
        ContactBean bean = queriesList.get(position);
        if (TextUtils.equals(queriesList.get(position).getId(), "100022")) {
            configureHeader((HeaderViewHolder) holder, position);
        } else {
            configureOther((OtherViewHolder) holder, position);
        }
    }

    private void configureOther(OtherViewHolder holder, int position) {
        holder.txtUserName.setText(queriesList.get(position).getName());
        if (position != queriesList.size() - 1) {
            if (TextUtils.equals(queriesList.get(position + 1).getId(), "100022")) {
                holder.devider.setVisibility(View.GONE);
            } else {
                holder.devider.setVisibility(View.VISIBLE);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.contactViewClick(queriesList.get(position));
            }
        });
    }

    private void configureHeader(HeaderViewHolder holder, int position) {
        holder.headerTag.setText(queriesList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.haderClick(v, position);
            }
        });
    }


    public void add(ContactBean item) {
        queriesList.add(item);
        notifyItemInserted(queriesList.size() - 1);
    }

    public void removeItem(int position) {
        queriesList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(ContactBean item, int position) {
        queriesList.add(position, item);
        notifyItemInserted(position);
    }

    public List<ContactBean> getData() {
        return queriesList;
    }

    public void remove() {
        // Remove last index of recycle view
        queriesList.remove(queriesList.size() - 1);
        notifyDataSetChanged();

    }

    public void replaceItem(int position, ContactBean item) {
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

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(37);
        mSectionPositions = new ArrayList<>(37);
        if (queriesList != null && queriesList.size() > 0) {
            for (int i = 0; i < queriesList.size(); i++) {
                String section = String.valueOf(queriesList.get(i).getName().charAt(0)).toUpperCase();
                if (!sections.contains(section)) {
                    sections.add(section);
                    mSectionPositions.add(i);
                }
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }


    private static class OtherViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imgUserImage;
        AppCompatTextView txtUserName;
        View devider;


        public OtherViewHolder(View itemView) {
            super(itemView);
            imgUserImage = itemView.findViewById(R.id.img_user_image);
            txtUserName = itemView.findViewById(R.id.txt_user_name);
            devider = itemView.findViewById(R.id.devider);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView headerTag;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTag = itemView.findViewById(R.id.header_tag);
        }
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new adapterFilter(queriesList, ContactAdepter.this);
        }
        return mFilter;
    }

    public class adapterFilter extends Filter {

        List<ContactBean> mFilterValues;
        ContactAdepter selectableAdapter;

        public adapterFilter(List<ContactBean> mFilterValues, ContactAdepter selectableAdapter) {
            this.mFilterValues = mFilterValues;
            this.selectableAdapter = selectableAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<ContactBean> tempSelectableItem = new ArrayList<>();
            if (constraint != null && !TextUtils.isEmpty(constraint)) {
                for (ContactBean selectableItem : mFilterValues) {
                    String fltString = constraint.toString().toLowerCase();

                    if (selectableItem.getName() != null && selectableItem.getName().toLowerCase().trim().startsWith(fltString) || selectableItem.getPhone_number() != null && selectableItem.getPhone_number().toLowerCase().trim().startsWith(fltString)) {
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
            selectableAdapter.queriesList = (List<ContactBean>) results.values;
            selectableAdapter.notifyDataSetChanged();
        }
    }

}
