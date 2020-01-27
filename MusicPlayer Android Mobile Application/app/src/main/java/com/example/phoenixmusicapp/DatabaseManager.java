
//Developed by: Pamy Ann Patrick

package com.example.phoenixmusicapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.phoenixmusicapp.model.Song;
import com.example.phoenixmusicapp.model.User;
import com.example.phoenixmusicapp.model.PlayList;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DatabaseManager {

    private DatabaseHandler dbHandler;
    private Context context;
    StringBuffer sb;
    private SQLiteDatabase database;

    //constructor of the class with dependency injection
    public DatabaseManager(Context c) {
        context = c;

    }

    //To open DB connection
    public DatabaseManager open() throws SQLException {
        //creating instance of db handler
        dbHandler = new DatabaseHandler(context);
        //open and/or create the DB that will be used for reading and writing.
        database = dbHandler.getWritableDatabase();
        return this;
    }
    //To close DB connection
    public void close() {
        dbHandler.close();
    }
    // to insert data to DB
    public boolean insertUser(String fName,String lName, String password,String email,String role) {
        try {
            //assigning to content value for adding to db. It let assigning in key-value pair in an object
            ContentValues contentValue = new ContentValues();
            contentValue.put(DatabaseHandler.Users_FName, fName);
            contentValue.put(DatabaseHandler.Users_LName, lName);
            contentValue.put(DatabaseHandler.Users_Email, email);
            contentValue.put(DatabaseHandler.Users_Password, password);
            contentValue.put(DatabaseHandler.Users_Rating, 0);
            contentValue.put(DatabaseHandler.Users_Role, role);
            long rowInserted=database.insert(DatabaseHandler.USER_TABLE_NAME, null, contentValue);
            if(rowInserted != -1)
                return true;
            else return false;
        }
        catch(Exception e){
            return false;
        }
    }
    public boolean insertUserLog(Integer UserID,String LogDate) {
        try {
            //assigning to content value for adding to db. It let assigning in key-value pair in an object
            ContentValues contentValue = new ContentValues();
            contentValue.put(DatabaseHandler.Users_ID, UserID);
            contentValue.put(DatabaseHandler.Users_Log_Date, LogDate);
            long rowInserted=database.insert(DatabaseHandler.USER_LOG_TABLE_NAME, null, contentValue);
            if(rowInserted != -1)
                return true;
            else return false;
        }
        catch(Exception e){
            return false;
        }
    }
    // for fetching data from DB and return as string
    public String getAllUsers() {
        String[] columns = new String[] { DatabaseHandler.Users_ID, DatabaseHandler.Users_FName, DatabaseHandler.Users_LName };
        Cursor cursor = database.query(DatabaseHandler.USER_TABLE_NAME, columns, null, null, null, null, null);
        sb=new StringBuffer();
        if (cursor != null) {
            //cursor move to the first result
            cursor.moveToFirst();
            getData(cursor);
            while(cursor.moveToNext()) {
                getData(cursor);
            }
        }
        cursor.close();
        return sb.toString();
    }
    // getting data from db to populate it in controls in UI. Return as cursor
    public Cursor Edit(int id) {
        // search query
        String query="SELECT * FROM "+DatabaseHandler.USER_TABLE_NAME +" where "+DatabaseHandler.Users_ID+"="+ id;
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }
    public  void getData(Cursor cursor)
    {
        // get the data from cursor to show it in the screen
        sb.append("\n\n ID :"+cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_ID)));
        sb.append("\n First Name :"+cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_FName)));
        sb.append("\n Last Name :"+cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_LName)));
    }
    //for searching by email
    public String SearchByEmail(String email) {
        // search query
        String query="SELECT * FROM "+DatabaseHandler.USER_TABLE_NAME +" where "+DatabaseHandler.Users_Email+" = '"+ email.trim()+"'";
        // get the cursor with result
        Cursor cursor = database.rawQuery(query, null);

        String data="No record found";
        sb=new StringBuffer();
        //assign the result to a string builder and then converting it as string
        if (cursor != null) {
            if(cursor.getCount()>0) {
                //cursor move to the first result
                cursor.moveToFirst();
                getData(cursor);
                while(cursor.moveToNext()) {
                    getData(cursor);
                }
                data=sb.toString();
            }
        }
        cursor.close();
        return data;
    }



    // to update data in DB
    public int updateRating(int id,String rate) {
        //assigning to content value to update DB. It let assigning in key-value pair in an object
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.Users_Rating, rate);
        //passing the content value to update in DB
        int i = database.update(DatabaseHandler.USER_TABLE_NAME, contentValue, DatabaseHandler.Users_ID + " = " + id, null);
        return i;
    }

    // to delete row from DB
    public boolean delete(int id,String table ) {
        //delete entry from DB based on id
        database.delete(table, DatabaseHandler.Users_ID + "=" + id, null);
        return true;
    }

    //for searching Password using email id
    public User SearchPassword(String email) {
        User u=new User();
        // search query
        String query="SELECT * FROM "+DatabaseHandler.USER_TABLE_NAME +" where "+DatabaseHandler.Users_Email+" = '"+ email.trim()+"'";
        // get the cursor with result
        Cursor cursor = database.rawQuery(query, null);

        String password="none";

                //cursor move to the first result
                if(cursor.moveToFirst())
                {
                 password = cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_Password));
                 int userID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_ID)));
                   String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_FName));
                   String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_LName));
                  // String appLastUsed = cursor.getString(cursor.getColumnIndex(DatabaseHandler.Updated_Date));
                   String rating = cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_Rating));

                   u=new User(userID,firstName,lastName,null,rating);
                   u.setpassword(password);
                }
        cursor.close();
        return u;
    }

    //for searching USERID using email id
    public int getUserID(String email) {

        // search query
        String query="SELECT "+DatabaseHandler.Users_Password+" FROM "+DatabaseHandler.USER_TABLE_NAME +" where "+DatabaseHandler.Users_Email+" = '"+ email.trim()+"'";
        // get the cursor with result
        Cursor cursor = database.rawQuery(query, null);

        int userID=0;

        //cursor move to the first result
        if(cursor.moveToFirst())
        {
            userID = cursor.getInt(cursor.getColumnIndex(DatabaseHandler.Users_ID));
        }

        cursor.close();
        return userID;
    }

    //Get data from database functionality
    public ArrayList<User> getAllAudience()
    {
        ArrayList<User> entries = new ArrayList<>();
        int userID;
        String firstName,  lastName,appLastUsed,  rating;
        User u;
        String query=
        String.format("SELECT * FROM %s join %s on %s.%s = %s.%s", DatabaseHandler.USER_LOG_TABLE_NAME, DatabaseHandler.USER_TABLE_NAME,
                DatabaseHandler.USER_TABLE_NAME, DatabaseHandler.Users_ID, DatabaseHandler.USER_LOG_TABLE_NAME, DatabaseHandler.Users_ID);
        // get the cursor with result
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            if(cursor.getCount()>0) {
                //cursor move to the first result
                cursor.moveToFirst();
                while(cursor.moveToNext()) {
                    String PlayListName,  CreatedDate,  UpdatedDate;
                    firstName=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_FName));
                    lastName=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_LName));
                    userID=Integer.parseInt((cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_ID))));
                    appLastUsed=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_Log_Date));
                    rating=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_Rating));
                    // UpdatedDate=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Updated_Date));
                    u=new User(userID, firstName, lastName, appLastUsed,rating);
                    entries.add(u);
                }
            }
        }
        cursor.close();
        return entries;
    }

    //Getting data logic
    public User createEntryFromCursor(Cursor c, Cursor d) {
        if (c == null)
            return null;

        User item = new User();

        item.setUserID(c.getInt(c.getColumnIndex("ID")));
        item.setFirstName(c.getString(c.getColumnIndex("FName")));
        item.setLastName(c.getString(c.getColumnIndex("LName")));
        item.setAppLastUsed(c.getString(c.getColumnIndex("LogDate")));
        item.setRating(c.getString(c.getColumnIndex("Rating")));

        return item;
    }
    public boolean addPlaylist(String playlist,int userid) {
        try {
            //assigning to content value for adding to db. It let assigning in key-value pair in an object
            ContentValues contentValue = new ContentValues();
            contentValue.put(DatabaseHandler.Playlist_Name, playlist);
            contentValue.put(DatabaseHandler.Users_ID, userid);
            contentValue.put(DatabaseHandler.Created_Date, DateFormat.getDateTimeInstance().format(new Date()));
            contentValue.put(DatabaseHandler.Updated_Date, DateFormat.getDateTimeInstance().format(new Date()));
            long rowInserted=database.insert(DatabaseHandler.PLAYLIST_TABLE_NAME, null, contentValue);
            if(rowInserted != -1)
                return true;
            else return false;
        }
        catch(Exception e){
            return false;
        }
    }
    public boolean addSong(String Name,String songpath,int userid,int playlistid) {
        try {
            //assigning to content value for adding to db. It let assigning in key-value pair in an object
            ContentValues contentValue = new ContentValues();
            contentValue.put(DatabaseHandler.Song_Name, Name);
            contentValue.put(DatabaseHandler.Playlist_ID, playlistid);
                 contentValue.put(DatabaseHandler.Song_Path, songpath);
            contentValue.put(DatabaseHandler.Added_Date, DateFormat.getDateTimeInstance().format(new Date()));
            long rowInserted=database.insert(DatabaseHandler.SONGS_TABLE_NAME, null, contentValue);
            if(rowInserted != -1)
                return true;
            else return false;
        }
        catch(Exception e){
            return false;
        }
    }
    public ArrayList<PlayList> getPlaylistByUser(int userid) {
        ArrayList<PlayList> p=new ArrayList<PlayList>();
        PlayList playlist;
        String query="SELECT * FROM "+DatabaseHandler.PLAYLIST_TABLE_NAME +" where "+DatabaseHandler.Users_ID+" = "+userid;
        // get the cursor with result
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            if(cursor.getCount()>0) {
                //cursor move to the first result
                cursor.moveToFirst();
                while(cursor.moveToNext()) {
                    String PlayListName,  CreatedDate,  UpdatedDate;
                    int UserID, PlayListID;
                    PlayListName=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Playlist_Name));
                    UserID=Integer.parseInt((cursor.getString(cursor.getColumnIndex(DatabaseHandler.Users_ID))));
                    PlayListID=Integer.parseInt((cursor.getString(cursor.getColumnIndex(DatabaseHandler.Playlist_ID))));
                    CreatedDate=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Created_Date));
                   // UpdatedDate=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Updated_Date));
                    playlist=new PlayList(PlayListName,UserID,CreatedDate,null,PlayListID);
                    p.add(playlist);
                }
            }
        }
        cursor.close();
        return p;
    }


    public ArrayList<Song> getSongsByPlaylist(int playid) {
        ArrayList<Song> p=new ArrayList<Song>();
        Song s;
        String query="SELECT * FROM "+DatabaseHandler.SONGS_TABLE_NAME +" where "+DatabaseHandler.Playlist_ID+" = "+playid;
        // get the cursor with result
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            if(cursor.getCount()>0) {
                //cursor move to the first result
                cursor.moveToFirst();
                while(cursor.moveToNext()) {
                    String Name,  CreatedDate,  path;
                    int UserID, PlayListID;
                    Name=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Song_Name));
                    path=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Song_Path));
                   CreatedDate=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Added_Date));
                    s=new Song(Name,CreatedDate,path);
                    p.add(s);
                }
            }
        }
        cursor.close();
        return p;
    }

    public ArrayList<Song> getSongsByUserSort(int Userid) {
        ArrayList<Song> p=new ArrayList<Song>();
        Song s;
        String query="SELECT * FROM "+DatabaseHandler.SONGS_TABLE_NAME +" join "+DatabaseHandler.PLAYLIST_TABLE_NAME +" on "
                +DatabaseHandler.SONGS_TABLE_NAME+"."+DatabaseHandler.Playlist_ID +" = "
                +DatabaseHandler.PLAYLIST_TABLE_NAME+" . "+DatabaseHandler.Playlist_ID
                +" where "+DatabaseHandler.PLAYLIST_TABLE_NAME+"."+DatabaseHandler.Playlist_ID+" = "+Userid;
        // get the cursor with result
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            if(cursor.getCount()>0) {
                //cursor move to the first result
                cursor.moveToFirst();
                while(cursor.moveToNext()) {
                    String Name,  CreatedDate,  path;
                    int UserID, PlayListID;
                    Name=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Song_Name));
                    path=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Song_Path));
                    CreatedDate=cursor.getString(cursor.getColumnIndex(DatabaseHandler.Added_Date));
                    s=new Song(Name,CreatedDate,path);
                    p.add(s);
                }
            }
        }
        cursor.close();
        return p;
    }
}

