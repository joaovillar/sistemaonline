package com.jornada.client.classes.widgets.panel;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author waf039
 */
public class MpPanelLogin extends VerticalPanel{
    
   

    public MpPanelLogin(String strTitle, String strImageAddress) {

        this.setStyleName("fundo_tabela_componente_white_login");
        //this.setBorderWidth(3);
        Label lblTitle = new Label();
        //vPanelTitle.setSize("500px", "25px");
        //this.setVerticalAlignment(ALIGN_MIDDLE);
//        HorizontalPanel h1 = new HorizontalPanel();
        HorizontalPanel h2 = new HorizontalPanel();
        h2.setWidth("100%");
        h2.setStyleName("designLogin");
        Image image = new Image(strImageAddress);
        image.setSize("24px", "24px");
        lblTitle.setText(strTitle);
        lblTitle.setHorizontalAlignment(ALIGN_LEFT);
        lblTitle.setStyleName("label_login");
        
//        h1.add(new InlineHTML("&nbsp"));
//        h1.add(image);
//        h1.add(new InlineHTML("&nbsp"));
//        h1.add(lblTitle);
//        h1.setVerticalAlignment(ALIGN_TOP);
//        h2.setVerticalAlignment(ALIGN_TOP);
//        h2.add(h1);
        //h2.setBorderWidth(3);
//        h2.add(new InlineHTML("&nbsp"));

        this.add(h2); 
        
        Grid grid = new Grid(1,3);
        grid.setCellSpacing(2);
        grid.setCellPadding(2);
        grid.setStyleName("designLogin");
        //this.setBorderWidth(3);
//        grid.setWidget(0, 0, image);
        grid.setWidget(0, 0, new InlineHTML("&nbsp;"));
//        grid.setWidget(0, 1, image);
        grid.setWidget(0, 1, new InlineHTML("&nbsp;"));
        grid.setWidget(0, 2, lblTitle);
        h2.add(grid);
        
        this.setVerticalAlignment(ALIGN_TOP);

        
    }
    
    
    public void addPage(Widget widget){
        
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.add(new InlineHTML("&nbsp"));
        //hPanel.setBorderWidth(3);
        hPanel.add(widget);
        this.add(hPanel);
        
    }
    
    public void addPage(Widget widget, boolean hasHorizontalSpace){
        
        HorizontalPanel hPanel = new HorizontalPanel();
        if(hasHorizontalSpace){
        	hPanel.add(new InlineHTML("&nbsp"));	
        }        
        hPanel.add(widget);
        this.add(hPanel);
        
    }    

    
}
