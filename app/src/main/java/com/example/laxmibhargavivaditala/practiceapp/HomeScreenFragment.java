package com.example.laxmibhargavivaditala.practiceapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laxmibhargavivaditala.practiceapp.model.SearchResponse;

/**
 * Created by laxmibhargavivaditala on 4/25/17.
 */

public class HomeScreenFragment extends Fragment implements LoaderManager.LoaderCallbacks<SearchResponse>{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homescreen_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public Loader<SearchResponse> onCreateLoader(int id, Bundle args) {
        return new SearchLoaderTask(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<SearchResponse> loader, SearchResponse data) {

    }

    @Override
    public void onLoaderReset(Loader<SearchResponse> loader) {

    }

    public static class SearchLoaderTask extends AsyncTaskLoader<SearchResponse> {

        public SearchLoaderTask(Context context) {
            super(context);
        }

        @Override
        public SearchResponse loadInBackground() {
            return null;
        }
    }
}
