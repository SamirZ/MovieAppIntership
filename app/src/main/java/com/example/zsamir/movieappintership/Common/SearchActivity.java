package com.example.zsamir.movieappintership.Common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.SearchResultAdapter;
import com.example.zsamir.movieappintership.Modules.Result;
import com.example.zsamir.movieappintership.Modules.SearchResult;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    List<Result> resultList = new ArrayList<>();
    SearchResultAdapter mResultAdapter = new SearchResultAdapter(resultList);
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.search_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mResultAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                searchForData(query,1);
                //
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                resultList.clear();
                recyclerView.removeAllViews();
                mResultAdapter.notifyDataSetChanged();
                return false;
            }
        });

        return true;
    }

    private void searchForData(String query,int page) {

        ApiHandler.getInstance().requestSearch(query, page, new ApiHandler.SearchResultListener() {
            @Override
            public void success(SearchResult response) {
                resultList.clear();
                recyclerView.removeAllViews();
                resultList.addAll(response.results);
                mResultAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
