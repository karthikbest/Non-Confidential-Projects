using System;

namespace AddIfToString
{
	//  Write a C# Sharp program to create a new string where 'if' is added to the front of a given string. If the string already begins with 'if', return the string unchanged.

	class Program
	{
		public static void Main()
		{
			string input = Console.ReadLine();
			char[] inputArray = input.ToCharArray();
			if ((char.ToUpperInvariant(inputArray[0]).ToString() == "I") && char.ToUpperInvariant(inputArray[1]).ToString() == "F")
			{
				Console.WriteLine(input);

			}
			else
			{
				addToString(inputArray);
			}
		}


		public static void addToString(char[] inputArray)
		{
			char[] outputArray = new char[inputArray.Length + 3];
			for (int i = 0; i < outputArray.Length; i++)
			{
				if (i == 0)
				{
					outputArray[0] = 'I';
					continue;
				}
				if (i == 1)
				{
					outputArray[1] = 'F';
					continue;
				}

				if (i == 2)
				{
					outputArray[2] = ' ';
					continue;
				}

				outputArray[i] = inputArray[i - 3];


			}
			Console.WriteLine(outputArray);
		}
	}


}
