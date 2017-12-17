package com.blackmanatee.manateebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.*;

import com.blackmanatee.manatb.Contract;
import com.blackmanatee.manatb.ManaTB;
import com.blackmanatee.manatb.ManaTBActivity;
import com.blackmanatee.manatb.TableViewActivity;

import static com.blackmanatee.manateebase.ShellStream.out;

/**
 * Created by DeCorben on 7/30/2017.
 */

public class MainActivity extends TableViewActivity{
	//@Override
	public void engage(View v){
		testContractLoad();
	}

	private void testContractLoad(){
		SharedPreferences pref = getSharedPreferences("manaTables", Context.MODE_PRIVATE);
		Contract one = new Contract("lorem",new String[]{"ipsum","dolor"},new int[]{0,1},new int[]{3,1},new String[]{"Ipsum","Dolor"});
		Contract two = new Contract("sit",new String[]{"amet","consectetuer"},new int[]{0,1},new int[]{3,1},new String[]{"Amet","Consectetuer"});
		SharedPreferences.Editor ed = pref.edit();
		ed.putString("contractList","lorem;sit");
		ed.putString("lorem",one.toXml());
		ed.putString("sit",two.toXml());
		ed.commit();
		ManaTB tb = ManaTB.get(pref);
		if(one.equals(tb.getTable("lorem")))
			System.out.println("Contract one matches");
		else
			out("No match one");
		if(two.equals(tb.getTable("sit")))
			System.out.println("Contract two matches");
		else
			out("No match two");
	}
}
