//Developed by: Karthik Appaswamy

package com.example.phoenixmusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AdminloginActivity extends AppCompatActivity {

    //Creating the objects of type UI
    EditText edtName, edtPassword;
    Button btnSignin, btnGotoUser;
    TextView txtAttempt; //to show incorrect Attempts
    int attemptBalance = 3; //Max allowed wrong attempt
    DatabaseManager dm;
    int userId;

//Following oncreate method is called first when the activity starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);//Mapping XML and this java file

//Mapping the ID of UI elements to the objects declared earlier, using findViewbyId
        edtName = findViewById(R.id.edtFirstName);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignUp); // Its signin only.
        //  Android studio keeps changing the id's w.r.t other login windows automatically.Hence, it is id is kept as signup for namesake. Wont impact user
        btnGotoUser=findViewById(R.id.btnGotoUser);
        txtAttempt=findViewById(R.id.txtResult); //Attempt
        //  Android studio keeps changing the id's w.r.t other login windows automatically.Hence, it is id is kept as Attempt for namesake. Wont impact user
         dm = new DatabaseManager(this); //Creating an instance of database manager

        //Setting up on click listener for the signinbutton
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((edtName.getText().toString().equalsIgnoreCase("admin"))&&(edtPassword.getText().toString().equals("cone")))
                {



                    dm.open(); //Calling the open method of database which establishes connection with database
                    if(dm.SearchByEmail("adminphoenix@gmail.com") =="No record found")
                    //If records of admin is not found inside the database, we are creating the record
                    {
                        dm.insertUser("admin","admin","cone","adminphoenix@gmail.com","admin");
                         userId = dm.getUserID("adminphoenix@gmail.com");


                    }


                    Date dateTimeNow = Calendar.getInstance().getTime(); //Current date time
                    DateFormat dateFormatRequired = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    String strDate = dateFormatRequired.format(dateTimeNow);

                    dm.insertUserLog(userId, strDate); //Creating log of login time

                    //Creating shared preferences for the sharing the admin id & role
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE); //No other application can modify

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("AdminId", Integer.toString( userId ));
                    editor.putString("role","admin");
                    editor.apply();
//Creating an intent to start the main activity
                    Intent adminLoginSucessIntent = new Intent(AdminloginActivity.this
                            , MainActivity.class);
                    adminLoginSucessIntent.putExtra("role","admin");

                    startActivity(adminLoginSucessIntent);


                }
                else{


                    attemptBalance--; //Attempt is decreased in case of wrong password
                    txtAttempt.setText("Wrong Credentials : You have " + attemptBalance + " attempts left" );
                    if(attemptBalance==0)
                    {
                        btnSignin.setEnabled(false); //Signin button is disabled on Max wrong password
                        txtAttempt.setText("Login disabled due to multiple wrong attempts." );
                    }
                }

            }
        });

        //Button to Go to user login page & its listener
        btnGotoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating intent to go to user login page
                Intent gotoSigin = new Intent (AdminloginActivity.this,LoginActivity.class);
                startActivity(gotoSigin);
            }
        });








    }
}
