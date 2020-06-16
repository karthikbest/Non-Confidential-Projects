
/*
There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

You may assume nums1 and nums2 cannot be both empty.

Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0

Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5

 
*/


using System;
using System.Collections.Generic;
using System.Linq;

namespace MedianofTwoSortedArrays
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
        }

        public double FindMedianSortedArrays(int[] nums1, int[] nums2)
        {

            int[] combined = new int[nums1.Length + nums2.Length];
            for (int i = 0; i < nums1.Length; i++)
            {
                combined[i] = nums1[i];
            }   

            for (int i = 0; i < nums2.Length; i++)
            {
                combined[nums1.Length - 1 + i] = nums2[i];
            }

            List<int> listCombined = combined.ToList();

            int x, y;

            for (int i = 0; i < combined.Length/2 ; i++)
            {
                x = combined[i];
                y = combined[combined.Length - 1 - i];
                listCombined.RemoveAt(combined.Length - 1 - i);
                listCombined.RemoveAt(i);
            }
        }
    }
