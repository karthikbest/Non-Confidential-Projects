/*Make a program that filters a list of strings and returns a list with only your friends name in it.

If a name has exactly 4 letters in it, you can be sure that it has to be a friend of yours! Otherwise, you can be sure he's not...

Ex: Input = ["Ryan", "Kieran", "Jason", "Yous"], Output = ["Ryan", "Yous"]

Coding Challenge from Codewars.com

SOLVED BY KARTHIK APPASWAMY
.*/

using System;
using System.Collections.Generic;
using System.Linq;

namespace CodingChallengeFromCodeWars
{
    class Program
    {
        static void Main()
        {
            string[] inputNames = new string[3] {
                "Ryan", "Kieran", "Mark"
            };
            var output = FriendOrFoe(inputNames);
            foreach (var o in output)
                Console.WriteLine(o);
            Console.ReadLine();
        }
        public static IEnumerable<string> FriendOrFoe(string[] names)
        {
            return names.Where(x=>x.Length==4);
        }
    }
}

