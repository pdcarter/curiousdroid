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
