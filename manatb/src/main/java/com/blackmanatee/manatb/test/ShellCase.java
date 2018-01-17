package com.blackmanatee.manatb.test;
import org.junit.*;

public class ShellCase{
	protected int testCount = 0;
	
	@BeforeClass
	public static void shellFirst(){}
	
	@Before
	public void shellBefore(){}
	
	@After
	public void shellAfter(){}
	
	@AfterClass
	public static void shellLast(){}
	
	public void runTest(int t) throws Exception{}
	
	public int getTestCount(){
		return testCount;
	}
}
