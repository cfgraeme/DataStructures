import java.util.Scanner;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Author: Marge Marshall
 * Date: Feb 9, 2016
 * Class: CSCI 230 02
 * Assignment: HW #2
 * Task: Count the number of districts in a 2D array, as marked by any contiguous
 * grouping of asterisks; two asterisks are contiguous if they are directly above or 
 * beside one another. Do this with one recursive and one iterative method (iterative
 * method is optional) 
 * Input: a number identifying which method to use, the dimensions of a 2D array, and
 * the index of asterisks in the array
 * Output: The number of districts in the 'county'(2D array)
 * 
 * I certify that this code is entirely my own work.
*/

public class Gerrymander{
	
	public Gerrymander()
	{
	}
	
	/**
	 * Takes a 2D array populated with asterisks and returns an arraylist of points
	 * containing row and column index of each asterisk.  This is a helper method
	 *
	 * @param	matrix a 2D array populated with asterisks
	 * @return an ArrayList of Points corresponding to locations of asterisks
	 */
	static ArrayList<Point> asteriskFinder(String[][] matrix)
	{
		ArrayList<Point> result = new ArrayList<Point>(); //initialize list
		for (int i = 0; i < matrix.length; i++)
		{
			for(int j = 0; j< matrix[0].length; j++)
			{
				if (matrix[i][j].equals("*"))
				{
					result.add(new Point(i, j));
				}
			}
		} //result now contains points corresponding to asterisk locations
		return result;
	}
	
	/**
	 *A recursive method to count the number of districts represented by contiguous asterisks in a 2d array
	 *
	 * @param	locations, a list of points containing asterisks within a 2D array
	 * @return the total number of districts found
	 */
	static int countDistrictsRecursive(ArrayList<Point> locations, ArrayList<Point> branches)
	{
		int result;
		if (locations.size() == 0) //base case #1 - no asterisks
		{
			result = 0; //there are no districts
		}
		else if (locations.size() == 1) //base case #2 - one asterisk
		{
			result = 1; //there is one district
		}
		else
		{
			if (branches.size() == 0) //branched paths of contiguous asterisks have not been initiated, or have been resolved
			{
				ArrayList<Point> newBranches = new ArrayList<Point>(); //an arraylist of contiguous asterisks
				Point current = locations.get(0); //grab the first item in the list
				int x = (int)current.getX(); //hold onto the x coordinate for later comparison
				int y = (int)current.getY(); //hold onto the y cooordinate for later comparison
				locations.remove(current); //item has been accounted for
				for (int i = 0; i < locations.size(); i++)
				{
					Point current2 = locations.get(i); //second point for comparison
					int x2 = (int)current2.getX(); //second x coordinate for comparison
					int y2 = (int)current2.getY(); //second y coordinate for comparison
					if (x == x2+1 || x == x2-1) //contiguous coordinates
					{
						if (y == y2)
						{
							newBranches.add(current2); //becomes a branch
							locations.remove(current2); //item has been accounted for
						}
					}
					else if (y == y2+1 || y == y2-1) //contiguous coordinates
					{
						if(x == x2)
						{
							newBranches.add(current2); //becomes a branch
							locations.remove(current2); //item has been accounted for
						}
					}
				} //now newBranches contains points contiguous to initial point,
				  //and locations contains those not yet accounted for by a district
				if (newBranches.size() == 0) //contiguous asterisks not found
				{
					result = 1 + countDistrictsRecursive(locations, newBranches);
				}
				else //contiguous asterisks were found, district cannot be incremented yet
				{
					result = countDistrictsRecursive(locations, newBranches);
				}
			}
			else //unresolved branches currently exist
			{
				ArrayList<Point> newBranches = new ArrayList<Point>();
				for (int j = 0; j < branches.size(); j++)
				{
					Point currentBranch = branches.get(j); //grab the next item in the branch list
					int x = (int)currentBranch.getX(); //hold onto the x coordinate for later comparison
					int y = (int)currentBranch.getY(); //hold onto the y cooordinate for later comparison
					branches.remove(currentBranch); //item has been accounted for
					for (int i = 0; i < locations.size(); i++)
					{
						Point current2 = locations.get(i); //second point for comparison
						int x2 = (int)current2.getX(); //second x coordinate for comparison
						int y2 = (int)current2.getY(); //second y coordinate for comparison
						if (x == x2+1 || x == x2-1) //contiguous coordinates
						{
							if (y == y2)
							{
								newBranches.add(current2); //becomes a branch
								locations.remove(current2); //item has been accounted for
							}
						}	
						else if (y == y2+1 || y == y2-1) //contiguous coordinates
						{
							if (x == x2)
							{
								newBranches.add(current2); //becomes a branch
								locations.remove(current2); //item has been accounted for
							}
						}
					} 
				}//now branches has been updated to branch outward from the previous branch points
				if(newBranches.size() == 0) //all branches have been resolved
				{
					result = 1 + countDistrictsRecursive(locations, newBranches);
				}
				else //contiguous asterisks were found, district cannot be incremented yet
				{
					result = countDistrictsRecursive(locations, newBranches);
				}
			}
		}
		return result;
	}
	
	/**
	 * Main driver method
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int methodNumber = in.nextInt(); //which method to call
		int row = in.nextInt(); //number of rows in array
		int col = in.nextInt(); //number of columns in array
		String[][] county = new String[row][col]; //creates array with specified dimensions
		for(int i = 0; i < row; i++)
		{
			for (int j = 0; j < col; j++)
			{
				county[i][j] = "";
			}
		}
		int ast = in.nextInt(); //index of first asterisk in array
		while (ast != -1 && row > 0 && col > 0) //continues to get asterisk indexes until exited
		{
			int currentcol = ast%col; //row index of asterisk
			int currentrow = ast/col; //column index of asterisk
			county[currentrow][currentcol] = "*";
			ast = in.nextInt(); //gets next asterisk index
		} //district is now populated with asterisks
		
		ArrayList<Point> asts = asteriskFinder(county); //converts to input for recursive method
		ArrayList<Point> branchInit = new ArrayList<Point>(); //no branches when initialized
		
		if (methodNumber == 1)
		{
			int districtsFound = countDistrictsRecursive(asts, branchInit); //districts found is counted
			
			System.out.println(districtsFound);
		}
		
	}
	
		
}
