package com.blackmanatee.manatb.test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static com.blackmanatee.lagoon.Sugar.echo;
import org.junit.*;
import java.util.ArrayList;
import com.blackmanatee.manatb.*;

public class ManaTBInstanceTest extends ShellCase{
	private static ManaTB tb;
	private static final String DATA = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
	"<manatb>\n" +
	"    <db_name>testTb.db</db_name>\n" +
	"    <contract>\n" +
	"        <table_name>lorem</table_name>\n" +
	"        <column>\n" +
	"            <column_name>ipsum</column_name>\n" +
	"            <type>string</type>\n" +
	"            <label>Ipsum</label>\n" +
	"            <weight>3</weight>\n" +
	"            <show_column>true</show_column>\n" +
	"            <primary>true</primary>\n" +
	"        </column>\n" +
	"        <column>\n" +
	"            <column_name>dolor</column_name>\n" +
	"            <type>integer</type>\n" +
	"            <label>Dolor</label>\n" +
	"            <weight>1</weight>\n" +
	"            <show_column>true</show_column>\n" +
	"            <primary>false</primary>\n" +
	"        </column>\n" +
	"    </contract>\n" +
	"    <contract>\n" +
	"        <table_name>sit</table_name>\n" +
	"        <column>\n" +
	"            <column_name>amet</column_name>\n" +
	"            <type>string</type>\n" +
	"            <label>Amet</label>\n" +
	"            <weight>1</weight>\n" +
	"            <show_column>true</show_column>\n" +
	"            <primary>true</primary>\n" +
	"        </column>\n" +
	"        <column>\n" +
	"            <column_name>consectetuer</column_name>\n" +
	"            <type>string</type>\n" +
	"            <label>Consectetuer</label>\n" +
	"            <weight>1</weight>\n" +
	"            <show_column>true</show_column>\n" +
	"            <primary>false</primary>\n" +
	"        </column>\n" +
	"    </contract>\n" +
	"</manatb>";

	@Override
	@Before
	public void shellBefore(){
		tb = new ManaTB();
		tb.setDb("testTb.db");
		tb.addTable(new Contract("lorem",new String[]{"ipsum","dolor"},new int[]{0,1},new int[]{3,1},new String[]{"Ipsum","Dolor"}));
		tb.addTable(new Contract("sit",new String[]{"amet","consectetuer"},new int[]{0,0},new int[]{1,1},new String[]{"Amet","Consectetuer"}));
	}

	@Test
	public void testEquals(){
		assertThat(false,equalTo(tb.equals("nope")));
		ManaTB ay = new ManaTB();
		assertThat(ay,not(equalTo(tb)));
		ay.setDb("testTb.db");
		assertThat(ay,not(equalTo(tb)));
		ay.addTable(new Contract("lorem",new String[]{"ipsum","dolor"},new int[]{0,1},new int[]{3,1},new String[]{"Ipsum","Dolor"}));
		ay.addTable(new Contract("sit",new String[]{"porttitor","consectetuer"},new int[]{0,0},new int[]{1,1},new String[]{"Amet","Consectetuer"}));
		assertThat(ay,not(equalTo(tb)));
		try {
			ay.loadXml(new StubParser(DATA));
		}
		catch(Exception ex){
			ex.printStackTrace(System.out);
		}
		assertThat(ay,equalTo(tb));
	}

	//instantiate from xml resource
	@Test
	public void testCreateXml() throws Exception{
		ManaTB ay = new ManaTB(new StubParser(DATA));
		assertThat(ay,is(tb));
	}

	//refresh/load xml
	@Test
	public void testLoadXml() throws Exception{
		ManaTB ay = new ManaTB();
		assertThat(ay,not(equalTo(tb)));
		ay.loadXml(new StubParser(DATA));
		assertThat(ay,equalTo(tb));
	}

	//addGetContract
	@Test
	public void testAddGetContract(){
		tb.addTable(new Contract("adipiscing","elit;vestibulum","0;0","3;2"));
		assertThat(tb.getTable("adipiscing"),is(new Contract("adipiscing","elit;vestibulum","0;0","3;2")));
	}

	//removeContract
	@Test
	public void testRemoveContract(){
		tb.deleteTable("sit");
		ManaTB ay = new ManaTB();
		ay.setDb("testTb.db");
		ay.addTable(new Contract("lorem","ipsum;dolor","0;1","3;1"));
		assertThat(tb,equalTo(ay));
	}

	//setGetDbFilename
	@Test
	public void testSetGetFilename(){
		tb.setDb("foo.db");
		assertThat(tb.getDb(),equalTo("foo.db"));
	}
		
	//getTableList
	@Test
	public void testTableList(){
		ArrayList<String> bee = new ArrayList<>();
		bee.add("lorem");
		bee.add("sit");
		assertThat(tb.getTableList(),equalTo(bee));
	}

	//getDefaultTable
	@Test
	public void testDefaultTable(){
		assertThat(tb.getDefaultTable(),equalTo(new Contract("lorem","ipsum;dolor","0;1","3;1")));
		assertThat(new ManaTB().getDefaultTable(),equalTo(new Contract()));
	}
}
