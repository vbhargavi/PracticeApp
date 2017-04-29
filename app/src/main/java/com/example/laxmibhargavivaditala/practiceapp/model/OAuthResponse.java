package com.example.laxmibhargavivaditala.practiceapp.model;


import com.google.gson.annotations.SerializedName;

public class OAuthResponse {
    @SerializedName("access_token")
    private String accessToken;

    long expiresIn;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRequestHeader(){
        return tokenType + " " + accessToken;
    }
}
