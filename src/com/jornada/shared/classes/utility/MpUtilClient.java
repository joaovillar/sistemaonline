package com.jornada.shared.classes.utility;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.jornada.client.classes.widgets.dialog.MpDialogBoxRefreshPage;

public class MpUtilClient {
	
	public static final String FORMAT_HOUR="HH:mm a";
	public static final String FORMAT_DATE_1="yyyy MMM,dd";
	public static final String FORMAT_DATE_2="yyyy-MM-dd";
	
	public static Time convertStringToTime(String strValue){		
		Date dateHora = DateTimeFormat.getFormat(FORMAT_HOUR).parse(strValue);		
		
		/****BEGIN removing actualDate and leaving just the hour***/
		Date d = new Date(dateHora.getTime()); 
		Date d1 = new Date();		
		d1.setTime(MpUtilClient.removingHourFromDate(d1).getTime());
		long longHoraReal = d.getTime()-d1.getTime();
		dateHora.setTime(longHoraReal);
		/****END removing actualDate and leaving just the hour***/		
		
		Time timeHora = new java.sql.Time(dateHora.getTime());
		return timeHora;
	}
	
	public static Date convertStringToDate(String strValue){
		Date dateHora = null;
		try{
			dateHora = DateTimeFormat.getFormat(FORMAT_DATE_1).parse(strValue);
		}catch(Exception ex1){
			try{
			dateHora = DateTimeFormat.getFormat(FORMAT_DATE_2).parse(strValue);
			}catch(Exception ex2){
				dateHora=null;
				System.out.println("convertStringToDate:"+ex2.getMessage());
			}
		}
		return dateHora;
		

	}	
	
	public static String convertTimeToString(Time time){
		String strHora = ((time != null) ? DateTimeFormat.getFormat(FORMAT_HOUR).format(time) : null);
		return strHora;
	}
	
	public static String convertDateToString(Date date){
		String strDate = ((date!=null)?DateTimeFormat.getFormat(FORMAT_DATE_1).format(date):null); 
		return strDate;
	}
	
	public static String convertDateToString(Date date, String formatDate){
		String strDate = ((date!=null)?DateTimeFormat.getFormat(formatDate).format(date):null); 
		return strDate;
	}
	
	public static Date removingHourFromDate(Date date){
		String strDate = MpUtilClient.convertDateToString(date);
		Date newDate = new Date();
		newDate.setTime(MpUtilClient.convertStringToDate(strDate).getTime());
		return newDate;
	}
	
	
	public static String getHourFromString(String strFormat){
		String strFormatHora = strFormat;
		strFormatHora = strFormatHora.substring(0,strFormatHora.lastIndexOf(":"));
//		String strHoraDB = strFormatHora.substring(0, strFormatHora.lastIndexOf(":"));
		return strFormatHora;
	}
	
	public static String getMinutesFromString(String strFormat){
		String strFormatHora = strFormat;
//		strFormatHora = strFormatHora.substring(0,strFormatHora.lastIndexOf(":"));
		String strMinutosDB = strFormatHora.substring(strFormatHora.lastIndexOf(":")+1,strFormatHora.lastIndexOf(":")+3);
		return strMinutosDB;
	}


	public static String add_AM_PM(String strHora){
		strHora = strHora.substring(0, strHora.lastIndexOf(":"));
		String aux = MpUtilClient.getHourFromString(strHora);
		int intaux = Integer.parseInt(aux);
		if (intaux>=12){
			strHora = strHora + " PM";
		}
		else{
			strHora = strHora + " AM";
		}
		return strHora;
	}
	
	public static void isRefreshRequired(ArrayList<?> list){
		if(list==null) {
			MpDialogBoxRefreshPage mp = new MpDialogBoxRefreshPage();
			mp.showDialog();
		}
	}
	
	public static void isRefreshRequired(String[][] list){
		if(list==null) {
			MpDialogBoxRefreshPage mp = new MpDialogBoxRefreshPage();
			mp.showDialog();
		}
	}

}
