package satPack;

import java.util.Arrays;
import java.util.List;

public class DPLLSAT {

	public boolean DPLLSATISFIABLE(List<Clause> clauses){

		int[] symbols = new int[100];

		for (int i = 0; i < 100; symbols[i] = i + 1, i++);

		// Create the list of unique symbols with truth values set to null
		int[] model = new int[100];

		return DPLL(clauses, symbols, model);
	}	

	private boolean DPLL(List<Clause> clauses, int[] symbols, int[] model) {

		Symbol P;

		if (Satisfiable(clauses, model)) {
			System.out.println(Arrays.toString(model));
			return true;
		}
		if (Unsatisfiable(clauses, model)) return false;
		P = findPureSymbol(clauses, symbols, model);
		if (P != null) {	
			if (P.tVal == true) model[Math.abs(P.sVal) - 1] = P.sVal;
			else model[Math.abs(P.sVal) - 1] = P.sVal * -1;			
			symbols[Math.abs(P.sVal) - 1] = 0;
			return DPLL(clauses, symbols, model);
		}
		P = findUnitClause(clauses, model);
		if (P != null) {
			model[Math.abs(P.sVal) - 1] = Math.abs(P.sVal);				
			symbols[Math.abs(P.sVal) - 1] = 0;
			return DPLL(clauses, symbols, model);
		}

		for (int i : symbols) {
			if (i != 0) {
				P = new Symbol(i, true);
				symbols[i - 1] = 0;
				break;
			}
		}
		
		if (P == null) { 
			return false; }

		int[] model2 = new int[100];
		model2 = Arrays.copyOf(model, 100);


		model2[P.sVal - 1] = P.sVal * -1;
		model[P.sVal - 1] = P.sVal;

		
		return (DPLL(clauses, symbols, model) || DPLL(clauses, symbols, model2));
	}

	private boolean Satisfiable(List<Clause> clauses, int[] model){

		for (Clause c : clauses){
			if (!c.evaluate(model)) return false;
		}

		return true;
	}

	private boolean Unsatisfiable(List<Clause> clauses, int[] model){

		for (Clause c : clauses){
			if (c.isFalse(model)) return true;
		}		
		return false;
	}

	private Symbol findPureSymbol(List<Clause> clauses, int[] symbols, int[] model){

		boolean[][] sym = new boolean[symbols.length][2];

		// intailize every symbol to false false
		// first false is whether you have seen it yet
		// second false is whether you have seen it negated 
		for (int i = 0; i < symbols.length; sym[i][0] = false, sym[i][1] = false, i++);

		for (Clause c : clauses) {
			for (int i : c.clause) {
				if (model[Math.abs(i) - 1] != 0) continue;

				if (i > 0) sym[i - 1][0] = true;	// i = 1 index in symbols will be [0]
				else sym[Math.abs(i) - 1][1] = true;
			}
		}

		for (int i = 0; i < 100; i++) {
			if (sym[i][0] == true && sym[i][1] == false) {
				Symbol s = new Symbol(i + 1, true);
				return s;
			}
			if (sym[i][0] == false && sym[i][1] == true) {
				Symbol s = new Symbol(i + 1, false);
				return s;
			}
		}		
		return null;
	}

	private Symbol findUnitClause(List<Clause> clauses, int[] model) {

		// go through each clause
		// if the first is false or undefined continue
		// if the first is true go next clause this can not have a unit clause symbol		
		
		int index = 0;

		for (Clause c : clauses) {
			boolean undef = false;
			
			if ((model[Math.abs(c.clause[0]) - 1] < 0 && c.clause[0] < 0)
					|| (model[Math.abs(c.clause[0]) - 1] > 0 && c.clause[0] > 0)) { continue; }

			if (model[Math.abs(c.clause[0]) - 1] == 0) { 
				undef = true;
				index = 0;
			}

			if ((model[Math.abs(c.clause[1]) - 1] == 0 && undef)
					|| (model[Math.abs(c.clause[1]) - 1] > 0 && c.clause[1] > 0)
					|| (model[Math.abs(c.clause[1]) - 1] < 0 && c.clause[1] < 0)) { continue; }

			if (model[Math.abs(c.clause[1]) - 1] == 0) { 
				undef = true; 
				index = 1;
			}

			if ((model[Math.abs(c.clause[2]) - 1] == 0 && undef)
					|| (model[Math.abs(c.clause[2]) - 1] > 0 && c.clause[2] > 0)
					|| (model[Math.abs(c.clause[2]) - 1] < 0 && c.clause[2] < 0)) { continue; }

			if (model[Math.abs(c.clause[2]) - 1] == 0) { 
				Symbol s = new Symbol(c.clause[2], true);
				return s; 
			}

			Symbol s = new Symbol(c.clause[index], true);
			return s;
		}		
		return null;
	}
}
