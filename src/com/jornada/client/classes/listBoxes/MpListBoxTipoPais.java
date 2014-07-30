package com.jornada.client.classes.listBoxes;


public class MpListBoxTipoPais extends MpSelection {
	
	public MpListBoxTipoPais(){
		
		addItem(txtConstants.tipoPaiMae(),"mae");
		addItem(txtConstants.tipoPaiPai(),"pai");
		addItem(txtConstants.tipoPaiMadastra(), "madastra");
		addItem(txtConstants.tipoPaiPadastro(),"padastro");
		addItem(txtConstants.tipoPaiOutros(),"outros");
	}
	
}
