package cis550;

import java.util.ArrayList;

public class GraphObject {
	
	private ArrayList<Integer> years;
	private ArrayList<Integer> golds;
	private ArrayList<Integer> totals;
	
	public GraphObject(ArrayList<Integer> years, ArrayList<Integer> golds, ArrayList<Integer> totals){
		this.years = years;
		this.golds = golds;
		this.totals = totals;
	}
	
	public GraphObject(ArrayList<Integer> years){
		this.years = years;
	}

}
