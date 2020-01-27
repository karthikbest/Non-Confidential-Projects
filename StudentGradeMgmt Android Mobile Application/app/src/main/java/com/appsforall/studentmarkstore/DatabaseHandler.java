package com.appsforall.studentmarkstore;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// I have created this database handler class to support the application with necessary  database interactions like connections, query etc
public class DatabaseHandler extends SQLiteOpenHelper {


//Following function is used to Change Records of the database. When ever the user clicks on 'Update button', this function will be used by MainActivity

    public int  changeMarks(String firstName, String lastName, String marks, String course, String credits, int id) {
        //Creating instance of the database class
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//Following are the codes which map the table column name and the data typed by the user
        //The data typed by user is received through function arguements
        cv.put("firstName", firstName);
        cv.put("lastName", lastName);
        cv.put("marks", marks);
        cv.put("course", course);
        cv.put("credits", credits);
        cv.put("id", id);
        //update function takes 3 parameters - table name, where clause & where arguments
        int numbRows =db.update("marks", cv, "id=" + id, null);
        return numbRows;
    }






    //CONSTRUCTOR. This method is invoked automatically when instance of this class is created
    public DatabaseHandler(@Nullable Context context) {
        super(context, "marks.db", null, 1); //Database will be created when ever this constructor is called

    }
//Following method is used to execute the SQL query to create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table marks ( id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT, lastName TEXT,  marks INTEGER, course TEXT, credits INTEGER )");
    }

    //Following method is used to execute the SQL query to drop already existing table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists marks"); //DROPPING ALREADY EXISTING TABLE
        onCreate(db);

    }
    //Following method is used to execute the SQL query to add the data
    //When ever user fills in data and clicks on 'Add' button following method is called by mainactivity
    public boolean addMarks(String firstName, String lastName, String marks, String course, String credits)
    {
        //instance of SQL Lite Database
        SQLiteDatabase db = this.getWritableDatabase();
        //Getting the instance of content values
        ContentValues contentValues = new ContentValues();
        //Taking Content value instance and putting data into the columns
        //Content value is gonna take 2 arguements. 1) Column name 2) Function variable
        contentValues.put("firstName",firstName);
        contentValues.put("lastName",lastName);
        contentValues.put("marks",marks);
        contentValues.put("course",course);
        contentValues.put("credits",credits);
        //If data is not inserted following method is going to return '-1' to us.
        // If inserted sucessfully, inserted row will be returned by following method
        long result = db.insert("marks", null, contentValues);

        if(result == -1) //When the result is -1 , Addition didnot occcur to the database.
        {

            return  false;//Addtion of record failed
        }
        else { return true; }

    }
//Cursor class provides random read - write access to the result
    public Cursor viewData() {
        SQLiteDatabase db = this.getWritableDatabase();
        //We are querying the database and storing the result in cursor instance
        Cursor cursor;
        cursor = db.rawQuery("select * from marks",null);
        //Returning the cursor instance
        return cursor;


    }


    //Following method is used to execute the SQL query to delete the record
    //Whenever, the user clicks on delete record. Mainactivity calls this method. It takes id as parameter
    public Integer deleteStudentRecord (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("marks", "id="+id,null );

    }

    //Following method is used to execute the SQL query to search the record by course
    //Whenever, the user clicks on search record by course. Mainactivity calls this method. It takes course name as parameter
public Cursor searchByCourse (String course)
{
    SQLiteDatabase db = this.getWritableDatabase();
    course = '"' + course + '"'; // We need to send the value to database as - ...WHERE COURSE = "PROG 8020". HENCE WE are putting double ticks
    String query = "Select * from marks where course="+course;
    Cursor cursorCourseSearch = db.rawQuery(query,null);
    return cursorCourseSearch;//RETURNS THE CURSOR WITH RESULT

}
    //Following method is used to execute the SQL query to search the record by id
    //Whenever, the user clicks on search record by id. Mainactivity calls this method. It takes id as parameter

    public Cursor searchByID (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        id = '"' + id + '"';
        String query = "Select * from marks where id="+id;
        Cursor cursorIDSearch = db.rawQuery(query,null);
        return cursorIDSearch;

    }

    //Following method is used to populate the UI with data , when ever user enters a value in ID. This is useful during UPDATE
    //This method takes id as parameter and returns cursor
    public Cursor populateWithExistingData(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        id = '"' + id + '"';
        String query = "Select * from marks where id="+id; //Searches based on id
        Cursor cursorPopulate = db.rawQuery(query,null);
        return cursorPopulate;//Returns cursor as result


    }


}