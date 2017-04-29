package com.example.laxmibhargavivaditala.practiceapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laxmibhargavivaditala.practiceapp.adapter.SearchAdapter;
import com.example.laxmibhargavivaditala.practiceapp.model.Business;
import com.example.laxmibhargavivaditala.practiceapp.model.MyLocation;
import com.example.laxmibhargavivaditala.practiceapp.model.SearchResponse;
import com.example.laxmibhargavivaditala.practiceapp.service.ServiceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by laxmibhargavivaditala on 4/25/17.
 */

public class HomeScreenFragment extends Fragment implements LoaderManager.LoaderCallbacks<SearchResponse> {
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<Business> businesses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homescreen_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<SearchResponse> onCreateLoader(int id, Bundle args) {
        return new SearchLoaderTask(getActivity(), "restaurants");
    }

    @Override
    public void onLoadFinished(Loader<SearchResponse> loader, SearchResponse data) {
        ArrayList<Business> results = null;
        if (data != null && data.getBusinesses() != null) {
            results = new ArrayList<>(Arrays.asList(data.getBusinesses()));
            if (businesses == null) {
                businesses = results;
            } else {
                businesses.addAll(results);
            }
            populateView();

        }
    }

    private void populateView() {
        recyclerView.setVisibility(View.VISIBLE);
        if (searchAdapter == null) {
            searchAdapter = new SearchAdapter(getActivity(), businesses);
            recyclerView.setAdapter(searchAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<SearchResponse> loader) {


    }

    public static class SearchLoaderTask extends AsyncTaskLoader<SearchResponse> {
        String query;

        public SearchLoaderTask(Context context, String query) {
            super(context);
            this.query = query;
        }

        @Override
        public SearchResponse loadInBackground() {
            try {
                MyLocation myLocation = MyLocation.getMyLocation();
                if (myLocation.getLat() == 0 && myLocation.getLng() == 0) {
                    return ServiceManager.searchBusiness(query, myLocation.getCity(), myLocation.getState());
                } else {
                    return ServiceManager.searchBusiness(query, myLocation.getLat(), myLocation.getLng());
                }
            } catch (IOException exception) {

            }
            return null;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

    }

}
