package com.example.zsamir.movieappintership.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zsamir.movieappintership.Modules.Result;
import com.example.zsamir.movieappintership.R;
import com.example.zsamir.movieappintership.ViewHolders.ResultViewHolder;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {

    private List<Result> resultsList;

    public SearchResultAdapter(List<Result> resultsList){
        this.resultsList = resultsList;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        return new ResultViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        Result result = resultsList.get(position);
        holder.bindResult(result);
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }
}
