package com.example.laxmibhargavivaditala.practiceapp.model;


import com.google.gson.annotations.SerializedName;

public class OAuthRequest {
    @SerializedName("client_id")
    private String clientId;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("grant_type")
    private String grantType;

    public OAuthRequest(String clientId, String clientSecret, String grantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
    }
}
