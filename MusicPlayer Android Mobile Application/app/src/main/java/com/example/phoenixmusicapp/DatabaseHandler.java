package com.example.phoenixmusicapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler  extends SQLiteOpenHelper {
    // Database Information
    static final String DB_NAME = "Music_Album";
    public static final String USER_TABLE_NAME = "Users";
    public static final String USER_LOG_TABLE_NAME = "UserLogs";
    public static final String PLAYLIST_TABLE_NAME = "Playlists";
    public static final String SONGS_TABLE_NAME = "Songs";

    // Table columns for users
    public static final String Users_ID = "ID";
    public static final String Users_FName = "FName";
    public static final String Users_LName = "LName";
    public static final String Users_Email = "Email";
    public static final String Users_Password = "Passowrd";
    public static final String Users_Rating = "Rating";
    public static final String Users_Role = "Role";

    public static final String Users_Log_Date = "LogDate";

    //Table columns for playlist
    public static final String Playlist_ID = "PlaylistID";
    public static final String Playlist_Name = "PlaylistName";
    public static final String Created_Date = "CreatedDate";
    public static final String Updated_Date = "UpdatedDate";

    //Table columns for song
    public static final String Song_ID = "SongID";
    public static final String Song_Name = "SongName";
    public static final String Song_Path = "SongPath";
    public static final String Added_Date = "AddedDate";

    // Creating table query
    private static final String CREATE_USERS_TABLE = "create table " + USER_TABLE_NAME + "(" + Users_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Users_FName + " Varchar NOT NULL, "
            + Users_LName + " Varchar,"
            + Users_Email + " Varchar NOT NULL,"
            + Users_Password + " Varchar NOT NULL,"
            + Users_Rating + "  INTEGER, "
        + Users_Role + " Varchar );";
    private static final String CREATE_USER_LOGS_TABLE = "create table " + USER_LOG_TABLE_NAME + "(" + Users_ID
            + " INTEGER , "
            + Users_Log_Date + " DateTime NOT NULL);";
    private static final String CREATE_PLAYLIST_TABLE = "create table " + PLAYLIST_TABLE_NAME + "(" + Playlist_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Playlist_Name + " Varchar NOT NULL, "
            + Users_ID + " INTEGER NOT NULL, "
            + Created_Date + " DateTime NOT NULL, "
            + Updated_Date + " DateTime);";
    private static final String CREATE_SONG_TABLE = "create table " + SONGS_TABLE_NAME + "(" + Song_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Playlist_ID + " INTEGER NOT NULL, "
            + Song_Name + " Varchar NOT NULL, "
            + Song_Path + " Varchar NOT NULL, "
            + Added_Date + " DateTime NOT NULL);";

    //constructor of the class
    public DatabaseHandler(Context context)
    {
        //call the IntentService
        super(context, DB_NAME, null, 1);
    }

    // for implementing SQLiteOpenHelper
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the users table in DB
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_USER_LOGS_TABLE);
        db.execSQL(CREATE_PLAYLIST_TABLE);
        db.execSQL(CREATE_SONG_TABLE);
    }

    // for implementing SQLiteOpenHelper
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //delete the table and create again on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + USER_LOG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PLAYLIST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SONGS_TABLE_NAME);
        onCreate(db);
    }
}