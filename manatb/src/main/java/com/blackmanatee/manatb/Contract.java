package com.blackmanatee.manatb;
import android.provider.*;

import com.blackmanatee.lagoon.Tag;

import org.xmlpull.v1.*;
import java.io.*;
import java.util.*;

public class Contract implements BaseColumns{
	//needs:
	//handling when setters have larger arrays than grid
	//weights need defaults when columns are added
	//full type constants

	private static final boolean debug = true;

	public static final int T_TEXT = 0;
	public static final int T_INT = 1;
	
	protected ArrayList<Column> grid;
	protected String table;

	public Contract(){
		init("",new String[]{},new int[]{},new int[]{},new String[]{});
	}

	public Contract(String n,String[] c,int[] t){
		int[] l = new int[c.length];
		for(int z=0;z < l.length;z++) l[z] = 1;
		init(n,c,t,l,c);
	}
	
	public Contract(String n,String[] c,int[] t,int[] w,String[] h){
		init(n,c,t,w,h);
	}

	public Contract(Tag t){
		grid = new ArrayList<>();
		for(Tag c:t.getTags()){
			if("table_name".equals(c.getTag_name()))
				table = c.getContent();
			else if("column".equals(c.getTag_name()))
				grid.add(new Column(c));
		}
	}

	private void init(String n,String[] c,int[] t,int[] w,String[] h){
		table = n;
		grid = new ArrayList<>();
		for(int z=0;z<c.length;z++){
			boolean pk = z==0?true:false;
			addColumn(new Column(c[z],t[z],h[z],w[z],true,pk));
		}
	}

	public String toXml(){
		String out = "<contract>\n";
		if(table != null)
			out += "\t<table_name>"+table+"</table_name>\n";
		for(Column c:grid){
			out += "\t"+c.toXml().replaceAll("\n","\n\t")+"\n";
		}
		return out + "</contract>";
	}

	public String[] getColumnList(){
		String[] out = new String[getColCount()];
		for(int z=0;z<getColCount();z++){
			out[z] = grid.get(z).getName();
		}
		return out;
	}
	
	public void addColumn(Column c){
		grid.add(c);
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof Contract))
			return false;
		Contract comp = (Contract) o;
		if (!table.equals(comp.getName()))
			return false;
		if (!grid.equals(comp.getColumns()))
			return false;
		return true;
	}

	public void setName(String n){
		table = n;
	}

	public String getName(){
		return table;
	}

	public void setColumns(ArrayList<Column> c){
		grid = c;
	}

	public ArrayList<Column> getColumns(){
		return grid;
	}

	public String getColumn(int i){
		return grid.get(i).getName();
	}

	public int getColCount(){
		return grid.size();
	}

	public void setTypes(int[] t){
		for(int z=0;z<t.length;z++){
			grid.get(z).setType(t[z]);
		}
	}

	public int getType(int i){
		return grid.get(i).getType();
	}

	public void setLayoutWeights(int[] w){
		for(int z=0;z<w.length;z++){
			grid.get(z).setWeight(w[z]);
		}
	}

	public void setLabels(String[] l){
		 for(int z=0;z<l.length;z++){
			 grid.get(z).setLabel(l[z]);
		 }
	}
}
