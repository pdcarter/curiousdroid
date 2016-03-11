package edu.neu.ccs.curiousdroid;

/**
 * Name: CuriousDroid
 * Description: This class provides a number of static methods in support of
 * 		the monkey runner.  Currently it is very simple and only provides a method for 
 * 		discovering Views/ViewGroups inheriting from some *top-level* ViewGroup that
 * 		that's passed in.  It also provides a couple get methods for data generated
 * 		by the above method.  Presently, the reflection method can be ignored.  I'll
 * 		likely fill it out when needed, but not before.
 * Author: Patrick Carter, Northeastern University, PhD Student-Information Assurance
 * Date: January 10, 2013
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.inputmethodservice.ExtractEditText;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.VideoView;
import android.widget.ZoomButton;


public class CuriousDroid {
	private View root;
	private ViewNode rootNode;
	private HashMap<String, ActivityContainer> activityViews;
	private ExecutionState state;
	private boolean isDialog = false;
	private boolean displaySet = false;
	private String lastActivity;
	private String appName;
	private Activity currAct = null;
	private int screenHeight, screenWidth;
	public PrintWriter output;
	ActivityContainer actCont = null;
	Date d;
	public static final boolean isEmulated = true;
	public static final int KEYPAUSE = 400000; //Added to try and increase coverage on instrumented emulator
	
	public CuriousDroid() {
		try {
			output = new PrintWriter(new BufferedWriter(new FileWriter("/data/local/tmp/log", true)));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String init(View root) {
		this.root = root;
		state = new ExecutionState();
		this.activityViews = new HashMap<String, ActivityContainer>();
		this.state = new ExecutionState();
		//rootNode = new ViewNode(root);
		if(root.getContext() instanceof Activity) {
			appName = ((Activity)root.getContext()).getPackageName();
			
			Point size = new Point();
			screenHeight = size.y;
			screenWidth = size.x;
			displaySet = true;
		}
		else {
			try {
				output = new PrintWriter("/data/local/tmp/noname_log.out", "UTF-8");
			}catch(Exception e) {
				e.printStackTrace();
				output.println("CuriousDroid->" + System.currentTimeMillis() + ": Stacktrace + " + e.getLocalizedMessage());
				output.flush();
			}
		}
		return "blargh";
	}
	
	
	
	public void fromOnResume(View parent) {
		ArrayList<View> views = new ArrayList<View>();
		views.add(parent);
		views = expandChildViews(views);
		
		for(int i = 0; i < views.size(); i++) {
			int width = views.get(i).getWidth()/2;
			int height = views.get(i).getHeight()/2;
			int[] location = new int[2];
			views.get(i).getLocationOnScreen(location);
			location[0] += width;
			location[1] += height;
		}
	}
	
	public String checkRoot(Activity tmp) {
		if(lastActivity.contains(tmp.getLocalClassName()) && lastActivity.contains("Dialog"))
			return "true";
		if(actCont.getIsTaskRoot())
			return "true";
		else if(tmp.isTaskRoot())
			return "true";
		else
			return "false";
	}
	
	public String stopIntent(Intent intent) {
		if(intent == null) {
			return "false";
		}
		String uri = intent.toURI();
		String action = intent.getAction();
		ComponentName cn = intent.getComponent();
		
		if(cn != null) {
			String cname = cn.flattenToString();
			String[] cnameparts = cname.split("/");
			if(!cnameparts[0].equalsIgnoreCase(appName)) {
				return "true";
			}
		}
		
		if(uri.contains("android.intent.action.VIEW")) {
			return "true";
		}
		if(uri.contains("http://") || uri.contains("https://")) {
			return "true";
		}
		if(uri.contains("android.intent.action.SEND")) {
			return "true";
		}
		if(uri.contains("android.intent.action.CHOOSER")) {
			return "true";
		}
		return "false";
	}
	
	public String expandChildViews(View parent, String isOnResume) {
		if(parent == null) {
			Log.i("CuriousDroid", "parent is null in expandChildViews.  Returning.");
			return "";
		}
		ArrayList<ViewNode> viewnodes = new ArrayList<ViewNode>();
		ArrayList<View> views = new ArrayList<View>();
		views.add(parent);
		views = expandChildViews(views);
		ViewNode vn = new ViewNode(parent, 0, -1);
		viewnodes.add(0, vn);
		viewnodes = expandChildViews(vn, viewnodes);
		currAct = (Activity)parent.getContext();
		ArrayList<View> touchables = parent.getTouchables();
		
		View tmp = null;
		String viewHierarchy = "";
		for(int i = 0; i < views.size(); i++) {
			tmp = views.get(i);
			if(tmp.getVisibility() == View.VISIBLE) { //Changed from 0
				viewHierarchy += tmp.getClass().getSimpleName() + ": visible.\n";
			}
			else if(tmp.getVisibility() == View.INVISIBLE) { // Changed from 4
				viewHierarchy += tmp.getClass().getSimpleName() + ": not visible.\n";
			}
			else if(tmp.getVisibility() == View.GONE){ // Changed from 8
				viewHierarchy += tmp.getClass().getSimpleName() + ": invisible.\n";
			}
			if(tmp.getClass().getSimpleName().equals("Gallery")) {
				touchables.add(tmp);
			}
		}
		
		String actName = currAct.getLocalClassName();
		this.lastActivity = actName;
		if(isOnResume.equals("true")) {
			return "";
		}
		
		if(!this.activityViews.containsKey(actName)) {
			actCont = new ActivityContainer(currAct, viewnodes, viewHierarchy);
			this.activityViews.put(actName, actCont);
			this.state.addState(actCont);
			output.println("CuriousDroid->" + System.currentTimeMillis() + ": " + state.toString() + "\n\n");
			output.flush();
		}
		else {
			actCont = this.activityViews.get(actName);
			this.state.addState(actCont);
			actCont.addViewList(viewnodes, viewHierarchy);
			actCont.updateCounter();
			output.println("CuriousDroid->" + System.currentTimeMillis() + ": " + state.toString() + "\n\n");
			output.flush();
		}
		
		String outputstr = "";
		actCont.setGenericSignature(viewnodes);
        output.println("CuriousDroid->" + System.currentTimeMillis() + ": About to call exploreStateSpace method from expandChildViews.");
        output.flush();
		outputstr = exploreStateSpace(viewnodes, actName);
		if(outputstr.equals("")){
			output.println("CuriousDroid->" + System.currentTimeMillis() + ": " + "WARNING: NO INTERACTIVE WIDGETS FOUND IN " + actName);
			output.flush();
		}
		return outputstr;
	}
	
	public String expandDialogViews(View parent) {
		this.isDialog = true;
		if(parent == null) {
			return "";
		}
		ArrayList<ViewNode> viewnodes = new ArrayList<ViewNode>();
		ArrayList<View> views = new ArrayList<View>();
		views.add(parent);
		views = expandChildViews(views);
		ViewNode vn = new ViewNode(parent, 0, -1);
		viewnodes.add(0, vn);
		viewnodes = expandChildViews(vn, viewnodes);
		View tmp = null;
		String appendix = "";
		String viewHierarchy = "";
		for(int i = 0; i < views.size(); i++) {
			tmp = views.get(i);
            if(tmp.getVisibility() == View.VISIBLE) { //Changed from 0
                viewHierarchy += tmp.getClass().getSimpleName() + ": visible.\n";
            }
            else if(tmp.getVisibility() == View.INVISIBLE) { // Changed from 4
                viewHierarchy += tmp.getClass().getSimpleName() + ": not visible.\n";
            }
            else if(tmp.getVisibility() == View.GONE){ // Changed from 8
                viewHierarchy += tmp.getClass().getSimpleName() + ": invisible.\n";
            }
		}
		
		String actName = "";
		String outputActName = "";
		try {
			actName = this.currAct.getLocalClassName() + "-Dialog"; // + appendix;
			outputActName = this.currAct.getLocalClassName() + "-Dialog";
		}catch(Exception e){
			actName = "NULL";
		}
		
		if(!this.activityViews.containsKey(actName)) {
			actCont = new ActivityContainer(currAct, actName, viewnodes, viewHierarchy, outputActName);
			this.activityViews.put(actName, actCont);
			this.state.addState(actCont);
			output.println("CuriousDroid->" + System.currentTimeMillis() + ": " + state.toString() + "\n\n");
			output.flush();
		}
		else {
			actCont = this.activityViews.get(actName);
			this.state.addState(actCont);
			actCont.addViewList(viewnodes, viewHierarchy);
			actCont.updateCounter();
			output.println("CuriousDroid->" + System.currentTimeMillis() + ": " + state.toString() + "\n\n");
			output.flush();
			if(actCont.getVisitCount() > 20) {
				String out = PressGenerator.randomEvents(720, 1280, 3, isEmulated);
				return out;
			}
		}
		
		this.lastActivity = actName;
		String outputstr = "";
		outputstr = exploreStateSpace(viewnodes, actName);
		this.isDialog = false;
		return outputstr;
	}
	
	public ArrayList<View> expandChildViews(ArrayList<View> views) {
		View parent = views.get(views.size()-1);
		ViewGroup vgTemp;
		try {
			vgTemp = (ViewGroup)parent;
		}
		catch(Exception cce) {
			return views;
		}
		View temp;
		int childCount = vgTemp.getChildCount();
		for(int i = 0; i < childCount; i++) {
			temp = vgTemp.getChildAt(i);
			//vnTemp = new ViewNode(views.size(), parentView.getID(), viewTemp);
			
			views.add(views.size(), temp);
			//parentView.addChild(vnTemp.getSignature());
			//}
			try {
				views = expandChildViews(views);
			}
			catch(Exception cce) {
				continue;
			}
			//vnTemp.getView().invalidate();
		}
		return views;
	}
	
	
	
	/**
	 * Name: expandChildViews
	 * Description: expandChildViews takes a ViewGroup object and checks to see if it
	 *  	 has any child Views.  Stores values in a HashMap so only unique values are
	 *  	 logged.  Uses recursion.
	 * @param ViewNode parentView, ArrayList<ViewNode> views
	 * @return ArrayList<ViewNode> views
	 */
	public static ArrayList<ViewNode> expandChildViews(ViewNode parentView, ArrayList<ViewNode> views) {
    	// Generate Lists of the Views descended from the current View
        // Collect all child Views into one data structure (HashMap)
		ViewGroup vgTemp;
		View viewTemp;
		try {
			vgTemp = (ViewGroup)parentView.getView();
		}
		catch(Exception cce) {
			return views;
		}
		ViewNode vnTemp;
		int childCount = vgTemp.getChildCount();
		for(int i = 0; i < childCount; i++) {
			viewTemp = vgTemp.getChildAt(i);
			parentView.addChild(views.size());
			vnTemp = new ViewNode(viewTemp, views.size(), parentView.getIndex());
			views.add(views.size(), vnTemp);
			try {
				views = expandChildViews(vnTemp, views);
			}
			catch(Exception cce) {
				continue;
			}
		}
		return views;
    }
	
	
	public String exploreStateSpace(ArrayList<ViewNode> touchables, String actName) {
        //Define each type of widget we want to track

		ArrayList<ViewNode> textFields = new ArrayList<ViewNode>();
		ArrayList<ViewNode> buttons = new ArrayList<ViewNode>();
		ArrayList<ViewNode> imageButtons = new ArrayList<ViewNode>();
		ArrayList<ViewNode> radiogroups = new ArrayList<ViewNode>();
		ArrayList<ViewNode> checks = new ArrayList<ViewNode>();
		ArrayList<ViewNode> lists = new ArrayList<ViewNode>();
		ArrayList<ViewNode> scrolls = new ArrayList<ViewNode>();
		ArrayList<ViewNode> spinners = new ArrayList<ViewNode>();
		ArrayList<ViewNode> pickers = new ArrayList<ViewNode>();
		ArrayList<ViewNode> misc = new ArrayList<ViewNode>();
		
		ActivityContainer ac = activityViews.get(actName);
		boolean hasWebView = false;
		boolean webViewTriggered = false;
		String interactions = "";
		
		for(int i = 0; i < touchables.size(); i++) {
			//Class<?> type = touchables.get(i).getView().getClass();
			// Determine the View's subtype
			// Indirectly inherited from View class
			int typeCount = 0;
			/*if(touchables.get(i).getView() instanceof AutoCompleteTextView) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"AutoCompleteTextView discovered.");
				//output.flush();
				textFields.add(touchables.get(i));
				typeCount++;
			}*/

			if(touchables.get(i).getView() instanceof Button) {
				if(!(touchables.get(i).getView() instanceof CompoundButton)) {
					if(touchables.get(i).getVisibility() == 0) {
						buttons.add(touchables.get(i));
						//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Button discovered.");
						//output.flush();
						typeCount++;
					}
				}
			}

			if(touchables.get(i).getView() instanceof CheckBox) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"CheckBox discovered.");
				checks.add(touchables.get(i));
				typeCount++;
			}

			/*if(touchables.get(i).getView() instanceof CheckedTextView) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"CheckedTextView discovered.");
				misc.add(touchables.get(i));
				typeCount++;
			}
		/*	if(touchables.get(i).getView() instanceof CompoundButton) {
				if(!(touchables.get(i).getView() instanceof CheckBox)) {
					checks.add(touchables.get(i).getView());
					output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"CompoundButton discovered.");
					typeCount++;
				}
			}*/
			if(touchables.get(i).getView() instanceof DatePicker) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"DatePicker discovered.");
				//output.flush();
				pickers.add(touchables.get(i));
				typeCount++;
			}
			if(touchables.get(i).getView() instanceof EditText) {
				textFields.add(touchables.get(i));
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"EditText discovered.");
				//output.flush();
				typeCount++;
			}
			if(touchables.get(i).getView() instanceof ExpandableListView) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ExpandableListView discovered.");
				//output.flush();
				lists.add(touchables.get(i));
				typeCount++;
			}
			/*if(touchables.get(i).getView() instanceof ExtractEditText) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ExtractEditText discovered.");
				//output.flush();
				textFields.add(touchables.get(i));
				typeCount++;
			}*/
			/*if(touchables.get(i).getView() instanceof FragmentBreadCrumbs) {
				output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"FragmentBreadCrumbs discovered.");
				output.flush();
				misc.add(touchables.get(i));
				typeCount++;
			}*/
			if(touchables.get(i).getView() instanceof HorizontalScrollView) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"HorizontalScrollView discovered.");
				//output.flush();
				scrolls.add(touchables.get(i));
				typeCount++;
			}
			if(touchables.get(i).getView() instanceof ImageButton) {
				imageButtons.add(touchables.get(i));
				typeCount++;
			}
			if(touchables.get(i).getView() instanceof ListView) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ListView discovered.");
				//output.flush();
				lists.add(touchables.get(i));
				typeCount++;
			}
			/*if(touchables.get(i).getView() instanceof NumberPicker) {
				output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"NumberPicker discovered.");
				output.flush();
				pickers.add(touchables.get(i));
				typeCount++;
			}*/
			/*if(touchables.get(i).getView() instanceof RadioButton) {
				output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"RadioButton discovered.");
				output.flush();
				radios.add(touchables.get(i));
				typeCount++;
			}*/
			if(touchables.get(i).getView() instanceof RadioGroup) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"RadioGroup discovered.");
				//output.flush();
				radiogroups.add(touchables.get(i));
				typeCount++;
			}
			if(touchables.get(i).getView() instanceof ScrollView) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ScrollView discovered.");
				//output.flush();
				scrolls.add(touchables.get(i));
				typeCount++;
			}
			if(touchables.get(i).getView() instanceof Spinner) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Spinner discovered.");
				//output.flush();
				spinners.add(touchables.get(i));
				typeCount++;
			}
			if(touchables.get(i).getView() instanceof TimePicker) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"TimePicker discovered.");
				//output.flush();
				pickers.add(touchables.get(i));
				typeCount++;
			}
			if(touchables.get(i).getView() instanceof VideoView) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"VideoView discovered.");
				//output.flush();
				misc.add(touchables.get(i));
				typeCount++;
			}
			if(touchables.get(i).getView() instanceof WebView) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"WebView discovered.");
				//output.flush();
				misc.add(touchables.get(i));
				typeCount++;
				hasWebView = true;
			}
			if(touchables.get(i).getView() instanceof ZoomButton) {
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ZoomButton discovered.");
				//output.flush();
				misc.add(touchables.get(i));
				typeCount++;
			}
            if(typeCount > 1)
                Log.i("CuriousDroid", "Class: " + touchables.get(i).getName());
		}
		
		
		//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Finished categorizing Views.");
		
		//String activityName = ac.getActivityName();//t.getLocalClassName();
		//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Got activity name.");

		/*ViewNode[] urls;
		if(txtViews.size() > 0) {
			for(int i = 0; i < txtViews.size(); i++) {
				TextView tv = (TextView)txtViews.get(i).getView();
				URLSpan[] urlsspan = tv.getUrls();
				if(urlsspan.length > 0) {
					urls = new ViewNode[urlsspan.length];
					for(int j = 0; j < urlsspan.length; j++) {
						urls[j] = txtViews.get(i);//tv;
					}
				}
			}
		}*/
		Interaction screenInteractions = new Interaction();
        ArrayList<ViewNode> allViews = new ArrayList<ViewNode>();
		//if(textFields.size() > 0) {
		//	textFields = DetermineContext.order(textFields);
		//}
		String keyStrokes = "", keystroke, interactionStroke;
		String mtext = "";
		for(int i = 0; i < textFields.size(); i++) {
			String input = "";
            keystroke = "";
            interactionStroke = "";
            View tmp = textFields.get(i).getView();
            allViews.add(textFields.get(i));
            ((EditText)tmp).getText().clear();
			/*try{
				input = DetermineContext.findEditableTextContext(tmp);//"Hello" + this.textCount++;
			}catch(Exception e) {
				e.printStackTrace();
			}
			output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ContextGenerator returned: " + input);
			/*int width = tmp.getWidth()/2;
			int height = tmp.getHeight()/2;
			int[] location = new int[2];
			textFields.get(i).getView().getLocationOnScreen(location);
			location[0] += width;
			location[1] += height;
			if(i == 0) {
               // keystroke = PressGenerator.genericPress(location[0], location[1], KEYPAUSE, isEmulated);
                keyStrokes += PressGenerator.genericPress(location[0], location[1], KEYPAUSE, isEmulated);
            }
			//int keyboard = textFields.get(i).getResources().getConfiguration().keyboard;
			//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +textFields.get(i).toString() + "keyboard type = " + keyboard);
			if(input.equals(DetermineContext.PHONE))  {
                keystroke = PressGenerator.genKeyStrokes(input, true, isEmulated);
				keyStrokes += keystroke;
				mtext = ((EditText)textFields.get(i).getView()).getHint().toString();
			}
			else {
                keystroke = PressGenerator.genKeyStrokes(input, false, isEmulated);
				keyStrokes += keystroke;
				mtext = ((EditText)textFields.get(i).getView()).getHint().toString();
			}
			
			if(i < textFields.size()-1) {
                if (textFields.get(i+1).getTakesNextFocus()) {
                    //keystroke += PressGenerator.genericPress(PressGenerator.NEXTx, PressGenerator.NEXTy, KEYPAUSE, isEmulated);
                    keyStrokes += PressGenerator.genericPress(PressGenerator.NEXTx, PressGenerator.NEXTy, KEYPAUSE, isEmulated);
                }
                else {
                    //keystroke = PressGenerator.genericPress(location[0], location[1], KEYPAUSE, isEmulated);
                    keyStrokes += PressGenerator.genericPress(location[0], location[1], KEYPAUSE, isEmulated);
                }
            }
			if(i == textFields.size()-1)
				keyStrokes += PressGenerator.pressBackButton(isEmulated);
			interactions += mtext + ": " + input + "\n";
			if(this.isDialog) {
				if(location[0] == 0 && location[1] == 0) {
					((EditText)textFields.get(i).getView()).getText().append(input);
					keyStrokes = "";
				}
			}
            interactionStroke = keyStrokes;
            String[] arr = new String[2];
            arr[0] = keystroke;
            arr[1] = input;
            //screenInteractions.addInteraction(textFields.get(i), arr);*/
			
		}
		
		//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Checking ScrollViews...");
		/**
		 * This section will handle VERTICAL scroll only. HorizontalScrollViews must be used for horizontal
		 */
