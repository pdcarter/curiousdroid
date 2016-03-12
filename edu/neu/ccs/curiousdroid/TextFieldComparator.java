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
