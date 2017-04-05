package com.example.zsamir.movieappintership.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Cinema.ReservationActivity;
import com.example.zsamir.movieappintership.Firebase.CinemaSeat;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.SeatsViewHolder;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatsViewHolder> {

    private List<CinemaSeat> seats;
    private Context context;
    private TextView total;

    public SeatAdapter(List<CinemaSeat> seats, Context context, TextView total) {
        this.seats = seats;
        this.context = context;
        this.total = total;
    }

    @Override
    public SeatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_item, parent, false);
        return new SeatsViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(final SeatsViewHolder holder, int position) {
        CinemaSeat seat = seats.get(position);
        holder.bindSeat(seat);
        if(seat.isFree())
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.selected){
                    if(ReservationActivity.getSelected()<ReservationActivity.getMaxSelected()) {
                        ReservationActivity.setSelected(ReservationActivity.getSelected() + 1);
                        holder.selected = true;
                        ReservationActivity.getSeatIDs().add(holder.seat.getId());
                        Glide.with(holder.itemView.getContext()).load(R.drawable.yellow_circle).into(holder.seatImage);
                        String s = context.getString(R.string.total) + " $"+String.valueOf(ReservationActivity.getSelected()*20);
                        total.setText(s);
                    }
                }else{
                    ReservationActivity.setSelected(ReservationActivity.getSelected()-1);
                    if(ReservationActivity.getSeatIDs().contains(holder.seat.getId()))
                        ReservationActivity.getSeatIDs().remove(ReservationActivity.getSeatIDs().indexOf(holder.seat.getId()));
                    holder.selected = false;
                    Glide.with(holder.itemView.getContext()).load(R.drawable.white_circle).into(holder.seatImage);
                    String s = context.getString(R.string.total) + " $"+String.valueOf(ReservationActivity.getSelected()*20);
                    total.setText(s);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return seats.size();
    }
}
