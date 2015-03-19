package com.jornada.client.classes.widgets.suggestoracle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
//import com.google.gwt.user.client.ui.SuggestOracle;

public class MpSuggestOracle extends MultiWordSuggestOracle {

    private List<String> data;

    public MpSuggestOracle(List<String> data) {
        this.data = data;
    }
    
    public MpSuggestOracle(ListBox listBox) {
        List<String> data = new ArrayList<String>();
        for (int i=0;i<listBox.getItemCount();i++) {
            data.add(listBox.getItemText(i));            
        }
        this.data = data;
    }
    
//    public MpSuggestOracle() {
////        this.data = data;
//    }

    @Override
    public void requestSuggestions(final Request request, final Callback callback) {
        String userInput = request.getQuery();
        List<Suggestion> suggestions = new LinkedList<Suggestion>();
        for (final String s : data) {
            if (s.toUpperCase().contains(userInput.toUpperCase())) {
                suggestions.add(new Suggestion() {
                    @Override
                    public String getReplacementString() {
                        return s;
                    }

                    @Override
                    public String getDisplayString() {
                        return s;
                    }
                });
            }
        }
        Response response = new Response(suggestions);
        callback.onSuggestionsReady(request, response);
    }
}