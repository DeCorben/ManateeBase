package com.blackmanatee.manatb;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.*;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.*;
import static android.support.test.espresso.intent.matcher.IntentMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
/**
 * Created by DeCorben on 12/29/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TableListActivityTest {
    @Rule
    public IntentsTestRule<TableListActivity> rule = new IntentsTestRule<TableListActivity>(TableListActivity.class);

    @Test
    public void testDeleteItem(){
        //When
        onData(is("lorem")).inAdapterView(withId(R.id.tableList));
        onView(allOf(withContentDescription("lorem"),withParent(withChild(withText("lorem"))))).perform(click());
        //Then
        onView(withId(R.id.tableList)).check(matches(not(hasDescendant(withText("lorem")))));
    }

    @Test
    public void testAddButton(){
        //Given
        Contract one = new Contract("magna",new String[]{},new int[]{},new int[]{},new String[]{});
        Intent i = new Intent();
        i.putExtra("name","magna");
        intending(toPackage("com.blackmanatee.manatb.TableEditActivity")).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK,i));
        //When
        onView(withId(R.id.dbAddAction)).perform(click());

        //Then
        onData(is("magna")).inAdapterView(withId(R.id.tableList)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditClick(){
        //When
        onData(is("lorem")).inAdapterView(withId(R.id.tableList)).perform(longClick());
        //Then
        intended(hasExtra("name","lorem"));
    }

    @Test
    public void testView(){
        //When
        onData(equalTo("lorem")).inAdapterView(withId(R.id.tableList)).perform(click());
        //Then
        intended(hasExtra("name","lorem"));
    }
}
