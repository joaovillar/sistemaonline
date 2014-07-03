package com.jornada.client.classes.widgets.cells;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
//import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class MpStyledSelectionCell extends AbstractInputCell<String, String> {

    interface Template extends SafeHtmlTemplates {
        @Template("<option value=\"{0}\">{1}</option>")
        SafeHtml deselected(String index, String value);

        @Template("<option value=\"{0}\" selected=\"selected\">{1}</option>")
        SafeHtml selected(String index, String value);
    }

    private static Template template;

    private final HashMap<String, String> indexForOption = new HashMap<String, String>();

    private final LinkedHashMap<String, String> options;

    private String style;

    public MpStyledSelectionCell(LinkedHashMap<String, String> options) {
        super("change");
        if (template == null) {
            template = GWT.create(Template.class);
        }
        this.options = new LinkedHashMap<String, String>(options);
//        int index = 0;
        for (String key : options.keySet()){
//        for (HashMap<String, String> option : options) {
        	indexForOption.put(options.get(key), key);
        }
    }

    public MpStyledSelectionCell(LinkedHashMap<String, String> options, String style) {
        super("change");
        this.style = style;
        if (template == null) {
            template = GWT.create(Template.class);
        }
        this.options = new LinkedHashMap<String, String>(options);

//        int index = 0;
        for (String key : options.keySet()){
//          for (HashMap<String, String> option : options) {
              indexForOption.put(options.get(key), key);
          }
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
        
    	super.onBrowserEvent(context, parent, value, event, valueUpdater);
        String type = event.getType();
        
        if ("change".equals(type)) {
        	
            Object key = context.getKey();
            SelectElement select = parent.getFirstChild().cast();
//            int intTest = select.getSelectedIndex();
            
            
//            String newValue = options.get(select.getSelectedIndex());
            String newValue = (String)options.keySet().toArray()[select.getSelectedIndex()];
            setViewData(key, newValue);
            finishEditing(parent, newValue, key, valueUpdater);
            
            if (valueUpdater != null) {
                valueUpdater.update(newValue);
            }
            
        }
    }

    @Override
    public void render(Context context, String value, SafeHtmlBuilder sb) {
        // Get the view data.
        Object key = context.getKey();
        String viewData = getViewData(key);
        if (viewData != null && viewData.equals(value)) {
            clearViewData(key);
            viewData = null;
        }

        String selectedIndex = getSelectedIndex(viewData == null ? value : viewData);
        if (style != null && !"".equals(style)) {
            String html = "<select tabindex=\"-1\" class=\"" + style + "\">";
            sb.appendHtmlConstant(html);
        } else {
            sb.appendHtmlConstant("<select tabindex=\"-1\">");
        }

//        for (String option : options) {
        for (String keyValue : options.keySet()) {
            if (keyValue.equals(selectedIndex)) {
                sb.append(template.selected(keyValue,options.get(keyValue)));
//                sb.append(template.selected(options.get(keyValue)));
            } else {
                sb.append(template.deselected(keyValue,options.get(keyValue)));
//                sb.append(template.deselected(options.get(keyValue)));
            }
        }
        sb.appendHtmlConstant("</select>");
    }

    private String getSelectedIndex(String value) {
        
    	String strSelectedKey="";
    	for (String key : options.keySet()) {
    		if(key.equals(value)){
    			strSelectedKey = key;
    		}
    	}    	
    	return strSelectedKey;
//    	String index = indexForOption.get(value);
//        if (index == null) {
//            return "";
//        }
//        return index;
    }

}
