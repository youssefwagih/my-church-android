package com.example.youssefwagih.mychurchapp.news.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.youssefwagih.mychurchapp.R;
import com.example.youssefwagih.mychurchapp.news.models.NewsModel;

import java.util.List;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item1, parent, false);
        view.setOnClickListener(mOnClickListener);
        return new NewsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.ViewHolder holder, int position) {
/*        NewsDetailsModel item = mDataset.get(position);
        ImageLoader.loadImage(item.getPhoto(), R.drawable.image_list_placeholder, holder.newsImageView);
        holder.newsTitleTextView.setText(item.getTitle());
        holder.createdAtTextView.setText(item.getDisplayCreatedAtDateTime());
        // holder.updatedAtTextView.setText("12 Feb 2018, 12:00 pm");
        holder.descriptionTextView.setText(item.getDescription());*/
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
/*        @BindView(R.id.img_news)
        ImageView newsImageView;
        @BindView(R.id.txt_news_title)
        TextView newsTitleTextView;
        @BindView(R.id.txt_news_description)
        TextView descriptionTextView;
        @BindView(R.id.txt_time_stamp)
        TextView createdAtTextView;*/

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}