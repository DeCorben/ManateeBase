package com.blackmanatee.manatb;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.blackmanatee.lagoon.DeleteAdapter;

/**
 * Created by DeCorben on 6/2/2017.
 */

public class TableListFragment extends Fragment {
	public static final boolean debug = false;
	public ManaTB tb;

	//menu handling doesn't appear to work via layout reference

	@Override
	public void onCreate(Bundle state){
		super.onCreate(state);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup vg, Bundle state) {
        return inf.inflate(R.layout.table_view,vg,false);
    }

    @Override
	public void onResume() {
        super.onResume();
        try {
			tb = new ManaTB(getResources().getXml(R.xml.manatb));
		}
		catch(Exception ex){
        	ex.printStackTrace(System.out);
        	tb = new ManaTB();
		}
        ListView lv = (ListView) getActivity().findViewById(R.id.tableList);
        lv.setAdapter(new DeleteAdapter(getActivity(),R.layout.item_with_del,tb.getTableList(),TableEditActivity.class));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inf){
        inf.inflate(R.menu.db_menu, menu);
    }

	public void addClick(MenuItem m){
		startActivity(new Intent(getActivity(),TableEditActivity.class));
	}
	
	public void editClick(View v){
		Intent in = new Intent(getActivity(),TableViewActivity.class);
		in.putExtra("name",((TextView)v).getText().toString());
		startActivity(in);
	}
	
	public void deleteClick(View v){
		String item = v.getContentDescription().toString();
		tb.deleteTable(item);
		DeleteAdapter da = (DeleteAdapter)((ListView)getActivity().findViewById(R.id.tableList)).getAdapter();
		da.remove(item);
		da.notifyDataSetChanged();
	}
}
