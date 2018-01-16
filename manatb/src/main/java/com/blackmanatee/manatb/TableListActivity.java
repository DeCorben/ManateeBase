package com.blackmanatee.manatb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.blackmanatee.lagoon.DeleteAdapter;

/**
 * Created by DeCorben on 6/2/2017.
 */

public class TableListActivity extends Activity{
	public static final boolean debug = false;
	public ManaTB tb;

	@Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.table_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
			tb = new ManaTB(getResources().getXml(R.xml.manatb));
		}
		catch(Exception ex){
        	ex.printStackTrace(System.out);
        	tb = new ManaTB();
		}
        ListView lv = (ListView) findViewById(R.id.tableList);
        lv.setAdapter(new DeleteAdapter(this,R.layout.item_with_del,tb.getTableList(),TableEditActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.db_menu, menu);
        return true;
    }

	public void addClick(MenuItem m){
		startActivity(new Intent(this,TableEditActivity.class));
	}
	
	public void editClick(View v){
		Intent in = new Intent(this,TableViewActivity.class);
		in.putExtra("name",((TextView)v).getText().toString());
		startActivity(in);
	}
	
	public void deleteClick(View v){
		String item = v.getContentDescription().toString();
		tb.deleteTable(item);
		DeleteAdapter da = (DeleteAdapter)((ListView)findViewById(R.id.tableList)).getAdapter();
		da.remove(item);
		da.notifyDataSetChanged();
	}
}
