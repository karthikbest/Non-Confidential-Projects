package com.appsforall.studentmarkstore;


import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
//Following are the variable declarations. We are declaring these variables as per the UI elements in layout.
// We will use these variables to map UI elements
    EditText edtFirstName, edtLastName, edtMarks, edtId;
    ListView lvCourse;
    RadioGroup rgCredits;
    RadioButton selectedRadioButton, rbTwoCredit;
    //Following is the array with data for list view.
    String[] courses = {"PROG 8110", "PROG 8215", "PROG 1815"};
    //Following variable will be used to make instance of the database handler class
    DatabaseHandler dbh;
    //Following are the variable declarations. We are declaring these variables as per the UI elements in layout.
// We will use these variables to map UI elements
    Button btnAdd, btnViewData, btnSearchId, btnSearchProgCode, btnDelete, btnUpdate;
    String courseSelected;
    String updateId;
    ArrayAdapter<String> CourseAdapter;//Array adapter to extract data from the array into the UI
    Boolean addComplete, updateComplete;
    int numbRowsUpdated;
    String stringOfSelectedRadioButton;
    String toastMsgSearchByCourse;

//Following method will be called first whenever the application is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);//Linking the layout and this Java activity file

        dbh = new DatabaseHandler(this);//Creating an instance of the database handler class. T
        //Database handler class is declared in a separate file. Please go thorugh it.
courseSelected=null;//When the app starts, course selected is null - Unless the user changes it.

         //When the app starts, course selected is null  - Unless the user changes it.*/
        //Following are the statements to link the UI elements and its corresponding Java variable. (DONE USING ID OF UI ELEMENT)
        edtFirstName = findViewById(R.id.edtFirstName);
        edtId = findViewById(R.id.edtId);
        edtLastName = findViewById(R.id.edtLastName);
        edtMarks = findViewById(R.id.edtMarks);
        rgCredits = findViewById(R.id.rgCredit);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnSearchId = findViewById(R.id.btnSearchId);
        btnSearchProgCode = findViewById(R.id.btnSearchProgCode);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnViewData = findViewById(R.id.btnViewData);
        rgCredits.check(R.id.rbTwoCredit);
        selectedRadioButton = findViewById(R.id.rbTwoCredit);




        lvCourse = findViewById(R.id.lvCourse);
//Following array adapter is used to fetch data from data source (array ) into the List view
//2nd arguement is to tell how do we want the list items to be formatted
        CourseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courses);
        lvCourse.setAdapter(CourseAdapter);
       /* lvCourse.setItemChecked(0, true);*/
//Follwing listener is used to know when user fills in the id  in edit text box
        //This listner is used to populate the data of other fields from the database using this id
        edtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//Leaving this empty as we dont need this method as of now.

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Leaving this empty as we dont need this method as of now


            }

            //Fpollowing method is triggered after the text in the id edit text has been changed
            @Override
            public void afterTextChanged(Editable s) {
//Taking the id  from UI
                updateId = edtId.getText().toString().trim();//TRIM IS USED TO REMOVE WHITE SPACES
               //Calling the populateWithExistingData method of dtabase handler and storing the result to a cursor
                Cursor cursorPopulate = dbh.populateWithExistingData(updateId);


                if (cursorPopulate.moveToFirst() ) //if movetoFirst() returns value then the cursor is not empty
                {
                   //Following temporary variables are used to stored teh data fectchd from teh cursor
                    String tempId = cursorPopulate.getString(cursorPopulate.getColumnIndex("id"));
                    String tempFirstName = cursorPopulate.getString(cursorPopulate.getColumnIndex("firstName"));
                    String tempLastName = cursorPopulate.getString(cursorPopulate.getColumnIndex("lastName"));
                    String tempMarks = cursorPopulate.getString(cursorPopulate.getColumnIndex("marks"));
                    String tempCourse = cursorPopulate.getString(cursorPopulate.getColumnIndex("course"));
                    String tempCredits = cursorPopulate.getString(cursorPopulate.getColumnIndex("credits"));





                    edtFirstName.setText(tempFirstName);
                 edtLastName.setText(tempLastName);
                    edtMarks.setText(tempMarks);
                    lvCourse.clearChoices();
                 /*   lvCourse.setItemChecked(1,true);*/

                    if(DataValidation.stringNotNull(tempCourse)) {

                        if (tempCourse.equals("PROG 8110"))
                        {

                            lvCourse.setSelection(0);

                         }

                        else if (tempCourse.equals("PROG 8215"))
                            lvCourse.setSelection(1);

                        else if (tempCourse.equals("PROG 1815"))
                            lvCourse.setSelection(2);
                    }

                    if(DataValidation.stringNotNull(tempCredits))
                    {
                        switch (tempCredits)
                        {
                            case "2":
                                rgCredits.check(R.id.rbTwoCredit);
                                selectedRadioButton = findViewById(R.id.rbTwoCredit);
                                break;

                            case "3":
                                rgCredits.check(R.id.rbThreeCredit);
                                selectedRadioButton = findViewById(R.id.rbThreeCredit);
                                break;

                            case "4":
                                rgCredits.check(R.id.rbFourCredit);
                                selectedRadioButton = findViewById(R.id.rbFourCredit);



                        }


                    }



                    }


            }
        });

