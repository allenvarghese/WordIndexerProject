// Allen Varghese
// UIN: 225009553

import java.util.Scanner;

class Driver
{
        // Test program
    public static void main( String [ ] args )
    {
    	Scanner sc = new Scanner(System.in);
    	//String filterfile = args[0];
    	//String datafile = args[1];
    	String filterfile = sc.next();
    	String datafile = sc.next();
    	sc.close();
    
    	
        Indexer i = new Indexer(filterfile, datafile);
        i.DoIndex();
    }
}