package com.example.zsamir.movieappintership.Common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.zsamir.movieappintership.API.ApiHandler;
import com.example.zsamir.movieappintership.Adapters.SearchResultAdapter;
import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Firebase.FirebaseUtils;
import com.example.zsamir.movieappintership.Modules.Result;
import com.example.zsamir.movieappintership.Modules.SearchResult;
import com.example.zsamir.movieappintership.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    List<Result> resultList = new ArrayList<>();
    SearchResultAdapter mResultAdapter = new SearchResultAdapter(resultList);
    RecyclerView recyclerView;
    String query = "";
    boolean wait = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FirebaseUtils utils = new FirebaseUtils();
        utils.addDummyMoviesToFirebase();

        setTitle("");

        setUpSearchItems();

    }

    private void setUpSearchItems() {

        recyclerView = (RecyclerView) findViewById(R.id.search_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mResultAdapter);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager ) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //if(page+1<=numberOfPages)
                searchForDataLoop(query,page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem item = menu.findItem(R.id.menuSearch);
        final SearchView searchView = (SearchView)item.getActionView();
        searchView.setQueryHint(getString(R.string.search_title));
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                if(!wait)
                if(!query.equalsIgnoreCase(SearchActivity.this.query)){
                    resultList.clear();
                    mResultAdapter.notifyDataSetChanged();
                    SearchActivity.this.query = query;
                    wait = true;
                    searchForData(query,1);
                }
                //
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!wait)
                if(!query.equalsIgnoreCase(newText)){
                    resultList.clear();
                    mResultAdapter.notifyDataSetChanged();
                    query = newText;
                    if(query!=null) {
                        if (!query.equals("")) {
                            wait = true;
                            searchForData(query, 1);
                        }
                    }
                }
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setVisibility(View.GONE);
                onBackPressed();
                return false;
            }
        });

        return true;
    }

    private void searchForData(String query,int page) {

        ApiHandler.getInstance().requestSearch(query, page, new ApiHandler.SearchResultListener() {
            @Override
            public void success(SearchResult response) {
                if(response!=null)
                if(response.getResults()!=null){
                    resultList.addAll(response.getResults());
                    for (int i=0;i<resultList.size();i++){
                        if(resultList.get(i).getMediaType().equalsIgnoreCase("person")){
                            resultList.remove(i);
                        }
                    }
                }
                wait = false;
                mResultAdapter.notifyDataSetChanged();
            }
        });

    }

    private void searchForDataLoop(String query,int page) {

        if(page>1)
        ApiHandler.getInstance().requestSearch(query, page, new ApiHandler.SearchResultListener() {
            @Override
            public void success(SearchResult response) {
                resultList.addAll(response.getResults());
                for (int i=0;i<resultList.size();i++){
                    if(resultList.get(i).getMediaType().equalsIgnoreCase("person")){
                        resultList.remove(i);
                    }
                }
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
