package com.blackmanatee.manatb;
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class TableEditActivity extends Activity{
	private static final boolean debug = false;
	
	//Needs:
	//handle invalid input to boxes
	
	@Override
	public void onCreate(Bundle state){
		super.onCreate(state);
		setContentView(R.layout.contract);
	}

	@Override
	protected void onResume(){
		super.onResume();
		Intent in = getIntent();
		String conName = in.getStringExtra("name");
		Contract con;
		if(conName != null)
			con = ManaTB.get(this).getTable(conName);
		else
			con = new Contract();
		//populate table name
		String value = con.getName();
		String cols = "";
		String types = "";
		String labels = "";
		String weights = "";
		EditText box = (EditText)findViewById(R.id.tableName);
		SharedPreferences pref = getSharedPreferences("tableEdit",Context.MODE_PRIVATE);
		String pStore = pref.getString("tableName","");
		if(debug)
			Log.d("manatb","Stored:"+pStore);
		if(pStore.equals(""))
			if(value == null)
				box.setText("");
			else
				box.setText(value);
		else
			box.setText(pStore);
		//populate column names
		for(Column s:con.getColumns()){
			cols += ";"+s.getName();
			types += ";"+s.getType();
			labels += ";"+s.getLabel();
			weights += ";"+s.getWeight();
		}
		pStore =pref.getString("tableCols","");
		box = (EditText)findViewById(R.id.columns);
		if(pStore.equals(""))
			box.setText(cols.length()>0?cols.substring(1):"");
		else
			box.setText(pStore);
		//populate column types
		box = (EditText)findViewById(R.id.types);
		pStore =pref.getString("tableTypes","");
		if(pStore.equals(""))
			box.setText(types.length()>0?types.substring(1):"");
		else
			box.setText(pStore);
		//populate labels
		box = (EditText)findViewById(R.id.labels);
		pStore =pref.getString("tableLabels","");
		if(pStore.equals(""))
			box.setText(labels.length()>0?labels.substring(1):"");
		else
			box.setText(pStore);
		//populate weights
		box = (EditText)findViewById(R.id.weights);
		pStore =pref.getString("tableWeights","");
		if(pStore.equals(""))
			box.setText(weights.length()>0?weights.substring(1):"");
		else
			box.setText(pStore);
	}

	@Override
	protected void onPause(){
		super.onPause();
		SharedPreferences.Editor pref = getSharedPreferences("tableEdit",Context.MODE_PRIVATE).edit();
		pref.putString("tableName",((EditText)findViewById(R.id.tableName)).getText().toString());
		pref.putString("tableCols",((EditText)findViewById(R.id.columns)).getText().toString());
		pref.putString("tableTypes",((EditText)findViewById(R.id.types)).getText().toString());
		pref.putString("tableLabels",((EditText)findViewById(R.id.labels)).getText().toString());
		pref.putString("tableWeights",((EditText)findViewById(R.id.weights)).getText().toString());
		pref.commit();
	}

	public void commitClick(View v){
		String name = ((EditText)findViewById(R.id.tableName)).getText().toString();
		String cols = ((EditText)findViewById(R.id.columns)).getText().toString();
		String types = ((EditText)findViewById(R.id.types)).getText().toString();
		String labels = ((EditText)findViewById(R.id.labels)).getText().toString();
		String weights = ((EditText)findViewById(R.id.weights)).getText().toString();
		String editTable = getIntent().getStringExtra("name");
		Contract con = editTable==null?new Contract():ManaTB.get(this).getTable(editTable);
		boolean first = true;
		for(String n:cols.split(";")){
			con.addColumn(new Column(n,0,"",1,true,first?true:false));
			if(first)
				first = false;
		}
		String[] t = types.split(";");
		int[] ti = new int[t.length];
		for(int z=0;z<t.length;z++){
			ti[z] = Integer.parseInt(t[z]);
		}
		con.setTypes(ti);
		con.setLabels(labels.split(";"));
		t = weights.split(";");
		ti = new int[t.length];
		for(int z=0;z<t.length;z++){
			ti[z] = Integer.parseInt(t[z]);
		}
		con.setLayoutWeights(ti);
		if(con.getName().length() == 0){
			con.setName(name);
			ManaTB.get(this).addTable(con);
		}
		else
			con.setName(name);
		((EditText)findViewById(R.id.tableName)).setText("");
		((EditText)findViewById(R.id.columns)).setText("");
		((EditText)findViewById(R.id.types)).setText("");
		((EditText)findViewById(R.id.labels)).setText("");
		((EditText)findViewById(R.id.weights)).setText("");
		finish();
	}
	
	public void cancelClick(View v){
		((EditText)findViewById(R.id.tableName)).setText("");
		((EditText)findViewById(R.id.columns)).setText("");
		((EditText)findViewById(R.id.types)).setText("");
		((EditText)findViewById(R.id.labels)).setText("");
		((EditText)findViewById(R.id.weights)).setText("");
		finish();
	}
}
