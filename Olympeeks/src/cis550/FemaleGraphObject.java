package cis550;
//package cis550;

import java.util.ArrayList;
public class FemaleGraphObject {
	
	private ArrayList<ArrayList<Integer>> pairs = new ArrayList<ArrayList<Integer>>();
	private ArrayList<String> years = new ArrayList<String>();
	
	
	public FemaleGraphObject(ArrayList<String> years, ArrayList<ArrayList<Integer>> pairs){
		this.pairs = pairs;
		this.years = years;
	}
}
