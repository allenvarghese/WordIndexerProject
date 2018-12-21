import java.io.*;
import java.util.*;


public class Indexer 
{
	private BinarySearchTree<Word> filteredBST;
	private BinarySearchTree<Word> indexedBST;
	private String datafile;
	private String filterfile;
	private PrintWriter outFile;
	private PrintWriter filterFile;
	
	
	public Indexer(String filterName, String dataFile)
	{
		this.filterfile = filterName;
		this.datafile = dataFile;
		
		PrintWriter outfile = null;
		try
		{
			outfile = new PrintWriter("results.txt");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}
		
		outFile = outfile;
		
		PrintWriter filterfile = null;
		try
		{
			filterfile = new PrintWriter("filterResults.txt");
		}
		catch (FileNotFoundException e)
		{
		System.out.println("File not found");
		e.printStackTrace(); // prints error(s)
		System.exit(0); // Exits entire program
		}
		
		filterFile = filterfile;
	}
	
	
	public void DoIndex() 
	/*
	 *A method DoIndex() that will build the BSTs and show the proper output. 
		Build the filteredBST. Build the indexedBST. Output the results.
	 */
	{
		this.filteredBST = FileFilterReader(filterfile);
		this.indexedBST = FileWordReader(datafile);
		this.OutputResults();
	}
	
	public boolean FileExists(String filename)
	{
		File f = new File(filename);
		if(f.exists())
			return true;
		else
			return false;
	}
	
	public BinarySearchTree<Word> FileFilterReader(String filename)
	{
		BinarySearchTree<Word> filterBST = new BinarySearchTree<Word>();
		
		if(!this.FileExists(filename)) // checking if file is there
			System.out.println("Please enter a correct file name!");
		
		Scanner infile = null;
		try
		{
			
			int lineNumber = 0; // line number
			infile = new Scanner(new FileReader(filename)); // reading the file
			
			while(infile.hasNextLine())
			{
				lineNumber++; // incrementing each line
				String line = infile.nextLine();
				StringTokenizer tokenizer = new StringTokenizer(line);
				
				while(tokenizer.hasMoreTokens())
				{
					String word = tokenizer.nextToken();
					word.toLowerCase(); // lowercase
					
					Word filterWord = new Word(word, lineNumber);
					filterBST.insert(filterWord);
				}
			}
			
			infile.close();

		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}

		return filterBST;
	}
	
	public BinarySearchTree<Word> FileWordReader(String filename)
	{
		BinarySearchTree<Word> indexBST = new BinarySearchTree<Word>();
		
		if(!this.FileExists(filename)) // checking if file is there
			System.out.println("Please enter a correct file name!");
		
		Scanner infile = null;
		try
		{
			Integer lineNumber = 0; // line number
			infile = new Scanner(new FileReader(filename)); // reading the file
			
			while(infile.hasNextLine())
			{
				lineNumber++; // incrementing each line
				String line = infile.nextLine();
				
				line = line.replaceAll("(\\p{Space}|^)\\p{Punct}+", " ");
				line = line.toLowerCase().replaceAll("[\\W&&[^']&&[^-]]", " ");
				line = line.replaceAll("\\d", " ");
				
				StringTokenizer tokenizer = new StringTokenizer(line);
				
				while(tokenizer.hasMoreTokens()) // there are more words left in the line
				{
					String word = tokenizer.nextToken(); // get the next word
					
					word = word.replaceAll("\\W\\p{Punct}", "");
					word = word.replaceAll("'$", "");
					
					word = word.replaceAll("^-", "");
					word = word.replaceAll("-$", "");
					
					Word indexWord = new Word(word, lineNumber); // new word object
					
					// if the word is not already in the binary search tree and isn't a filtered word
					if(!indexBST.contains(indexWord) && !filteredBST.contains(indexWord)) 
					{
						indexBST.insert(indexWord); // insert the word into the BST
					}
					else if(!filteredBST.contains(indexWord))
					{
						// call the count word function for the node in the BST
						indexBST.get(indexWord).element.CountWord(lineNumber); 
					}
				}
			}
			
			infile.close();

		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program
		}

		return indexBST;
	}
	
	public void OutputResults()
	{
		LinkedList<Word> filterlist  = filteredBST.convertTree();
		for(int i = 1; i < filterlist.size(); i++)
		{
			filterlist.get(i).filterOutput(filterFile);
		}
		filterFile.close();
		
		
		LinkedList<Word> list = indexedBST.convertTree(); // converting tree to flattened list
		if(list.get(0).getWord().equals(""))
			list.remove(list.get(0));
		
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).output(outFile); // O(n)
		}
		outFile.close();
		System.out.println("Done");
	}
}
