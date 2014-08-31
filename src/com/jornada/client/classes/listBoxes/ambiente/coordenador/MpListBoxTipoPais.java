package com.jornada.client.classes.listBoxes.ambiente.coordenador;

import com.jornada.client.classes.listBoxes.MpSelection;


public class MpListBoxTipoPais extends MpSelection {
	
	public MpListBoxTipoPais(){
		
		addItem(txtConstants.tipoPaiMae(),"mae");
		addItem(txtConstants.tipoPaiPai(),"pai");
		addItem(txtConstants.tipoPaiMadastra(), "madastra");
		addItem(txtConstants.tipoPaiPadastro(),"padastro");
		addItem(txtConstants.tipoPaiOutros(),"outros");
	}
	
}
