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
Also add information on how to contact you by electronic and paper mail.package edu.neu.ccs.curiousdroid;
**/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ActivityContainer {
	
	// Class members
	private Activity root = null;
	private ArrayList<ArrayList<ViewNode>> viewLists = null;
	private ArrayList<View> touchables = null;
	private ArrayList<String> viewSignature = null;
	private ArrayList<ExecutionState> states = null;
	private ArrayList<ViewNode> genericSignature = null;
	private ArrayList<ViewNode> touchNodes = null;
	private ArrayList<String> interactions = null;
	private int[] genericIndex;
	private int visitCounter = 0;
	private String activityName = "";
	private String outputName = "";
	private ArrayList<String> viewHierarchies = null;
	public boolean registered = false;
	private boolean isTaskRoot = false;
	private ArrayList<String> buttonPushes = null;
	private String compName = "";
	private PrintWriter output;
	
	/*public ActivityContainer(Activity root, ArrayList<ViewNode> viewLists) {
		this.root = root;
		this.viewLists = new ArrayList<ArrayList<ViewNode>>();
		this.viewLists.add(viewLists);
		this.activityName = root.getLocalClassName();
		genSignature(viewLists);
	}*/
	
	public ActivityContainer(Activity root, ArrayList<ViewNode> viewList, String viewHierarchy) {
		this.root = root;
		this.viewLists = new ArrayList<ArrayList<ViewNode>>();
		this.viewLists.add(viewList);
		this.activityName = root.getLocalClassName();
		this.outputName = this.activityName;
		this.viewHierarchies = new ArrayList<String>();
		this.viewHierarchies.add(viewHierarchy);
		this.interactions = new ArrayList<String>();
		genSignature(viewList);
		setGenericSignature(viewList);
		this.genericIndex = new int[this.genericSignature.size()];
		for(int i = 0; i < this.genericIndex.length; i++)
			this.genericIndex[i] = 0;
		buttonPushes = new ArrayList<String>();
		isTaskRoot = root.isTaskRoot();
		initOutput();
		try {
			ComponentName cn = root.getComponentName();
			this.compName = cn.flattenToString();
			Log.i("CuriousDroid", "ActivityContainer: <init> " + activityName + " called by " + compName);
		}
		catch(Exception e) {
			Log.i("CuriousDroid", "ActivityContainer: " + activityName + " does not conatin ComponentName. Root Activity?");
			e.printStackTrace();
		}
		
	}
	
	public ActivityContainer(Activity root, String name, ArrayList<ViewNode> viewList, String viewHierarchy) {
		this.root = root;
		this.viewLists = new ArrayList<ArrayList<ViewNode>>();
		this.viewLists.add(viewList);
		this.activityName = name;
		this.outputName = this.activityName;
		this.viewHierarchies = new ArrayList<String>();
		this.viewHierarchies.add(viewHierarchy);
		this.interactions = new ArrayList<String>();
		genSignature(viewList);
		setGenericSignature(viewList);
		this.genericIndex = new int[this.genericSignature.size()];
		for(int i = 0; i < this.genericIndex.length; i++)
			this.genericIndex[i] = 0;
		buttonPushes = new ArrayList<String>();
		isTaskRoot = false;//root.isTaskRoot();
		this.compName = "Dialog";
		initOutput();
	}
	
	public ActivityContainer(Activity root, String name, ArrayList<ViewNode> viewList, String viewHierarchy, String outputName) {
		this.root = root;
		this.viewLists = new ArrayList<ArrayList<ViewNode>>();
		this.viewLists.add(viewList);
		this.activityName = name;
		this.outputName = outputName;
		this.viewHierarchies = new ArrayList<String>();
		this.viewHierarchies.add(viewHierarchy);
		this.interactions = new ArrayList<String>();
		genSignature(viewList);
		setGenericSignature(viewList);
		this.genericIndex = new int[this.genericSignature.size()];
		for(int i = 0; i < this.genericIndex.length; i++)
			this.genericIndex[i] = 0;
		buttonPushes = new ArrayList<String>();
		isTaskRoot = false;//root.isTaskRoot();
		this.compName = "Dialog";
		initOutput();
	}
	
	public void initOutput() {
		try {
			output = new PrintWriter(new BufferedWriter(new FileWriter("/data/local/tmp/log", true)));
		}catch(Exception e) {
			e.printStackTrace();
			//output.println("CuriousDroid->" + System.currentTimeMillis() + ": Stacktrace + " + e.getLocalizedMessage());
			//output.flush();
		}
	}
	
	public void addViewList(ArrayList<ViewNode> views, String hierarchy) {
		if(!compareSignature(genTempSignature(views), hierarchy))
			this.viewLists.add(views);
		//else
			//Log.i("CuriousDroid", "ActivityContainer->addViewList: signature already exists, not adding to container - no change.");
	}
	
	public void addTouchables(ArrayList<View> touchables) {
		//Log.i("CuriousDroid", "just entered ActivityContainer.addTouchables().");
		this.touchables = touchables;
		generateTouchableSignature();
		//Log.i("CuriousDroid", "about to finish ActivityContainer.addTouchables().");
	}
	
	public void addHierarchy(String hierarchy) {
		this.viewHierarchies.add(hierarchy);
	}
	
	public void addButtonPush(ViewNode button) {
		String buttonName = "";
		try{
			buttonName = ((Button)button.getView()).getText().toString();
		}
		catch(Exception cce) {
			//buttonName = "";//((ImageButton)button.getView()).getContentDescription().toString();
		}
		if(!buttonName.equals("") && !buttonPushes.contains(buttonName))  {
			//output.println("AcitivityContainer->" + System.currentTimeMillis() + ": button name: " + buttonName + ", incrementing it here.");
			//output.flush();
			buttonPushes.add(buttonName);
			//Log.i("CuriousDroid", "Adding " + buttonName + " to pushed-buttons list in " + this.activityName + "'s ActivityContainer.");
			this.genericSignature.get(button.getIndex()).incNumInteractions();
			this.genericIndex[button.getIndex()] += 1;
		}
		else {
			//output.println("AcitivityContainer->" + System.currentTimeMillis() + ": incrementing \"" + buttonName + "\" here.");
			//output.flush();
			this.genericSignature.get(button.getIndex()).incNumInteractions();
			//output.println("AcitivityContainer->" + System.currentTimeMillis() + ": button has now been labeled with " 
			//		+ this.genericSignature.get(button.getIndex()).getNumInteractions() + " interactions.");
			//output.flush();
			this.genericIndex[button.getIndex()] += 1;
		}
	}
	
	public void addInteractionSet(String set) {
		this.interactions.add(set);
	}
	
	public void updateCounter() {
		this.visitCounter++;
	}
	
	public void setIsTaskRoot(boolean isTaskRoot) {
		this.isTaskRoot = isTaskRoot;
	}
	
	public int viewListCount() {
		return this.viewLists.size();
	}
	
	public int getVisitCount() {
		return this.visitCounter;
	}
	
	public boolean getIsTaskRoot() {
		return this.isTaskRoot;
	}
	
	public String getCallingName() {
		return this.compName;
	}
	
	public ArrayList<String> getInteractionsSets() {
		return this.interactions;
	}
	
	public boolean compareViewNodes(ViewNode vn) {
		int id = vn.getIndex();
		ViewNode vn2 = genericSignature.get(id);
		double value = vn.compare(vn2);
		if(value > 0.7) {
			//Log.i("CuriousDroid", "Same node: value of " + value);
			return true;
		}
		else {
			//Log.i("CuriousDroid", "Different node: value of " + value);
			return false;
		}
	}

	private void genSignature(ArrayList<ViewNode> views) {
		this.viewSignature = genTempSignature(views);
	}

	public ArrayList<String> genTempSignature(ArrayList<ViewNode> views) {
		ArrayList<String> temp = new ArrayList<String>(views.size());
		for(int i = 0; i < views.size(); i++) {
			temp.add(i, views.get(i).getView().getClass().getCanonicalName());
		}
		return temp;
	}
	
	public void populateViewNodeList(ArrayList<View> rawViews) {
		for(int i = 0; i < rawViews.size(); i++) {
			this.genericSignature.add(i, new ViewNode(rawViews.get(i), i));
		}
	}
	
	public void setGenericSignature(ArrayList<ViewNode> vn) {
		this.genericSignature = vn;
	}

	public ArrayList<String> getViewSignature() {
		return this.viewSignature;
	}
	
	public ArrayList<ViewNode> getGenericSignature() {
		return this.genericSignature;
	}
	
	public int getNumberInteractions(ViewNode button) {
		//if(compareViewNodes(button)) {
			return this.genericSignature.get(button.getIndex()).getNumInteractions();
		//}
		//else
			//return -1;
	}

	public boolean compareSignature(ArrayList<String> list, String hierarchy) {
		return this.viewSignature.equals(list) && compareHierarchies(hierarchy);
	}
	
	public boolean compareSignature(ArrayList<ViewNode> viewnodes) {
		if(viewnodes.size() != this.genericSignature.size())
			return false;
		else {
			for(int i = 0; i < this.genericSignature.size(); i++) {
				if(compareViewNodes(viewnodes.get(i)))//.compare(this.genericSignature.get(i)) < .70)
					return false;
			}
		}
		return true;
	}
	
	public boolean compareHierarchies(String hierarchy) {
		for(int i = 0; i < this.viewHierarchies.size(); i++) {
			if(this.viewHierarchies.get(i).equalsIgnoreCase(hierarchy))
				return true;
		}
		return false;
	}
	
	public ArrayList<ViewNode> getViewList(int index) {
		if(index > viewListCount() - 1)
			return null;
		else
			return this.viewLists.get(index);
	}
	
	public String getActivityName() {
		//return this.activityName;
		return this.outputName;
	}
	
	public void generateTouchableSignature() {
		//Log.i("CuriousDroid", "just entered ActivityContainer.generateTouchableSignature().");
		for(int i = 0; i < touchables.size(); i++) {
			this.touchNodes.add(i, new ViewNode(touchables.get(i), i));
		}
		//Log.i("CuriousDroid", "about to end ActivityContainer.generateTouchableSignature().");
	}
	
	public ArrayList<String> getButtonPushes() {
		return buttonPushes;
	}
	
	public boolean buttonPushed(ViewNode button) {
		String buttonName = "";
		try{
			buttonName = ((Button)button.getView()).getText().toString();
		}
		catch(Exception cce) {
			//buttonName = ((ImageButton)button.getView()).getContentDescription().toString();
		}
		//output.println("ActivityContainer->" + System.currentTimeMillis() + ": " +"Checking to see if button has been pushed...");
		//output.flush();
		if(!buttonName.equals("")) {
			output.println("ActivityContainer->" + System.currentTimeMillis() + ": " +"button has a name: " + buttonName);
			output.flush();
			if(buttonPushes.contains(buttonName)) {
				output.println("ActivityContainer->" + System.currentTimeMillis() + ": " + buttonName + " is in the list of button pushes. returning true.");
				output.flush();
				Log.i("CuriousDroid", buttonName + " in " + this.activityName + "'s ActivityContainer pushed-buttons list.");
				return true;
			}
			else {
				output.println("ActivityContainer->" + System.currentTimeMillis() + ": " + buttonName + " is not in the list of button pushes. returning false.");
				output.flush();
				Log.i("CuriousDroid", buttonName + " not in " + this.activityName + "'s ActivityContainer pushed-buttons list.");
				return false;
			}
		}
		else if(compareViewNodes(button)) {
			//output.println("ActivityContainer->" + System.currentTimeMillis() + ": No name, but turned up in the compareViewNodes method.");
			//output.flush();
			int ni = this.genericIndex[button.getIndex()]; //getNumberInteractions(button);
			if(ni > 0) {
				//output.println("ActivityContainer->" + System.currentTimeMillis() + ": Has " + ni + " interactions. returning true.");
				//output.flush();
				return true;
			}
			else {
				//output.println("ActivityContainer->" + System.currentTimeMillis() + ": Has " + ni + " interactions. returning false.");
				//output.flush();
				return false;
			}
		}
		else
			return false;
	}
	
	public String toString() {
		String output = "GenericSignature = \n";
		for(int i = 0; i < this.genericSignature.size(); i++) {
			output += this.genericSignature.get(i).toString() + "\n\n";
		}
		return output;
	}

}
