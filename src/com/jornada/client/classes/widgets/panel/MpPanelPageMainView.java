package com.jornada.client.classes.widgets.panel;

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
public class MpPanelPageMainView extends VerticalPanel{
    
   

    public MpPanelPageMainView(String strTitle, String strImageAddress) {

        this.setStyleName("fundo_tabela_componente_white");
        //this.setBorderWidth(3);
        Label lblTitle = new Label();
        //vPanelTitle.setSize("500px", "25px");
        //this.setVerticalAlignment(ALIGN_MIDDLE);
        HorizontalPanel h1 = new HorizontalPanel();
        HorizontalPanel h2 = new HorizontalPanel();
        h2.setWidth("100%");
        h2.setStyleName("designStandardLink");
        Image image = new Image(strImageAddress);
        image.setSize("16px", "16px");
        lblTitle.setText(strTitle);
        lblTitle.setHorizontalAlignment(ALIGN_LEFT);
        lblTitle.setStyleName("label_comum");
        
        h1.add(new InlineHTML("&nbsp"));
        h1.add(image);
        h1.add(new InlineHTML("&nbsp"));
        h1.add(lblTitle);
        h1.setVerticalAlignment(ALIGN_TOP);
        h2.setVerticalAlignment(ALIGN_TOP);
        h2.add(h1);
        //h2.setBorderWidth(3);
        h2.add(new InlineHTML("&nbsp"));


        this.add(h2); 
        //this.setBorderWidth(3);
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
