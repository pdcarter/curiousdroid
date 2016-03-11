package edu.neu.ccs.curiousdroid;

import android.view.View;

public class TextFieldComparator implements Comparable<TextFieldComparator> {
	public View view = null;
	public int weight = 0;
	public int dx, dy, dtotal;
	
	
	// This is just for passing to the PriorityQueue.  Don't actually use this.
	public TextFieldComparator() {
		dx = dy = weight = 0;
	}
	
	public TextFieldComparator(View view, int dx, int dy, int direction) {
		this.view = view;
		this.dx = dx;
		this.dy = dy;
		this.dtotal = (int)Math.sqrt(Math.pow((double)dx, 2.0) + Math.pow((double)dy, 2.0));
		this.weight = this.dtotal + direction;
	}
	

	@Override
	public int compareTo(TextFieldComparator tfc) {
		int wt = tfc.weight;
		if(wt < weight) 
			return -1;
		if(wt == weight)
			return 0;
		else
			return 1;
	}

}
