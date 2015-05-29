package satPack;

import java.util.Random;

public class Clause {

	public int[] clause;	// the clause values
	boolean tVal;	// the truth value of the clause
	final int SIZE = 3; // our SAT size in this case we are only using 3SAT
	Random randomGenerator = new Random();	// Random number generator to get values at random from clause

	public Clause(int a, int b, int c){		
		clause = new int[SIZE];
		clause[0] = a;
		clause[1] = b;
		clause[2] = c;
		calcTruth();
	}

	private void calcTruth(){		
		tVal = false;

		for (int i : clause){
			if (i > 0) tVal = true;
		}		
	}

	public boolean evaluate(int[] model){		

		int a = 0;
		int b = 0;
		int c = 0;

		for (int i : model){
			if (i == clause[0] || i == -clause[0]) { 
				if (clause[0] < 0)
					a = -i;
				else
					a = i;
			}
			if (i == clause[1] || i == -clause[1]) {
				if (clause[1] < 0)
					b = -i;
				else
					b = i; }
			if (i == clause[2] || i == -clause[2]) { 
				if (clause[2] < 0)
					c = -i;
				else
					c = i; }
		}

		if (a > 0 || b > 0 || c > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isFalse(int[] model){		

		int a = 0;
		int b = 0;
		int c = 0;

		for (int i : model){
			if (i == clause[0] || i == -clause[0]) { 
				if (clause[0] < 0)
					a = -i;
				else
					a = i;
			}
			if (i == clause[1] || i == -clause[1]) {
				if (clause[1] < 0)
					b = -i;
				else
					b = i; }
			if (i == clause[2] || i == -clause[2]) { 
				if (clause[2] < 0)
					c = -i;
				else
					c = i; }
		}

		if (a < 0 && b < 0 && c < 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public int getRandom(){
		int index = randomGenerator.nextInt(SIZE);
		int symbol = clause[index];
		return symbol;
	}

	public void printClause(){
		System.out.println("Clause: " + clause[0] + " v " + clause[1] + " v " + clause[2] + " = " + tVal);
	}
}
