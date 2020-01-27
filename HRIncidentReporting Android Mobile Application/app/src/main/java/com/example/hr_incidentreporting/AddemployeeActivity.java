package com.example.hr_incidentreporting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddemployeeActivity extends MainActivity {
    //Creating objects for all components
    DatabaseContext employeeDB;
    RadioButton rbtnMale, rbtnFemale;
    EditText edtEmployeeName, edtDepartment, edtPosition;
    Button btnAddEmployee;

    //Following method is called first when ever the activity is opened
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Linking the layout xml file with this java file
        setContentView(R.layout.activity_addemployee);

        //Mapping the variables with the UI element using the id of UI elements
        employeeDB = new DatabaseContext(this);
        rbtnMale = findViewById(R.id.RBMale);
        rbtnFemale = findViewById(R.id.RBFemale);
        edtEmployeeName = findViewById(R.id.ETName);
        edtDepartment = findViewById(R.id.ETDepartment);
        edtPosition = findViewById(R.id.ETPosition);
        btnAddEmployee = findViewById(R.id.btnAddEmployee);

        AddEmployee(); //User defined method
    }

    public void AddEmployee(){
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = "";
                if (rbtnMale.isChecked()) { //Logic to determine the gender
                    gender = "Male";
                } else if (rbtnFemale.isChecked()) {
                    gender = "Female";
                }
//Datavalidation for any null
                if (edtEmployeeName.getText().toString().matches("") ||
                        edtDepartment.getText().toString().matches("") ||
                        edtPosition.getText().toString().matches("") ) {
                    Toast.makeText(AddemployeeActivity.this, "Try again by entering valid data into all input fields", Toast.LENGTH_LONG).show();
                }
                else {
                    //Calling databasehandler class's method and passing the user entered data to it
                    boolean isInserted = employeeDB.insertEmployee(edtEmployeeName.getText().toString(),
                            gender,
                            edtDepartment.getText().toString(),
                            edtPosition.getText().toString());
                    if (isInserted == true)//Insert success
                        Toast.makeText(AddemployeeActivity.this, "Employee successfully added", Toast.LENGTH_LONG).show();
                    else //Inser failed
                        Toast.makeText(AddemployeeActivity.this, "Employee was not added", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}