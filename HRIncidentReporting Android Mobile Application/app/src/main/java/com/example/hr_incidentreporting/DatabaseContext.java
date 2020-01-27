package com.example.hr_incidentreporting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseContext extends SQLiteOpenHelper {
    //Declare constants for database opertations
    public static final String DATABASE_NAME = "EmployeeData.db";
    public static final String TABLE_1_NAME = "IncidentHistory";
    public static final String INCIDENTID = "IncidentID";
    public static final String INCIDENTDATE = "IncidentDate";
    public static final String EMPLOYEENUMBER = "EmployeeNumber";
    public static final String EMPLOYEENAME = "EmployeeName";
    public static final String GENDER = "Gender";
    public static final String SHIFT = "Shift";
    public static final String DEPARTMENT = "Department";
    public static final String POSITION = "Position";
    public static final String INCIDENTTYPE = "IncidentType";
    public static final String INJUREDBODYPART = "InjuredBodyPart";

    //Declare elements for employee table
    public static final String TABLE_2_NAME = "Employee";

    SQLiteDatabase db;

    public DatabaseContext(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    //Create database functionality
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Executing SQL queries to create tables
        db.execSQL("CREATE TABLE " + TABLE_1_NAME + " (IncidentID INTEGER PRIMARY KEY AUTOINCREMENT,IncidentDate TEXT,EmployeeNumber TEXT,EmployeeName TEXT,Gender TEXT,Shift TEXT,Department TEXT, Position TEXT,IncidentType TEXT,InjuredBodyPart TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_2_NAME + " (EmployeeNumber INTEGER PRIMARY KEY AUTOINCREMENT,EmployeeName TEXT,Gender TEXT,Department TEXT, Position TEXT)");
    }

    //Upgrade functionality
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ "IncidentHistory");
        db.execSQL("DROP TABLE IF EXISTS "+ "Employee");
        onCreate(db);
    }

    //Insert data to database functionality
    public boolean insertData(String incidentDate,String employeeNumber,String employeeName, String gender, String shift, String department, String position, String incidentType, String injuredBodyPart) {
        SQLiteDatabase db = this.getWritableDatabase(); // Getting instance of database class
        ContentValues contentValues = new ContentValues(); //Creating instance of content value class
        contentValues.put(INCIDENTDATE, incidentDate); //Adding data to content values
        contentValues.put(EMPLOYEENUMBER, employeeNumber);
        contentValues.put(EMPLOYEENAME, employeeName);
        contentValues.put(GENDER, gender);
        contentValues.put(SHIFT, shift);
        contentValues.put(DEPARTMENT, department);
        contentValues.put(POSITION, position);
        contentValues.put(INCIDENTTYPE, incidentType);
        contentValues.put(INJUREDBODYPART, injuredBodyPart);

        long result = db.insert(TABLE_1_NAME,null ,contentValues); //Inserting content values to database
        if(result == -1)
            return false; //Data Insert not successful
        else
            return true; //Insert successful
    }

    //Insert employee to database functionality
    public boolean insertEmployee(String employeeName, String gender, String department, String position) {
        SQLiteDatabase db = this.getWritableDatabase(); //Creating an instance of database class
        ContentValues contentValues = new ContentValues();//Creating an instance of Content value class
        contentValues.put(EMPLOYEENAME, employeeName);//Adding data to content values
        contentValues.put(GENDER, gender);
        contentValues.put(DEPARTMENT, department);
        contentValues.put(POSITION, position);

        long result = db.insert(TABLE_2_NAME,null ,contentValues);
        if(result == -1)
            return false;//Data Insert not successful
        else
            return true;//Insert successful
    }


    //Get data from database functionality
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase(); //Creating an instance of database class
        Cursor res = db.rawQuery("select * from "+TABLE_1_NAME,null); //Querying the database and storing the results in cursor
        return res; //Returing the cursor to the caller
    }

    //Get data by ID from database functionality
    public IncidentReport getDataByID()
    {
        SQLiteDatabase db = this.getWritableDatabase(); //Creating an instance of database class
        IncidentReport entry = new IncidentReport();//Creating instance of Incident Report Class
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_1_NAME + " WHERE IncidentID = (SELECT MAX(IncidentID) FROM IncidentHistory)", null);
        if(c.moveToFirst()){
            do{ //Data from cursor are added to the object of incident report class
                entry.IncidentID = c.getString(0);
                entry.IncidentDate = c.getString(1);
                entry.EmployeeNumber = c.getString(2);
                entry.EmployeeName = c.getString(3);
                entry.Gender = c.getString(4);
                entry.Shift = c.getString(5);
                entry.Department = c.getString(6);
                entry.Position = c.getString(7);
                entry.IncidentType = c.getString(8);
                entry.InjuredBodyPart = c.getString(9);
            }while(c.moveToNext());
        }
        return entry;
    }



    //Get data by ID from database functionality
    public Employee getDataByNumber(String employeeNumber)
    {
        SQLiteDatabase db = this.getWritableDatabase(); //Creating instance of database
        Employee entry = new Employee(); //Creating instance of employee class
        Cursor c = db.rawQuery("SELECT * FROM "+ TABLE_2_NAME +" WHERE EmployeeNumber = ?",new String[] {employeeNumber});
        if(c.moveToFirst()){
            do{ //Adding the result of the cursor to the employee object
                entry.EmployeeNumber = c.getString(0);
                entry.EmployeeName = c.getString(1);
                entry.Gender = c.getString(2);
                entry.Department = c.getString(3);
                entry.Position = c.getString(4);
            }while(c.moveToNext());
        }
        return entry; //Returning the object
    }
//THIS methods is used to find max of incident id
    public Cursor findMaxID() {

        SQLiteDatabase db = this.getWritableDatabase();
              String query = "Select MAX(IncidentID) from IncidentHistory"; //Searches FOR max of id
        Cursor cursorPopulate = db.rawQuery(query,null);
        return cursorPopulate;//Returns cursor as result



    }





}
