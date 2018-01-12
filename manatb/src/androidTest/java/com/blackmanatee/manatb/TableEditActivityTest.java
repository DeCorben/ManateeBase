package com.blackmanatee.manatb;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.fail;

/**
 * Created by DeCorben on 11/19/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TableEditActivityTest {
    @Rule
    public ActivityTestRule<TableListActivity> rule = new ActivityTestRule<TableListActivity>(TableListActivity.class);

    @Before
    public void before(){
        onView(withId(R.id.dbAddAction)).perform(click());
        onView(withId(R.id.tableName)).perform(typeText("foo"));
        onView(withId(R.id.columns)).perform(typeText("one;two"));
        onView(withId(R.id.types)).perform(typeText("0;1"));
        onView(withId(R.id.labels)).perform(typeText("One;Two"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.weights)).perform(typeText("3;1"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.contractCommitButton)).perform(click());
    }

    @Ignore
    @Test
    public void testTableEditDBChange(){
        fail();
    }

    @Test
    public void testSaveWhenPaused(){
        onData(is("foo")).inAdapterView(withId(R.id.tableList)).perform(longClick());
        onView(withId(R.id.columns)).perform(replaceText("three;four"));
        Espresso.pressBack();
        Espresso.pressBack();
        onData(is("foo")).inAdapterView(withId(R.id.tableList)).perform(longClick());
        onView(withId(R.id.columns)).check(matches(withText("three;four")));
        onView(withId(R.id.columns)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.contractCancelButton)).perform(click());
    }

    @Test
    public void testLoadWithIntentExtra(){
        onData(is("foo")).inAdapterView(withId(R.id.tableList)).perform(longClick());
        onView(withId(R.id.tableName)).check(matches(withText("foo")));
        onView(withId(R.id.columns)).check(matches(withText("one;two")));
        onView(withId(R.id.types)).check(matches(withText("0;1")));
        onView(withId(R.id.labels)).check(matches(withText("One;Two")));
        onView(withId(R.id.weights)).check(matches(withText("3;1")));
        onView(withId(R.id.contractCancelButton)).perform(click());
    }

    @Test
    public void testCommitClick(){
        onData(is("foo")).check(matches(notNullValue()));
    }

    @Test
    public void testCancelClick(){
        onView(withId(R.id.dbAddAction)).perform(click());
        onView(withId(R.id.tableName)).perform(typeText("bar"));
        onView(withId(R.id.columns)).perform(typeText("three;four"));
        onView(withId(R.id.types)).perform(typeText("1;0"));
        onView(withId(R.id.labels)).perform(typeText("Three;Four"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.weights)).perform(typeText("1;3"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.contractCancelButton)).perform(click());
        onData(isA(String.class)).inAdapterView(withId(R.id.tableList)).check(matches(not(withText("bar"))));
    }

    @After
    public void after(){
        onView(withId(R.id.itemDelete)).perform(click());
    }
}
