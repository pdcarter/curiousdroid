package edu.neu.ccs.curiousdroid;

import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;
import android.view.View;

public class ViewNode {
	private String name;
    private String input = "";
	//private String label;
	private int x;
	private int y;
	private int width;
	private int height;
    private int xcenter;
    private int ycenter;
	private int index;
	private int parent;
	private int numInterations = 0;
	private int usability;
	private boolean touchable;
	private ArrayList<Integer> children;
	private int visibility;
	private View view = null;
    private boolean takesNextFocus = false;

	public ViewNode(View view) {
		this.name = view.getClass().getSimpleName();
		int[] location = new int[2];
		this.width = view.getWidth();
		this.height = view.getHeight();
		view.getLocationOnScreen(location);
		this.x = location[0];
		this.y = location[1];
        this.xcenter = this.x + this.width/2;
        this.ycenter = this.y + this.height/2;
		//this.parent = view.getParent();
		this.numInterations = 0;
		this.touchable = view.isClickable();
		this.view = view;
		this.usability = -1;
		this.visibility = view.getVisibility();
		this.children = new ArrayList<Integer>();
	}

	public ViewNode(View view, int index) {
		this.name = view.getClass().getSimpleName();
		int[] location = new int[2];
		this.width = view.getWidth();
		this.height = view.getHeight();
		view.getLocationOnScreen(location);
		this.x = location[0];
		this.y = location[1];
        this.xcenter = this.x + this.width/2;
        this.ycenter = this.y + this.height/2;
		this.index = index;
		//this.parent = view.getParent();
		this.numInterations = 0;
		this.touchable = view.isClickable();
		this.visibility = view.getVisibility();
		this.view = view;
		this.usability = -1;
		this.children = new ArrayList<Integer>();
	}
	
	public ViewNode(View view, int index, int parent) {
		this.name = view.getClass().getSimpleName();
		int[] location = new int[2];
		this.width = view.getWidth();
		this.height = view.getHeight();
		view.getLocationOnScreen(location);
		this.x = location[0];
		this.y = location[1];
        this.xcenter = this.x + this.width/2;
        this.ycenter = this.y + this.height/2;
		this.index = index;
		this.parent = parent;
		this.numInterations = 0;
		this.touchable = view.isClickable();
		this.visibility = view.getVisibility();
		this.view = view;
		this.usability = -1;
		this.children = new ArrayList<Integer>();
	}

	/**********************************************
	* The meat of the operation
	**********************************************/

	public double compare(ViewNode vn) {
		double score = 0.0;
		if(this.name.equals(vn.getName()))	
			score += 1.0/7;
		if(this.x == vn.getX())
			score += 1.0/7;
		if(this.y == vn.getY())
			score += 1.0/7;
		if(this.index == vn.getIndex())
			score += 1.0/7;
		if(this.parent == vn.getParent())
			score += 1.0/7;
		if(this.children.equals(vn.getChildren()))
			score += 1.0/7;
		if(this.touchable && vn.getVisibility() == 0)
			score += 1.0/7;
		//Log.i("CuriousDroid", "ViewNode comparison score: " + score);
		return score;
	}

	/**
	* Set Methods
	**/

	public void setName(String name) {
		this.name = name;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setParent(int pid) {
		this.parent = pid;
	}

    public void setTakesNextFocus(boolean takesNextFocus) {
        this.takesNextFocus = takesNextFocus;
    }

	public void incNumInteractions() {
		this.numInterations++;
	}
	
	public void SetUsability(int value) {
		this.usability = value;
	}
	
	public void addChild(int cid) {
		this.children.add(cid);
	}

	/**
	* Get Methods
	**/

	public String getName() {
		return this.name;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}

    public int getXcenter() {
        return xcenter;
    }

    public int getYcenter() {
        return ycenter;
    }

	public int getIndex() {
		return this.index;
	}

	public int getParent() {
		return this.parent;
	}

	public int getNumInteractions() {
		return this.numInterations;
	}
	
	public int getUsability() {
		return this.usability;
	}

    public boolean getTakesNextFocus() {
        return this.takesNextFocus;
    }

	public boolean isTouchable() {
		return this.touchable;
	}

	public ArrayList<Integer> getChildren() {
		return this.children;
	}
	
	public int getVisibility() {
		return this.visibility;
	}
	
	public View getView() {
		return this.view;
	}
	
	public String toString() {
		String output = "CuriousDroid: " + System.currentTimeMillis() + ": Name = " + this.name + "\n";
		output += "CuriousDroid: " + System.currentTimeMillis() + ": Index = " + this.index + "\n";
		output += "CuriousDroid: " + System.currentTimeMillis() + ": ParentID = " + this.parent + "\n";
		output += "CuriousDroid: " + System.currentTimeMillis() + ": ChildIDs = " + Arrays.toString(this.children.toArray()) + "\n";
		output += "CuriousDroid: " + System.currentTimeMillis() + ": xPos = " + this.x + "\n";
		output += "CuriousDroid: " + System.currentTimeMillis() + ": yPos = " + this.y + "\n";
		output += "CuriousDroid: " + System.currentTimeMillis() + ": Height = " + this.height + "\n";
		output += "CuriousDroid: " + System.currentTimeMillis() + ": Width = " + this.width + "\n";
		output += "CuriousDroid: " + System.currentTimeMillis() + ": Visibity = " + this.visibility;
		return output;
	}
}
