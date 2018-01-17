package com.blackmanatee.manatb;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.*;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.*;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;
/**
 * Created by DeCorben on 12/29/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TableListActivityTest {
    @Rule
    public IntentsTestRule<TableListActivity> rule = new IntentsTestRule<TableListActivity>(TableListActivity.class);

    @Test
    public void testDeleteAdapter(){
        onView(withId(R.id.tableList)).check(matches(hasDescendant(allOf(withText("lorem"),withId(R.id.itemLabel)))));
        onView(withId(R.id.tableList)).check(matches(hasDescendant(allOf(withText("sit"),withId(R.id.itemLabel)))));
    }

    @Test
    public void testDeleteItem(){
        //When
        onData(is("lorem")).inAdapterView(withId(R.id.tableList));
        onView(allOf(withContentDescription("lorem"),withParent(withChild(withText("lorem"))))).perform(click());
        //Then
        onView(withId(R.id.tableList)).check(matches(not(hasDescendant(allOf(withId(R.id.itemLabel),withText("lorem"))))));
    }

    @Test
    public void testAddButton(){
       //When
        onView(withId(R.id.dbAddAction)).perform(click());
        //Then
        intended(allOf(hasComponent(hasShortClassName("com.blackmanatee.manatb.TableEditActivity")),not(hasExtraWithKey("name"))));
    }

    @Test
    public void testEditClick(){
        //When
        onData(is("lorem")).inAdapterView(withId(R.id.tableList)).perform(longClick());
        //Then
        intended(allOf(hasComponent(hasShortClassName("com.blackmanatee.manatb.TableEditActivity")),hasExtra("name","lorem")));
    }

    @Test
    public void testView(){
        //When
        onData(equalTo("lorem")).inAdapterView(withId(R.id.tableList)).perform(click());
        //Then
        intended(allOf(hasComponent(hasShortClassName("com.blackmanatee.manatb.TableViewActivity")),hasExtra("name","lorem")));
    }
}
