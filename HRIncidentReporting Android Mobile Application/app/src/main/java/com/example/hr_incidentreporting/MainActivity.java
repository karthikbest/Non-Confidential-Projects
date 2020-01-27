package com.example.hr_incidentreporting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Creating objects for all components
    DatabaseContext employeeDB;
    TextView txtIncidentId, txtDate, txtIncidentNumber;
    RadioGroup rdgGender;
    RadioButton rbtnMale, rbtnFemale;
    EditText edtEmployeeNumber, edtEmployeeName, edtDepartment, edtPosition;
    Spinner spnShiftsList, spnIncidentTypeList, spnInjuredBodyPartList;
    Button btnReportIncident, btnGetEmployeeData;
    File fileForImage=null;
    //Creating variables which are needed for the program. The purpose of these variables are explained in the program
    String MessageString;
    Uri uriImage=null;
    final int CAMERA_REQUEST_CODE = 1000; //To ensure request matches with response. It can be of any value.
    String fileName;

    //Following method is called first when ever the app is opened
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Linking the layout xml file with this java file
        setContentView(R.layout.activity_main);

        //Mapping the variables with the UI element using the id of UI elements
        employeeDB = new DatabaseContext(this);
        txtIncidentId = findViewById(R.id.txtId);
        txtDate = findViewById(R.id.txtPresentDate);
        rdgGender = findViewById(R.id.rdGender);
        rbtnMale = findViewById(R.id.rdMale);
        rbtnFemale = findViewById(R.id.rdFemale);
        edtEmployeeNumber = findViewById(R.id.edtEmployeeNumber);
        edtEmployeeName = findViewById(R.id.edtEmployeeName);
        edtDepartment = findViewById(R.id.edtDepartment);
        edtPosition = findViewById(R.id.edtPosition);
        spnShiftsList = findViewById(R.id.spnShift);
        spnIncidentTypeList = findViewById(R.id.spnIncidentType);
        spnInjuredBodyPartList = findViewById(R.id.spnInjuredBodyPart);
        btnReportIncident = findViewById(R.id.btnReportIncident);
        btnGetEmployeeData = findViewById(R.id.btnGetEmployeeData);
        txtIncidentNumber = findViewById(R.id.txtIncidentIdValue);

//Calling the function in database handler class to find the max id and getting back the cursor
        Cursor cursorMaxNumb = employeeDB.findMaxID();
