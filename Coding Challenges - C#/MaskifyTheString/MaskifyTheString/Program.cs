/*
 Maskify the String
Usually when you sign up for an account to buy something, your credit card number, phone number or answer to a secret question is partially obscured in some way. Since someone could look over your shoulder, you don't want that shown on your screen. Hence, the website masks these strings.

Your task is to create a function that takes a string, transforms all but the last four characters into "#" and returns the new masked string.

Examples
Maskify("4556364607935616") ➞ "############5616"

Maskify("64607935616") ➞ "#######5616"

Maskify("1") ➞ "1"

Maskify("") ➞ ""
Notes
The maskify function must accept a string of any length.
An empty string should return an empty string (fourth example above).
Programming Challenge from https://edabit.com/challenge/RFeBL2TzSf7mRMNJi

SOLVED BY KARTHIK APPASWAMY

*/


using System;

namespace MaskifyTheString
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine("Please enter the string that you wish to maskify");
            Console.WriteLine(Maskify(Console.ReadLine()));
            Main();
        }

        static string Maskify(string input)
        {
            if (String.IsNullOrWhiteSpace(input))
            {
                return String.Empty;
            }
            char[] inputArray = input.ToCharArray();

            for (int i = 0; i < (input.Length - 4); i++)
            {
                inputArray[i] = '#';
            }

            string maskedString = new string(inputArray);
            return maskedString;
        }
    }


}
