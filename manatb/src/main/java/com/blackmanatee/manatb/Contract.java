package com.blackmanatee.manatb;
import android.app.*;
import android.content.*;
import android.provider.*;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.blackmanatee.manatb.*;
import com.blackmanatee.lagoon.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;

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

	public Contract(String x) throws Exception{
		XmlPullParser parse = XmlPullParserFactory.newInstance().newPullParser();
		parse.setInput(new StringReader(x));
		ArrayDeque<String> tag = new ArrayDeque<>();
		boolean parsing = true;
		Column c = null;
		while(parsing){
			switch(parse.next()){
				case XmlPullParser.START_TAG:
					tag.push(parse.getName());
					break;
				case XmlPullParser.TEXT:
					switch(tag.peek()){
						case "table_name":
							table = parse.getText();
						case "column":
							c = new Column();
						case "column_name":
							c.setName(parse.getText());
						case "type":
							if(parse.getText().equals("string"))
								c.setType(Contract.T_TEXT);
							else if(parse.getText().equals("integer"))
								c.setType(Contract.T_INT);
						case "label":
							c.setLabel(parse.getText());
						case "weight":
							c.setWeight(Integer.parseInt(parse.getText()));
						case "show_column":
							c.setShow(Boolean.parseBoolean(parse.getText()));
						case "primary":
							c.setPrim(Boolean.parseBoolean(parse.getText()));
					}
					break;
				case XmlPullParser.END_TAG:
					if(tag.peek().equals("column"))
						addColumn(c);
					tag.pop();
					break;
				case XmlPullParser.END_DOCUMENT:
					parsing = false;
					break;
			}
		}
		System.out.println(toXml());
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
