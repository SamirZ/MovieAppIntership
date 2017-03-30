package com.example.zsamir.movieappintership.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Firebase.CinemaSeat;
import com.example.zsamir.movieappintership.R;

public class SeatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private CinemaSeat seat;
    private ImageView seatImage;
    private boolean selected = false;

    public SeatsViewHolder(View inflatedView) {
        super(inflatedView);
        seatImage = (ImageView) inflatedView.findViewById(R.id.seat_image);
        itemView.setOnClickListener(this);
    }

    public void bindSeat(CinemaSeat seat) {
        this.seat = seat;

        for (int i = 2; i < 110; i+=11) {
            if(seat.getId()==i){
                itemView.setVisibility(View.GONE);
            }
        }
        for (int i = 8; i < 110; i+=11) {
            if(seat.getId()==i){
                itemView.setVisibility(View.GONE);
            }
        }

        if(seat.getId()==0 || seat.getId()==10){
            itemView.setVisibility(View.GONE);
        }else{
            if(seat.isFree()){
                Glide.with(itemView.getContext()).load(R.drawable.white_circle).into(seatImage);
            }else{
                Glide.with(itemView.getContext()).load(R.drawable.taken_circle).into(seatImage);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(seat.isFree() && !selected){
            Glide.with(itemView.getContext()).load(R.drawable.yellow_circle).into(seatImage);
            selected = true;
        }else{
            Glide.with(itemView.getContext()).load(R.drawable.white_circle).into(seatImage);
            selected = false;
        }
    }
}
