import java.io.*;
import java.util.Queue;
import java.util.LinkedList;

public class Word implements Comparable<Word>
{
	private int count;
	private Queue<Integer> lineNumbers;
	private String wordText;
	
	public Word(String inWord, Integer lineNumber)
	{
		this.wordText = inWord;
		this.count = 1;
		lineNumbers = new LinkedList<Integer>();
		lineNumbers.add(lineNumber);
	}
	
	public String getWord()
	{
		return wordText;
	}
	
	public void CountWord(Integer lineNumber)
	{
		count++;
		if(!lineNumbers.contains(lineNumber))
			lineNumbers.add(lineNumber);
	}
	
	public int getCount()
	{
		return count;
	}
	
	public String getStringCount()
	{
		Integer i = count;
		
		return i.toString();
	}
	
	// used for binary serach tree comparison 
	public int compareTo(Word x)
	{
		return this.getWord().compareTo(x.getWord());
	}
	
	// writing to file for each word
	public void output(PrintWriter outfile)
	{
		int numDots = 24;
		numDots = numDots - (this.getWord().length() + this.getStringCount().length());
		
		outfile.print(this.getWord());

		for(int i = 0; i < numDots; i++)
		{
			outfile.print(".");
		}
		outfile.print(this.getStringCount() + ": ");

		
		int size = lineNumbers.size();
		for(int i = 0; i < size; i++)
		{
			outfile.print(lineNumbers.poll().toString() + " ");
		}
		outfile.println("");
	}
	
	public void filterOutput(PrintWriter outfile)
	{
		outfile.print(this.getWord());
		outfile.println(""); // just printing the words
	}
}
