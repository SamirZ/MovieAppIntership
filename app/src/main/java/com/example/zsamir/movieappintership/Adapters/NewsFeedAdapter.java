package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.NewsFeed.FeedItem;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.NewsItemViewHolder;

import java.util.ArrayList;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsItemViewHolder> {

    private ArrayList<FeedItem> feedItems;

    public NewsFeedAdapter(ArrayList<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_item, parent, false);
        return new NewsItemViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        FeedItem feedItem = feedItems.get(position);
        holder.bindFeedItem(feedItem);
    }

    @Override
    public int getItemCount() {
        if(feedItems!=null)
            return feedItems.size();
        else
            return 0;
    }
}
