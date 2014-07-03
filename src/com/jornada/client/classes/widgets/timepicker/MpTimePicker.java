package com.jornada.client.classes.widgets.timepicker;

import java.sql.Time;
import java.util.Date;

import com.google.gwt.user.client.ui.ListBox;
import com.jornada.shared.classes.utility.MpUtilClient;

public class MpTimePicker extends ListBox{
	
	
	public MpTimePicker(int startHous, int endHours){
		
		setStyleName("design_text_boxes");	
		
		for (int horas = startHous; horas <= endHours; horas++) {
			for (int minutos = 0; minutos < 60; minutos=minutos+10) {
				String strHoras = "";
				String strMinutos = "";
				String strAmPM = "AM";

				if (horas < 10)
					strHoras = "0" + Integer.toString(horas);
				else
					strHoras = Integer.toString(horas);

				if (minutos < 10)
					strMinutos = "0" + Integer.toString(minutos);
				else
					strMinutos = Integer.toString(minutos);

				if (horas >= 12)
					strAmPM = "PM";

				addItem(strHoras + ":" + strMinutos + " " + strAmPM, strHoras+ ":" + strMinutos + " " + strAmPM);

			}
		}		
		
	}
	

	
	@SuppressWarnings("deprecation")
	public void setTime(Time time){
		Date horaComunicadoDB = new java.util.Date(time.getTime());

		//Selecting Item listBoxTime 
		for(int i=0;i<this.getItemCount();i++){
			String strHora = this.getValue(i);				
			if((MpUtilClient.convertStringToTime(strHora).getHours()==horaComunicadoDB.getHours())&&(MpUtilClient.convertStringToTime(strHora).getMinutes()==horaComunicadoDB.getMinutes())){
				this.setSelectedIndex(i);
			}			
		}
	}
}
