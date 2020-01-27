package com.example.phoenixmusicapp.model;


import java.sql.Date;

public class PlayList {

    private String playListName;
    private int userID;
    private int playListID;
    private String createdDate;
    private String updatedDate;



    public PlayList(String PlayListName, int UserID, String CreatedDate, String UpdatedDate,int PlayListID) {
        this.playListName = PlayListName;
        this.userID = UserID;
        this.playListID = PlayListID;
        this.createdDate = CreatedDate;
        this.updatedDate = UpdatedDate;
    }

    public String getplayListName() {
        return playListName;
    }

    public void setplayListName(String playListName) {
        this.playListName = playListName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int UserID) {
        this.userID = UserID;
    }
    public String getcreatedDate() {
        return createdDate;
    }

    public void setPlayListID(int PlayListID) {
        this.playListID = PlayListID;
    }
    public int getPlayListID() {
        return playListID;
    }
    public String getPlayListIDStr() {
        return String.valueOf(playListID);
    }

    public void setcreatedDate(String CreatedDate) {
        this.createdDate = CreatedDate;
    }
    public String getupdatedDate() {
        return updatedDate;
    }

    public void setupdatedDate(String UpdatedDate) {
        this.updatedDate = UpdatedDate;
    }


}