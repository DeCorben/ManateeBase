package com.blackmanatee.manatb;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.*;

class ManaTBInstanceTest{
	private static ManaTB tb;
	
	@Before
	public void before(){
		tb = new ManaTB();
		tb.setDb("testTb.db");
		tb.addTable(new Contract("lorem",new String[]{"ipsum","dolor"},new int[]{0,1},new int[]{3,1},new String[]{"Ipsum","Dolor"}));
		tb.addTable(new Contract("sit",new String[]{"amet","consectetuer"},new int[]{0,0},new int[]{1,1},new String[]{"Amet","Consectetuer"}));
	}
	
	//instantiate from xml
	@Test
	public void testCreateXml(){
		ManaTB ay = new ManaTB("testTb.xml");
		assertThat(ay,is(tb));
	}
	
	//refresh/load xml
	@Test
	public void testLoadXml(){
		ManaTB ay = new ManaTB();
		assertThat(ay,not(equalTo(tb)));
		ay.loadXml("testTb.xml");
		assertThat(ay,equalTo(tb));
	}
	
	//save to xml
	@Test
	public void testToXml(){
		String base = "";
		FileReader read = new FileReader(new File(getExternalFilesDir("manatb"),"testTb.xml"));
	}
	
	//addGetContract
	
	//removeContract
	
	//setGetDbFilename?
	
	//getTableList
	
	//getDefaultTable
}
