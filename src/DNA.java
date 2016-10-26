import java.util.Scanner;



/*
 * Author: Marge Marshall
 * Date: 03/01/2016
 * Class: CSCI 230 02
 * Assignment: HW #03
 * Task: Design an abstract data type that models the DNA helix, allowing 
 * 		for insertion and removal of base pairs, insertion and removal of a sequence, 
 * 		printing a given range of base pairs, printing the left or right helix, 
 * 		clearing the structure, counting the number of base pairs in the structure
 *		and determining whether the structure is empty or not.  
 * Input: the number of lines/methods that will be called, each method and its 
 * 		input (indexes, sequences of nucleotides, helix index)
 * Output: 
 * 
 * I certify that this code is entirely my own work.
*/


public class DNA {
	
	public Nucleotide leftHelix;
	public Nucleotide rightHelix;
	public int numberOfNucleotides;
	
	
	public DNA()
	{
		numberOfNucleotides = 0;
	}
	
	public class Nucleotide
	{
		public Character base; //represents the chemical base for the nucleotide represented
		public Nucleotide next; //nucleotide that follows this one on the same helix
		public Nucleotide across; //nucleotide that is connected to this one on the opposite helix
		public Nucleotide(){}
	}
	
	
	/**
	 * Method Number 1
	 * This method is used to insert a base pair of nucleotides into the helix at a given index
	 *
	 * @param index	where in the list we are adding the basePair
	 * @param basePair a string of the two characters for the left and right nucleotide bases
	 */
	public void insert(int index, String basePair)
	{
		if (index < 0 || index > numberOfNucleotides)
			throw new IndexOutOfBoundsException("Invalid index");
		
		else
		{
			Character leftBase = basePair.charAt(0); //character to be inserted in left helix
			Character rightBase = basePair.charAt(1); //character to be inserted in right helix
			
			Nucleotide leftNew = new Nucleotide(); 
			Nucleotide rightNew = new Nucleotide();
			leftNew.base = leftBase;
			rightNew.base = rightBase;
			leftNew.across = rightNew;
			rightNew.across = leftNew;
			//the nucleotides have been created with the characters specified as their base and coupled
			
			if (numberOfNucleotides == 0) //first sequence to be added
			{
				this.leftHelix = leftNew;
				this.rightHelix = rightNew;
				//sets pointer to new nucleotides
			}
			else if (index == 0)
			{
				leftNew.next = this.leftHelix;
				rightNew.next = this.rightHelix;
				this.leftHelix = leftNew;
				this.rightHelix = rightNew;
			}
			else
			{
				Nucleotide leftPoint = this.leftHelix;
				Nucleotide rightPoint = this.rightHelix;
				//initializes a pointer to point to the nucleotide preceding the point of insertion
			
				for (int i = 0; i<index-1; i++)
				{
					leftPoint = leftPoint.next;
					rightPoint = rightPoint.next;
				}
				if (index < numberOfNucleotides) //the new pair is not last
				{
					leftNew.next = leftPoint.next;
					rightNew.next = rightPoint.next;
					//sets nucleotides currently at index of insertion as next after new nucleotides being inserted
				}
				
				leftPoint.next = leftNew;
				rightPoint.next = rightNew;
				//inserts nucleotides at the index
			
			}
			
						
			numberOfNucleotides++;
			//increment the count of Nucleotides
		}
	}
	
	/**
	 * Method number 2
	 * This method removes a base pair on the helix, given a particular index.
	 * @param	index the index of the base pair to be removed
	 * @return 	a string representing the two nucleotides removed from the left and right helixes
	 */
	public String remove(int index)
	{
		String result = "";
		if (index < 0 || index >= numberOfNucleotides)
			throw new IndexOutOfBoundsException("Invalid index");
		
		else
		{
			
			Nucleotide leftPoint = this.leftHelix;
			Nucleotide rightPoint = this.rightHelix;
			
			//initializes a pointer that will point to the nucleotide preceding the point of removal
			
			Nucleotide leftRemoved;
			Nucleotide rightRemoved;
			//pointers to store the removed nucleotide for deconstruction
			
			if(index == 0)
			{
				leftRemoved = this.leftHelix;
				rightRemoved = this.rightHelix;
				//removed nucleotides will be at the beginning
				
				this.leftHelix = this.leftHelix.next;
				this.rightHelix = this.rightHelix.next;
				//start of list is set to nucleotides after removed pair
			}
			else
			{
				for (int i = 0; i<index - 1; i++)
				{
					leftPoint = leftPoint.next;
					rightPoint = rightPoint.next;
				}
				//now we are at the index of the nucleotide directly before the point of removal
				
				leftRemoved = leftPoint.next;
				rightRemoved = rightPoint.next;
				//store each nucleotide for deconstruction
				
				leftPoint.next = leftRemoved.next;
				rightPoint.next = rightRemoved.next;
				//the removed nucleotide is now skipped
			}
				
			result = Character.toString(leftRemoved.base) + Character.toString(rightRemoved.base);
			//each character has been added to the string
				
			leftPoint.next = leftRemoved.next;
			rightPoint.next = rightRemoved.next;
			//removed points are no longer referred to in the helix sequence
				
			leftRemoved.next = null;
			rightRemoved.next = null;
			leftRemoved.across = null;
			rightRemoved.across = null;
			//removed points no longer reference anything in helix sequence, nor do they reference each other
			
			numberOfNucleotides = numberOfNucleotides - 1;
			//de-increment the count of Nucleotides
			
		}
		
		return result;
	}
	
