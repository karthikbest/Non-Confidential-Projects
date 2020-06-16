/* Is the Word an Isogram?
An isogram is a word that has no repeating letters, consecutive or nonconsecutive.Create a function that takes a string and returns either true or false depending on whether or not it's an "isogram".

Examples
IsIsogram("Algorism") ➞ true

IsIsogram("PasSword") ➞ false
// Not case sensitive.

IsIsogram("Consecutive") ➞ false
Notes
Ignore letter case (should not be case sensitive).
All test cases contain valid one word strings.
Programming challenge from https://edabit.com/challenge/aoR4PFS6FfpJs6v79

SOLVED BY KARTHIK APPASWAMY
*/

using System;

namespace Isogram
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine("Enter the word to check for isogram");
            Console.WriteLine(isIsogram(Console.ReadLine()));
        }

        static Boolean isIsogram(String wordToBeChecked)
        {
            char[] charArray = wordToBeChecked.ToCharArray();
            foreach (char c in charArray)
            {
                int equalCounter = 0;
                foreach (char x in charArray)
                {
                    if(Equals(Char.ToLower(c), Char.ToLower(x)))
                    {
                        equalCounter++;
                    }
                }
                if (equalCounter > 1) // If a char is repeated more than once, then it is not an isogram
                {
                    return false;
                }
            }
            return true;
        }
    }
}
