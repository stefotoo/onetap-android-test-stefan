package com.receiptbank.android.testapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.receiptbank.android.testapp.R;
import com.receiptbank.android.testapp.models.CustomItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    // variables
    private Context mContext;
    private List<CustomItem> mData;

    // constructor
    public CustomAdapter(Context context) {
        this.mContext = context;

        mData = new ArrayList<>();
    }

    // methods
    public void addData(List<CustomItem> data, boolean notify) {
        int startCount = mData.size();

        mData.addAll(data);

        if (notify) {
            try {
                notifyItemRangeInserted(startCount, data.size());
            } catch (IndexOutOfBoundsException e) {
                notifyDataSetChanged();
                e.printStackTrace();
            }
        }
    }

    public void clearData(boolean notify) {
        mData.clear();

        if (notify) {
            notifyDataSetChanged();
        }
    }

    public List<CustomItem> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public CustomItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomHolder viewHolder = (CustomHolder) holder;
        CustomItem item = mData.get(position);

        viewHolder.iv.setImageResource(item.getImageResId());
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvContent.setText(item.getTitle());
    }

    // inner classes
    public class CustomHolder extends RecyclerView.ViewHolder {

        // UI variables
        @Bind(R.id.iv_item_recyclerview)
        ImageView iv;
        @Bind(R.id.tv_item_recyclerview_title)
        TextView tvTitle;
        @Bind(R.id.tv_item_recyclerview_content)
        TextView tvContent;

        // constructor
        public CustomHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

