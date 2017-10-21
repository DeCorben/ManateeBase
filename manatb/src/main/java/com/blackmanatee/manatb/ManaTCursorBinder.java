package com.blackmanatee.manatb;
import android.widget.*;
import android.database.*;
import android.view.*;
import android.database.sqlite.*;
import android.content.*;
import android.util.*;

public class ManaTCursorBinder implements SimpleCursorAdapter.ViewBinder{
	private static final boolean debug = true;
	private String table_name;
	private Context env;
	
	public ManaTCursorBinder(Context e,Contract c){
		table_name = c.getName();
		env = e;
	}
	
	@Override
	public boolean setViewValue(View v, Cursor c, int i){
		try{
			if(debug)
				Log.d("manaT","Id:"+v.getContentDescription());
			LinearLayout l = (LinearLayout)v;
			ManaTB tb = ManaTB.get(env);
			Contract con = tb.getTable(table_name);
			SQLiteCursor cur = (SQLiteCursor)c;
			cur.moveToPosition(i);
			for(Column col:con.getColumns()){
				TextView t = new TextView(env);
				t.setText(col.getName());
				LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,col.getWeight());
				l.addView(t,parm);
			}
		}
		catch(ClassCastException ex){
			return false;
		}
		return true;
	}
}