	/**
	 * Method number 3
	 * outputs the sequence of nucleotide base pairs for a given range
	 * @param startIndex	index of the first base pair in the list
	 * @param endIndex		index of the end of the list; base pair at this index not included in the list
	 */
	public void print(int startIndex, int endIndex)
	{
		String result = "";
		if (startIndex < 0 || startIndex >= numberOfNucleotides ||
			endIndex < 0 || endIndex > numberOfNucleotides ||	
			startIndex > endIndex)
			throw new IndexOutOfBoundsException("Invalid index range");
		
		else
		{
			
			Nucleotide leftPoint = leftHelix;
			Nucleotide rightPoint = rightHelix;
			//initializes a pointer that points to the start of the DNA helix
			if (startIndex != 0)
			{
				for (int i = 0; i < startIndex; i++)
				{
					leftPoint = leftPoint.next;
					rightPoint = rightPoint.next;
				}
				//now we are at the index of the nucleotide at the start index
			}
			for (int i = startIndex; i<endIndex; i++)
			{
				result = result + Character.toString(leftPoint.base) + Character.toString(rightPoint.base);
				//each character is added to the string
				leftPoint = leftPoint.next;
				rightPoint = rightPoint.next;
				//the pointers are advanced to the next on the helix
			}
		}
		System.out.println(result);
	}
	
	/**
	 * Method number 4
	 * clears the dna structure, resetting it to an empty sequence for each helix 
	 */
	public void clear()
	{	
		Nucleotide leftPoint = leftHelix;
		Nucleotide rightPoint = rightHelix;
		//initializes a pointer that points to the start of the DNA helix
		
		for (int i = 0; i < numberOfNucleotides; i++)
		{
			Nucleotide leftTemp = leftPoint;
			Nucleotide rightTemp = rightPoint;
			//stores the current nucleotide for later alteration
			
			leftPoint.across = null;
			rightPoint.across = null;
			//removes pointers for easy trash collection
			
			leftPoint = leftPoint.next;
			rightPoint = rightPoint.next;
			//advances to the next nucleotide
			
			leftTemp.next = null;
			rightTemp.next = null;
			//removes pointers for easy trash collection	
		}
		//now all nucleotides are separated
		
		numberOfNucleotides = 0;
		//count for the number of nucleotides is reset
	}
	
	/**
	 * Method number 5
	 * this method tells whether the DNA structure is empty or not 
	 * @return	whether the sequence is empty -- true if the sequence is empty, false otherwise
	 */
	public Boolean isEmpty()
	{
		//numberOfNucleotides will be 0 if the structure is empty
		return (numberOfNucleotides == 0);
	}
	
	/**
	 * Method number 6
	 * this method returns the length of the DNA helix, as measured by the total number of base pairs 
	 * @return 	total number of base pairs in the DNA sequence
	 */
	public int getLength()
	{
		//numberOfElements contains the current length of the dna strand
		return numberOfNucleotides;
	}
	
	/**
	 * Method number 7
	 * This method finds the first instance of a given base pair in the DNA sequence
	 * @param basePair	a string of the two characters that represent the base pair being searched for
	 * @return the index of the base pair
	 */
	public int find(String basePair)
	{
		Character first = basePair.charAt(0);
		Character second = basePair.charAt(1);
		//the string can now be used to match the characters to the nucleotide bases
		
		Boolean found = false;
		//the pair has not been found
		
		Nucleotide leftPoint = leftHelix;
		//initialized to first nucleotide on left helix
		
		int result = 0;
		//initialized to keep track of the current index
		
		while (result < numberOfNucleotides && !found)
		{
			if (leftPoint.base.equals(first) && leftPoint.across.base.equals(second))
				//check if the base pair is present in left-right order
			{
				found = true;
			}
			else
			{
				leftPoint = leftPoint.next;
				result++;
			}
		}
		//now the list has been fully searched, or a match was found
		if (!found)
		{
			result = -1;
			//sets result to -1 if a match was never found
		}
		return result;
	}
	
