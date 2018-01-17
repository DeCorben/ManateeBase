package com.blackmanatee.manateebase;
import android.widget.*;
import java.io.*;

public class ShellStream extends OutputStream{
	private TextView console;

	public ShellStream(TextView o){
		console = o;
	}

	@Override
	public void write(int p1){
		console.append(Character.toString((char)p1));
	}
	
	public static void echo(String s){
		System.out.println(s);
	}
}
