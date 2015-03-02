package com.jornada.client.ambiente.coordenador.relatorio;

import java.util.ArrayList;
import java.util.Set;

import com.google.gwt.dom.builder.shared.DivBuilder;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.AbstractCellTableBuilder;
import com.google.gwt.user.client.ui.Widget;

public class CustomCellTableBuilder extends AbstractCellTableBuilder<ArrayList<String>>{
  //here go fields, ctor etc.

  public CustomCellTableBuilder(AbstractCellTable<ArrayList<String>> cellTable) {
        super(cellTable);
        // TODO Auto-generated constructor stub
    }

//ids of elements which details we are going to show 
  private Set<Integer> elements;


  private void buildExtraRow(int index, Object rowValue){
      TableRowBuilder row = startRow();
      TableCellBuilder td = row.startTD().colSpan(getColumns().size());
      DivBuilder div = td.startDiv();

      Widget widget = new Widget();

      //update widget state and appearance here depending on rowValue

      div.html(SafeHtmlUtils.fromTrustedString(widget.getElement().getInnerHTML()));

      div.end();
      td.endTD();
      row.endTR();
  }

@Override
protected void buildRowImpl(ArrayList<String> rowValue, int absRowIndex) {
    if(elements.contains(absRowIndex)){
        buildExtraRow(absRowIndex, rowValue);
        elements.add(absRowIndex);
    }
    
}}
