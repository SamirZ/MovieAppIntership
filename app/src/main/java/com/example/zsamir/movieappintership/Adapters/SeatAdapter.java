package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Firebase.CinemaSeat;
import com.example.zsamir.movieappintership.Modules.Backdrop;
import com.example.zsamir.movieappintership.Modules.Movie;
import com.example.zsamir.movieappintership.Modules.TVShow;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.DetailsImagesViewHolder;
import com.example.zsamir.movieappintership.ViewHolders.SeatsViewHolder;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatsViewHolder> {

    private List<CinemaSeat> seats;

    public SeatAdapter(List<CinemaSeat> seats) {
        this.seats = seats;
    }

    @Override
    public SeatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_item, parent, false);
        return new SeatsViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(SeatsViewHolder holder, int position) {
        CinemaSeat seat = seats.get(position);
        holder.bindSeat(seat);
    }


    @Override
    public int getItemCount() {
        return seats.size();
    }
}
