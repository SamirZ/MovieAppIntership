package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Common.SearchActivity;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.Result;
import com.example.zsamir.movieappintership.Modules.TvSeries;
import com.example.zsamir.movieappintership.Movies.MovieDetailsActivity;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.TVSeries.TVSeriesDetailsActivity;

import java.util.Locale;

/**
 * Created by zsami on 26-Jan-17.
 */
public class ResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView name;
    private TextView date;
    private TextView rating;
    private ImageView image;
    private Movie m;
    private TvSeries tv;

    public ResultViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.result_name);
        date = (TextView) itemView.findViewById(R.id.result_date);
        rating = (TextView) itemView.findViewById(R.id.result_rating);
        image = (ImageView) itemView.findViewById(R.id.result_image);

        itemView.setOnClickListener(this);

    }

    public void bindResult(Result result) {
        if(result.title!=null){
            //tv series
            m = result.toMovie();
            name.setText(m.getTitle());
            date.setText("("+m.getReleaseYear()+")");
            rating.setText(String.format(Locale.getDefault(),"%1$.1f",m.getVoteAverage())+" /10");
            Glide.with(image.getContext()).load(m.getPosterUrl()).into(image);
        }

        if(result.mediaType.equals("tv")){
            //movie
            tv = result.toTvSeries();
            name.setText(tv.getName());
            date.setText("("+tv.getReleaseYear()+")");
            rating.setText(String.format(Locale.getDefault(),"%1$.1f",tv.getVoteAverage())+" /10");
            Glide.with(image.getContext()).load(tv.getPosterUrl()).into(image);
        }
    }

    @Override
    public void onClick(View v) {
        if(m!=null) {
            Intent i = new Intent(v.getContext(), MovieDetailsActivity.class);
            i.putExtra("Movie", m);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            v.getContext().startActivity(i);
        }
        if(tv!=null){
            Intent i = new Intent(v.getContext(), TVSeriesDetailsActivity.class);
            i.putExtra("TVSeries", tv);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            v.getContext().startActivity(i);
        }
    }
}
