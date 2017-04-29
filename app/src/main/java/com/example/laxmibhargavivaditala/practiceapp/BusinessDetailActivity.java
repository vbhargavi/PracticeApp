package com.example.laxmibhargavivaditala.practiceapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by laxmibhargavivaditala on 4/27/17.
 */

public class BusinessDetailActivity extends AppCompatActivity{
    public static final String EXTRA_ID = "id";
    private Fragment businessDetailFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent().getExtras();
        setContentView(R.layout.business_detail_activity);
    }

    private void addDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        businessDetailFragment = new BusinessDetailFragment();
        fragmentTransaction.add(R.id.fragment_container, businessDetailFragment);
        fragmentTransaction.commit();


    }
}
