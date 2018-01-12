package com.blackmanatee.manatb;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import java.util.HashMap;

/**
 * Created by DeCorben on 12/30/2017.
 */

public class ManaTProvider extends ContentProvider{
    public static final String AUTH = "com.blackmanatee.manatb.provider";

    private HashMap<String,Contract> contractMap;
    private ContractDbHelper conHelp;

    @Override
    public boolean onCreate() {
        contractMap = new HashMap<>();
        contractMap.put("meta",new Contract("meta",new String[]{"name","columns","types","weights","labels"},new int[]{1,1,1,1,1},new int[]{1,1,1,1,1},new String[]{"Name","Columns","Types","Weights","Labels"}));
        conHelp = new ContractDbHelper(getContext(),"manat.db",contractMap.get("meta"));
        return true;
    }

    @Override
    public Cursor query(Uri uri,String[] cols,String where,String[] arg,String sort){
        if(sort.length() == 0)
            sort = "_ID ASC";
        if(parseId(uri) >= 0)
            where += (where.length()>0?"":",")+"_ID = "+ ContentUris.parseId(uri);
        conHelp.setContract(contractMap.get(parseTable(uri)));
        return conHelp.getReadableDatabase().query(conHelp.getContract().getName(),cols,where,arg,null,null,sort);
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor."+(parseId(uri)>=0?"item":"dir")+"/vnd."+AUTH+parseTable(uri);
    }

    @Override
    public Uri insert(Uri uri,ContentValues values) {
        conHelp.setContract(contractMap.get(parseTable(uri)));
        long id = conHelp.getWritableDatabase().insert(conHelp.getContract().getName(),null,values);
        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(Uri uri,String selection,String[] selectionArgs) {
        Contract c = contractMap.get(parseTable(uri));
        conHelp.setContract(c);
        return conHelp.getWritableDatabase().delete(c.getName(),selection,selectionArgs);
    }

    @Override
    public int update(Uri uri,ContentValues values,String selection,String[] selectionArgs) {
        Contract c = contractMap.get(parseTable(uri));
        conHelp.setContract(c);
        return conHelp.getWritableDatabase().update(c.getName(),values,selection,selectionArgs);
    }

    private String parseTable(Uri u){
        return u.getPathSegments().get(0);
    }

    private int parseId(Uri u){
        try{
            return (int)ContentUris.parseId(u);
        }
        catch(NumberFormatException ex){}
        return -1;
    }
}
