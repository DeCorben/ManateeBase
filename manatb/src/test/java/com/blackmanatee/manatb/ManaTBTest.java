package com.blackmanatee.manatb;

import org.junit.*;
import static org.junit.Assert.*;
import com.blackmanatee.lagoon.Lagoon;

/**
 * Created by DeCorben on 8/1/2017.
 */

public class ManaTBTest {
    private ManaTB mtb;

    @Before
    public void before(){
        mtb = ManaTB.get();
    }

    @After
    public void after(){
        for(String s:mtb.getTableList()){
            mtb.deleteTable(s);
        }
        mtb.setDb("");
    }
}
