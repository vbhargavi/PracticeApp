package com.example.laxmibhargavivaditala.practiceapp.serviceManager;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by laxmibhargavivaditala on 4/23/17.
 */

public class Service {
    private static final String BASE_URL = "https://api.yelp.com/";

    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();

    public static String makeCall(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.d(Service.class.getSimpleName(), "Request url " + url);

        Response response = client.newCall(request).execute();
        String json = response.body().string();
        Log.d(Service.class.getSimpleName(), "Response " + json);
        return json;
    }

    public static String makePostCall(String url, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Log.d(Service.class.getSimpleName(), "Request url " + url);
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    //https://api.yelp.com/oauth2/token
    public static String getAuthToken() throws IOException {
        Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
        builder.appendPath("oauth2");
        builder.appendPath("token");
        String url = builder.build().toString();
        String response = makeCall(url);

        return gson.fromJson(response);
    }
}


}
