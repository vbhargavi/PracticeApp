package com.example.laxmibhargavivaditala.practiceapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laxmibhargavivaditala on 4/24/17.
 */

public class SearchResponse {
    public Business[] getBusinesses() {
        return businesses;
    }

    private Business[] businesses;
}
