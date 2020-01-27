package com.appsforall.studentmarkstore;

//I am creating this Class to declare a data validation method
public class DataValidation {


//Static method to ensure that string is not null.
    public static boolean stringNotNull(String x)

    {
        if(x==null || x.trim().isEmpty() || x.length() ==0 ) // logic to determine if string is null or not
        {
            return false;
        }
        return true;//If string is not null true will be returned
    }

}