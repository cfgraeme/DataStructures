import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
import java.math.*;


/*
 * Author: Marge Marshall
 * Date: April 26, 2016
 * Class: CSCI 230 02
 * Assignment: HW #05
 * Task: Use a timer to test the real time complexity of three different sorting algorithms: 
 * insertion sort, merge sort, and quick sort.
 * Input: A value (negative to generate a list, positive to input the list by hand) to 
 * determine which algorithm to use, and a value to determine the number of items in the list.
 * Optionally, the list of items itself
 * Output: The sorted list, and how much time it took to run through the sorting algorithm
 * 
 * I certify that this code is entirely my own work, with the exception of the previously designed algorithms.
*/


public class compareSorts {

	/**
	 * Method 1
	 * Insertion Sort
	 * This method employs the insertion sort algorithm, which is O(n^2)
	 * according to notes
	 *
	 * @param a		an array of integers to be sorted
	 */
	public static void insertionSort(int [] a)
	{
		for (int separator = 1; separator < a.length; separator++)
			//anything to the left of the separator is sorted
		{
			int valueToInsert = a[separator]; //holds value as a temp
			int j = separator - 1;
			while(j>=0 && valueToInsert < a[j])
			{
				a[j+1] = a[j]; //shift items to the right
				j--; //points to previously sorted item
			}
			//now j+1 points to the insertion point
			a[j+1] = valueToInsert;
		}
	}
	/**
	 * Method 2
	 * Merge Sort
	 * This method employs the merge sort algorithm, 
	 * which is O(nlogn) according to notes
	 *
	 * @param a		an array of integers to be sorted
	 */
	public static void mergeSort(int [] a)
	{
		int[] temp = new int[a.length]; //create space for temp to be used during merging
		mergeSort(a, temp, 0, a.length-1);
	}
	
	/**
	 * Method 2.1
	 * Merge Sort, private method called by the public Method 2
	 * This method employs the insertion sort algorithm, 
	 * which is O(nlogn) according to notes
	 *
	 * @param a		an array of integers to be sorted
	 * @param temp	a temporary array to store integers during sorting
	 * @param left	leftmost item index
	 * @param right	rightmost item index
	 */
	private static void mergeSort(int [] a, int [] temp, int left, int right)
	{
		if (left<right)
		{
			int center = ( left + right ) / 2;
			mergeSort( a, temp, left, center );
			mergeSort( a, temp, center + 1, right );
			merge( a, temp, left, center + 1, right );
		}
	}
		
	/**
	 * Method 2.2
	 * Merge, a helper method for merge sort as demonstrated by the book
	 * @param a		an array of integers to be sorted
	 * @param temp	a temporary array to store integers during sorting
	 * @param leftPos	left most index
	 * @param rightPos 	index of the beginning of the second half
	 * @param rightEnd 	right most index
	 */
	private static void merge( int [] a, int [] temp, int leftPos, int rightPos, int rightEnd )
	{
	    int leftEnd = rightPos - 1; //last item of the left portion
	    int tmpPos = leftPos;
	    int numElements = rightEnd - leftPos + 1;
	    // Main loop
	    
	    while( leftPos <= leftEnd && rightPos <= rightEnd ) //neither list exhausted
	        if( a[leftPos] <= a[rightPos])  //left item smaller than right
	            temp[tmpPos++] = a[leftPos++]; //copy left item
	        else //right item is smaller
	            temp[tmpPos++] = a[rightPos++];
	    while( leftPos <= leftEnd )    // Copy rest of first half
	        temp[tmpPos++] = a[leftPos++];
	    while( rightPos <= rightEnd )  // Copy rest of right half
	        temp[tmpPos++] = a[rightPos++];
	    // Copy temp back
	    for( int i = 0; i < numElements; i++, rightEnd-- )
	    {
	    	a[rightEnd] = temp[rightEnd]; 
	    }
	}
	
	/**
	 * Method 3
	 * Quick Sort, 
	 * This method employs the quick sort algorithm, 
	 * which is O(nlogn) according to notes
	 *
	 * @param a		an array of integers to be sorted
	 */
	public static void quickSort(int [] a)
	{
		quickSort(a, 0, a.length - 1);
	}
	
