package com.jornada.client.classes.listBoxes.ambiente.coordenador.curso;

import com.jornada.client.classes.listBoxes.MpSelection;
import com.jornada.shared.classes.curso.Ensino;


public class MpListBoxEnsino extends MpSelection {
	
	public MpListBoxEnsino(){		
	    
	    Ensino ensino = new Ensino();	    
	    for(int i=0;i<ensino.getListEnsino().size();i++){	        
	        addItem(ensino.getListEnsino().get(i).getName(), ensino.getListEnsino().get(i).getValue());
	    }
	    
//	    addItem("Educação Infantil",Ensino.INFANTIL);
//		addItem("Ensino Fundamental",Ensino.FUNDAMENTAL);
//		addItem("Ensino Médio",Ensino.MEDIO);
//		addItem("Ensino Profissionalizante", Ensino.PROFISSIONALIZANTE);
//		addItem("Graduação",Ensino.GRADUACAO);
//		addItem("Pós-graduação",Ensino.POS_GRADUACAO);
//		addItem("Mestrado",Ensino.MESTRADO);
//		addItem("Doutorado",Ensino.DOUTORADO);
//		addItem("Pós-Doutorado",Ensino.POS_DOUTORADO);
		
	}
	
}
