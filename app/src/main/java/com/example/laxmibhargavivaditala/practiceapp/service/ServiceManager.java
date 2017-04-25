package com.example.laxmibhargavivaditala.practiceapp.service;

import android.net.Uri;
import android.util.Log;

import com.example.laxmibhargavivaditala.practiceapp.model.OAuthRequest;
import com.example.laxmibhargavivaditala.practiceapp.model.OAuthResponse;
import com.example.laxmibhargavivaditala.practiceapp.model.SearchResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.laxmibhargavivaditala.practiceapp.BuildConfig.YELP_BASE_URL;
import static com.example.laxmibhargavivaditala.practiceapp.BuildConfig.YELP_CLIENT_ID;
import static com.example.laxmibhargavivaditala.practiceapp.BuildConfig.YELP_CLIENT_SECRET;
import static com.example.laxmibhargavivaditala.practiceapp.BuildConfig.YELP_GRANT_TYPE;

/**
 * Created by laxmibhargavivaditala on 4/23/17.
 */

public class ServiceManager {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();

    private static OAuthResponse oAuthResponse;

    private static String makeCall(String url, RequestBody requestBody, Map<String, String> headers) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        if (requestBody != null) {
            requestBuilder.post(requestBody);
        }

        if (headers != null) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }

        Request request = requestBuilder.build();
        Log.d(ServiceManager.class.getSimpleName(), "Request url " + url);

        Response response = client.newCall(request).execute();
        String json = response.body().string();
        Log.d(ServiceManager.class.getSimpleName(), "Response " + json);
        return json;
    }

    public static String makeCall(String url, Map<String, String> headers) throws IOException {
        return makeCall(url, null, headers);
    }

    //https://api.yelp.com/oauth2/token
    public static OAuthResponse getAuthToken() throws IOException {
        Uri.Builder builder = Uri.parse(YELP_BASE_URL).buildUpon();
        builder.appendPath("oauth2");
        builder.appendPath("token");
        String url = builder.build().toString();

        RequestBody formBody = new FormBody.Builder().add("client_id", YELP_CLIENT_ID)
                .add("client_secret", YELP_CLIENT_SECRET)
                .add("grant_type",YELP_GRANT_TYPE)
                .build();

        String response = makeCall(url, formBody, null);

        oAuthResponse = gson.fromJson(response, OAuthResponse.class);

        return oAuthResponse;
    }

    public static SearchResponse searchBusiness(String query, double lat, double lng) throws IOException {
        Uri.Builder builder = Uri.parse(YELP_BASE_URL).buildUpon();
        builder.appendPath("businesses");
        builder.appendPath("search");
        builder.appendQueryParameter("term", query);
        builder.appendQueryParameter("latitude", String.valueOf(lat));
        builder.appendQueryParameter("longitude", String.valueOf(lng));
        String url = builder.build().toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", oAuthResponse.getRequestHeader());

        String response = makeCall(url, headers);

        return gson.fromJson(response, SearchResponse.class);
    }
}
