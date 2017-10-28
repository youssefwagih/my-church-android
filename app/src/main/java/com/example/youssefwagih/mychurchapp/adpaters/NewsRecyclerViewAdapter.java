package com.example.youssefwagih.mychurchapp.adpaters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.youssefwagih.mychurchapp.R;
import com.example.youssefwagih.mychurchapp.models.NewsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by y.wagih on 10/22/2017.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {
    List<NewsModel> mDataset;
    View.OnClickListener mOnClickListener;

    public NewsRecyclerViewAdapter(List<NewsModel> items, View.OnClickListener onClickListener) {
        this.mDataset = items;
        this.mOnClickListener = onClickListener;
    }

    @Override
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new NewsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.ViewHolder holder, int position) {
        NewsModel item = mDataset.get(position);
        holder.newsTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }

    public void clearDataset() {
        if (mDataset != null) {
            mDataset.clear();
        }
        notifyDataSetChanged();
    }

    public void setDataset(List<NewsModel> dataset) {
        this.mDataset = dataset;
    }

    public void addAll(List<NewsModel> items) {
        if (mDataset == null) {
            mDataset = items;

        } else {
            mDataset.addAll(items);
        }
        notifyItemRangeInserted(mDataset.size() - items.size(), items.size());
    }

    public NewsModel getItemByPosition(int position) {
        return mDataset.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_title_textview)
        TextView newsTextView;
        @BindView(R.id.news_description_textview)
        TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}