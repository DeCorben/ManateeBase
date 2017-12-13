package com.blackmanatee.manatb;
import android.provider.*;
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
					if("column".equals(tag.peek()))
						c = new Column();
					if(debug)
						System.out.println("Start tag:"+parse.getName());
					break;
				case XmlPullParser.TEXT:
					switch(tag.peek()){
						case "table_name":
							if(debug)
								System.out.println("table_name:"+parse.getText());
							table = parse.getText();
							break;
						case "column_name":
							if(debug)
								System.out.println("column_name:"+parse.getText());
							c.setName(parse.getText());
							break;
						case "type":
							if(debug)
								System.out.println("type:"+parse.getText());
							if(parse.getText().equals("string"))
								c.setType(Contract.T_TEXT);
							else if(parse.getText().equals("integer"))
								c.setType(Contract.T_INT);
							break;
						case "label":
							if(debug)
								System.out.println("label:"+parse.getText());
							c.setLabel(parse.getText());
							break;
						case "weight":
							if(debug)
								System.out.println("weight:"+parse.getText());
							c.setWeight(Integer.parseInt(parse.getText()));
							break;
						case "show_column":
							if(debug)
								System.out.println("show_column:"+parse.getText());
							c.setShow(Boolean.parseBoolean(parse.getText()));
							break;
						case "primary":
							if(debug)
								System.out.println("primary:"+parse.getText());
							c.setPrim(Boolean.parseBoolean(parse.getText()));
							break;
					}
					break;
				case XmlPullParser.END_TAG:
					if(tag.peek().equals("column"))
						addColumn(c);
					tag.pop();
					if(debug)
						System.out.println("End tag:"+parse.getName());
					break;
				case XmlPullParser.END_DOCUMENT:
					if(debug)
						System.out.println("End Document:"+parse.getName());
					parsing = false;
					break;
			}
		}
		System.out.println("Xml:"+toXml());
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
