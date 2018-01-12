package com.blackmanatee.manatb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blackmanatee.lagoon.DeleteAdapter;
import android.util.*;

/**
 * Created by DeCorben on 6/2/2017.
 */

public class TableListActivity extends Activity{
	public static final boolean debug = false;
	public static final int EDIT_CONTRACT = 1;

	@Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.table_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView lv = (ListView) findViewById(R.id.tableList);
        lv.setAdapter(new DeleteCursorAdapter(this,getContentResolver().query(Uri.parse("content://com.blackmanatee.manatb.provider/meta"),new String[]{"name"},"",null,"name ASC"),TableEditActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.db_menu, menu);
        return true;
    }

    @Override
	protected void onActivityResult(int req,int res,Intent in){
    	if(req == EDIT_CONTRACT && res == Activity.RESULT_OK){
			((DeleteCursorAdapter)((ListView)findViewById(R.id.tableList)).getAdapter()).notifyDataSetChanged();
		}
	}

	public void addClick(MenuItem m){
		startActivityForResult(new Intent(this,TableEditActivity.class),TableListActivity.EDIT_CONTRACT);
	}
	
	public void editClick(View v){
		Intent in = new Intent(this,TableViewActivity.class);
		in.putExtra("name",((TextView)v).getText().toString());
		startActivityForResult(in,TableListActivity.EDIT_CONTRACT);
	}
	
	public void deleteClick(View v){
		//ManaTB tb = ManaTB.get(null);
		String item = v.getContentDescription().toString();
		//tb.deleteTable(item);
		//DeleteAdapter da = (DeleteAdapter)((ListView)findViewById(R.id.tableList)).getAdapter();
		//da.remove(item);
		//da.notifyDataSetChanged();
	}
}
