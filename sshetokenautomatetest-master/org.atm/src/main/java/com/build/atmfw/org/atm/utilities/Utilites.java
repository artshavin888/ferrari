package com.build.atmfw.org.atm.utilities;

import java.io.IOException;

public class Utilites {

	public Utilites() {
		
	}
	
	public void killProcess(String process) {
		try {

	        //Runtime.getRuntime().exec("mspaint");
			if (process=="-") {
				process = "chromedriver.exe";
			}
	         

	        Runtime.getRuntime().exec("taskkill /F /IM " +process );

	    } catch (IOException e) {

	        e.printStackTrace();

	    }
	}
	public static String writelog(String TextLog) {
		String txt;
		txt=TimeUtil.GetDateTime()+" "+ TextLog;
		System.out.println(txt);
		return txt;
	}
	

}
