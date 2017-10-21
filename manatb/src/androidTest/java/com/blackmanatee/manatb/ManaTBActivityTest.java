package com.blackmanatee.manatb;

import org.junit.*;
import org.junit.runner.*;

import static org.junit.Assert.*;
import android.support.test.*;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.*;

/**
 * Created by DeCorben on 8/9/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ManaTBActivityTest {
    @Rule
    public ActivityTestRule<TableViewActivity> rule = new ActivityTestRule<TableViewActivity>(TableViewActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testAddTable(){
        Espresso.onView(ViewMatchers.withId(R.id.dbAddAction)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.tableName)).perform(ViewActions.typeText("foo"));

    }

    @After
    public void tearDown() throws Exception {

    }

}