package com.blackmanatee.manatb;

import android.content.SharedPreferences;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.*;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.intent.Intents.*;
import static android.support.test.espresso.intent.matcher.IntentMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by DeCorben on 8/9/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ManaTBActivityTest {
    @Rule
    public ActivityTestRule<ManaTBActivity> rule = new ActivityTestRule<>(ManaTBActivity.class);
    @Rule
    public IntentsTestRule<MaintainActivity> addRule = new IntentsTestRule<>(MaintainActivity.class);

    private static SharedPreferences pref;

    @BeforeClass
    public static void setup(){
        pref = mock(SharedPreferences.class);
        Contract one = new Contract("lorem",new String[]{"ipsum;dolor"},new int[]{0,1},new int[]{3,1},new String[]{"Ipsum","Dolor"});
        when(pref.getString("contractList","lorem"));
        when(pref.getString("lorem",one.toXml()));
    }



    @Test
    public void testAddRow(){
        onView(withId(R.id.dbAddAction)).perform(click());
        intended(hasExtra("table","lorem"));
    }
}