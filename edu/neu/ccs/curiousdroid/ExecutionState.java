package edu.neu.ccs.curiousdroid;

import java.util.ArrayList;
import java.util.HashMap;


public class ExecutionState {
	// Add class variables here
	private ActivityContainer parent = null;
	private HashMap<String,ViewNode> clickables = null;
	private ArrayList<ActivityContainer> stateChain = null;
	
	
	/**
	 * Override the constructor
	 */
	public ExecutionState(ActivityContainer parent) {
		this.parent = parent;
		this.stateChain = new ArrayList<ActivityContainer>();
		this.stateChain.add(0,parent);
	}

	public ExecutionState() {
		this.stateChain = new ArrayList<ActivityContainer>();
	}
	
	/*public ExecutionState(ViewNode parent, HashMap<String,ViewNode> clickables) {
		this.parent = parent;
		this.clickables = clickables;
	}*/
	
	/*public ExecutionState(ViewNode parent, ArrayList<ViewNode> stateChain) {
		this.parent = parent;
		this.stateChain = stateChain;
	}*/

	public void addState(ActivityContainer act) {
		int numStates = this.stateChain.size();
		this.stateChain.add(numStates, act);
		if(numStates < 1)
			this.parent = act;
	}
	
	public ArrayList<ActivityContainer> getStateChain() {
		return this.stateChain;
	}

	public String toString() {
		String out = "StateChain: {";
		for(int i = 0; i < stateChain.size(); i++) {
			out += stateChain.get(i).getActivityName() + ", ";
		}
		return out + "}";
	}
	
	/*public View getRootView() {
		return parent.getView();
	}*/
}