if(cursorMaxNumb.moveToFirst())  //Checking if cursor is not empty
{
    //Finding the max id from dataase column and adding 1 to it => that will be our alloted id
    int allotedID = Integer.parseInt(cursorMaxNumb.getString(0)) + 1;
//Populating the text view with alloted id
    txtIncidentNumber.setText(Integer.toString(allotedID));

}

        //Creating a date string
        String date = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault()).format(new Date());

        //Auto filling date field with current date
        txtDate.setText(date);

        //Setting the shifts listed in the array in strings.xml to the respective spinner (drop-down list)
        final String[] shiftsArray = getResources().getStringArray(R.array.shifts);
        ArrayAdapter<String> shiftsArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        shiftsArray);
        shiftsArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnShiftsList.setAdapter(shiftsArrayAdapter);

        //Setting the incident type listed in the array in strings.xml to the respective spinner (drop-down list)
        final String[] incidentTypeArray = getResources().getStringArray(R.array.incident_type);
        //Creating the array adapter to extract the data from the array
        ArrayAdapter<String> incidentTypeArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        incidentTypeArray);
        //Creating the array adapter to extract the data from the array
        incidentTypeArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //Setting the array adapter to the spinner
        spnIncidentTypeList.setAdapter(incidentTypeArrayAdapter);

        //Populating the data for body parts array from strings. xml
        final String[] bodyPartsArray = getResources().getStringArray(R.array.body_parts);
        //Creating the array adapter to extract the data from the array
        ArrayAdapter<String> bodyPartsArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        bodyPartsArray);
        //Setting the layout resource to create the drop down views.
        bodyPartsArrayAdapter .setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //Setting the array adapter to the spinner
        spnInjuredBodyPartList.setAdapter(bodyPartsArrayAdapter);



        //Setting up an onClickListener for the button Report incident.
        btnReportIncident.setOnClickListener(new View.OnClickListener()
        {
            //This method will be called whenever the user clicks on the Report Incident button
            @Override
            public void onClick(View v)
            {
                //Calling the user defined function for capturing the photo
                takePhoto();
                insertReport();
            }
        });

        //Setting up an onClickListener for the button get employee data.
        btnGetEmployeeData.setOnClickListener(new View.OnClickListener()
        {
            //This method will be called whenever the user clicks on the Report Incident button
            @Override
            public void onClick(View v)
            {
                //Calling the user defined function for getting data
                getEmployeeData();
            }
        });
    }

    //This user defined function is used to populate employee data
    private void getEmployeeData()
    {
        Employee employee = employeeDB.getDataByNumber(edtEmployeeNumber.getText().toString());
        if (employee.EmployeeNumber == null) {
            Toast.makeText(this, "No employee associated with mentioned ID", Toast.LENGTH_LONG).show();
        }
        else {
            edtEmployeeNumber.setText(employee.getEmployeeNumber());
            edtEmployeeName.setText(employee.getEmployeeName());
            edtDepartment.setText(employee.getDepartment());
            edtPosition.setText(employee.getPosition());
            if (employee.getGender().equals("Male")) {
                rbtnMale.setChecked(true);
            } else{
                rbtnFemale.setChecked(true);
            }
        }
    }

    private void insertReport(){
        String gender = "";
        Date todaysDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(todaysDate);
        if (rbtnMale.isChecked()) {
            gender = "Male";
        } else if (rbtnFemale.isChecked()) {
            gender = "Female";
        }

        if (edtEmployeeNumber.getText().toString().matches("") ||
                edtEmployeeName.getText().toString().matches("") ||
                edtDepartment.getText().toString().matches("") ||
                edtPosition.getText().toString().matches("") ) {
            Toast.makeText(MainActivity.this, "Try again by entering valid data into all input fields", Toast.LENGTH_LONG).show();
        }
        else {
            boolean isInserted = employeeDB.insertData(formattedDate, edtEmployeeNumber.getText().toString(),
                    edtEmployeeName.getText().toString(),
                    gender,
                    spnShiftsList.getSelectedItem().toString(),
                    edtDepartment.getText().toString(),
                    edtPosition.getText().toString(),
                    spnIncidentTypeList.getSelectedItem().toString(),
                    spnInjuredBodyPartList.getSelectedItem().toString());
            if (isInserted == true)
                Toast.makeText(MainActivity.this, "Incident report successfully added. Click a photo of the incident now...", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.this, "Incident report was not added", Toast.LENGTH_LONG).show();
        }
    }

    //This user defined function is used to capture the photo
    private void takePhoto()
    {
        //Following if statement checks whether the user has given permission for the app to us e camera
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
            //We create new intent to capture photo. Intent is messaging object used to request action from another app component.
            Intent IntentCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);


            //If the device does not have Camera, we are toasting an error message
            if (IntentCamera.resolveActivity(getPackageManager()) == null)
            {

                makeToast("Your device doesn't have camera.Camera is needed for this application");

            }
            else //IF the device has camera
            {
                try
                {
                    //Calling the user defined function to create the file & assigning the return value of the function to a variable
                    fileForImage = createImageFile();

                    // Continue only if the File was successfully created
                    if (fileForImage != null) {
                        //Getting the Uniform Resource Identifier of the file
                        uriImage = FileProvider.getUriForFile(this, "com.example.hr_incidentreporting.fileprovider", fileForImage);
                        //Adding the URI to the camera Intent as extra information
                        IntentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);

                        //Starting another activity for camera and passing on the Intent created and the request code. Request code is used to ensure that the result corresponds to this request
                        startActivityForResult(IntentCamera, CAMERA_REQUEST_CODE);
                    }
                }

                catch (Exception e)
                {
                    // Catching any exception that happens during file creation. As of now, we aren't doing anything with this exception
                }
            }
        }
        else {
            //If the user has not given the permission for camera, we are now going to request for his permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    //Inflating the menu resource
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    //Responding to menu item selection
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuReportIncident:
                Intent reportIncident = new Intent(this,MainActivity.class);
                startActivity(reportIncident);
                return true;
            case R.id.menuViewIncidents:
                Intent viewIncident = new Intent(MainActivity.this,ViewActivity.class);
                startActivity(viewIncident);
                return true;
            case R.id.menuAddEmployee:
                Intent addEmployee = new Intent(MainActivity.this,AddemployeeActivity.class);
                startActivity(addEmployee);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //This function is used to create the image file before taking the photo
    public File createImageFile() throws IOException
    {
        // Create an image file name using time - It will be unique for all photos as we are using seconds in file name
        fileName = new SimpleDateFormat("YYMMDDHHMMSS").format(new Date());
        //Creating & Returning the file using it's file name &  getExternalFilesDir() (returns the path to files)
        return (File.createTempFile(fileName, ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
    }

    //Following method is used to receive the response of Camera activity. This method is called once the photo is captured
    @Override
    protected void onActivityResult(int Request_Code, int Result_Code, Intent data)
    {
        //Checking if the photo was clicked successfully
        if(Result_Code == RESULT_OK)
        {
            //Checking if this photo is a response for our request using the request code
            if(Request_Code==CAMERA_REQUEST_CODE)
            {
                //Calling the user defined function to create the message text
                createMessageString();
                //Calling the user defined function to send email
                sendEmail();
            }
        }
        else //If the user cancels the photo inside the camera
        {
            makeToast("You cancelled the capture inside the camera");
        }
    }

    //User defined function to make toast in easy way. Just to avoid typing
    private void makeToast(String str)
    {
        Toast.makeText(MainActivity.this,str ,Toast.LENGTH_SHORT).show();
    }

    //User defined function to send email with message and attachment. We are using intent to create email
    public void sendEmail()
    {
        //Creating an intent to send email. We use intent send data to other applications
        Intent mail = new Intent(android.content.Intent.ACTION_SEND);
        //ACTION_SEND is used to send data from one activity to another. This is mandatory information

        //We can also provide additional information using PUTEXTRA
        mail.putExtra(android.content.Intent.EXTRA_SUBJECT,"HR Incident Reporting");
        //The above syntax ads subject of the email
        mail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"tmathew@conestogac.on.ca"});
        //The above syntax adds email id of the receiver. We can even add more than one email id here
        mail.putExtra(android.content.Intent.EXTRA_TEXT, MessageString);
        //The above syntax adds text to the email body. We have formed this variable using another function
        mail.putExtra(Intent.EXTRA_STREAM, uriImage);
        //The above syntax attaches the photo to the email body using its Uniform Resource Identifier
        mail.setType("application/image");
        //SetType is used to create intents that only specifies a type
        startActivity(Intent.createChooser(mail, "email"));
    }

    //Creating string which is to be put in the email body
    public void createMessageString()
    {
        employeeDB = new DatabaseContext(this);
        IncidentReport iR = employeeDB.getDataByID();
        //Creating the email message based on auto populated values from the database and the user selected values in the UI
        MessageString = "Incident ID : " + iR.getIncidentID() + "\n" +
                "Incident Date: " + iR.getIncidentDate() + "\n" +
                "Employee Number: " + iR.getEmployeeNumber() + "\n" +
                "Employee Name: " +  iR.getEmployeeName() + "\n" +
                "Gender: " + iR.getGender() + "\n" +
                "Shift: " + iR.getShift() + "\n" +
                "Department: " + iR.getDepartment() + "\n" +
                "Position: " + iR.getPosition() + "\n" +
                "Incident type: "+ iR.getIncidentType() + "\n" +
                "Injured body part: " + iR.getInjuredBodyPart() + "\n";
    }
}
