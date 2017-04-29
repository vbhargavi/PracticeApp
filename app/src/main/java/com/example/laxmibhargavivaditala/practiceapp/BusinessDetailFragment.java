package com.example.laxmibhargavivaditala.practiceapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.TextView;

import com.example.laxmibhargavivaditala.practiceapp.model.Business;
import com.example.laxmibhargavivaditala.practiceapp.service.ServiceManager;

import java.io.IOException;

import static com.example.laxmibhargavivaditala.practiceapp.BusinessDetailActivity.EXTRA_ID;

/**
 * Created by laxmibhargavivaditala on 4/27/17.
 */

public class BusinessDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Business> {
    private CollapsingToolbarLayout collapsingToolbar;
    private String businessId;

    public static BusinessDetailFragment newInstance(String id) {
        BusinessDetailFragment fragment = new BusinessDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_detail_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        businessId = getArguments().getString(EXTRA_ID);
        getLoaderManager().initLoader(0, null, this);

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        final ActionBar actionBar = appCompatActivity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

    @Override
    public Loader<Business> onCreateLoader(int id, Bundle args) {
        return new BusinessDetailLoader(getActivity(), "estaurant details");
    }

    @Override
    public void onLoadFinished(Loader<Business> loader, Business data) {
        getLoaderManager().destroyLoader(0);

    }

    @Override
    public void onLoaderReset(Loader<Business> loader) {

    }

    public static class BusinessDetailLoader extends AsyncTaskLoader<Business> {
        String businessId;

        public BusinessDetailLoader(Context context, String businessId) {
            super(context);
            this.businessId = businessId;
        }

        @Override
        public Business loadInBackground() {
            try {
                return ServiceManager.getBusinessData(businessId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

    }
}

