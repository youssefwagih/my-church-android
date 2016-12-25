package com.example.youssefwagih.mychurchapp;

/**
 * Created by youssef.wagih on 12/21/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

public class CustomFeedsListAdapter extends ArrayAdapter<HashMap<String,String>> {
    Context context;

    public CustomFeedsListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public CustomFeedsListAdapter(Context context, int resource, List<HashMap<String,String>> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return (position % 2) ;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            if (getItemViewType(position) == 1) {
                v = vi.inflate(R.layout.list_item1, null);
/*                // List item connected with service data
                v = vi.inflate(R.layout.list_item, null);
                HashMap<String, String> newsDetails = getItem(position);
                TextView newsTitleTV = (TextView) v.findViewById(R.id.newsTitleTV);
                ImageView newsIV = (ImageView) v.findViewById(R.id.newsImageView);
                TextView numOfViewsTV = (TextView) v.findViewById(R.id.numOfViewsTV);
                TextView likesTV = (TextView) v.findViewById(R.id.likesTV);
                TextView postDateTV = (TextView) v.findViewById(R.id.postDateTV);

                newsTitleTV.setText(newsDetails.get("NewsTitle"));
                numOfViewsTV.setText(newsDetails.get("NumofViews") + "views");
                likesTV.setText("Likes(" + newsDetails.get("Likes") + ")");
                postDateTV.setText(newsDetails.get("PostDate"));*/
            }
            else{
                v = vi.inflate(R.layout.list_item2, null);
            }
        }



        return v;
    }

}