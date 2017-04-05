package com.example.zsamir.movieappintership.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Firebase.CinemaSeat;
import com.example.zsamir.movieappintership.R;

public class SeatsViewHolder extends RecyclerView.ViewHolder{

    public CinemaSeat seat;
    public ImageView seatImage;
    public boolean selected = false;

    public SeatsViewHolder(View inflatedView) {
        super(inflatedView);
        seatImage = (ImageView) inflatedView.findViewById(R.id.seat_image);
    }

    public void bindSeat(CinemaSeat seat) {
        this.seat = seat;

        if(seat.getId().equalsIgnoreCase("")){
            itemView.setVisibility(View.GONE);
        }else{
            if(seat.isFree()){
                Glide.with(itemView.getContext()).load(R.drawable.white_circle).into(seatImage);
            }else{
                Glide.with(itemView.getContext()).load(R.drawable.taken_circle).into(seatImage);
            }
        }
    }

}
