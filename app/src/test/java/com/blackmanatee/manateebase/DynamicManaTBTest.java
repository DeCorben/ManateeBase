package com.blackmanatee.manateebase;

import org.junit.Test;

import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DynamicManaTBTest {
    //save to xml
    @Test
    public void testToXml(){
        String base = "";
        FileReader read = new FileReader(new File(getExternalFilesDir("manatb"),"testTb.xml"));
    }
}