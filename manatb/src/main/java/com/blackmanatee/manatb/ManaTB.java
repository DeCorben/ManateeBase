package com.blackmanatee.manatb;

import java.io.File;
import java.util.*;
import android.content.*;
import android.database.sqlite.*;
import android.util.*;

import org.xmlpull.v1.XmlPullParser;

public final class ManaTB {
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

    private static ManaTB me;
	//private static Context context;
    private static String db;
    private static HashMap<String,Contract> tables;

    public ManaTB(SharedPreferences p){
        db = "";
        tables = new HashMap<String,Contract>();
		//context = c;
		//loadMeta();
		if(p != null)
			loadContracts(p);
    }

    public static ManaTB get(SharedPreferences p){
        if(me == null) {
            if(debug)
                System.out.println("constructing ManaTB");
            me = new ManaTB(p);
        }
        return me;
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
            try {
                if(debug)
                    System.out.println("adding table:"+t);
                addTable(Contract.parseContract(pref.getString(t, "")));
            }
            catch(Exception ex){
				System.out.println(ex.toString());
			}
        }
	}

    public static void clear(){
    	me = null;
    	//File f = new File("/data/data/com.blackmanatee.manatb/databases/manat.db");
    	//f.delete();
	}
	
	/*private void loadMeta(){
		ContractDbHelper db = new ContractDbHelper(context,MANA_DB,META);
		SQLiteCursor cur = (SQLiteCursor)db.getReadableDatabase().query(META.getName(),null,null,null,null,null,META.getColumn(1)+" ASC",null);
		cur.moveToFirst();
		while(!cur.isAfterLast()){
			String[] temp = cur.getString(3).split(";");
			int[] ty = new int[temp.length];
			for(int z=0;z<temp.length;z++){
				ty[z] = Integer.parseInt(temp[z]);
			}
			temp = cur.getString(4).split(";");
			int[] w = new int[temp.length];
			for(int z=0;z<temp.length;z++){
				w[z] = Integer.parseInt(temp[z]);
			}
			tables.put(cur.getString(1),new Contract(cur.getString(1),cur.getString(2).split(";"),ty,w,cur.getString(5).split(";")));
			cur.moveToNext();
		}
		cur.close();
	}*/

    public void setDb(String d){
        db = d;
    }

    public String getDb(){
        return db;
    }

    public void addTable(Contract c){
        tables.put(c.getName(),c);
		if(debug){
			Log.d("manaT","added contract:"+tables.get(c.getName()).getName());
		}
		//update contract table
		/*SQLiteDatabase db = new ContractDbHelper(context,MANA_DB,META).getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("name",c.getName());
		String cols = "";
		String types = "";
		String weights = "";
		String labels = "";
		for(Column s:c.getColumns()){
			cols += ";"+s.getName();
			types += ";"+s.getType();
			weights += ";"+s.getWeight();
			labels += ";"+s.getLabel();
		}
		cv.put("cols",cols.substring(1));
		cv.put("types",types.substring(1));
		cv.put("weights",weights.substring(1));
		cv.put("labels",labels.substring(1));
		SQLiteCursor cur = (SQLiteCursor)db.query(META.getName(),null,"name = '"+c.getName()+"'",null,null,null,null,null);
		db.beginTransaction();
		if(cur.getCount() > 0){
			if(debug)
				Log.d("manaT","update add");
			db.update(META.getName(),cv,"name = '"+c.getName()+"'",null);
		}
		else{
			if(debug)
				Log.d("manaT","insert add");
			long result = db.insert(META.getName(),null,cv);
			if(debug)
				Log.d("manaT","result:"+result);
		}
		db.endTransaction();
		if(debug){
			cur = (SQLiteCursor)db.query(META.getName(),null,"name = '"+c.getName()+"'",null,null,null,null,null);
			cur.moveToFirst();
			if(cur.getCount() > 0)
				Log.d("manaT","added to meta:"+cur.getString(1));
			else
				Log.d("manaT","add failed");
		}
		cur.close();*/
    }

    public Contract getTable(String t){
        return tables.get(t);
    }
	
	public void deleteTable(String t){
		if(debug)
			Log.d("manaT","ManaTB Marco "+t);
		tables.remove(t);
		//update contract table
		/*if(debug)
			Log.d("manaT","ManaTB delete: begin meta update");
		SQLiteDatabase db = new ContractDbHelper(context,MANA_DB,META).getWritableDatabase();
		db.beginTransaction();
		db.delete(META.getName(),"name = '"+t+"'",null);
		if(debug)
			Log.d("manaT","ManaTB delete: end meta update");
		db.endTransaction();
		//delete actual table
		if(debug){
			Log.d("manaT","begin meta check");
			SQLiteCursor cur = (SQLiteCursor)db.query(META.getName(),null,"name = '"+t+"'",null,null,null,null,null);
			if(cur.getCount() == 0)
				Log.d("manaT","meta delete successful");
			else{
				cur.moveToFirst();
				Log.d("manaT","Row:"+cur.getString(1)+"/"+cur.getString(2)+"/"+cur.getString(3)+"/"+cur.getString(4)+"/"+cur.getString(5));
			}
			Log.d("manaT","end meta check");
		}
		db.close();
		if(debug)
			Log.d("manaT","ManaTB delete: begin table update");
		db = new ContractDbHelper(context,getDb(),getTable(t)).getWritableDatabase();
		db.beginTransaction();
		if(debug)
			Log.d("manaT","begin rows delete");
		db.delete(t,null,null);
		if(debug){
			Log.d("manaT","end rows delete");
			SQLiteCursor cur = (SQLiteCursor)db.query(t,null,null,null,null,null,null,null);
			if(cur.getCount() == 0)
				Log.d("manaT","row delete successful");
			else
				Log.d("manaT","rows remain");
			Log.d("manaT","begin table drop");
		}
		db.execSQL("DROP TABLE "+t);
		db.endTransaction();
		if(debug){
			//try{
				Log.d("manaT","cursor check");
				SQLiteCursor cur = (SQLiteCursor)db.query(t,null,null,null,null,null,null,null);
			}
			catch(SQLiteException ex){
				Log.d("manaT","table dropped");
			}
			Log.d("manaT","end table drop");
			Log.d("manaT","ManaTB delete: end table update");
		}
		db.close();
		if(debug)
			Log.d("manaT","ManaTB Polo");*/
	}

    public Contract getDefaultTable(){
        for(String k:tables.keySet()){
            return tables.get(k);
        }
        return null;
    }
	
	public ArrayList<String> getTableList(){
		ArrayList<String> l = new ArrayList<>();
		for(String s:tables.keySet()){
			l.add(s);
		}
		return l;
	}
}