//This listener is set on the list view. It is used to know when  users selects an item is list view
        lvCourse.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//Following variable stores the value of the item selected by the user in the list view
                        courseSelected = String.valueOf(parent.getItemAtPosition(position));

                    }
                }
        );
//Following listener is used to know when user clicks the add button
        btnAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //(edtFirstName.getText().toString().length()==0 || edtLastName.getText().toString().equals(null) || edtMarks.getText().toString().equals(null) || courseSelected.equals(null)|| selectedRadioButton.getText().toString().equals(null))
                //Following if statement is used to ensure that data is not null or emoty. Other error message is given
                if ((!DataValidation.stringNotNull(courseSelected))||edtFirstName.getText().toString().length() == 0 || edtLastName.getText().toString().length() == 0 || edtMarks.getText().toString().length() == 0 || courseSelected.length() == 0 || selectedRadioButton.getText().toString().length() == 0)
                    Toast.makeText(MainActivity.this, "Error: Incomplete details. Please fill in the First Name, Last Name, Marks. And Select Course & Credits", Toast.LENGTH_SHORT).show();

                else {
                    //add marks method of database handler is called
                    addComplete = dbh.addMarks(edtFirstName.getText().toString(), edtLastName.getText().toString(), edtMarks.getText().toString(), courseSelected, selectedRadioButton.getText().toString());
                    if (addComplete) { //Sucess message
                        Toast.makeText(MainActivity.this, "Record Added Successfully", Toast.LENGTH_SHORT).show();

                    } else if (!addComplete) { //Fail message
                        Toast.makeText(MainActivity.this, "Record not Added", Toast.LENGTH_SHORT).show();

                    }
                }
            }


        });

//Following listener monitors when user clicks update button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtId.getText().toString().length() == 0) {//iF USER doesnot enter id
                    makeToast("Error: Please enter ID of the student to update data");
                }
        /*        else if (DataValidation.stringNotNull(edtFirstName.getText().toString()) ||DataValidation.stringNotNull(edtLastName.getText().toString())|| DataValidation.stringNotNull(edtMarks.getText().toString()) || DataValidation.stringNotNull(courseSelected)|| DataValidation.stringNotNull(selectedRadioButton.getText().toString()))
                    Toast.makeText(MainActivity.this, "Error: Incomplete details", Toast.LENGTH_SHORT).show();*/
               /* else if (( selectedRadioButton.getText().toString().matches("")|| !DataValidation.stringNotNull(courseSelected))|| edtFirstName.getText().toString().length() == 0 || edtLastName.getText().toString().length() == 0 || edtMarks.getText().toString().length() == 0 || courseSelected.length() == 0 )
                    Toast.makeText(MainActivity.this, "Error: Incomplete details. Please fill in the First Name, Last Name, Marks. And Select Course & Credits", Toast.LENGTH_SHORT).show();*/


                else  {
                    //Change marks methods of databse handler class is called
                    numbRowsUpdated = dbh.changeMarks(edtFirstName.getText().toString(), edtLastName.getText().toString(), edtMarks.getText().toString(), courseSelected, selectedRadioButton.getText().toString(), Integer.parseInt(edtId.getText().toString()));
                    if (numbRowsUpdated > 0) {//Sucess message
                        makeToast("Update Success");
                    } else {//Failure message
                        makeToast("There is no existing record for this ID available. Hence it cannot be updated");

                    }
                }


            }
        });
