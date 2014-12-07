package com.jornada.client.classes.listBoxes.ambiente.coordenador;

import com.jornada.client.classes.listBoxes.MpSelection;


public class MpListBoxPesoPeriodo extends MpSelection {
	
	public MpListBoxPesoPeriodo(int numeroInicial, int numeroFinal){
		
	    super();
		
		for(int i=numeroInicial;i<=numeroFinal;i++){
		    addItem(Integer.toString(i),Integer.toString(i));
		}
		
	}
	
}
