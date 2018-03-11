package com.blackmanatee.manateebase;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import android.support.v4.app.FragmentActivity;

public class ShellActivity extends FragmentActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shell_layout);
		System.setOut(new PrintStream(new ShellStream((TextView)findViewById(R.id.console))));
	}
	
	public void engage(View v){
		System.out.println("Hellooooooooooo,Nurse!");
	}
	
}
