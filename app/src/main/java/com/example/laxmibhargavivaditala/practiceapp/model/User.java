package com.example.laxmibhargavivaditala.practiceapp.model;

/**
 * Created by laxmibhargavivaditala on 4/23/17.
 */

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String city;
    private String state;

    private static User sUser;

    private User(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static User createUser(String id, String firstName, String lastName) {
        if (sUser == null) {
            sUser = new User(id, firstName, lastName);
        }
        return sUser;
    }

    public static synchronized User getUser() {
        return sUser;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
