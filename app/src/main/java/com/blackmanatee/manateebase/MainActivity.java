package com.blackmanatee.manateebase;

import static com.blackmanatee.lagoon.Sugar.echo;

import android.view.View;

import com.blackmanatee.manatb.test.*;
import java.lang.reflect.*;

/**
 * Created by DeCorben on 7/30/2017.
 */

public class MainActivity extends ShellActivity {	
	//@Override
	public void engage(View v){
		try{
			runSuite(new TableListActivityTest());
			//runSuite(new ColumnTest());
			//runSuite(new ContractTest());
			//runSuite(new ManaTBInstanceTest());
		}
		catch(Throwable ex){
			ex.printStackTrace(System.out);
		}
	}
	
	private void runSuite(ShellCase s)throws Exception{
		//check and load associate Fragments
		s.loadFragment();
		Class<?> suite = s.getClass();
		echo(suite.getSimpleName()+":");
		//locate and run @BeforeClass annotations
		s.shellFirst();
		//locate and store @Before and @After annotations
		for(Method m:suite.getMethods()){
			if(m.getName().startsWith("test")){
				echo(suite.getSimpleName()+"."+m.getName()+":");
				s.shellBefore();
				m.invoke(s);
				s.shellAfter();
				echo(suite.getSimpleName()+"."+m.getName()+":passed");
			}
		}
		//locate and run @AfterClass annotations
		s.shellLast();
		echo(suite.getSimpleName()+":end");
	}
}
