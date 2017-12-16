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
 * Created by DeCorben on 8/9/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ManaTBActivityTest {
    @Rule
    public ActivityTestRule<TableViewActivity> rule = new ActivityTestRule<>(TableViewActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testAddTable(){
        onView(withId(R.id.dbAddAction)).perform(click());
        onView(withId(R.id.tableName)).perform(typeText("foo"));

    }

    @After
    public void tearDown() throws Exception {

    }

}