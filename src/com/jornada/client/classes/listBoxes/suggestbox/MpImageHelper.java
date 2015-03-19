package com.jornada.client.classes.listBoxes.suggestbox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
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
import com.jornada.client.classes.widgets.suggestoracle.MpSuggestOracle;

public class MpImageHelper extends Image {

    SuggestBox box;
    PopupPanel myPopup;
    ListBox listBox;
    Collection<String> defaults;

    public MpImageHelper(ListBox list) {

        this.setUrl("images/magnifier_medium.png");
        this.listBox = list;        
       
//        MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
        MpSuggestOracle oracle = new MpSuggestOracle(listBox);
        defaults = new ArrayList<String>();
        box = new SuggestBox(oracle);

        setStyleName("img_style");
        // box.setLimit(5);

        this.addDomHandler(new MouseDownHandler() {

            @Override
            public void onMouseDown(MouseDownEvent event) {
                myPopup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
                    public void setPosition(int offsetWidth, int offsetHeight) {
                        int left = listBox.getAbsoluteLeft();
                        int top = listBox.getAbsoluteTop();
                        myPopup.setPopupPosition(left, top);

                    }
                });

                myPopup.show();
                box.showSuggestionList();
                box.setText("");
                requestFocus();
            }
        }, MouseDownEvent.getType());

        int itens = listBox.getItemCount();
        for (int i = 0; i < itens; i++) {
            oracle.add(listBox.getItemText(i));
            defaults.add(listBox.getItemText(i));
        }

        oracle.setDefaultSuggestionsFromText(defaults);

        box.setStyleName("design_text_boxes");

        box.setAutoSelectEnabled(true);
        box.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

            @Override
            public void onSelection(SelectionEvent<Suggestion> event) {

                int itens = listBox.getItemCount();
                for (int i = 0; i < itens; i++) {
                    if (listBox.getItemText(i).equals(box.getText())) {
                        listBox.setSelectedIndex(i);
                        DomEvent.fireNativeEvent(Document.get().createChangeEvent(), listBox);
                        myPopup.hide();
                        break;
                    }
                }
            }
        });

        myPopup = new PopupPanel(true);
        myPopup.setWidget(box);
        myPopup.setStyleName("design_text_boxes");
        myPopup.setGlassEnabled(true);
    }

    public void requestFocus() {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            public void execute() {
                box.setWidth(Integer.toString(listBox.getElement().getOffsetWidth() - 1) + "px");
                box.setHeight(Integer.toString(listBox.getElement().getOffsetHeight()) + "px");
                box.setFocus(true);
            }
        });
    }
}
