/*
Check if a Number is Prime
Create a function that outputs true if a number is prime, and false otherwise.

Examples
isPrime(31) ➞ true

isPrime(18) ➞ false

isPrime(11) ➞ true
Notes
A prime number has no other factors except 1 and itself.
1 is not considered a prime number.

SOLVED BY KARTHIK APPASWAMY
*/
using System;

namespace Check_if_a_Number_is_Prime
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine("Enter the number");
            int number = Convert.ToInt32(Console.ReadLine());
            if (isPrime(number))
            {
                Console.WriteLine("The number that you have entered is a prime number");
                Main();
            }
            Console.WriteLine("The number that you have entered is NOT a prime number");
            Main();
        }

        static bool isPrime(int number)
        {
            if(number == 1)
            {
                return false;
            }
            int loopMaxLimit = (number - 1) > 10 ? 10 : (number - 1);
            for (int i = 2; i <= loopMaxLimit; i++)
            {
                if(number % i == 0)
                {
                    return false;
                }
            }
            return true;
        }
    }
}
