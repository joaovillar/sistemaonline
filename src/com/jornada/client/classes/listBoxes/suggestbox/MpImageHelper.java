package com.jornada.client.classes.listBoxes.suggestbox;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class MpImageHelper extends Image {


    SuggestBox box;
    PopupPanel myPopup;
    ListBox listBox;
    Collection<String> defaults;
    
    public MpImageHelper(ListBox list){
        
        this.setUrl("images/magnifier_medium.png");
        this.listBox = list;
        MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
        defaults = new ArrayList<String>();
        box = new SuggestBox(oracle);

        box.setWidth(Integer.toString(list.getElement().getOffsetWidth())+"px");      
        
        setStyleName("img_style");
        box.setStyleName("design_list_boxes");
        box.setLimit(5);

        
      this.addDomHandler(new MouseDownHandler() {
      
      @Override
      public void onMouseDown(MouseDownEvent event) {
          myPopup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
              public void setPosition(int offsetWidth, int offsetHeight) {
                  int left = listBox.getAbsoluteLeft();
                  int top = listBox.getAbsoluteTop() + 19;                  
                  myPopup.setPopupPosition(left, top);
              }
          });                 
//          myPopup.setStyleName("documentClass-PopPup");
          box.setFocus(true);
          myPopup.show();
          
          box.setText("");      
          box.setFocus(true);
//          box.setAutoSelectEnabled(true);
//          box.setAnimationEnabled(true);
          box.showSuggestionList();
          box.setFocus(true);
      }
  }, MouseDownEvent.getType());
        
//        listBox.addDomHandler(new MouseDownHandler() {
//            
//            @Override
//            public void onMouseDown(MouseDownEvent event) {
//                myPopup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
//                    public void setPosition(int offsetWidth, int offsetHeight) {
//                        int left = listBox.getAbsoluteLeft();
//                        int top = listBox.getAbsoluteTop() + 19;
//                        
//                        myPopup.setPopupPosition(left, top);
//                    }
//                });                 
//                myPopup.setStyleName("documentClass-PopPup");
//                myPopup.show();
//                box.setFocus(true);
//                box.setText("");
//                box.showSuggestionList();                
//            }
//        }, MouseDownEvent.getType());
        
        
        int itens = listBox.getItemCount();
        for (int i = 0; i < itens; i++) {
            oracle.add(listBox.getItemText(i));
            defaults.add(listBox.getItemText(i));
        }
        
        oracle.setDefaultSuggestionsFromText(defaults);
        
        
        box.setWidth("250px");
        box.setStyleName("design_list_boxes");
        
        
        box.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
            
            @Override
            public void onSelection(SelectionEvent<Suggestion> event) {
                
                int itens = listBox.getItemCount();
                for(int i=0; i<itens;i++){
                    if(listBox.getItemText(i).equals(box.getText())){
                        listBox.setSelectedIndex(i);
                        myPopup.hide();
                        break;                        
                    }
                }        
            }
        });
        
        myPopup = new PopupPanel(true);
        myPopup.setWidget(box);
        
        
    }

//    @Override
//    public void onClick(ClickEvent event) {
//        myPopup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
//            public void setPosition(int offsetWidth, int offsetHeight) {
//                int left = listBox.getAbsoluteLeft();
//                int top = listBox.getAbsoluteTop() + 19;
//                
//                myPopup.setPopupPosition(left, top);
//            }
//        });                 
//        myPopup.setStyleName("documentClass-PopPup");
//        myPopup.show();
//        box.setFocus(true);
//        box.setText("");
//        box.showSuggestionList();  
//        
//    }



}
