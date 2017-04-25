package com.example.laxmibhargavivaditala.practiceapp.model;

/**
 * Created by laxmibhargavivaditala on 4/25/17.
 */

public class MyLocation {
    private double lat;
    private double lng;
    private String city;
    private String state;

    private static MyLocation sMyLocation;

    private MyLocation(String city, String state, double lat, double lng) {
        this.lng = lng;
        this.lat = lat;
        this.city = city;
        this.state = state;
    }

    public static MyLocation createLocation(String city, String state) {
        if (sMyLocation == null) {
            sMyLocation = new MyLocation(city, state, 0, 0);
        }
        return sMyLocation;
    }

    public static MyLocation createLocation(double lat, double lng) {
        if (sMyLocation == null) {
            sMyLocation = new MyLocation(null, null, lat, lng);
        }
        return sMyLocation;
    }

    public static synchronized MyLocation getMyLocation() {
        return sMyLocation;
    }

}
