package com.blackmanatee.manatb;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by DeCorben on 7/31/2017.
 */

public class ContractTest {
    private Contract c;

    @Before
    public void before(){
        c = new Contract("foo",new String[]{"name","value"},new int[]{0,1},new int[]{3,1},new String[]{"Name","Value"});
    }

    @Test
    public void testAddGetType(){
        try{
            int dummy = c.getType(2);
            fail();
        }
        catch(Exception ex){
            c.addColumn(new Column("lorem",0,"Ipsum",1,true,false));
            assertEquals(0,c.getType(2));
        }
    }

    @Test
    public void testAddGetColumn(){
        try{
            String dummy = c.getColumn(2);
            fail();
        }
        catch(Exception ex){
            c.addColumn(new Column("lorem",0,"Ipsum",1,true,false));
            assertEquals("lorem",c.getColumn(2));
        }
    }

    @Test
    public void testSetGetColumns(){
        ArrayList<Column> base = new ArrayList<>();
        base.add(new Column("name",0,"Name",3,true,true));
        base.add(new Column("value",1,"Value",1,true,false));
        assertEquals(base,c.getColumns());
        base.add(new Column("lorem",0,"Ipsum",1,true,false));
        c.setColumns(base);
        ArrayList<Column> comp = new ArrayList<>();
        comp.add(new Column("name",0,"Name",3,true,true));
        comp.add(new Column("value",1,"Value",1,true,false));
        comp.add(new Column("lorem",0,"Ipsum",1,true,false));
        assertNotSame(comp,c.getColumns());
        assertEquals(comp,c.getColumns());
    }

    @Test
    public void testColumnList(){
        assertArrayEquals(new String[]{"name","value"},c.getColumnList());
    }

    @Test
    public void testCreateStringStringIntIntString(){
        Contract comp = null;
        assertNull(comp);
        comp = new Contract("foo",new String[]{"name","value"},new int[]{0,1},new int[]{3,1},new String[]{"Name","Value"});
        assertNotNull(comp);
        assertTrue(c.equals(comp));
    }

    @Test
    public void testCreate(){
        c = null;
        assertNull(c);
        c = new Contract();
        assertNotNull(c);
    }

    @Test
    public void testCreateStringStringInt(){
        Contract comp = new Contract("foo",new String[]{"name","value"},new int[]{0,1});
        assertTrue(new Contract("foo",new String[]{"name","value"},new int[]{0,1},new int[]{1,1},new String[]{"name","value"}).equals(comp));
    }

    @Test
    public void testEquals(){
        //needs to test ALL exits of equals
        assertFalse(c.equals("foo"));
        assertFalse(c.equals(new Contract("lorem",new String[]{},new int[]{},new int[]{},new String[]{})));
        assertFalse(c.equals(new Contract("foo",new String[]{},new int[]{},new int[]{},new String[]{})));
        Contract base = new Contract();
        base.setName("foo");
        base.addColumn(new Column("name",0,"Name",3,true,true));
        base.addColumn(new Column("value",1,"Value",1,true,false));
        assertTrue(base.equals(c));
    }

    @Test
    public void testSetGetTable(){
        c.setName("lorem");
        assertEquals(c.getName(),"lorem");
    }
}
