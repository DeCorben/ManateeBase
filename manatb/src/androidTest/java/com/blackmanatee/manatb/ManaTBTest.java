package com.blackmanatee.manatb;

import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.*;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static junit.framework.Assert.*;

/**
 * Created by DeCorben on 11/19/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ManaTBTest {
    private static Context con;

    @BeforeClass
    public static void setup(){
        con = InstrumentationRegistry.getContext();
    }

    @After
    public void after(){
        ManaTB.clear();
    }

    @Test
    public void testMetaAddDelete(){
        ManaTB tb = ManaTB.get(con);
        tb.addTable(new Contract("lorem",new String[]{"ipsum","dolor"},new int[]{0,1},new int[]{3,1},new String[]{"Ipsum","Dolor"}));
        ContractDbHelper help = new ContractDbHelper(con,ManaTB.MANA_DB,ManaTB.META);
        SQLiteDatabase db = help.getReadableDatabase();
        SQLiteCursor cur = (SQLiteCursor)db.rawQuery("select * from meta where name = 'lorem'",null);
        cur.moveToFirst();
        int oldCount = cur.getCount();
        if(oldCount > 0){
            assertEquals("lorem",cur.getString(1));
            assertEquals("ipsum;dolor",cur.getString(2));
            assertEquals("0;1",cur.getString(3));
            assertEquals("3;1",cur.getString(4));
            assertEquals("Ipsum;Dolor",cur.getString(5));
        }
        else
            fail("Added row not found");
        tb.deleteTable("lorem");
        cur = (SQLiteCursor)db.rawQuery("select * from meta where name = 'lorem'",null);
        assertEquals(oldCount-1,cur.getCount());
    }

    @Test
    public void testGet(){
        ManaTB one = null;
        one = ManaTB.get(con);
        assertNotNull(one);
        assertSame(one,ManaTB.get(con));
    }

    @Test
    public void testSetGetDb(){
        ManaTB one = ManaTB.get(con);
        one.setDb("lorem.db");
        assertEquals("lorem.db",one.getDb());
    }

    @Test
    public void testAddGetDeleteTable(){
        ManaTB tb = ManaTB.get(con);
        tb.setDb("test.db");
        tb.addTable(new Contract("sample",new String[]{"lorem","ipsum"},new int[]{0,1},new int[]{3,1},new String[]{"Lorem","Ipsum"}));
        assertEquals(new Contract("sample",new String[]{"lorem","ipsum"},new int[]{0,1},new int[]{3,1},new String[]{"Lorem","Ipsum"}),tb.getTable("sample"));
        tb.deleteTable("sample");
        Contract c = tb.getTable("sample");
        if(c != null)
            Log.d("manaT",c.toString());
        assertNull(c);
    }

    @Test
    public void testGetDefaultTable(){
        ManaTB tb = ManaTB.get(con);
        assertNull(tb.getDefaultTable());
        tb.addTable(new Contract("sample",new String[]{"lorem","ipsum"},new int[]{0,1},new int[]{3,1},new String[]{"Lorem","Ipsum"}));
        assertEquals(new Contract("sample",new String[]{"lorem","ipsum"},new int[]{0,1},new int[]{3,1},new String[]{"Lorem","Ipsum"}),tb.getDefaultTable());
    }

    @Test
    public void testGetTableList(){
        ManaTB tb = ManaTB.get(con);
        tb.addTable(new Contract("lorem",new String[]{"sit"},new int[]{0},new int[]{1},new String[]{"Sit"}));
        tb.addTable(new Contract("ipsum",new String[]{"amet"},new int[]{0},new int[]{1},new String[]{"Amet"}));
        tb.addTable(new Contract("dolor",new String[]{"consectetuer"},new int[]{0},new int[]{1},new String[]{"Consectetuer"}));
        assertTrue(tb.getTableList().contains("lorem"));
        assertTrue(tb.getTableList().contains("ipsum"));
        assertTrue(tb.getTableList().contains("dolor"));
    }
}
