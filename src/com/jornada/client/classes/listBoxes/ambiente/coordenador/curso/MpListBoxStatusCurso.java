package com.jornada.client.classes.listBoxes.ambiente.coordenador.curso;

import com.jornada.client.classes.listBoxes.MpSelection;



public class MpListBoxStatusCurso extends MpSelection {
	
	
	public MpListBoxStatusCurso(){
	    
	   addItem(txtConstants.cursoAtivo(), "true");
	   addItem(txtConstants.cursoDesativado(), "false");		
	
	}
	
}
