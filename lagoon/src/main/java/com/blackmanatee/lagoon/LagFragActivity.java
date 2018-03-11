package com.blackmanatee.lagoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class LagFragActivity extends FragmentActivity {
    protected Fragment lagFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lag_frag);
        getSupportFragmentManager().beginTransaction().add(R.id.lagFrag,lagFrag).commit();
    }
}
