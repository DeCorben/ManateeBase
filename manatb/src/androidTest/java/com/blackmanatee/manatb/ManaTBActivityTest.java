package com.blackmanatee.manatb;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.*;
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
public class ManaTBActivityTest{
    //GAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH
    //Stupid Activity won't test because backend contract tracking won't mock
    @Rule
    public IntentsTestRule<ManaTBActivity> rule = new IntentsTestRule<ManaTBActivity>(ManaTBActivity.class){
        protected void beforeActivityLaunched(){
            super.beforeActivityLaunched();
            Contract one = new Contract("lorem",new String[]{"ipsum","dolor"},new int[]{0,1},new int[]{3,1},new String[]{"Ipsum","Dolor"});
            ManaTB.get(null).addTable(one);
        }

        protected void afterActivityLaunched(){
            super.afterActivityLaunched();
            SharedPreferences pref = getActivity().getSharedPreferences("manaTB",Context.MODE_PRIVATE);
            ManaTB.get(pref).saveContracts(pref.edit());
            ContractDbHelper help = new ContractDbHelper(getActivity(),"test.db",ManaTB.get(null).getTable("lorem"));
            SQLiteDatabase db = help.getReadableDatabase();
        }
    };
    //@Rule
    //public IntentsTestRule<MaintainActivity> addRule = new IntentsTestRule<>(MaintainActivity.class);

    @Test
    public void testAddRow(){
        SharedPreferences pref = rule.getActivity().getSharedPreferences("manaTB", Context.MODE_PRIVATE);
        Contract one = new Contract("lorem",new String[]{"ipsum;dolor"},new int[]{0,1},new int[]{3,1},new String[]{"Ipsum","Dolor"});
        ManaTB.get(pref).addTable(one);
        assertEquals(one,ManaTB.get(pref).getTable("lorem"));
        onView(withId(R.id.dbAddAction)).perform(click());
        intended(hasExtra("table","lorem"));
    }
}