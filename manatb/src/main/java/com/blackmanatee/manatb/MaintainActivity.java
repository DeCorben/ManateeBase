package com.blackmanatee.manatb;

import android.app.*;
import android.os.*;
import android.view.*;
import android.database.sqlite.*;
import android.content.*;
import android.widget.*;
import android.database.*;
import java.util.*;
import android.util.*;

public class MaintainActivity extends Activity{
	private static final boolean debug = true;
	//needs:
	//selectable table
	//reformatting as Fragment for tablet use
	
	protected HashMap<String,EditText> row;
	protected ManaTB tb;
	protected Contract schema;
	protected int key;
	
	@Override
	public void onCreate(Bundle state){
		super.onCreate(state);
		setContentView(R.layout.raw_maint);
		tb = ManaTB.get(null);
		String table = getIntent().getStringExtra("table");
		schema = table==null?tb.getDefaultTable():tb.getTable(table);
		row = new HashMap<>();
		key = getIntent().getIntExtra("key",0);
		SQLiteCursor curs = null;
		if(key > 0){
			ContractDbHelper conHelp = new ContractDbHelper(this,tb.getDb(),schema);
			SQLiteDatabase db = conHelp.getReadableDatabase();
			curs = (SQLiteCursor)db.query(schema.getName(),null
				,"_id = '"+key+"'"
				,null,null,null,null);
		}
		LinearLayout v = (LinearLayout)findViewById(R.id.cursorList);
		for(Column r:schema.getColumns()){
			LinearLayout i = (LinearLayout)getLayoutInflater().inflate(R.layout.column_item,v,false);
			EditText e = (EditText)i.findViewById(R.id.columnBox);
			((TextView)i.findViewById(R.id.columnLabel)).setText(r.getName());
			e.setId(r.hashCode());
			if(curs != null){
				curs.moveToFirst();
				e.setText(curs.getString(curs.getColumnIndex(r.getName())));
			}
			row.put(r.getName(),e);
			v.addView(i);
		}
	}
	
	public void addClick(View v){
		ContentValues cv = new ContentValues();
		//pull inputs
		int i = 0;
		for(Column c:schema.getColumns()){
			switch(schema.getType(i)){
				case Contract.T_TEXT:
					cv.put(c.getName(),row.get(c.getName()).getText().toString());
					break;
				case Contract.T_INT:
					try{
						cv.put(c.getName(),Integer.parseInt((row.get(c.getName()).getText().toString())));
					}
					catch(NumberFormatException ex){
						cv.put(c.getName(),0);
					}
					break;
			}
			i++;
		}
		SQLiteDatabase db = new ContractDbHelper(this,tb.getDb(),schema).getWritableDatabase();
		if(key > 0)
			db.update(schema.getName(),cv,"_id = "+key,null);
		else
			db.insert(schema.getName(),null,cv);
		if(debug)
			Log.d("manaT","finish now");
		finish();
	}
	
	public void cancelClick(View v){
		finish();
	}
	
	public void deleteClick(View v){
		SQLiteDatabase db = new ContractDbHelper(this,tb.getDb(),schema).getWritableDatabase();
		db.delete(schema.getName(),"_id = "+key,null);
		finish();
	}
}
