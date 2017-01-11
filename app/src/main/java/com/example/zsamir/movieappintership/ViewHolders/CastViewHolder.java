package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.ActorProfileActivity;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.R;

import java.util.List;

public class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Cast actor;
    private ImageView mCastImage;
    private TextView mCastName;
    private TextView mCastRoleName;

    public CastViewHolder(View itemView) {
        super(itemView);

        mCastImage = (ImageView) itemView.findViewById(R.id.cast_image);
        mCastName = (TextView) itemView.findViewById(R.id.cast_name);
        mCastRoleName = (TextView) itemView.findViewById(R.id.cast_role_name);

        itemView.setOnClickListener(this);
    }
    public void bindCast(Cast cast) {
        actor = cast;
        mCastName.setText(cast.name);
        mCastRoleName.setText(cast.character);
        Glide.with(mCastImage.getContext()).load(actor.getImageUrl()).into(mCastImage);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(view.getContext(), ActorProfileActivity.class);
        i.putExtra("Actor", actor);
        view.getContext().startActivity(i);
    }
}
