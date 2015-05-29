package satPack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException{
		
		Clause c;
		int maxFlips = 10000000; // standard max flips setting
		int numVars; // number of maximum unique variables
		int numClauses; // number of clauses in the cnf
		int randomSeed; // seed used to generate the cnf
		double p = 0.5; // probability to randomly assign a flip
		
		String workingDirectory = System.getProperty("user.dir");
        File tempFile = new File(workingDirectory + File.separator + "test.txt");
		
		Scanner sc = new Scanner(tempFile);
		List<Clause> clauses = new ArrayList<Clause>();
		String line = sc.nextLine();		
		
		// Collect the 3SAT stats
		String[] config = line.split(" ");
		numVars = Integer.parseInt(config[3]);
		numClauses = Integer.parseInt(config[6]);
		randomSeed = Integer.parseInt(config[9]);
		
		// Collect all clauses from the 3SAT
		while(sc.hasNextLine()){			
			line = sc.nextLine();		
			if (line.equals("-1")) { break; }
			String[] symbols = line.split(" ");
			c = new Clause(Integer.parseInt(symbols[0]), Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
			//c.printClause();				
			clauses.add(c);
		}			
		sc.close();
		
		System.out.println("Starting Search");
		
		DPLLSAT dp = new DPLLSAT();
		if (dp.DPLLSATISFIABLE(clauses)) System.out.println("SUCCESS");
		else System.out.println("FAILURE");
	}	
}
