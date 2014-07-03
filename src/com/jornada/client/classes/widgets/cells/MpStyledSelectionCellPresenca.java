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
//import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;

public class MpStyledSelectionCellPresenca extends AbstractInputCell<String, String> {
	
    private static final String PRESENCA = "1";
    private static final String FALTA = "2";
    private static final String FALTA_JUSTIFICADA="3";	

    interface Template extends SafeHtmlTemplates {
        @Template("<option value=\"{0}\">{1}</option>")
        SafeHtml deselected(String index, String value);
        
        @Template("<option value=\"{0}\" class='presente'>{1}</option>")
        SafeHtml deselectedPresente(String index, String value);
        
        @Template("<option value=\"{0}\" class='falta'>{1}</option>")
        SafeHtml deselectedFalta(String index, String value);
        
        @Template("<option value=\"{0}\" class='faltaJustificada'>{1}</option>")
        SafeHtml deselectedFaltaJustificada(String index, String value);        

        @Template("<option value=\"{0}\" class='semMatricula'>{1}</option>")
        SafeHtml deselectedSemMatricula(String index, String value);        

        
        @Template("<option value=\"{0}\" selected=\"selected\">{1}</option>")
        SafeHtml selected(String index, String value);
        
        @Template("<option value=\"{0}\" class='presente' selected=\"selected\">{1}</option>")
        SafeHtml selectedPresente(String index, String value);
        
        @Template("<option value=\"{0}\" class='falta' selected=\"selected\">{1}</option>")
        SafeHtml selectedFalta(String index, String value);
        
        @Template("<option value=\"{0}\" class='faltaJustificada' selected=\"selected\">{1}</option>")
        SafeHtml selectedFaltaJustificada(String index, String value);        

        @Template("<option value=\"{0}\" class='semMatricula' selected=\"selected\">{1}</option>")
        SafeHtml selectedSemMatricula(String index, String value);        
        
    }

    private static Template template;

    private final HashMap<String, String> indexForOption = new HashMap<String, String>();

    private final LinkedHashMap<String, String> options;

    private String style;

    public MpStyledSelectionCellPresenca(LinkedHashMap<String, String> options) {
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

    public MpStyledSelectionCellPresenca(LinkedHashMap<String, String> options, String style) {
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
            String newValue = (String)options.keySet().toArray()[select.getSelectedIndex()];
            
            SelectElement selected = select.getChild(select.getSelectedIndex()).cast();
            select.setClassName(selected.getClassName()+"Select");

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
        String htmlSelect;
//        if (style != null && !"".equals(style)) {
//            htmlSelect = "<select tabindex=\"-1\" class=\"" + style + "\">";
//            sb.appendHtmlConstant(htmlSelect);
//        } else {
//        	htmlSelect = "<select tabindex=\"-1\">";
////            sb.appendHtmlConstant("<select tabindex=\"-1\">");
//        }


        
        SafeHtmlBuilder sbOption = new SafeHtmlBuilder();

//        for (String option : options) {
        
        String styleSelected = style;
        
        for (String keyValue : options.keySet()) {
            if (keyValue.equals(selectedIndex)) {
            	
				if (keyValue.equals(PRESENCA)) {
					sbOption.append(template.selectedPresente(keyValue,options.get(keyValue)));
					styleSelected = "presenteSelect";
				} else if (keyValue.equals(FALTA)) {
					sbOption.append(template.selectedFalta(keyValue,options.get(keyValue)));
					styleSelected = "faltaSelect";
				} else if (keyValue.equals(FALTA_JUSTIFICADA)) {
					sbOption.append(template.selectedFaltaJustificada(keyValue,options.get(keyValue)));
					styleSelected = "faltaJustificadaSelect";
				} 
				else{
					sbOption.append(template.selectedSemMatricula(keyValue, options.get(keyValue)));
					styleSelected = "semMatriculaSelect";
				}            	               

            } else {
            	
				if (keyValue.equals(PRESENCA)) {
					sbOption.append(template.deselectedPresente(keyValue,options.get(keyValue)));
				} else if (keyValue.equals(FALTA)) {
					sbOption.append(template.deselectedFalta(keyValue,options.get(keyValue)));
				} else if (keyValue.equals(FALTA_JUSTIFICADA)) {
					sbOption.append(template.deselectedFaltaJustificada(keyValue,options.get(keyValue)));
				} 
				else {
					sbOption.append(template.deselectedSemMatricula(keyValue,options.get(keyValue)));
				} 
            }
        }
        
        
//        htmlSelect = "<select tabindex=\"-1\" class=\"" + styleSelected + "\">";
        htmlSelect = "<select class=\"" + styleSelected + "\" id='presencaSelection' >";
        sb.appendHtmlConstant(htmlSelect);
        sb.append(sbOption.toSafeHtml());
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