	/**
	 * Method number 8
	 * prints the complete sequence of nucleotides from the left helix
	 */
	public void printLeft()
	{
		
		Nucleotide leftPoint = leftHelix;
		//initializes a pointer that points to the start of the DNA helix
		String result = "";
		if (numberOfNucleotides > 0)
		{
		
			for (int i = 0; i < numberOfNucleotides; i++)
			{
				String currentNuc = String.valueOf(leftPoint.base);
				result = result + currentNuc;
				leftPoint = leftPoint.next;
			}
			//now we have a string of all nucleotides on the left helix
		}
			System.out.println(result);
	}
	
	/**
	 * Method number 9
	 * prints the complete sequence of nucleotides from the left helix
	 */
	public void printRight()
	{
		String result = "";
		Nucleotide rightPoint = rightHelix;
		//initializes a pointer that points to the start of the DNA helix
		
		for (int i = 0; i < numberOfNucleotides; i++)
		{
			result = result + Character.toString(rightPoint.base);
			rightPoint = rightPoint.next;
		}
		//now we have a string of all nucleotides on the right helix
		
		System.out.println(result);
	}
	
	/**
	 * Method number 10
	 * prints a base pair given an index and a starting helix
	 * @param index	the index of the base pair in the helix
	 * @param helix which helix to traverse to find the base pair (0 for left, 1 for right) 
	 */
	public void printBasePair(int index, int helix)
	{
		String result = "";
		if (index < 0 || index >= numberOfNucleotides ||
			helix < 0 || helix > 1)
			throw new IndexOutOfBoundsException("Invalid index");
		
		else
		{
			Nucleotide startPoint;
			if (helix == 0)
			{
				startPoint = leftHelix;
				//sets the helix to be traversed to the left side
			}
			else //helix is 1 if not 0, according to error checking
			{
				startPoint = rightHelix;
				//sets the helix to be traversed to the right side
			}
			
			for(int i = 0; i < index; i++)
			{
				startPoint = startPoint.next;
			}
			
			result = Character.toString(startPoint.base) + Character.toString(startPoint.across.base);
			
		}
		System.out.println(result);
		
	}
	
	/**
	 * Method number 11 BONUS
	 * inserts a sequence of nucleotides at a given index
	 * @param index the first spot of insertion
	 * @param sequence the sequence to be added
	 */
	public void insertSequence(int index, String sequence)
	{
		if (index < 0 || index > numberOfNucleotides)
			throw new IndexOutOfBoundsException("Invalid Index");
		else if (sequence.length()%2 == 1) //not an even number of nucleotides, one is unpaired
			throw new IllegalArgumentException("Uneven count of nucleotides");
		else
		{
			int helixIndex = index;//index of the point of insertion on the helix
			int strI = 0; //index of the current base pair
			while (strI < sequence.length())
			{
				this.insert(helixIndex, sequence.substring(strI, strI + 2)); //adds pair to the helix
				strI = strI + 2; //increments to the next pair in the sequence
				helixIndex++; //increments to the next index in the helix
			}
			//now the sequence is added to the helix
		}			
	}
	
	/**
	 * Main driver method
	 */
	public static void main(String[] args)
	{
		DNA tester = new DNA(); //creates a new DNA structure for use with testing the methods
		Scanner in = new Scanner(System.in); //reading the input
		int currentLine = 0; //a counter for the number of lines processed so far
		int numberOfLines = in.nextInt(); //total number of lines to be processed
		while (currentLine < numberOfLines)
		{
			currentLine++;
			//advance to the next line of input
			int methodNumber = in.nextInt();
			
			
			if (methodNumber == 1)
			{
				int ind = in.nextInt(); //index of insertion from input
				String baseP = in.next(); //the base pair to be inserted, from input
				tester.insert(ind, baseP);
				
			}
			else if (methodNumber == 2)
			{
				int ind = in.nextInt(); 
				System.out.println(tester.remove(ind));
			}
			else if (methodNumber == 3)
			{
				int startI = in.nextInt();
				int endI = in.nextInt();
				tester.print(startI,  endI);
			}
			else if (methodNumber == 4)
			{
				tester.clear();
			}
			else if (methodNumber == 5)
			{
				System.out.println(tester.isEmpty());
			}
			else if (methodNumber == 6)
			{
				System.out.println(tester.getLength());
			}
			else if (methodNumber == 7)
			{
				String searchable = in.next();
				System.out.println(tester.find(searchable));
				
			}
			else if (methodNumber == 8)
			{
				tester.printLeft();
			}
			else if (methodNumber == 9)
			{
				tester.printRight();
			}
			else if (methodNumber == 10)
			{
				int ind = in.nextInt();
				int hel = in.nextInt();
				tester.printBasePair(ind, hel);
			}
			else if (methodNumber == 11)
			{
				int ind = in.nextInt();
				String seq = in.next();
				tester.insertSequence(ind, seq);
			}
		}
		
		
	}
	
		
}
