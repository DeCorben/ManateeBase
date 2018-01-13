package com.blackmanatee.manatb;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.database.*;
import android.app.*;
import android.util.*;
import android.database.sqlite.*;
import java.util.*;

public class ManaTBCursorAdapter extends SimpleCursorAdapter{
	private Class<?> glove;
	private Context con;
	private String table;
	private ManaTB tb;

	public ManaTBCursorAdapter(Context co,int l,Cursor c,String t,Class<?> g){
		super(co,l,c,new String[]{},new int[]{},0);
		table = t;
		glove = g;
		con = co;
	}
	
	@Override
	public void bindView(View v,Context co,Cursor c){
		LinearLayout ll = (LinearLayout)v;
		ll.removeAllViews();
		try {
			tb = new ManaTB(con.getResources().getXml(R.xml.manatb));
		}
		catch(Exception ex){
			//stuff
		}
		Contract tab = tb.getTable(table);
		ArrayList<Column> cols = tab.getColumns();
		for(int z=0;z<cols.size();z++){
			TextView t = new TextView(con);
			switch(cols.get(z).getType()){
				case Contract.T_TEXT:
					t.setText(c.getString(z+1));
					break;
				case Contract.T_INT:
					t.setText(Integer.toString(c.getInt(z+1)));
			}
			ll.addView(t,new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,cols.get(z).getWeight()));
		}
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent){
		ViewGroup layout = (ViewGroup)LayoutInflater.from(con).inflate(R.layout.db_list,parent,false);
		return layout;
	}
}