//Following listener is used to monitor when user clicks delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Database handlers' delete student method is called
                int countDeletedRow = dbh.deleteStudentRecord(edtId.getText().toString());

                if (countDeletedRow > 0)
                    makeToast("The record was sucessfully deleted"); //Sucess message

                if (countDeletedRow == 0)
                    makeToast("Error: The Record that you are trying to delete do not exists.");

            }
        });
        //Following listener is used to monitor when user clicks vIEW Data button

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbh.viewData();
                if (cursor.getCount() == 0) {
//If count is zero, then DB dont have any data available
                    Toast.makeText(MainActivity.this, "No records available", Toast.LENGTH_SHORT).show();
                    buildAlert("DATABASE IS EMPTY", "NO DATA FOUND");
                }
                //If there is result, I am using stringbuffer to display the data
                else if (cursor.getCount() != 0) {
                    StringBuffer buffer = new StringBuffer();
                    //I am getting all records one by one using cursor object
                    while (cursor.moveToNext()) {
                        buffer.append("id-" + cursor.getString(0) + "\n");
                        buffer.append("First Name-" + cursor.getString(1) + "\n");
                        buffer.append("Last Name-" + cursor.getString(2) + "\n");
                        buffer.append("Marks-" + cursor.getString(3) + "\n");
                        buffer.append("Course-" + cursor.getString(4) + "\n");
                        buffer.append("Credit-" + cursor.getString(5) + "\n\n");
                    }
                    buildAlert("Following Data are found in database", buffer.toString());
                }


            }
        });
        //Following listener is used to monitor when user clicks search by prog code button

        btnSearchProgCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataValidation.stringNotNull(courseSelected))
                {

//Calling databse handler method
                   Cursor cursorSearchByCourse =  dbh.searchByCourse(courseSelected);

                   if (cursorSearchByCourse.moveToFirst() ) //if move to first works then data is there in cursor
                   {
                       //Fetching data from cursor for preparing the toast message.
                       String tempId = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("id"));
                       String tempFirstName = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("firstName"));
                       String tempLastName = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("lastName"));
                       String tempMarks = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("marks"));
                       String tempCourse = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("course"));
                       String tempCredits = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("credits"));
                       //Preparing the toast message
                       toastMsgSearchByCourse = ("id- "+tempId+". First Name- " +tempFirstName+". Last Name- "+tempLastName+". Marks- "+tempMarks+". Course- "+tempCourse+". Credits- "+tempCredits);

//checking if we have more rows available in the cursor. As long as the record is available while loop will be executed.
                       while (cursorSearchByCourse.moveToNext())
                       {
                           //to get the second record & other records if available
                           String line = "\n"; //to put the another record in new line
                           tempId = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("id"));
                           tempFirstName = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("firstName"));
                           tempLastName = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("lastName"));
                           tempMarks = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("marks"));
                           tempCourse = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("course"));
                           tempCredits = cursorSearchByCourse.getString(cursorSearchByCourse.getColumnIndex("credits"));
                           toastMsgSearchByCourse += line + ("id- "+tempId+". First Name- " +tempFirstName+". Last Name- "+tempLastName+". Marks- "+tempMarks+". Course- "+tempCourse+". Credits- "+tempCredits);


                       }
                       makeToast(toastMsgSearchByCourse);

                   }



                   else //If no records are available in the cursor following statement will be executed and alert dialog will be shown to the user
                   {
                       buildAlert("No records available","There are no records available for this Prog Code.");
                   }



                }

                else//Toasting an errror message when user has not made any selections OF COURSE
                    makeToast("Error: Course not selected");


            }
        });
//Following listener is used to monitor when user clicks search by id method
        btnSearchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Using our custom made method in datavalidation class to ensure that the string is not null.
                if(DataValidation.stringNotNull(edtId.getText().toString())) {
                    //calling the searchbyid method in the database handler class and passing id as its parameter
                    Cursor cursorSearchID = dbh.searchByID(edtId.getText().toString());

                    if(cursorSearchID.moveToFirst())//if move to first works then data is there in cursor
                    {
                        //Fetching data from cursor for preparing the toast message.
                        String tempId = cursorSearchID.getString(cursorSearchID.getColumnIndex("id"));
                        String tempFirstName = cursorSearchID.getString(cursorSearchID.getColumnIndex("firstName"));
                        String tempLastName = cursorSearchID.getString(cursorSearchID.getColumnIndex("lastName"));
                        String tempMarks = cursorSearchID.getString(cursorSearchID.getColumnIndex("marks"));
                        String tempCourse = cursorSearchID.getString(cursorSearchID.getColumnIndex("course"));
                        String tempCredits = cursorSearchID.getString(cursorSearchID.getColumnIndex("credits"));
                        makeToast("id- "+tempId+". First Name- " +tempFirstName+". Last Name- "+tempLastName+". Marks- "+tempMarks+". Course- "+tempCourse+". Credits- "+tempCredits);


                    }

                    else{//If no records are available in the cursor following statement will be executed and alert dialog will be shown to the user

                        buildAlert("No records available","There are no records available for this ID.");
                    }
                }
                else{//Toasting an errror message when user has not entered an ID
                    makeToast("Error: Please enter an ID, to search");
                }
            }
        });


    }



//Custom made functions to make toast message. Just for ease of typing in.
    public void makeToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

    }

    public void buildAlert(String title, String message) {//Function to create POP up message

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true) //We can cancel the alert dialog after its use
        .setTitle(title). //Title of the POP up window
        setMessage(message) //Message of the POP up window
                .setPositiveButton("Ok", null) //Setting up a OK button in the POP up window

        .show(); //This will show the alert dialog

    }

    public void onClickRadio(View v) { //Function to know which radio button is clicked
        int selectedRadioID = rgCredits.getCheckedRadioButtonId();
        selectedRadioButton = findViewById(selectedRadioID);
        stringOfSelectedRadioButton = selectedRadioButton.getText().toString();//Extracting the string value of the selected radio button



    }




}


