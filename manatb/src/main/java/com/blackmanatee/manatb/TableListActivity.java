package com.blackmanatee.manatb;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.blackmanatee.lagoon.LagFragActivity;

public class TableListActivity extends LagFragActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lagFrag = TableListFragment.instantiate(this,TableListFragment.class.getName());
        super.onCreate(savedInstanceState);
    }
}
