package com.example.phoenixmusicapp.model;

public class Song {

    private String name;
    private String addedDate;
    private String path;

    public Song(){}

    public Song(String Name,String AddedDate, String Path) {
        this.name = Name;
        this.addedDate = AddedDate;
        this.path = Path;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String AddedDate) {
        this.addedDate = AddedDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String Path) {
        this.path = Path;
    }
}