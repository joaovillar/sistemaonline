package com.jornada.client.classes.resources;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTree;


public interface CellTreeStyle extends CellTree.Resources  {

	@Source({CellTree.Style.DEFAULT_CSS, "com/jornada/client/classes/resources/CellTreeStyle.css"})

	CellTree.Style cellTreeStyle();
	
	@Source("images/cellTreeOpenItem.gif")
	ImageResource cellTreeOpenItem();
	
	@Source("images/cellTreeClosedItem.gif")
	ImageResource cellTreeClosedItem();

}


