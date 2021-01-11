using System;
using System.Linq;

/*
 *The goal of this exercise is to convert a string to a new string where each character in the new string is "(" if that character appears only once in the original string, or ")" if that character appears more than once in the original string. Ignore capitalization when determining if a character is a duplicate.

Examples
"din"      =>  "((("
"recede"   =>  "()()()"
"Success"  =>  ")())())"
"(( @"     =>  "))((" 
Notes

Assertion messages may be unclear about what they display in some languages. If you read "...It Should encode XXX", the "XXX" is the expected result, not the input!

Coding Challenge from CodeWars.com
Solved by Karthik Appaswamy
 */

namespace DuplicateEncoder
{
    class Program
    {
        static void Main(string[] args)
        {
            string output = DuplicateEncode("din");
            Console.WriteLine(output);
            Console.ReadLine();
        }
        public static string DuplicateEncode(string word)
        {
            char[] words = word.ToCharArray();

            char[] wordsCopy = new Char[words.Length];

            words.CopyTo(wordsCopy, 0);

            foreach(char w1 in words)
            {
                int i = 0;
                foreach (char w2 in words)
                {
                    if (w1 == w2)
                    {
                        i++;
                    }
                
                }

                if(i>1)
                {
                    wordsCopy.Where(x => x == w1).Select(item => item = ')');

                }
                else
                {
                    wordsCopy.Where(x => x == w1).Select(item => item = '(');
                }

            }

            return new string(wordsCopy);
        }
    }
}
