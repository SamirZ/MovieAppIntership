package com.example.zsamir.movieappintership.ViewHolders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zsamir.movieappintership.Common.ActorProfileActivity;
import com.example.zsamir.movieappintership.Modules.Cast;
import com.example.zsamir.movieappintership.Modules.EpisodeCast;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.RealmUtils.RealmUtils;

public class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Cast actor;
    private EpisodeCast cast;
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
        if(cast.getName()!=null)
        mCastName.setText(cast.getName());
        if(cast.getCharacter()!=null)
        mCastRoleName.setText(cast.getCharacter());
        if(actor.getImageUrl()!=null)
        Glide.with(mCastImage.getContext()).load(actor.getImageUrl()).into(mCastImage);
    }

    public void bindCast(EpisodeCast cast) {
        this.cast = cast;

        if(cast.getName()!=null)
            mCastName.setText(cast.getName());
        if(cast.getCharacter()!=null)
            mCastRoleName.setText(cast.getCharacter());
        if(cast.getImageUrl()!=null)
            Glide.with(mCastImage.getContext()).load(cast.getImageUrl()).into(mCastImage);
    }

    @Override
    public void onClick(View view) {
        if(isNetworkAvailable() || RealmUtils.getInstance().readRealmActorDetails(actor.getId())!=null) {
            Intent i = new Intent(view.getContext(), ActorProfileActivity.class);
            if (actor != null)
                i.putExtra("Actor", actor);
            if (cast != null)
                i.putExtra("Actor1", cast);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            view.getContext().startActivity(i);
        }else{
            showNoDataDialog();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.itemView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showNoDataDialog(){
        AlertDialog dialog = new AlertDialog.Builder(itemView.getContext(), R.style.MyDialogTheme)
                .setTitle(itemView.getContext().getString(R.string.connection_warrning))
                .setMessage(itemView.getContext().getString(R.string.connection_required) + "\n" + "\n" + "\n" + "\n" + "\n")
                .setPositiveButton(R.string.connect, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with login
                        itemView.getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // set pref to no
                    }
                })
                .show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorAccent));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorText));

        //Delete user from shared preferences
    }
}
