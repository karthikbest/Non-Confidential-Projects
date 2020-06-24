/*
Hey awesome programmer!

You've got much data to manage and of course you use zero-based and non-negative ID's to make each data item unique!

Therefore you need a method, which returns the smallest unused ID for your next new data item...

Note: The given array of used IDs may be unsorted. For test reasons there may be duplicate IDs, but you don't have to find or remove them!

Go on and code some pure awesomeness!

  [TestCase(new int[]{0,1,2,3,5}, ExpectedResult=4)]
  [TestCase(new int[]{0,1,2,3,4,5,6,7,8,9,10}, ExpectedResult=11)]
  [TestCase(new int[]{9,9,8}, ExpectedResult=0)]

  Coding Challenge from codewars.com
  Solved by Karthik Appaswamy

 */

using System;

namespace smallestUnusedId
{
    class Program
    {
        static void Main()
        {
            int result = NextId(new int[] { 0, 1, 2, 3, 5 });
            Console.WriteLine(result);
            Console.ReadLine();
    
        }

        public static int NextId(int[] ids)
        {
            Array.Sort(ids);
            int i;
            
            for( i=0; i < ids[ids.Length-1]; i++)
            {
                foreach(int id in ids)
                {
                    if (i==id)
                    {
                       
                        break;
                    }

                    if (i < id)
                        return i;
                }
           
            }
            return i + 1;
        }
    }
}
