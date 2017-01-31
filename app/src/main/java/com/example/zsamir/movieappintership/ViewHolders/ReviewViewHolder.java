package com.example.zsamir.movieappintership.ViewHolders;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.zsamir.movieappintership.Widgets.ExpandableTextView;
import com.example.zsamir.movieappintership.Modules.MovieReview;
import com.example.zsamir.movieappintership.R;

/**
 * Created by zsami on 19-Jan-17.
 */
public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private MovieReview review;
    private TextView name;
    private ExpandableTextView text;
    private TextView more;
    private boolean clicked = false;
    public ReviewViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.review_name);
        text = (ExpandableTextView) itemView.findViewById(R.id.review_text);
        more = (TextView) itemView.findViewById(R.id.review_more);
    }

    public void bindReview(MovieReview review) {
        this.review = review;

        if(review.getAuthor()!=null)
            name.setText(review.getAuthor());
        if(review.getContent()!=null){
            text.setText(review.getContent());
            if(text.getOriginalTextSize()>200){
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text.trim = !text.trim;
                        text.setText();
                        //text.requestFocusFromTouch();
                        if(!clicked) {
                            more.setText(R.string.hide_text);
                            clicked = true;
                        }else{
                            more.setText(R.string.see_more);
                            clicked = false;
                        }
                    }
                });
            }else{
                more.setEnabled(false);
                more.setTextColor(ContextCompat.getColor(this.itemView.getContext(), R.color.colorAccentPressed));
            }


        }

    }

}
