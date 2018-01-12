package com.blackmanatee.manatb;

import android.content.SharedPreferences;
import org.junit.*;
import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by DeCorben on 11/19/2017.
 */

public class ManaTBTest {
    @After
    public void after(){
        ManaTB.clear();
    }

    @Test
    public void testSaveContracts(){
        ManaTB tb = ManaTB.get(null);
        Contract one = new Contract("lorem",new String[]{"ipsum","dolor"},new int[]{0,1},new int[]{3,1},new String[]{"Ipsum","Dolor"});
        Contract two = new Contract("sit",new String[]{"amet","consectetuer"},new int[]{0,1},new int[]{3,1},new String[]{"Amet","Consectetuer"});
        tb.addTable(one);
        tb.addTable(two);
        SharedPreferences.Editor pref = mock(SharedPreferences.Editor.class);
        tb.saveContracts(pref);
        verify(pref).putString("lorem",one.toXml());
        verify(pref).putString("sit",two.toXml());
        verify(pref).putString("contractList","lorem;sit");
    }

    @Test
    public void testLoadContracts(){
        SharedPreferences pref = mock(SharedPreferences.class);
        Contract one = new Contract("lorem",new String[]{"ipsum","dolor"},new int[]{0,1},new int[]{3,1},new String[]{"Ipsum","Dolor"});
        Contract two = new Contract("sit",new String[]{"amet","consectetuer"},new int[]{0,1},new int[]{3,1},new String[]{"Amet","Consectetuer"});
        when(pref.getString("contractList","")).thenReturn("lorem;sit");
        when(pref.getString("lorem","")).thenReturn(one.toXml());
        when(pref.getString("sit","")).thenReturn(two.toXml());
        ManaTB tb = ManaTB.get(pref);
        assertEquals(one,tb.getTable("lorem"));
        assertEquals(two,tb.getTable("sit"));
    }

    @Test
    public void testGet(){
        ManaTB one = null;
        one = ManaTB.get(null);
        assertNotNull(one);
        assertSame(one,ManaTB.get(null));
    }

    @Test
    public void testSetGetDb(){
        ManaTB one = ManaTB.get(null);
        one.setDb("lorem.db");
        assertEquals("lorem.db",one.getDb());
    }

    @Test
    public void testAddGetDeleteTable(){
        ManaTB tb = ManaTB.get(null);
        tb.setDb("test.db");
        tb.addTable(new Contract("sample",new String[]{"lorem","ipsum"},new int[]{0,1},new int[]{3,1},new String[]{"Lorem","Ipsum"}));
        assertEquals(new Contract("sample",new String[]{"lorem","ipsum"},new int[]{0,1},new int[]{3,1},new String[]{"Lorem","Ipsum"}),tb.getTable("sample"));
        tb.deleteTable("sample");
        Contract c = tb.getTable("sample");
        assertNull(c);
    }

    @Test
    public void testGetDefaultTable(){
        ManaTB tb = ManaTB.get(null);
        assertNull(tb.getDefaultTable());
        tb.addTable(new Contract("sample",new String[]{"lorem","ipsum"},new int[]{0,1},new int[]{3,1},new String[]{"Lorem","Ipsum"}));
        assertEquals(new Contract("sample",new String[]{"lorem","ipsum"},new int[]{0,1},new int[]{3,1},new String[]{"Lorem","Ipsum"}),tb.getDefaultTable());
    }

    @Test
    public void testGetTableList(){
        ManaTB tb = ManaTB.get(null);
        tb.addTable(new Contract("lorem",new String[]{"sit"},new int[]{0},new int[]{1},new String[]{"Sit"}));
        tb.addTable(new Contract("ipsum",new String[]{"amet"},new int[]{0},new int[]{1},new String[]{"Amet"}));
        tb.addTable(new Contract("dolor",new String[]{"consectetuer"},new int[]{0},new int[]{1},new String[]{"Consectetuer"}));
        assertTrue(tb.getTableList().contains("lorem"));
        assertTrue(tb.getTableList().contains("ipsum"));
        assertTrue(tb.getTableList().contains("dolor"));
    }
}
