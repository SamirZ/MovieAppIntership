package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Common.ActorProfileActivity;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.R;

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
        if(cast.name!=null)
        mCastName.setText(cast.name);
        if(cast.character!=null)
        mCastRoleName.setText(cast.character);
        if(actor.getImageUrl()!=null)
        Glide.with(mCastImage.getContext()).load(actor.getImageUrl()).into(mCastImage);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(view.getContext(), ActorProfileActivity.class);
        i.putExtra("Actor", actor);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        view.getContext().startActivity(i);
    }
}
