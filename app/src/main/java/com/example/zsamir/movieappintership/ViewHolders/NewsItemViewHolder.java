package com.example.zsamir.movieappintership.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.zsamir.movieappintership.NewsFeed.FeedItem;
import com.example.zsamir.movieappintership.R;

public class NewsItemViewHolder extends RecyclerView.ViewHolder {

    private FeedItem feedItem;
    private TextView title;
    private TextView description;
    private TextView link;

    public NewsItemViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.news_feed_title);
        description = (TextView) itemView.findViewById(R.id.news_feed_description);
        link = (TextView) itemView.findViewById(R.id.news_feed_link);
    }

    public void bindFeedItem(FeedItem feedItem) {
        this.feedItem = feedItem;
        if(feedItem.getTitle()!=null)
            title.setText(feedItem.getTitle());
        else
            title.setVisibility(View.GONE);
        if(feedItem.getDescription()!=null && feedItem.getDescription().length()>10) {
            description.setText(feedItem.getDescription());
        }else
            description.setVisibility(View.GONE);
        if(feedItem.getLink()!=null)
            link.setText(feedItem.getLink());
        else
            link.setVisibility(View.GONE);

    }
}
