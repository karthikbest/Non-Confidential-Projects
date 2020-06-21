using System;
using System.Text.RegularExpressions;
/* 
ATM PIN Code Validation
ATM machines allow 4 or 6 digit PIN codes and PIN codes cannot contain anything but exactly 4 digits or exactly 6 digits. Your task is to create a function that takes a string and returns true if the PIN is valid and false if it's not.

Examples
ValidatePIN("1234") ➞ true

ValidatePIN("12345") ➞ false

ValidatePIN("a234") ➞ false

ValidatePIN("") ➞ false
Notes
Some test cases contain special characters.
Empty strings must return false.

Coding Challenge from Edabit.com

Solved by Karthik Appaswamy
*/
namespace CodingChallenge
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine("Please punch in your ATM pin");
            string inputString = Console.ReadLine();
            Console.WriteLine(validatePIN(inputString));
            Main();
            
        }
        public static bool validatePIN(string inputString)
        {
            // Regex for 4 digit ATM pin
            Regex regexFourDigitAtmPin = new Regex("^[0-9]{4}$");

            // Regex for 6 digit ATM pin
            Regex regexSixDigitAtmPin = new Regex("^[0-9]{6}$");

            return (regexFourDigitAtmPin.IsMatch(inputString) || regexSixDigitAtmPin.IsMatch(inputString)) ? true : false;


        }
    }
}
