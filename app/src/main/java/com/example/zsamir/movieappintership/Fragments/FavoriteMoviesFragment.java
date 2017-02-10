package com.example.zsamir.movieappintership.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zsamir.movieappintership.R;

/**
 * Created by zsami on 10-Feb-17.
 */

public class FavoriteMoviesFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public FavoriteMoviesFragment() {
    }

    public static FavoriteMoviesFragment newInstance(int sectionNumber) {
        FavoriteMoviesFragment fragment = new FavoriteMoviesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}