/*		if(scrolls.size()> 0) {
			ScrollView temp;
			//rand = new Random();
			// Should there ever be more than 1 ???
			for(int i = 0; i < scrolls.size(); i++) {
				try {
					temp = (ScrollView)scrolls.get(i).getView();
				}
				catch(Exception e) {
					output.println("CuriousDroid->" + System.currentTimeMillis() + ": problem casting ScrollView...");
					output.flush();
					output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ScrollView type = " + scrolls.get(i).getView().getClass().getCanonicalName());
					output.flush();
					e.printStackTrace();
					output.println("CuriousDroid->" + System.currentTimeMillis() + ": Stacktrace + " + e.getLocalizedMessage());
					output.flush();
					continue;
				}
				int maxScroll = temp.getMaxScrollAmount();
				int height = temp.getLayoutParams().height;
				int width = temp.getLayoutParams().width;
				output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ScrollView hieght: " + height + ", width: " + width + ", maxScrollAmount: " + maxScroll);
				output.flush();
				//if(maxScroll > root.getBottom()) {
					ArrayList<View> scrollHierarchy = new ArrayList<View>();
					scrollHierarchy.add(temp);
					scrollHierarchy = expandChildViews(scrollHierarchy); // all scrollable views
					// Need to determine which are on-screen and which aren't
				//}
				Log.i("CurioisDroid", "Printing ScrollView hierarchy:");
				for(int j = 0; j < scrollHierarchy.size(); j++) {
					output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"\t\tView: " + scrollHierarchy.get(j).toString());
					output.flush();
				}
				//temp.smoothScrollTo(0, rand.nextInt(maxScroll));
				output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ScrollView type = " + scrolls.get(i).getView().getClass().getCanonicalName());
				output.flush();
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ScrollView max: " + maxScroll);
			}
		}
*/		
		//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Setting RNG.");
		Random rng = new Random(System.currentTimeMillis());
		//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Setting RNG: success.");
		if(radiogroups.size() > 0) {
			//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"RadioGroup(s) exist. About to loop.");
			for(int i = 0; i < radiogroups.size(); i++) {
				RadioGroup rg = (RadioGroup)radiogroups.get(i).getView();
				int numchild = rg.getChildCount();
				ArrayList<ViewNode> alrb =  new ArrayList<ViewNode>();
				for(int j = 0; j < numchild; j++) {
					if(rg.getChildAt(j) instanceof RadioButton)
						alrb.add(new ViewNode(rg.getChildAt(j)));
				}
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ArraList size = " + alrb.size());
				int pos = 0;
				if(alrb.size() > 0) {
					pos = rng.nextInt(alrb.size());
					//rg.check(alrb.get(pos).getId());
					ViewNode rdb = alrb.get(pos);
                    allViews.add(rdb);
					keystroke = PressGenerator.genericPress(rdb.getX(), rdb.getY(), KEYPAUSE, isEmulated);
                    keyStrokes += keystroke;
                    String[] arr = new String[2];
                    arr[0] = keystroke;
                    arr[1] = "GENERIC-RadioButton";
                    //screenInteractions.addInteraction(rdb, arr);
                    interactions += "Checking RadioButton at: x = " + rdb.getX() + ", y = " + rdb.getY() + "\n";
				}
				//else {
				//	output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"RadioGroup empty...");
				//	output.flush();
				//}
			}
		}
		
		int type, first = 1000000;
		String cbText = "", cbPress = "";
		ViewNode trigger = null;
		if(checks.size() > 0) {
			for(int i = 0; i < checks.size(); i++) {
				CheckBox temp = (CheckBox)checks.get(i).getView();
				type = DetermineContext.searchCheckboxContext(temp);
				if(type < first) {
					trigger = new ViewNode(temp);
					first = type;
					cbText = temp.getText().toString();
				}
			}
		}
		
		if(trigger != null) {
			int width = trigger.getWidth()/2;
			int height = trigger.getHeight()/2;
			int[] location = new int[2];
			trigger.getView().getLocationOnScreen(location);
			location[0] += width;
			location[1] += height;
			if(this.isDialog) {
				if(location[0] == 0 && location[1] == 0) {
					trigger.getView().performClick();
					//return "";
				}
			}
			else {
                allViews.add(trigger);
				cbPress += PressGenerator.genericPress(location[0], location[1], KEYPAUSE, isEmulated);
                String[] arr = new String[2];
                arr[0] = cbPress;
                arr[1] = "GENERIC-CheckBox";
                //screenInteractions.addInteraction(trigger, arr);
				output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Pressing the \"" + cbText + "\" CheckBox");
				output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"CheckBox: x = " + location[0] + ", y = " + location[1]);
				output.flush();
				interactions += "Pressing the " + cbText + " CheckBox.\n";
			}
		}
		
		//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Checking Buttons...");
		ArrayList<ViewNode> allButtons = new ArrayList<ViewNode>();
		int numButtons = buttons.size();
		if(numButtons > 0) {
			for(int i = 0;i<numButtons; i++) {
				allButtons.add(buttons.get(i));
				//int type = DetermineContext.searchButtonContext((Button)buttons.get(i));
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Button type: " +  type);
			}
		}
		numButtons = imageButtons.size();
		if(numButtons > 0) {
			for(int i = 0;i<numButtons; i++) {
				allButtons.add(imageButtons.get(i));
				//int type = DetermineContext.searchImageButtonContext((ImageButton)imageButtons.get(i));
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ImageButton type: " +  type);
			}
		}
		
		first = 500;
		trigger = null;
		numButtons = allButtons.size();
		 
		//rand = new Random();
		//int index = rand.nextInt(numButtons);
		String buttPress = "";//, buttText = "";
		//if(activityCount < 100) {
		Button temp = null;
		ImageButton itemp = null;
		ArrayList<ViewNode> potentials = new ArrayList<ViewNode>();
		for(int i = 0; i < numButtons; i++) {
			try {
				temp = (Button)allButtons.get(i).getView();
				allButtons.get(i).SetUsability(DetermineContext.searchButtonContext(temp));
				//String bname = temp.getText().toString();
				boolean pushed = ac.buttonPushed(allButtons.get(i));
				if(pushed) {
					output.println("CuriousDroid->" + System.currentTimeMillis() + ": buttonPushed = " + pushed);
					output.flush();
					continue;
				}
				potentials.add(allButtons.get(i));
			}
			catch(Exception e) {
				itemp = (ImageButton)(allButtons.get(i).getView());
				if(itemp == null) {
					output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"ImageButton casted null variable.  Why?");
					output.flush();
					continue;
				}

				allButtons.get(i).SetUsability(1000);//DetermineContext.searchImageButtonContext(itemp));
				//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Context determination returned.");
				//output.flush();
				/*String bname = "";
				try {
					bname = itemp.getContentDescription().toString();
				}
				catch(Exception ex) {
					ex.printStackTrace();
					output.println("CuriousDroid->" + System.currentTimeMillis() + ": Stacktrace + " + ex.getLocalizedMessage());
					output.flush();
				}*/
				boolean pushed = ac.buttonPushed(allButtons.get(i));
				if(pushed) {
					output.println("CuriousDroid->" + System.currentTimeMillis() + ": buttonPushed = " + pushed);
					output.flush();
					continue;
				}
				else
					potentials.add(allButtons.get(i));
				
			}
		}
		if(potentials.size() > 0){
			int pos = 0;
			//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " + "potentials has " + potentials.size() + " buttons.");
			//output.flush();
			for(int i = 1; i < potentials.size(); i++) {
				if(potentials.get(pos).getUsability() > potentials.get(i).getUsability())
					pos = i;		
			}
			trigger = potentials.get(pos);
		}
		else if(hasWebView && !webViewTriggered) {
			trigger = null;
			webViewTriggered = true;
		}
		else if(allButtons.size() > 0){
			//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " + "allButtons has " + allButtons.size() + " buttons.");
			//output.flush();
			int pos = rng.nextInt(allButtons.size());
			trigger = allButtons.get(pos);
		}
		if(trigger != null) {
			String buttontext = "";
            allViews.add(trigger);
			if(trigger.getView() instanceof Button)
				buttontext = ((Button)trigger.getView()).getText().toString();
			//output.println("CuriousDroid->" + System.currentTimeMillis() + ": \"" + buttontext + "\" (" + trigger.getView().toString() + 
		//			") being used. Incremented. " + (ac.getNumberInteractions(trigger)) + " previous uses.");
			ac.addButtonPush(trigger);
			int width = trigger.getView().getWidth()/2;
			int height = trigger.getView().getHeight()/2;
			int[] location = new int[2];
			trigger.getView().getLocationOnScreen(location);
			location[0] += width;
			location[1] += height;
			buttPress += PressGenerator.genericPress(location[0], location[1], KEYPAUSE, isEmulated);
            String[] arr = new String[2];
            arr[0] = buttPress;
            arr[1] = "GENERIC-Button";
            //screenInteractions.addInteraction(trigger, arr);
			//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Pressing the \"" + trigger.getName() + "\" button");
			//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Button: x = " + location[0] + ", y = " + location[1]);
			//output.flush();
			interactions += "Button: \"" + buttontext + "\" pressed\n";
			if(this.isDialog) {
				if(location[0] == 0 && location[1] == 0) {
					trigger.getView().performClick();
					return "";
				}
			}
			
		}
		
		//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Number of misc views: " + misc.size());
		//output.flush();
		if(misc.size() > 0) {
			for(int i = 0; i < misc.size(); i++) {
				if(misc.get(i).getView() instanceof WebView) {
					WebView wv = (WebView)(misc.get(i).getView());
					if(trigger == null) {
                        ViewNode webview = new ViewNode(wv);
                        allViews.add(webview);
						int width = wv.getWidth()/2;
						int height = wv.getHeight()/2;
						int[] location = new int[2];
						wv.getLocationOnScreen(location);
						location[0] += width;
						location[1] += height;
						buttPress += PressGenerator.genericPress(location[0], location[1], KEYPAUSE, isEmulated);
                        String[] arr = new String[2];
                        arr[0] = buttPress;
                        arr[1] = "GENERIC-WebView";
                        //screenInteractions.addInteraction(webview, arr);
						//output.println("CuriousDroid->" + System.currentTimeMillis() + ": " +"Pressing WebView at: x = " + location[0] + ", y = " + location[1]);
						//output.flush();
						interactions += "Touching WebView at x = " + location[0] + ", y = " + location[1] + "\n";
					}
				}
				else if(misc.get(i).getView() instanceof VideoView) {
					VideoView vv = (VideoView)(misc.get(i).getView());
					if(trigger == null) {
                        ViewNode vidview = new ViewNode(vv);
                        allViews.add(vidview);
						int width = vv.getWidth()/2;
						int height = vv.getHeight()/2;
						int[] location = new int[2];
						vv.getLocationOnScreen(location);
						location[0] += width;
						location[1] += height;
						buttPress += PressGenerator.genericPress(location[0], location[1], KEYPAUSE, isEmulated);
                        String[] arr = new String[2];
                        arr[0] = buttPress;
                        arr[1] = "GENERIC-VideoView";
                        //screenInteractions.addInteraction(vidview, arr);
						interactions += "Touching VideoView at x = " + location[0] + ", y = " + location[1] + "\n";
					}
				}
			}
		}
		
		ac.addInteractionSet(interactions);
        //Log.i("CuriosDroid", "Interactions: " + interactions);
       // for(int i = 0; i < allViews.size(); i++)
         //   Log.i("CuriousDroid", allViews.get(i).toString() + "\n\n");
        screenInteractions.populateRawlist(allViews);
        //output.println("CuriousDroid->" + System.currentTimeMillis() + ": " + screenInteractions.toString());
        //output.flush();
		return screenInteractions.getInteractions();//
        //return keyStrokes + cbPress + buttPress;// + swipe;
	}
	

}
