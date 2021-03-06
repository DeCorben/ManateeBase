package com.blackmanatee.manatb.test;

/**
 * Created by DeCorben on 11/19/2017.
 */

import com.blackmanatee.lagoon.LagoonParser;
import com.blackmanatee.manatb.Column;

import org.junit.*;
import static org.junit.Assert.*;

public class ColumnTest extends ShellCase{
    private Column c;

    @Test
    public void testColumnTag(){
        assertEquals(c,new Column(LagoonParser.parse("<column>\n" +
                "\t<column_name>lorem</column_name>\n" +
                "\t<type>string</type>\n" +
                "\t<label>Ipsum</label>\n" +
                "\t<weight>1</weight>\n" +
                "\t<show_column>true</show_column>\n" +
                "\t<primary>false</primary>\n" +
                "</column>")));
    }

    @Test
    public void testToXml(){
        assertEquals("<column>\n" +
            "\t<column_name>lorem</column_name>\n" +
            "\t<type>string</type>\n" +
            "\t<label>Ipsum</label>\n" +
            "\t<weight>1</weight>\n" +
            "\t<show_column>true</show_column>\n" +
            "\t<primary>false</primary>\n" +
            "</column>"
            ,c.toXml());
    }

    @Override
    public void shellBefore(){
        c = new Column("lorem",0,"Ipsum",1,true,false);
    }

    @Test
    public void testEquals(){
        assertFalse(c.equals("foo"));
        assertFalse(c.equals(new Column("foo",0,"Ipsum",1,true,false)));
        assertFalse(c.equals(new Column("lorem",1,"Ipsum",1,true,false)));
        assertFalse(c.equals(new Column("lorem",0,"foo",1,true,false)));
        assertFalse(c.equals(new Column("lorem",0,"Ipsum",3,true,false)));
        assertFalse(c.equals(new Column("lorem",0,"Ipsum",1,false,false)));
        assertFalse(c.equals(new Column("lorem",0,"Ipsum",1,true,true)));
        assertTrue(c.equals(new Column("lorem",0,"Ipsum",1,true,false)));
    }

    @Test
    public void testSetIsPrim(){
        assertFalse(c.isPrim());
        c.setPrim(true);
        assertTrue(c.isPrim());
    }

    @Test
    public void testSetIsShow(){
        assertTrue(c.isShow());
        c.setShow(false);
        assertFalse(c.isShow());
    }

    @Test
    public void testSetGetType(){
        assertFalse(1 == c.getType());
        c.setType(1);
        assertEquals(1,c.getType());
    }

    @Test
    public void testSetGetWeight(){
        assertFalse(3 == c.getWeight());
        c.setWeight(3);
        assertEquals(3,c.getWeight());
    }

    @Test
    public void testSetGetName(){
        assertFalse(c.getName().equals("Dolor"));
        c.setName("Dolor");
        assertEquals("Dolor",c.getName());
    }

    @Test
    public void testSetGetLabel() {
        assertFalse(c.getLabel().equals("Dolor"));
        c.setLabel("Dolor");
        assertEquals("Dolor",c.getLabel());
    }

    @Test
    public void testCreate(){
        c = null;
        assertNull(c);
        c = new Column();
        assertNotNull(c);
    }

    @Test
    public void testCreateStringIntStringIntBoolBool(){
        assertEquals(c,new Column("lorem",0,"Ipsum",1,true,false));
    }
}
