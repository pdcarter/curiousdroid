package edu.neu.ccs.curiousdroid;

import android.view.View;

public class ViewPositionComparator implements Comparable<ViewPositionComparator>{
	public View view;
	
	public ViewPositionComparator(View view) {
		this.view = view;
	}
	
	// Sept 4th, 12:38 am I swapped the -1,1 values below. Experimental.
	public int compareTo(ViewPositionComparator v2){
		int[] v1pos = new int[2];
		int[] v2pos = new int[2];
		view.getLocationOnScreen(v1pos);
		v2.view.getLocationOnScreen(v2pos); // changed from view.getLocationOnScreen on 2/11/15
		int v1y = v1pos[1];
		int v2y = v2pos[1];
		if(v1y < v2y)
			return -1;
		if(v1y == v2y)
			return v1pos[0] - v2pos[0];
		else
			return 1;
	}
}
