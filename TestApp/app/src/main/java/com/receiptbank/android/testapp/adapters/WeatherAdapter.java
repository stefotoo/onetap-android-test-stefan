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
import com.receiptbank.android.testapp.rest.model.Forecast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    // variables
    private Context mContext;
    private List<Forecast> mData;
    private ForecastClickListener mCallback;

    // constructor
    public WeatherAdapter(Context context, ForecastClickListener callback) {
        this.mContext = context;
        this.mCallback = callback;

        mData = new ArrayList<>();
    }

    // methods
    public void addData(List<Forecast> data, boolean notify) {
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

    public List<Forecast> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Forecast getItem(int position) {
        return mData.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WeatherHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WeatherHolder viewHolder = (WeatherHolder) holder;
        Forecast item = mData.get(position);

        viewHolder.tvTitle.setText(item.getWeatherList().get(0).getTitle());
        viewHolder.tvContent.setText(item.getWeatherList().get(0).getDescription());
    }

    // inner classes
    public class WeatherHolder
            extends
            RecyclerView.ViewHolder
            implements
            View.OnClickListener {

        // UI variables
        @Bind(R.id.iv_item_forecast)
        ImageView iv;
        @Bind(R.id.tv_item_forecast_title)
        TextView tvTitle;
        @Bind(R.id.tv_item_forecast_content)
        TextView tvContent;

        // constructor
        public WeatherHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mCallback != null) {
                mCallback.onForecastClick(mData.get(getAdapterPosition()));
            }
        }
    }

    // interfaces
    public interface ForecastClickListener {
        void onForecastClick(Forecast forecast);
    }
}

