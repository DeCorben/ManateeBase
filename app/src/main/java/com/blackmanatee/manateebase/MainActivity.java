package com.blackmanatee.manateebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import com.blackmanatee.manatb.Contract;
import com.blackmanatee.manatb.ManaTB;
import com.blackmanatee.manatb.TableListActivity;
import com.blackmanatee.manatb.test.*;

import static com.blackmanatee.manateebase.ShellStream.echo;

/**
 * Created by DeCorben on 7/30/2017.
 */

public class MainActivity extends ShellActivity {	
	//@Override
	public void engage(View v){
		try{
			runSuite(new ManaTBInstanceTest());
		}
		catch(Exception ex){
			ex.printStackTrace(System.out);
		}
	}

	private void runSuite(ShellCase suite) throws Exception{
		suite.shellFirst();
		for(int z=0;z<suite.getTestCount();z++){
			suite.shellBefore();
			suite.runTest(z);
			suite.shellAfter();
		}
		suite.shellLast();
	}
}
