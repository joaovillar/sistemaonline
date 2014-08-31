package com.jornada.client.classes.listBoxes.ambiente.coordenador;

import com.jornada.client.classes.listBoxes.MpSelection;


public class MpListBoxSexo extends MpSelection {
	
	public MpListBoxSexo(){
		
		addItem(txtConstants.sexoFeminino(),"feminino");
		addItem(txtConstants.sexoMasculino(),"masculino");
		
	}
	
}
