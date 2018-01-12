package com.blackmanatee.manatb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by DeCorben on 12/30/2017.
 */

public class DeleteCursorAdapter extends SimpleCursorAdapter{
    private Class<?> launch;

    public DeleteCursorAdapter(Context context,Cursor c,Class l) {
        super(context,R.layout.item_with_del,c,new String[]{"name"},new int[]{R.id.itemLabel},0);
        launch = l;
    }

    @Override
    public View newView(Context co,Cursor cu,ViewGroup vg){
        return LayoutInflater.from(co).inflate(R.layout.item_with_del,vg);
    }

    @Override
    public void bindView(View lay,final Context co,Cursor cu){
        lay.findViewById(R.id.itemDelete).setContentDescription(cu.getString(0));
        ((TextView)lay.findViewById(R.id.itemLabel)).setText(cu.getString(0));
        lay.findViewById(R.id.itemLabel).setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                Intent in = new Intent(co,launch);
                in.putExtra("name",((TextView)v).getText().toString());
                ((Activity)co).startActivity(in);
                return true;
            }
        });
    }
}
