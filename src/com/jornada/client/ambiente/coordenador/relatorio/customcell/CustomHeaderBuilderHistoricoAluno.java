package com.jornada.client.ambiente.coordenador.relatorio.customcell;


import java.util.ArrayList;
import java.util.HashSet;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.builder.shared.TableCellBuilder;
import com.google.gwt.dom.builder.shared.TableRowBuilder;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.cellview.client.AbstractCellTable.Style;
import com.google.gwt.user.cellview.client.AbstractHeaderOrFooterBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.ColumnSortList.ColumnSortInfo;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextHeader;
import com.jornada.shared.FieldVerifier;

interface ResourcesHistorico extends ClientBundle {
    @Source("CwCustomCellTabeHistoricoAluno.css")
    Styles styles();
  }

interface StylesHistorico extends CssResource {
  String childCell();
  String groupHeaderCell();
}

public class CustomHeaderBuilderHistoricoAluno extends AbstractHeaderOrFooterBuilder<ArrayList<String>> {

    private ResourcesHistorico resourcesHistorico;

    final CellTable<ArrayList<String>> cellTable;
    final ArrayList<String> arrayHeadersText;
    ArrayList<String> listPeriodos;
  
    
    public CustomHeaderBuilderHistoricoAluno(CellTable<ArrayList<String>> cellTable, ArrayList<String> arrayHeadersText) {
      super(cellTable, false);
      this.cellTable = cellTable;
      this.arrayHeadersText = arrayHeadersText;
      resourcesHistorico = GWT.create(ResourcesHistorico.class);
      resourcesHistorico.styles().ensureInjected();
      
      
        HashSet<String> hashPeriodos = new HashSet<String>();
//        for (int i = 1; i < arrayHeadersText.size(); i++) {
//            String strHeaderText = arrayHeadersText.get(i);
//
//            String strPeriodo = strHeaderText.substring(strHeaderText.indexOf("[") + 1, strHeaderText.indexOf("]") - 1);
//
//            if (strPeriodo != null && !strPeriodo.isEmpty()) {
//                hashPeriodos.add(strPeriodo);
//            }
//
//        }
        hashPeriodos.add("CICLO I");
        hashPeriodos.add("CICLO II");

        listPeriodos = new ArrayList<String>(hashPeriodos);
      
     }

    @Override
    protected boolean buildHeaderOrFooterImpl() {
      Style style = cellTable.getResources().style();
      String groupHeaderCell = resourcesHistorico.styles().groupHeaderCell();
      
      TableRowBuilder tr;
      TableCellBuilder th;
      tr = startRow();
      
      // Add a 2x2 header above the checkbox and show friends columns.
      tr.startTH().colSpan(1).rowSpan(5).className(style.header() + " " + style.firstColumnHeader()).text("ÁREAS DE CONHECIMENTO");
      tr.endTH();
      
        for (int i = 0; i < listPeriodos.size(); i++) {

            String strText = listPeriodos.get(i).toUpperCase();
            th = tr.startTH().colSpan(5).className(groupHeaderCell);
            th.style().trustedProperty("border-right", "1px solid gray").endStyle();
            th.text(strText).endTH();
        }
        
      // Get information about the sorted column.
      ColumnSortList sortList = cellTable.getColumnSortList();
      ColumnSortInfo sortedInfo = (sortList.size() == 0) ? null : sortList.get(0);
      Column<?, ?> sortedColumn = (sortedInfo == null) ? null : sortedInfo.getColumn();
      boolean isSortAscending = (sortedInfo == null) ? false : sortedInfo.isAscending();

      // Add column headers.
      tr = startRow();

      for(int i=0;i<arrayHeadersText.size();i++){
          
          String strHeaderText = arrayHeadersText.get(i);
          
          String strNomeAval = strHeaderText.substring(strHeaderText.indexOf(FieldVerifier.INI_SEPARATOR) + FieldVerifier.INI_SEPARATOR.length());
          
          Header<String> mpHeader = new TextHeader(strNomeAval);         
          Column<String, String> mpColumn = null;
          
          buildHeader(tr, mpHeader, mpColumn, sortedColumn, isSortAscending, false, false);
                    
      }

      tr.endTR();

      return true;
    }


    private void buildHeader(TableRowBuilder out, Header<?> header, Column<String, ?> column,
        Column<?, ?> sortedColumn, boolean isSortAscending, boolean isFirst, boolean isLast) {

      Style style = cellTable.getResources().style();
      boolean isSorted = false;//(sortedColumn == column);
      StringBuilder classesBuilder = new StringBuilder(style.header());

      // Create the table cell.
      TableCellBuilder th = out.startTH().className(classesBuilder.toString());
 
      // Render the header.
      Context context = new Context(0, 2, header.getKey());
      renderSortableHeader(th, context, header, isSorted, isSortAscending);

      // End the table cell.
      th.endTH();
    }
  }

