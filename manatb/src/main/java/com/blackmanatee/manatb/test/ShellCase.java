package com.blackmanatee.manatb.test;
import org.junit.*;

public class ShellCase{
	@BeforeClass
	public static void shellFirst(){}
	
	@Before
	public void shellBefore(){}
	
	@After
	public void shellAfter(){}
	
	@AfterClass
	public static void shellLast(){}

	public void loadFragment(){}
}
