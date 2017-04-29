package com.example.laxmibhargavivaditala.practiceapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laxmibhargavivaditala on 4/25/17.
 */

public class Business {
    private String name;
    private String id;

    public String getId() {
        return id;
    }

    private double rating;
    @SerializedName("review_count")
    private int reviewCount;
    private String price;
    private String phone;
    @SerializedName("display_phone")
    private String displayPhone;

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    @SerializedName("image_url")
    private String imageUrl;
}
