
//Developed by: Karthik Appaswamy

package com.example.phoenixmusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
//Creating objects of the UI type
    EditText edtFirstName, edtLastName, edtEmail, edtPassword, edtConfirmPassword;
    Button btnSignup;
    TextView txtGotoAdmin, txtSignin, txtResult;
    DatabaseManager dm;

    //Following oncreate method will be called first when activity is started
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);//Mapping XML & this java file
        //Mapping the Ids of the UI elements with the objects
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignup = findViewById(R.id.btnSignUp);
        edtEmail = findViewById(R.id.edtEmail);
        txtGotoAdmin = findViewById(R.id.txtGotoAdmin);
        txtSignin = findViewById(R.id.txtSignIn);
        txtResult = findViewById(R.id.txtResult);
        //Creating an instance of the database manager class
        dm = new DatabaseManager(this);
        dm.open(); //Calling the open method of the database manager class to establish db connection


        //On click listener for the signup button
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Searching if the email id exists already
                if((dm.SearchByEmail(edtEmail.getText().toString())).equals("No record found") )
                {

                    //Checking if the email is in correct format
                    if(checkEmailFormat(edtEmail.getText().toString()))
                    {
                        //Checking for null characters
                        if((edtFirstName.getText().toString().matches(""))||(edtLastName.getText().toString().matches(""))||((edtPassword.getText().toString().matches("")))
                                ||(edtConfirmPassword.getText().toString().matches("")))
                        {

                            txtResult.setText("ERROR: Unable to login. Please enter all the details");

                        }
                        else
                        {
//Checking if the password entered by the user matches with the confirm password
                            if(edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString()))
                            {

                                boolean addSuccess = dm.insertUser(edtFirstName.getText().toString(),edtLastName.getText().toString(),edtPassword.getText().toString(),edtEmail.getText().toString(),"user");
                                if(addSuccess)//If user is added sucessfully
                                {

                                    txtResult.setText("Success. You have been registered. Please use your registered email & password to sign-in");
                                }

                                else
                                    txtResult.setText("ERROR: Data insert failed. ");


                            }
                            else//If passwords entered by the  user dont match
                            {
                                txtResult.setText("ERROR: Password & confirm does not match");
                            }

                        }




                    }
                    else {//Email id format is wrong

                        txtResult.setText("ERROR:Please enter a valid email address");
                    }
                }
                else //If the email id exists in the database alreadu
                {
                    txtResult.setText("ERROR: This email-id is already registered. You cannot register again");

                }

            }

        });
//When ever user clicks go-signin following listener will trigger the onclick method
        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Creating the intent to go to Login activity
                Intent gotoSigin = new Intent (RegistrationActivity.this,LoginActivity.class);
                startActivity(gotoSigin);
            }
        });
    }

    //User defined function to check the format of the email id
    public boolean checkEmailFormat(String email)
    {
        //Regex for email id
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email); //Comparing the email entered by user & Regex
        return m.matches();
    }
}