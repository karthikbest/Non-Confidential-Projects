/*
Next Prime
Given an integer, create a function that returns the next prime.If the number is prime, return the number itself.

Examples
NextPrime(12) ➞ 13

NextPrime(24) ➞ 29

NextPrime(11) ➞ 11
// 11 is a prime, so we return the number itself.
Programming challenge from: https://edabit.com/challenge/FKb8JY75nkaHz7B3F

SOLVED BY KARTHIK APPASWAMY

*/


using System;

namespace NextPrime
{
    class Program
    {
        static void Main()
        {
            Console.WriteLine($"Enter Number to the next prime (if the number is not prime)"); 
            int number = Convert.ToInt32(Console.ReadLine());
            int nextPrime = NextPrime(number); 
            if(number == nextPrime)
            {
                Console.WriteLine($"The number {number} itself is a Prime Number");
            }
            else
            {
                Console.WriteLine($"The number {number} is NOT Prime Number. The NEXT PRIME NUMBER IS {nextPrime}");
            }
            Main();


        }

        static int NextPrime(int number)
        {
            if (isPrime(number))
            {
                return number;
            }
            for (int i = 0; true; i++)
            {
                if (isPrime(number + i))
                {
                    return number + i;
                }
            }
        }

        static bool isPrime(int number)
        {
            if (number =  = 1)
            {
                return false;
            }
           /* int loopMaxLimit = (number - 1) > 10 ? 10 : (number - 1)*/;
            for (int i = 2; i < number; i++)
            {
                if (number % i == 0)
                {
                    return false;
                }
            }
            return true;
        }
    }
}
