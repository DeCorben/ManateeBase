package com.blackmanatee.manateebase;

import static com.blackmanatee.lagoon.Sugar.echo;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import com.blackmanatee.manatb.Contract;
import com.blackmanatee.manatb.ManaTB;
import com.blackmanatee.manatb.TableListActivity;
import com.blackmanatee.manatb.test.*;
import java.lang.reflect.*;

/**
 * Created by DeCorben on 7/30/2017.
 */

public class MainActivity extends ShellActivity {	
	//@Override
	public void engage(View v){
		try{
			runSuite(new ManaTBInstanceTest());
		}
		catch(Throwable ex){
			ex.printStackTrace(System.out);
		}
	}
	
	private void runSuite(ShellCase s)throws Exception{
		Class<?> suite = s.getClass();
		echo(suite.getSimpleName()+":");
		s.shellFirst();
		for(Method m:suite.getMethods()){
			if(m.getName().startsWith("test")){
				echo(suite.getSimpleName()+"."+m.getName()+":");
				s.shellBefore();
				m.invoke(s);
				s.shellAfter();
				echo(suite.getSimpleName()+"."+m.getName()+":passed");
			}
		}
		s.shellLast();
		echo(suite.getSimpleName()+":end");
	}
}
