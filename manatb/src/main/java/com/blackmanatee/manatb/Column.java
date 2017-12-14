package com.blackmanatee.manatb;

import com.blackmanatee.lagoon.Tag;

public class Column{
	private static final boolean debug = true;
	private String name,label;
	private int type,weight;
	private boolean show,prim;

	public Column(Tag t){
		for(Tag c:t.getTags()){
			switch(c.getTag_name()){
				case "column_name":
					name = c.getContent();
					break;
				case "type":
					if("string".equals(c.getContent()))
						type = Contract.T_TEXT;
					else if("integer".equals(c.getContent()))
						type = Contract.T_INT;
					break;
				case "label":
					label = c.getContent();
					break;
				case "weight":
					weight = Integer.parseInt(c.getContent());
					break;
				case "show_column":
					show = Boolean.parseBoolean(c.getContent());
					break;
				case "primary":
					prim = Boolean.parseBoolean(c.getContent());
			}
		}
	}

	public String toXml(){
		String out = "<column>\n";
		if(name != null)
			out += "\t<column_name>"+name+"</column_name>\n";
		switch(type){
			case Contract.T_TEXT:
				out += "\t<type>string</type>\n";
				break;
			case Contract.T_INT:
				out += "\t<type>integer</type>\n";
				break;
		}
		if(label != null)
			out += "\t<label>"+label+"</label>\n";
		out += "\t<weight>"+weight+"</weight>\n" +
			"\t<show_column>"+show+"</show_column>\n" +
			"\t<primary>"+prim+"</primary>\n";
		return out + "</column>";
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof Column))
			return false;
		Column comp = (Column)o;
		if(!name.equals(comp.getName()))
			return false;
		if(!label.equals(comp.getLabel()))
			return false;
		if(type != comp.getType())
			return false;
		if(weight != comp.getWeight())
			return false;
		if(show != comp.isShow())
			return false;
		if(prim != comp.isPrim())
			return false;
		return true;
	}
	
	public void setPrim(boolean prim)
	{
		this.prim = prim;
	}

	public boolean isPrim()
	{
		return prim;
	}

	public void setShow(boolean show)
	{
		this.show = show;
	}

	public boolean isShow()
	{
		return show;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getType()
	{
		return type;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getLabel()
	{
		return label;
	}
	
	private void init(String n,int t,String l,int w,boolean s,boolean p){
		name = n;
		type = t;
		label = l;
		weight = w;
		show = s;
		prim = p;
	}
	
	public Column(){
		init("",Contract.T_TEXT,"",1,true,false);
	}
	
	public Column(String n,int t,String l,int w,boolean s,boolean p){
		init(n,t,l,w,s,p);
	}
}
