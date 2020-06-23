/* 
 you are required to, given a string, replace every letter with its position in the alphabet.

If anything in the text isn't a letter, ignore it and don't return it.

"a" = 1, "b" = 2, etc.

Example
Kata.AlphabetPosition("The sunset sets at twelve o' clock.")
Should return "20 8 5 19 21 14 19 5 20 19 5 20 19 1 20 20 23 5 12 22 5 15 3 12 15 3 11" (as a string)

Coding Challenge from CodeWars

Solved by Karthik Appaswamy
 */
using System;
using System.Text;

class Program
{
    public static void Main()
    {
        Console.WriteLine(AlphabetPosition("The sunset sets at twelve o' clock."));

        Console.ReadLine();
    }

    public static string AlphabetPosition(string inputString)
    {
        inputString = inputString.ToUpper();
        byte[] asciiCodes = Encoding.ASCII.GetBytes(inputString);

        foreach (byte a in asciiCodes)
        {
            Console.WriteLine(a);
        }
        byte[] alphabetPositions = new byte[inputString.Length];
        byte calibrateValue = 64;

        for (int i = 0; i < inputString.Length; i++)
        {
            if (!(asciiCodes[i] < 65 || asciiCodes[i] > 90))
            {
                alphabetPositions[i] = (byte)(asciiCodes[i] - calibrateValue);
            }
            else
            {
                alphabetPositions[i] = 0;
            }

        }

        StringBuilder stringBuilder = new StringBuilder();

        foreach (byte a in alphabetPositions)
        {
            if (a != 0)
            {
                stringBuilder.Append(a + " ");
            }
        }

        return stringBuilder.ToString().TrimEnd();
    }



}