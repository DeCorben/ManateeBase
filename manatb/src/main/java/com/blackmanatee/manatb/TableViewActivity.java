package com.blackmanatee.manatb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blackmanatee.lagoon.DeleteAdapter;
import android.util.*;

/**
 * Created by DeCorben on 6/2/2017.
 */

public class TableViewActivity extends Activity{
	public static final boolean debug = false;
	
	@Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.table_view);
		if(debug)
			Log.d("manaT","TableView end Create");
    }

    @Override
    protected void onResume() {
        super.onResume();
		if(debug)
			Log.d("manaT","TableView marco");
		ManaTB tb = ManaTB.get(this);
		if(debug)
			Log.d("manaT","TableView end polo");
		if(tb.getDb().equals(""))
			tb.setDb("manatbase.db");
        ListView lv = (ListView) findViewById(R.id.tableList);
        lv.setAdapter(new DeleteAdapter(this,R.layout.item_with_del,tb.getTableList(),TableEditActivity.class));
		if(debug)
			Log.d("manaT","TableView end Resume");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.db_menu, menu);
        return true;
    }

	public void addClick(MenuItem m){
		startActivity(new Intent(this,TableEditActivity.class));
	}
	
	public void metaClick(MenuItem m){
		Intent in = new Intent(this,ManaTBActivity.class);
		in.putExtra("db","meta");
		startActivity(in);
	}
	
	public void editClick(View v){
		Intent in = new Intent(this,ManaTBActivity.class);
		in.putExtra("name",((TextView)v).getText().toString());
		startActivity(in);
	}
	
	public void deleteClick(View v){
		ManaTB tb = ManaTB.get(this);
		String item = v.getContentDescription().toString();
		tb.deleteTable(item);
		DeleteAdapter da = (DeleteAdapter)((ListView)findViewById(R.id.tableList)).getAdapter();
		da.remove(item);
		da.notifyDataSetChanged();
	}
}
