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

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;


/**
 * Created by pdc on 2/4/15.
 */
public class Interaction {
    private HashMap<ViewNode, String[]> interactionMap;
    private ArrayList<ViewNode> orderedList, rawlist;
    private String interactions = "";

    public Interaction() {
        interactionMap = new HashMap<ViewNode, String[]>();
        orderedList = new ArrayList<ViewNode>();
        rawlist = new ArrayList<ViewNode>();
    }

    public Interaction(ViewNode view, String code[]) {
        interactionMap = new HashMap<ViewNode, String[]>();
        interactionMap.put(view, code);
        orderedList = new ArrayList<ViewNode>();
        rawlist = new ArrayList<ViewNode>();
    }

    public void addInteraction(ViewNode view, String[] code) {
        interactionMap.put(view, code);
    }

    private void order() {
        //Log.i("CuriousDroid", "Interaction: ViewNode tmp = " + this.interactionMap.toString());
        //ArrayList<ViewNode> keys = new ArrayList<ViewNode>(interactionMap.keySet());
        String input, taps;
        ViewNode tmp;
        for(int i = 0; i < rawlist.size(); i++)
            Log.i("CuriousDroid", "Interation.java: rawlist(" + i + "): " + rawlist.get(i).getName());
        this.orderedList =  DetermineContext.order(rawlist);//rawlist);
        for(int i = 0; i < orderedList.size(); i++)
            Log.i("CuriousDroid", "Interation.java: orderedList(" + i + "): " + orderedList.get(i).getName());
        assert(orderedList.size() == rawlist.size());
        int textfields = 0;
        for(int i = 0; i < orderedList.size(); i++) {
            tmp = orderedList.get(i);
            if (tmp.getView() instanceof EditText) {
                textfields++;
                input = DetermineContext.findEditableTextContext(tmp.getView());
                if (input.equals(DetermineContext.PHONE))
                    taps = PressGenerator.genKeyStrokes(input, true, CuriousDroid.isEmulated);
                else
                    taps = PressGenerator.genKeyStrokes(input, false, CuriousDroid.isEmulated);
            } else {
                input = "GENERIC-TAP";
                taps = PressGenerator.genericPress(tmp.getXcenter(), tmp.getYcenter(),
                        CuriousDroid.KEYPAUSE, CuriousDroid.isEmulated);
            }

            interactionMap.put(tmp, new String[]{taps, input});
        }

        String[] tmpstr;
        boolean seedtap = true;
        int textfieldsTraversed = 0;
        for(int i = 0; i < orderedList.size(); i++) {
            tmp = orderedList.get(i);
            if(tmp.getView() instanceof EditText)
                textfieldsTraversed++;
            if(tmp.getTakesNextFocus())
                interactions += PressGenerator.genericPress(PressGenerator.NEXTx, PressGenerator.NEXTy,
                        CuriousDroid.KEYPAUSE, CuriousDroid.isEmulated);
            else if(tmp.getView() instanceof EditText && seedtap) {
                interactions += PressGenerator.genericPress(tmp.getXcenter(), tmp.getYcenter(),
                        CuriousDroid.KEYPAUSE, CuriousDroid.isEmulated);
                seedtap = false;
            }
            tmpstr = this.interactionMap.get(tmp);
            Log.i("CuriousDroid", "Interaction: String[] tmpstr type = " + tmpstr[1]);
            Log.i("CuriousDroid", "Interaction:  " + textfieldsTraversed + " out of " + textfields + " EditTexts evaluated");
            interactions += tmpstr[0];
            if(textfieldsTraversed == textfields || (tmp.getTakesNextFocus() &&
                (i != orderedList.size()-1 && !orderedList.get(i+1).getTakesNextFocus()))) {
                interactions += PressGenerator.pressBackButton(CuriousDroid.isEmulated);
                textfieldsTraversed++;
            }

        }
    }

    public void populateRawlist(ArrayList<ViewNode> views) {
        this.rawlist = views;
    }

    public String getInteractions() {
        order();
        return interactions;
    }

    public String getValue(View view) {
        return interactionMap.get(view)[1];
    }

    public String toString() {
        String output = "";
        ViewNode vtmp;
        if(orderedList.isEmpty()) {
            ArrayList<ViewNode> keys = new ArrayList<ViewNode>(interactionMap.keySet());
            for(int i = 0; i < keys.size(); i++) {
                vtmp = keys.get(i);
                output += interactionMap.get(vtmp)[1] + ":\n";
                output += interactionMap.get(vtmp)[0];
            }
        }
        else {
            for(int i = 0; i < orderedList.size(); i++) {
                vtmp = orderedList.get(i);
                output += vtmp.toString() + ": " + interactionMap.get(vtmp)[1] + "\n";
                output += interactionMap.get(vtmp)[0];
            }
        }
        return output;
    }
}
