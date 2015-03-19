package com.jornada.client.classes.listBoxes.ambiente.coordenador.curso;

import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.shared.classes.Ensino;


public class MpListBoxEnsino extends MpSelection {
	
	public MpListBoxEnsino(){
		
	    
	    
	    addItem("Educação Infantil",Ensino.INFANTIL);
		addItem("Ensino Fundamental",Ensino.FUNDAMENTAL);
		addItem("Ensino Médio",Ensino.MEDIO);
		addItem("Ensino Profissionalizante", Ensino.PROFISSIONALIZANTE);
		addItem("Graduação",Ensino.GRADUACAO);
		addItem("Pós-graduação",Ensino.POS_GRADUACAO);
		addItem("Mestrado",Ensino.MESTRADO);
		addItem("Doutorado",Ensino.DOUTORADO);
		addItem("Pós-Doutorado",Ensino.POS_DOUTORADO);
		
	}
	
}
