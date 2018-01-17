package com.blackmanatee.lagoon;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class DeleteAdapter extends ArrayAdapter<String>{
	//Needs:
	
	private Class<?> glove;
	private int layout;
	
	public DeleteAdapter(Context c,int l,List<String> d,Class<?> g){
		super(c,l,d);
		glove = g;
		layout = l;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null)
			convertView = ((Activity)getContext()).getLayoutInflater().inflate(layout,parent,false);
		convertView.findViewById(R.id.itemDelete).setContentDescription(getItem(position));
		((TextView)convertView.findViewById(R.id.itemLabel)).setText(getItem(position));
		((TextView)convertView.findViewById(R.id.itemLabel)).setOnLongClickListener(new View.OnLongClickListener(){
				@Override
				public boolean onLongClick(View v){
					Intent in = new Intent(getContext(),glove);
					in.putExtra("name",((TextView)v).getText().toString());
					((Activity)getContext()).startActivity(in);
					return true;
				}
		});
		return convertView;
	}
}
