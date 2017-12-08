package com.blackmanatee.manatb;

import android.os.Bundle;
import android.view.*;
import android.content.*;
import android.app.*;
import android.database.sqlite.*;
import android.util.*;
import android.os.*;
import java.io.*;
import android.widget.*;

public class ManaTBActivity extends Activity implements AdapterView.OnItemClickListener
	,AdapterView.OnItemLongClickListener{
	private static final boolean debug = false;
    
	//needs:
	//compatibility with META table
    //allow subset of columns from Contract for display
	//find configurable cursor solution
	//script language for multiple table updates

	private Contract schema;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_db);
    }
	
	@Override
	protected void onResume(){
		super.onResume();
		if(debug)
			Log.d("manaT","ManaTB resume");
		//retrieve database contents
		ManaTB tb = ManaTB.get(null);
		if(tb.getDb().equals(""))
			tb.setDb("manatbase.db");
		if(debug)
			Log.d("manaT","set up tb");
		ContractDbHelper schHelp = null;
		String dbName = getIntent().getStringExtra("db");
		if(dbName == null)
			dbName = "";
		/*if(dbName.equals("meta")){
			//schema = tb.META;
			//schHelp = new ContractDbHelper(this,tb.MANA_DB,schema);
		}
		else{*/
			String tab = getIntent().getStringExtra("name");
			if(debug)
				Log.d("manaT","Table:"+tab);
			schema = tab==null?tb.getDefaultTable():tb.getTable(tab);
			schHelp = new ContractDbHelper(this, tb.getDb(), schema);
		//}
		SQLiteDatabase db = schHelp.getReadableDatabase();
		SQLiteCursor cursor = (SQLiteCursor) db.query(schema.getName(), null, null, null, null, null, schema.getColumn(0) + " ASC", null);
		if(debug)
			Log.d("manaT","cursor complete");
		//format label bar
		LinearLayout labels = (LinearLayout) findViewById(R.id.labels);
		labels.removeAllViews();
		for(Column c:schema.getColumns()){
			LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,c.getWeight());
			TextView lab = new TextView(this);
			lab.setText(c.getLabel());
			labels.addView(lab,parm);
		}
		//populate List
		ListView lv = (ListView)findViewById(R.id.dbList);
		lv.setOnItemClickListener(this);
		lv.setOnItemLongClickListener(this);
		if(debug)
			Log.d("manaT","before list view adapter set");
		try {
			if(debug){
				String[] list = schema.getColumnList();
				for(String c:list){
					Log.d("manaT","Column:"+c);
				}
			}
			lv.setAdapter(new ManaTBCursorAdapter(this,R.layout.db_list,cursor,schema.getName(),ManaTBActivity.class));
		}
		catch (Exception ex) {
			Log.d("MEEP", ex.toString());
		}
		if(debug)
			Log.d("manaT","ManaTB resume complete");
	}
	
	public void addClick(MenuItem v){
		Intent in = new Intent(this,MaintainActivity.class);
		in.putExtra("table",schema.getName());
		startActivity(in);
	}
	
	public void metaClick(MenuItem m){
		
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.db_menu, menu);
        return true;
    }
	
	@Override
	public void onItemClick(AdapterView<?> a,View v,int p,long id){
		if(debug)
			Log.d("manaT","start onItemClick");
		SQLiteCursor cursor = (SQLiteCursor)((ListView)a).getItemAtPosition(p);
		cursor.moveToPosition(p);
		Intent in = new Intent(this,MaintainActivity.class);
		in.putExtra("table",schema.getName());
		//always use _id as key
		in.putExtra("key",cursor.getInt(0));
		if(debug)
			Log.d("manaT","start MaintainActivity");
		startActivity(in);
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> a,View v,int i,long id){
		SQLiteDatabase db = new ContractDbHelper(this,ManaTB.get(null).getDb(),schema).getWritableDatabase();
		SQLiteCursor cursor = (SQLiteCursor)((ListView)a).getItemAtPosition(i);
		cursor.moveToPosition(i);
		db.delete(schema.getName(),"_id = "+cursor.getInt(0),null);
		((SimpleCursorAdapter)a.getAdapter()).swapCursor((SQLiteCursor)db.query(schema.getName(),null,null,null,null,null, schema.getColumn(1)+" ASC",null));
		return true;
	}

	protected void runScript(){
		try{
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				File script = new File(getExternalFilesDir(null),"script.txt");
				if(script != null){
					SQLiteDatabase db = new ContractDbHelper(this,ManaTB.get(null).getDb(),ManaTB.get(null).getDefaultTable()).getWritableDatabase();
					BufferedReader in = new BufferedReader(new FileReader(script));
					while(in.ready()){
						db.execSQL(in.readLine());
					}
					in.close();
					FileWriter out = new FileWriter(script);
					out.write("");
					out.flush();
					out.close();
				}
			}
		}
		catch(FileNotFoundException ex){
			try{
				FileWriter script = new FileWriter(new File(getExternalFilesDir(null),"script.txt"));
				script.write("");
				script.flush();
				script.close();
			}
			catch(Exception exc){
				Log.d("ScriptError",ex.toString());
			}
		}
		catch(Exception ex){
			Log.d("ScriptError",ex.toString());
		}
	}
}
