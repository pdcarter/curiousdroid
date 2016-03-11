package edu.neu.ccs.curiousdroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class DetermineContext {
	
	// The following arrays contain string values that help determine a specific context
	// Most of these are taken from the AppsPlayground source code: Fuzzer.java
	static final String[] OK_WORDS = {"ok", "send", "next", "continue", "accept", "submit", "done", "agree", "finish", "connect", 
		/*Russian*/ "в порядке", "отправить", "рядом", "продолжать", "принять", "представить", "сделано", "согласен", "отделка", "подключить", "Далее",
		/*Japanese*/ "送信", "次", "続ける", "受け入れる", "提出する", "終わった", "同意する", "仕上げ", "接続する", 
		/*Korean*/ "확인", "보내", "다음", "계속", "동의", "제출", "완료", "동의", "끝", "연결", 
		/*Chinese Simple*/ "确定", "行", "好", "发送", "下一个", "继续", "接受", "提交", "结束", "同意",
		/*Chinese Traditional*/ "確定", "行", "好", "發送", "下一個", "繼續", "接受", "提交", "結束", "同意"};
	static final String[] CANCEL_WORDS = {"cancel", "back", "quit", "previous", "decline", "revert", "discard", "deny", "reject", "exit",
		/*Russian*/ "отменить", "назад", "бросить", "предыдущая", "снижение", "вернуться", "отбрасывания", "отказать", "отклонить", "выхо",
		/*Japanese*/"前", "衰退", "元に戻す", "破棄", "拒否", "拒否", "出口", "終了", "戻る", "キャンセル",
		/*Korean*/ "취소", "백", "종료", "이전", "쇠퇴", "복귀", "폐기", "거부", "거부", "출구",
		/*Chinese*/ "取消", "後退", "后退", "退出", "上一頁", "上一页", "恢復", "丟棄", "拒絕", "退回", "下降", "还原", "丢弃", "否认", "拒绝"};
	static final String[] REGISTER_WORDS = {"register", "sign up",
		/*Russian*/ "зарегистрироваться", "зарегистрироваться","Далее",
		/*Japanese*/ "登録する", "サインアップ",
		/*Korean*/ "레지스터", "가입",
		/*chinese*/ "注册", "报名", "註冊", "報名"};
	static final String[] EMAIL_WORDS = {"email",
		/*Russian*/"по электронной почте", "электронная почта",
		/*Japanese*/"メール",
		/*Korean*/"이메일",
		/*Chinese Simplified*/"電子郵件",
		/*Chinese Traditional*/"电子邮件"};
	static final String[] ADDRESS_WORDS = {"address", "street"};
	static final String[] NAME_WORDS = {"name", "first", "last", "middle", "initial"};
	static final String[] PHONE_WORDS = {"phone", "number", "cell", "home", "fax", "work", "office"};
	static final String[] CITY_WORDS = {"city", "location"};
	static final String[] STATE_WORDS = {"state"};
	static final String[] ZIPCODE_WORDS = {"zip", "code"};
	static final String[] YEAR_WORDS = {"year"};
	static final String[] DAY_WORDS = {"day", "date"};
	static final String[] MONTH_WORDS = {"month"};
	static final String[] AGE_WORDS = {"age", "year", "birth"};
	static final String[] PASSWORD_WORDS = {"password",
		/*Russian*/"пароль","пароля", "пароль Номер", "паролем",
		/*Japanese*/"パスワード", "のパスワード", "パスワードを", "パスワードの",
		/*Korean*/"암호", "비밀번호", "패스워드", "비밀 번호", "암호를",
		/*Chinese Simplified*/"密码", "口令", "的密码", "密码了", 
		/*Chinese Traditional*/"密碼", "口令", "的密碼", "密碼了"};
	static final String[] USERNAME_WORDS = {"username", "userid", "user",
		/*Russian*/"пользователь", "пользователю", "имя пользователя", "имени пользователя", "идентификатор пользователя", "ИД пользователя",
		/*Japanese*/"ユーザー", "ユーザは", "ユーザ名", "のユーザ名", "ユーザーID",
		/*Korean*/"사용자", "유저", "이름", "아이디", "사용자 명", 
		/*Chinese Simplified*/"用户", "使用者", "用户名", "帐号", "的用户名", "用户ID", "用户标识", 
		/*Chinese Traditional*/"用戶", "使用者", "用戶名", "的用戶名", "用戶標識"};
	static final String[] LOGIN_WORDS = {"profile", "sign in", "login"};
	static final String[] IGNORE_WORDS = {"zoom", "preferences", "settings", "options", "sign out", "signout", "logout", "log out"};
	static final String[] BIRTH_WORDS = {"birthday", "birth", "born"};
	
	// This set of strings are context strings for buttons
	static final int OK = 1;
	static final int REGISTER = 0;
	static final int ACCEPT = 0;
	static final int CANCEL = 500;
	static final int LOGIN = 2;
	static final int FACEBOOK = 3;
	static final int IGNORE = 1000;
	static final int UNKNOWN = 4;
	
	// These are meant to be filled into editable text fields
	public static final String EMAIL = "neu.curiousdroid@gmail.com";
	public static final String ADDRESS = "360 Huntington Ave";
	public static final String CITY = "Boston";
	public static final String STATE = "MA";
	public static final String ZIPCODE = "02115";
	public static final String COUNTRY = "United States";
	public static final String NAME = "Curious Droid";
	public static final String FIRST_NAME = "Curious";
	public static final String LAST_NAME = "Droid";
	public static final String USERNAME = "curiousdroid12345";
	public static final String PHONE = "6173738493";
	public static final String PASSWORD = "asdf1234";
	public static final String BIRTH_MONTH = "10";
	public static final String BIRTH_DAY = "13";
	public static final String BIRTH_YEAR = "1984";
	public static final String BIRTHDAY = BIRTH_MONTH + "-" + BIRTH_DAY + "-" + BIRTH_YEAR;
	public static final String AGE = "29";
	public static final String DEFAULT = "banana";
	
	
	public static ArrayList<ViewNode> order(ArrayList<ViewNode> views) {
		// first, find touchables that are on the current screen.  For ones that aren't, there are a 2 ways (that I can think of now) 
		// to deal with them: 1) scroll down to them, 2) "next" to them using keyboard.  Either way will involve a little bit of thinking.
		PriorityQueue<ViewPositionComparator> visible = new PriorityQueue<ViewPositionComparator>();
		View tmp = null;
		for(int i = 0; i < views.size(); i++) {
			tmp = views.get(i).getView();
			visible.add(new ViewPositionComparator(tmp));
		}

        View tmpv = null;
		ArrayList<ViewNode> flist = new ArrayList<ViewNode>(visible.size());
        HashMap<View,ViewNode> viewmap = new HashMap<View,ViewNode>();
        int pos = 0;
		while(!visible.isEmpty()){
            tmpv = visible.poll().view;
            viewmap.put(tmpv, new ViewNode(tmpv));
            flist.add(pos++, viewmap.get(tmpv));
        }
		boolean hasNext = true;
		ArrayList<ViewNode> ordered = new ArrayList<ViewNode>();
		if(flist.size()>0) {
			ViewNode top = flist.remove(0);
			int index = 0;
            ordered.add(index, top);
            viewmap.remove(top.getView());
			View tmp2;
			while(hasNext) {
                if ((tmp2 = top.getView().focusSearch(View.FOCUS_LEFT)) != null) {
                    if(viewmap.containsKey(tmp2)) {
                        top = viewmap.get(tmp2);//new ViewNode(tmp2);
                        viewmap.remove(tmp2);
                    }
                    else {
                        top = flist.remove(0);
                        viewmap.remove(top.getView());
                        ordered.add(++index, top);
                        //continue;
                    }
                    top.setTakesNextFocus(true);
                    flist.remove(top);
                    ordered.add(++index, top);
                } 
                else if ((tmp2 = top.getView().focusSearch(View.FOCUS_DOWN)) != null) {
                    if(viewmap.containsKey(tmp2)) {
                        top = viewmap.get(tmp2);
                        viewmap.remove(tmp2);
                    }
                    else {
                        top = flist.remove(0);
                        viewmap.remove(top.getView());
                        ordered.add(++index, top);
                    }
                    top.setTakesNextFocus(true);
                    flist.remove(top);
                    ordered.add(++index, top);
                } else {
                    if (flist.size() > 0)
                        top = flist.remove(0);
                    viewmap.remove(top.getView());
                    hasNext = true;
                    top.setTakesNextFocus(false);
                    ordered.add(++index, top);
                }
                if (flist.size() == 0 || viewmap.size() == 0) {
                    hasNext = false;
                }
				
			}
			return ordered;
		}
		return flist;
	}
	
	

	
	
	public static String findEditableTextContext(View view) {
		String indicator = "";
		// First, I need to find out if view has any text of it's own.  If not, I need to check parent/adjacent 
		// Views for text that might indicate what the current view is looking for
		String mtext = "";
		try {
			mtext = ((EditText)view).getHint().toString();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//Log.i("CuriousDroid", "mtext: " + mtext);
		if(mtext != null && !mtext.equals(""))
			indicator = searchEditableContextStrings(mtext);
		else {
			Log.i("CuriousDroid", "No Hint available.  Generating context from surrounding views");
			int[] coords = new int[2]; 
			int[] tcoords = new int[2];
			view.getLocationOnScreen(coords);
			int width = view.getWidth()/2;
			int height = view.getHeight()/2;
			coords[0] += width;
			coords[1] += height;
			
			List<View> views = getSiblings(view);
			//Log.i("CuriousDroid", "Siblings: " + views.size());
			PriorityQueue<TextFieldComparator> pqueue = new PriorityQueue<TextFieldComparator>();
			//Log.i("CuriousDroid", "Just created PriorityQueue.  About to step through and fill it.");
			for(int i = 0; i < views.size(); i++) {
				if(views.get(i) == view){
					//Log.i("CuriousDroid", "I'm the current sibling. Skipping.");
					continue;
				}
				
				String fieldType = views.get(i).getClass().getSimpleName();
				//Log.i("CuriousDroid", "View type: " + fieldType);
				if(!fieldType.toLowerCase().contains("text"))
					continue;
				//Log.i("CuriousDroid", "Getting onScreenCoords.");
				views.get(i).getLocationOnScreen(tcoords);
				tcoords[0] += views.get(i).getWidth()/2;
				tcoords[1] -= views.get(i).getHeight()/2;
				//Log.i("CuriousDroid", "Just got onScreenCoords. About to check position and add to proirity queue.");
				
				
				if(tcoords[0]<coords[0] && tcoords[1]<coords[1]) {
					pqueue.add(new TextFieldComparator(views.get(i), tcoords[0], tcoords[1], 100));
					//Log.i("CuriousDroid", "Added northwest.");
				
				}if(tcoords[0]<coords[0] && tcoords[1]==coords[1]) {
					pqueue.add(new TextFieldComparator(views.get(i), tcoords[0], tcoords[1], 10000));
					//Log.i("CuriousDroid", "Added west.");
				
				}if(tcoords[0]<coords[0] && tcoords[1]>coords[1]) {
					pqueue.add(new TextFieldComparator(views.get(i), tcoords[0], tcoords[1], 100));
					//Log.i("CuriousDroid", "Added southwest.");
				
				}if(tcoords[0]==coords[0] && tcoords[1]<coords[1]) {
					pqueue.add(new TextFieldComparator(views.get(i), tcoords[0], tcoords[1], 7500));
					//Log.i("CuriousDroid", "Added north.");
				
				}if(tcoords[0]==coords[0] && tcoords[1]==coords[1]) {
					// This really shouldn't happen.  Might be a security flaw to look out for.  Possible clickjacking.
					// Either that or my algorithm is flawed.
					//Log.i("CuriousDroid", "This view sits directly above of below the view in question. Weird.");
			
				}if(tcoords[0]==coords[0] && tcoords[1]>coords[1]) {
					pqueue.add(new TextFieldComparator(views.get(i), tcoords[0], tcoords[1], 7500));
					//Log.i("CuriousDroid", "Added south.");
				
				}if(tcoords[0]>coords[0] && tcoords[1]<coords[1]) {
					pqueue.add(new TextFieldComparator(views.get(i), tcoords[0], tcoords[1], 100));
					//Log.i("CuriousDroid", "Added northeast.");
				
				}if(tcoords[0]>coords[0] && tcoords[1]==coords[1]) {
					pqueue.add(new TextFieldComparator(views.get(i), tcoords[0], tcoords[1], 2500));
					//Log.i("CuriousDroid", "Added east.");
				
				}if(tcoords[0]>coords[0] && tcoords[1]>coords[1]) {
					pqueue.add(new TextFieldComparator(views.get(i), tcoords[0], tcoords[1], 100));
					//Log.i("CuriousDroid", "Added southeast.");
				}
					
			}
			
			if(!pqueue.isEmpty()) {
				// Loop over PriorityQueue looking for context from the most likely TextViews to add context.
				while(!pqueue.isEmpty()) {
					View tmp = pqueue.poll().view;
					//mtext = ((TextView)pqueue.poll().view).getText().toString();
					mtext = ((TextView)tmp).getText().toString();
					//Log.i("CuriousDroid", tmp.toString() + ": mtext: " + mtext);
					indicator = searchEditableContextStrings(mtext);
					//Log.i("CuriousDroid", "Indicator from DetermineText = " + indicator);
					if(!indicator.equals(DEFAULT)) {
						//Log.i("CuriousDroid", "indicator: " + indicator);
						break;
					}
				}
			}
			else
				indicator = DEFAULT;
		}
		return indicator;
	}
	
	
	/**
	 * 
	 * @param keyword: any text associated with an editable text-field.  It could be default text from the 
	 * 		field itself or text from an adjacent text field that has been determined to lend context to the
	 * 		editable text field we're trying to fill in. 
	 * @return
	 */
	public static String searchEditableContextStrings(String keyword) {
		if(searchStringArray(EMAIL_WORDS, keyword.toLowerCase()))
			return EMAIL;
		else if(searchStringArray(ADDRESS_WORDS, keyword.toLowerCase()))
			return ADDRESS;
		else if(searchStringArray(USERNAME_WORDS, keyword.toLowerCase()))
			return USERNAME;
		else if(searchStringArray(NAME_WORDS, keyword.toLowerCase())) {
			if(keyword.toLowerCase().contains("last"))
				return LAST_NAME;
			else if(keyword.toLowerCase().contains("first"))
				return FIRST_NAME;
			else if(keyword.toLowerCase().contains("middle"))
				return "";
			else if(keyword.toLowerCase().contains("username"))
				return USERNAME;
			else
				return "Curious Droid";
		}
		else if(searchStringArray(PHONE_WORDS, keyword.toLowerCase()))
			return PHONE;
		else if(searchStringArray(CITY_WORDS, keyword.toLowerCase()))
			return CITY;
		else if(searchStringArray(STATE_WORDS, keyword.toLowerCase()))
			return STATE;
		else if(searchStringArray(ZIPCODE_WORDS, keyword.toLowerCase()))
			return ZIPCODE;
		else if(searchStringArray(YEAR_WORDS, keyword.toLowerCase()))
			return BIRTH_YEAR;
		else if(searchStringArray(DAY_WORDS, keyword.toLowerCase())) {
			if(keyword.toLowerCase().contains("day"))
				return BIRTH_DAY;
			else
				return BIRTHDAY;
		}
		else if(searchStringArray(MONTH_WORDS, keyword.toLowerCase()))
			return BIRTH_MONTH;
		else if(searchStringArray(AGE_WORDS, keyword.toLowerCase()))
			return AGE;
		else if(searchStringArray(PASSWORD_WORDS, keyword.toLowerCase()))
			return PASSWORD;
		else if(searchStringArray(BIRTH_WORDS, keyword.toLowerCase()))
			return BIRTHDAY;
		else
			return DEFAULT;
	}
	
	/**
	 * Description: Thus method determines the context of the button passed in and returns a corresponding string
	 * @param Button butt
	 * @return String: button context
	 */
	public static int searchButtonContext(Button butt) {
		String buttText = (String)butt.getText();
		Log.i("CuriousDroid", "Button text: " +  buttText);
		if(searchStringArray(OK_WORDS, buttText.toLowerCase())) {
			if(buttText.toLowerCase().contains("facebook"))
				return FACEBOOK;
			else
				return OK;
		}
		if(searchStringArray(REGISTER_WORDS, buttText.toLowerCase()))
			return REGISTER;
		if(searchStringArray(LOGIN_WORDS, buttText.toLowerCase()))
			return LOGIN;
		if(searchStringArray(CANCEL_WORDS, buttText.toLowerCase()))
			return CANCEL;
		if(searchStringArray(IGNORE_WORDS, buttText.toLowerCase()))
			return IGNORE;
		return UNKNOWN;
	}
	
	
	/*public static int searchImageButtonContext(ImageButton butt) {
		String buttText = "";
		buttText += (String)butt.getContentDescription();
		Log.i("CuriousDroid", "ImageButton text: " +  buttText);
		if(searchStringArray(OK_WORDS, buttText.toLowerCase()))
			return OK;
		if(searchStringArray(LOGIN_WORDS, buttText.toLowerCase()))
			return LOGIN;
		if(searchStringArray(CANCEL_WORDS, buttText.toLowerCase()))
			return CANCEL;
		if(searchStringArray(IGNORE_WORDS, buttText.toLowerCase()))
			return IGNORE;
		return UNKNOWN;
	}*/
	
	public static int searchCheckboxContext(CheckBox cb) {
		String cbtext = cb.getText().toString();
		if(searchStringArray(OK_WORDS, cbtext.toLowerCase()))
			return OK;
		if(searchStringArray(CANCEL_WORDS, cbtext.toLowerCase()))
			return CANCEL;
		return UNKNOWN;
	}
	
	public static int searchRadioButtonContext(RadioButton rb) {
		String cbtext = rb.getText().toString();
		if(searchStringArray(OK_WORDS, cbtext.toLowerCase()))
			return OK;
		if(searchStringArray(CANCEL_WORDS, cbtext.toLowerCase()))
			return CANCEL;
		return UNKNOWN;
	}
	
	/**
	 * Description: A simple method for searching a string[].  Returns true if the desired string is found.
	 * @param contextArray - String[] to search
	 * @param keyword - string to search for
	 * @return boolean
	 */
	public static boolean searchStringArray(String[] contextArray, String keyword) {
		for(String s: contextArray) {
			if(keyword.contains(s))
				return true;
		}
		return false;
	}
	
	
	public static ArrayList<View> getSiblings(View view) {
		ViewGroup parent = (ViewGroup)view.getParent();
		ArrayList<View> siblings = new ArrayList<View>();
		for(int i = 0; i < parent.getChildCount(); i++)
			siblings.add(i, parent.getChildAt(i));
		return siblings;
	}

}
