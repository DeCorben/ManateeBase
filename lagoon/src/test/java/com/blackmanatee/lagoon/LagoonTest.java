package com.blackmanatee.lagoon;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LagoonTest {
    @Test
    public void testCompString() throws Exception {
        assertTrue(Lagoon.compString(new String[]{"lorem","ipsum","dolor"},new String[]{"lorem","ipsum","dolor"}));
    }

    @Test
    public void testCompInt() throws Exception{
        assertTrue(Lagoon.compInt(new int[]{1,1,2,3,5,8,13},new int[]{1,1,2,3,5,8,13}));
    }

    @Test
    public void testLargest() throws Exception{
        assertEquals(13,Lagoon.largest(new int[]{1,3,5,1,13,2,8}));
    }
}