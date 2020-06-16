
/* Array of Multiples
Create a function that takes two numbers as arguments (num, length) and returns an array of multiples of num up to length.

Examples
ArrayOfMultiples(7, 5) ➞ [7, 14, 21, 28, 35]

ArrayOfMultiples(12, 10) ➞ [12, 24, 36, 48, 60, 72, 84, 96, 108, 120]

ArrayOfMultiples(17, 6) ➞ [17, 34, 51, 68, 85, 102]
Notes
Notice that num is also included in the returned array.
Programming Challenge from: https://edabit.com/challenge/2QvnWexKoLfcJkSsc

SOLVED BY KARTHIK APPASWAMY
 */
using System;

namespace Array_of_Multiples_Edabit
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine("Enter the number");
            int number = Convert.ToInt32(Console.ReadLine());
            Console.WriteLine("Enter the length");
            int length = Convert.ToInt32(Console.ReadLine());
            int[] generatedArray = generateArray(number, length);
            foreach(int i in generatedArray)
            {
                Console.WriteLine(i);
            }
        }

        static int[] generateArray(int number, int length)
        {
            int[] generatedArray = new int[length];
            for (int i = 0; i<length; i++)
            {
                generatedArray[i] = number * (i + 1);
            }
            return generatedArray;
        }
    }
}
