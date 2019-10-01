package com.build.atmfw.org.atm.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {

	public TimeUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String GetDateTime(){
		String txtDate="";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
	txtDate=dateFormat.format(cal.getTime());
		return txtDate;
	}
	public static String GetNameDateTime(){
		String txtDate="";
		
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
		Calendar cal = Calendar.getInstance();
	txtDate=dateFormat.format(cal.getTime());
		return txtDate;
	}
}
