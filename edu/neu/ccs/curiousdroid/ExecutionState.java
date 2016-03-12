/**
Copyright (C) 2016 Patrick Carter

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
Also add information on how to contact you by electronic and paper mail.
**/

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
