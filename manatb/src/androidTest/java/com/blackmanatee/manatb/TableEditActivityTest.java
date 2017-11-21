package com.blackmanatee.manatb;

import org.junit.*;
import org.junit.runner.*;
import static org.junit.Assert.*;
import android.support.test.*;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.*;

/**
 * Created by DeCorben on 11/19/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TableEditActivityTest {
    @Rule
    public ActivityTestRule<TableViewActivity> rule = new ActivityTestRule<TableViewActivity>(TableViewActivity.class);

    @Test
    public void testCommitClick(){
        fail();
    }

    @Test
    public void testCancelClick(){
        fail();
    }

    @Test
    public void testSaveWhenPaused(){
        fail();
    }
}
