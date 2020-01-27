
//Developed by: Pamy Ann Patrick

package com.example.phoenixmusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.phoenixmusicapp.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    //Declaring the objects of type UI
    EditText edtEmail,edtPassword;
    Button btnSignin;
    TextView txtResult; //to show feedbacks
    TextView txtSignUp, txtGotoAdmin;
    DatabaseManager dm;

    //Following method will be called first, when ever the activity is started
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //Mapping id of the UI with the javafile
        edtEmail = findViewById(R.id.edtFirstName);//Email - to avoid android studios bug which changes id in another layout automatically if we chang this
        edtPassword = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnSignUp); //Signin - to avoid android studios bug which changes id in another layout automatically if we chang this
        txtSignUp=findViewById(R.id.txtSignIn);////Signup - to avoid android studios bug which changes id in another layout automatically if we chang this
        txtGotoAdmin=findViewById(R.id.txtGotoAdmin);
        txtResult=findViewById(R.id.txtResult);
        //Creating an instance of the database manager and passing the context to its constructor
        dm = new DatabaseManager(this);
        dm.open(); // calling open function of the database manager which will establish database connection
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupUser(); //When ever user clicks signup text , this user defined function will be called
            }
        });





txtGotoAdmin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        gotoAdmin();//When ever user clicks Admin text , this user defined function will be called
    }
});

//Listener for sign in button, which triggers an onclick event when the button is clicked
btnSignin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        //When email id or password or both is not filled following message is shown to user
        if(edtEmail.getText().toString().equals("") || edtPassword.getText().toString().equals(""))
        {
            txtResult.setText("Please fill in both the email & password first");

        }
        //The email id entered by the user is validated against the database and error message is shown if not found
        else if((dm.SearchByEmail(edtEmail.getText().toString())).equals("No record found") )
        {

            txtResult.setText("This email id is not registered with us.Please sign-up");
        }
        else
            {
//Calling the searchpassword method of the database manager and passing email as its arguement
            User user = dm.SearchPassword(edtEmail.getText().toString());

//Checking if the password which user enters is equal to the password in the database
            if(edtPassword.getText().toString().equals(user.getpassword()))
            {
                txtResult.setText("Password is correct");
                int userId = user.getUserID(); //Gettig the user id from the database
                Date dateTimeNow = Calendar.getInstance().getTime(); //Current time
                DateFormat dateFormatRequired = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String strDate = dateFormatRequired.format(dateTimeNow);
                dm.insertUserLog(userId, strDate); //Inserting log in to the database
                //Creating an instance of shared preference class
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE); //No other application can modify

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userId", Integer.toString(userId)); //Passing userid to shared preference
                editor.putString("role","user"); // Passing role
                editor.apply();

//Creating an intent to start main activity
                Intent userLoginSucessIntent = new Intent(LoginActivity.this
                        , MainActivity.class);

                startActivity(userLoginSucessIntent);



            }
            else
            {
                txtResult.setText("Password is wrong");//error message

            }
        }

    }
});





    }

    private void gotoAdmin() {
        //Intent to go to admin login page
        Intent gotoAdminIntent = new Intent (LoginActivity.this,AdminloginActivity.class);
        startActivity(gotoAdminIntent);
    }

    private void signupUser() {
//Intent to go to signup user
        Intent signupIntent = new Intent (LoginActivity.this,RegistrationActivity.class);
        startActivity(signupIntent);

    }
}