	/**
	 * Method 3.1
	 * Quick Sort, private method called by the public Method 3
	 * This method employs the insertion sort algorithm, 
	 * which is O(nlogn) according to notes
	 *
	 * @param a		an array of integers to be sorted
	 * @param left	leftmost item index
	 * @param right	rightmost item index
	 */
	private static void quickSort(int [] a, int left, int right)
	{
		if (left < right)    // more remaining to sort?
			{
	        // get median of 3
            int pivot = getPivot(a, left, right);
                   
            // move pivot out of way
            swap(a, pivot, right);
            pivot = right;   // and update index
            
            // get partitions 
            int i = left;        // move forward from beginning
            int j = right - 1;   // move backward from end (skip pivot)
            
            // create partitions by moving larger items to right,
            // and smaller items to left partition
            while (i < j)
            {
                // keep moving i until we find an item that belongs
                // on the other side
                while (a[i] < a[pivot])
                    i++;
                // now, i points to first item > pivot value
                
                // keep moving j until we find an item that belongs
                // on the other side
                while (a[j] > a[pivot])
                    j--;
                // now, j points to first item < pivot value
                
                if (i < j)   // haven't crossed yet?
                {
                    swap(a, i, j);    
                    i++;   
                    j--;   
                }
            }
            // now, the two partitions have been created,
            // so, put pivot back in the middle
            
            swap(a, i, pivot);
            pivot = i;   // and update index
            
            // partitions are ready, so sort them
            quickSort(a, left, pivot-1);
            quickSort(a, pivot+1, right);
            
        } 
    }
	
	/**
	 * Method 4
	 * a helper method to find the pivot for quicksort method
	 * Finds the median of 3 random selections
	 *
	 * @param a		an array of integers to be sorted
	 * @param left	leftmost item index
	 * @param right	rightmost item index
	 */
	private static int getPivot(int [] a, int left, int right)
	{
		int center = (left + right)/2;
		if (a[center] < a[left])
		{
			swap(a, left, center);
		}
		if (a[right] < a[left])
		{
			swap(a, left, right);
		}
		if (a[right] < a[center])
		{
			swap(a, center, right);
		}
		return center;
	}
	
	/**
	 * Method 5
	 * Swap, a helper method for quick sort, which swaps the values
	 * at two given indices.
	 *
	 * @param a		array where items are being swapped
	 * @param i index of the first item to be swapped
	 * @param j index of the second item to be swapped
	 */
	private static void swap(int[] a, int i, int j)
	{
		int k = a[i]; //item at index i, to be stored at index j later
		a[i] = a[j]; //item at index i now at index j
		a[j] = k; //item at index j now at index i
	}
	
	/**
	 * Main driver method
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int methodNum = in.nextInt(); //gets method number
		int absmethodNum = Math.abs(methodNum); //method number's absolute value
		int arrayLength = in.nextInt(); //gets length of test array
		int[] testArray = new int[arrayLength]; //creates an empty array
		String method; //contains the name of the method being used
		
		if (absmethodNum == 0 || absmethodNum > 3) //error checking
		{
			System.out.println("Invalid method number");
		}
		else
		{
			if (methodNum < 0) //array values should be generated
			{
				Random rnd = new Random();
				for (int i = 0; i < arrayLength; i++)
				{
					testArray[i] = rnd.nextInt(1001); //integers from 0 - 1000, inclusive
				}
				//testArray populated with random integers
			}
			else //array values will be given as input
			{
				for (int i = 0; i < arrayLength; i++)
				{
					testArray[i] = in.nextInt();
				}
			}
			//testArray is now populated
			in.close();
			
			for (int j = 0; j < arrayLength; j++)
			{
				System.out.print(testArray[j] + " ");
			}
			System.out.println();
			double runtimeInMilliseconds;
			
			if (absmethodNum == 1) //method should be insertionSort
			{
				method = "insertionsort";
				
				long startTime = System.nanoTime();   // start timing

				insertionSort(testArray);

				long endTime = System.nanoTime();    // end timing
				runtimeInMilliseconds = (double)(endTime - startTime) / 1000000.0;
			
			}
			else if (absmethodNum == 2) //method should be mergesort
			{
				method = "mergesort";
				
				long startTime = System.nanoTime();   // start timing

				mergeSort(testArray);

				long endTime = System.nanoTime();    // end timing
				runtimeInMilliseconds = (double)(endTime - startTime) / 1000000.0;
			}
			else //absmethodNum is 3, or quicksort
			{
				method = "quicksort";
				
				long startTime = System.nanoTime();   // start timing

				quickSort(testArray);

				long endTime = System.nanoTime();    // end timing
				runtimeInMilliseconds = (double)(endTime - startTime) / 1000000.0;
			}
			
			for (int j = 0; j < arrayLength; j++)
			{
				System.out.print(testArray[j] + " ");
			}
			System.out.println();
			System.out.println("Sorting by " + method + " took " + runtimeInMilliseconds +" milliseconds...");
		}
	}
	  
}
