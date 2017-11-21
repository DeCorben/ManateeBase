package com.blackmanatee.manatb;

/**
 * Created by DeCorben on 11/19/2017.
 */

import org.junit.*;
import static org.junit.Assert.*;

public class ColumnTest {
    private Column c;

    @Before
    public void setup(){
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
