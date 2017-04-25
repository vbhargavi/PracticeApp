package com.example.laxmibhargavivaditala.practiceapp.model;


public class OAuthResponse {
    private String accessToken;

    long expiresIn;

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
