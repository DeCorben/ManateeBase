package com.blackmanatee.manatb;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.Assert.*;

/**
 * Created by DeCorben on 11/19/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ManaTBTest {
    @Before
    public void setup(){

    }

    @Test
    public void testGet(){
        ManaTB one = null;
        one = ManaTB.get(InstrumentationRegistry.getContext());
        assertNotNull(one);
        assertSame(one,ManaTB.get(InstrumentationRegistry.getContext()));
    }

    @Test
    public void testLoadMeta(){
        //ManaTB one = ManaTB.get(InstrumentationRegistry.getContext());

    }

    @Test
    public void testSetGetDb(){
        fail();
    }

    @Test
    public void testAddGetDeleteTable(){
        fail();
    }

    @Test
    public void testGetDefaultTable(){
        fail();
    }

    @Test
    public void testGetTableList(){

    }
}
