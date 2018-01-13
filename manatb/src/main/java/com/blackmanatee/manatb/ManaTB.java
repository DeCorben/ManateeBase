package com.blackmanatee.manatb;

import java.io.File;
import java.io.IOException;
import java.util.*;
import android.content.*;
import android.content.res.XmlResourceParser;
import android.database.sqlite.*;
import android.util.*;

import static com.blackmanatee.lagoon.LagoonParser.*;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ManaTB {
	//Needs:
	//contract table
	//-update on delete
	//--id column is retaining rows causing non-deletes
	//multiple tables
	//multiple database files

    private static final boolean debug = false;
	//public static final String MANA_DB = "manat.db";
	//public static final Contract META =
	//	new Contract("meta",new String[]{"name","cols","types","weights","labels"},new int[]{0,0,0,0,0},new int[]{1,1,1,1,1},new String[]{"Name","Columns","Types","Weights","Labels"});

    //private static ManaTB me;
	//private static Context context;
    private static String db;
    private static HashMap<String,Contract> tables;

    public ManaTB(){
        db = "";
        tables = new HashMap<>();
    }

    public ManaTB(XmlResourceParser res) throws XmlPullParserException, IOException {
        while(res.getEventType() != XmlPullParser.END_DOCUMENT){
            switch(res.next()){
                case XmlPullParser.START_TAG:
                    if(res.getName().equals("contract"))
                        addTable(new Contract(res));
                    break;
                case XmlPullParser.TEXT:
                    setDb(res.getText());
                    break;
            }
        }
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof ManaTB))
            return false;
        ManaTB comp = (ManaTB)o;
        if(!db.equals(comp.getDb()))
            return false;
        for(String t:tables.keySet()){
            Contract c = comp.getTable(t);
            if(c == null)
                return false;
            if(!c.equals(getTable(t)))
                return false;
        }
        return true;
    }

    public ManaTB(SharedPreferences p){
        db = "";
        tables = new HashMap<String,Contract>();
		//context = c;
		//loadMeta();
		if(p != null)
			loadContracts(p);
    }

    public void loadXml(XmlResourceParser res){

    }

    public void saveContracts(SharedPreferences.Editor pref){
        String list = "";
        for(String s:tables.keySet()){
            list += ";"+s;
            Contract c = getTable(s);
            pref.putString(s,c.toXml());
        }
        pref.putString("contractList",list.substring(1));
        pref.commit();
	}

	public void loadContracts(SharedPreferences pref){
        if(debug)
            System.out.println("loadContracts");
        //assemble list of tables
        String[] table_list = pref.getString("contractList","").split(";");
        //parse tables
        for(String t:table_list){
            if(debug)
                System.out.println("adding table:"+t);
            addTable(new Contract(parse(pref.getString(t, ""))));
        }
	}

    public void setDb(String d){
        db = d;
    }

    public String getDb(){
        return db;
    }

    public void addTable(Contract c){
        tables.put(c.getName(),c);
		if(debug){
			System.out.println("added contract:"+tables.get(c.getName()).getName());
		}
    }

    public Contract getTable(String t){
        return tables.get(t);
    }
	
	public void deleteTable(String t){
		if(debug)
			System.out.println("ManaTB Marco "+t);
		tables.remove(t);
	}

    public Contract getDefaultTable(){
        for(String k:tables.keySet()){
            return tables.get(k);
        }
        return new Contract();
    }
	
	public ArrayList<String> getTableList(){
		ArrayList<String> l = new ArrayList<>();
		for(String s:tables.keySet()){
			l.add(s);
		}
		return l;
	}
}
