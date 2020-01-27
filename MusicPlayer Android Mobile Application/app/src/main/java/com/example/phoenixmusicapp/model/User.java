package com.example.phoenixmusicapp.model;

public class User {

    private int userID;
    private String firstName;
    private String lastName;
    private String appLastUsed;
    private String rating;
    private String password;

    public User(){}

    public User(int userID, String firstName, String lastName, String appLastUsed, String rating) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.appLastUsed = appLastUsed;
        this.rating = rating;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAppLastUsed() {
        return appLastUsed;
    }

    public void setAppLastUsed(String appLastUsed) {
        this.appLastUsed = appLastUsed;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }
}
