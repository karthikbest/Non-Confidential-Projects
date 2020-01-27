package com.example.hr_incidentreporting;

import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

//Activity for View Incidents
public class ViewActivity extends MainActivity {
//Objects of the UI Component types
    TextView reportData;
    DatabaseContext employeeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view); //Mapping this activity and corresponding  XML using ID

        //Mapping objects with UI elements using ID
        reportData = (TextView) findViewById(R.id.txtReportData);
        reportData.setMovementMethod(new ScrollingMovementMethod()); //Facility to scroll

        employeeDB = new DatabaseContext(this);  //Creating an instance of databasecontext class

        Cursor res = employeeDB.getAllData(); //Calling DatabaseContext method
        if(res.getCount() == 0) { //If cursor is empty
            // show message
            reportData.setText("No incidents found");
            return;
        }

        StringBuffer buffer = new StringBuffer(); //Creating instance of StringBuffer class
        while (res.moveToNext()) { //Extracting the data of the cursor and adding it to the String buffer
            buffer.append("Incident ID : "+ res.getString(0)+"\n");
            buffer.append("Incident Date : "+ res.getString(1)+"\n");
            buffer.append("Employee Number : "+ res.getString(2)+"\n");
            buffer.append("Employee Name : "+ res.getString(3)+"\n");
            buffer.append("Gender : "+ res.getString(4)+"\n");
            buffer.append("Shift : "+ res.getString(5)+"\n");
            buffer.append("Department : "+ res.getString(6)+"\n");
            buffer.append("Position : "+ res.getString(7)+"\n");
            buffer.append("Incident Type : "+ res.getString(8)+"\n");
            buffer.append("Injured Body-part : "+ res.getString(9)+"\n\n");
        }

        // Show all data using String buffer
        reportData.setText(buffer.toString());
    }
}
