package com.jornada.server.classes.utility;

import java.io.File;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MpUtilServer {
	
	public static final String FORMAT_HOUR="HH:mm a";
	public static final String FORMAT_DATE="yyyy MMM,dd";
	
	
	
	
	public static String convertDateToString_old_old(Date date, String strLocale){
		String arraylocale[];
		Locale LOCALE;
		
		if(strLocale.contains("_")){
			arraylocale = strLocale.split("_");
			LOCALE = new Locale(arraylocale[0],arraylocale[1]);  
		}else{
			LOCALE = new Locale(strLocale);  
		}
		 
		DateFormat df = new SimpleDateFormat(FORMAT_DATE, LOCALE);	
		
		String reportDate = df.format(date);		
		return reportDate;
	}
	
	public static String convertDateToString(Date date){

		DateFormat df = new SimpleDateFormat(FORMAT_DATE);			
		String reportDate = df.format(date);		
		return reportDate;
	}
	
	
	public static String convertTimeToString(Time time){
		DateFormat df = new SimpleDateFormat(FORMAT_HOUR);	
		Date newDate = new Date();
		newDate.setTime(time.getTime());
		String reportDate = df.format(newDate);		
		return reportDate;
	}
	
	
	public static boolean renameFile(String oldFileAddress, String newFileAddress){
		
		boolean isRenamed;
		
		File oldfile =new File(oldFileAddress);
		File newfile =new File(newFileAddress);
 
		if(oldfile.renameTo(newfile)){
			isRenamed=true;
			System.out.println("Rename succesful");
		}else{
			isRenamed=false;
			System.out.println("Rename failed");
		}	
		
		return isRenamed;
		
	}


	public static Date convertStringToDate(String strDate){
		Calendar mydate = new GregorianCalendar();
		try{		
		
		Date thedate = new SimpleDateFormat(FORMAT_DATE, Locale.ENGLISH).parse(strDate);
		mydate.setTime(thedate);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return mydate.getTime();
	}
	
	
	public static java.sql.Date convertStringToSqlDate(String strDate) {
		try {
			Date data = MpUtilServer.convertStringToDate(strDate);
			return new java.sql.Date(data.getTime());
		} catch (Exception ex) {
			Date date = new Date();
			return new java.sql.Date(date.getTime());
		}
	}
	
	public static java.sql.Time convertStringToSqlTime(String strDate){
		
		try {
			DateFormat formatter = new SimpleDateFormat("hh:mm");
			java.sql.Time timeValue = new java.sql.Time(formatter.parse(strDate).getTime());
			return timeValue;
		} catch (Exception ex) {
			return null;
		}		
		
	}

}
