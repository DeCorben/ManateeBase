package com.blackmanatee.manateebase;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;

public class ShellActivity extends Activity
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
