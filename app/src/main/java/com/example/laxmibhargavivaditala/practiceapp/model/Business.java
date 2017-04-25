package com.example.laxmibhargavivaditala.practiceapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by laxmibhargavivaditala on 4/25/17.
 */

public class Business {
    private String name;
    private int rating;
    @SerializedName("review_count")
    private int reviewCount;
    private String price;
    private String phone;
    @SerializedName("display_phone")
    private String displayPhone;
    @SerializedName("image_url")
    private String imageUrl;
}
