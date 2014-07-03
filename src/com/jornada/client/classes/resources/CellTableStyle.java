package com.jornada.client.classes.resources;

import com.google.gwt.user.cellview.client.CellTable;


//public interface TableStyle extends CellTable.Resources {
//	 @Override
//	    @Source(value = {CellTable.Style.DEFAULT_CSS, "com/jornada/client/classes/resources/DataGrid.css"})
//	 CellTable.Style dataGridStyle();
//  }

public interface CellTableStyle extends CellTable.Resources {

	@Source({CellTable.Style.DEFAULT_CSS, "com/jornada/client/classes/resources/CellTableStyle.css"})

	CellTable.Style cellTableStyle();

//interface TableStyle extends CellTable.Style {}
}


