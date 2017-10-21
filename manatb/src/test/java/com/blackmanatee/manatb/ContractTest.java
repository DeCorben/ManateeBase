package com.blackmanatee.manatb;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by DeCorben on 7/31/2017.
 */

public class ContractTest {
    private Contract c;

    @Before
    public void before(){
        c = new Contract("foo",new String[]{"name","value"},new int[]{0,1});
    }

    @Test
    public void testCreate(){
        Contract comp = new Contract("foo",new String[]{"name","value"},new int[]{0,1});
        assertTrue(c.equals(comp));
    }

    @Test
    public void testEquals(){
        Contract base = new Contract();
        base.setName("foo");
        base.setColumns(new String[]{"name","value"});
        base.setTypes(new int[]{0,1});
        assertTrue(base.equals(c));
    }

    @Test
    public void testSetGetTable(){
        c.setName("lorem");
        assertEquals(c.getName(),"lorem");
    }
}
