package com.jornada.client.classes.widgets.cells;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
//import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;

/** 
 * An editable text cell. Click to edit, escape to cancel, return to commit. 
 */ 
public class MpTextAreaEditCell extends AbstractEditableCell<String, MpTextAreaEditCell.ViewData> { 

    interface Template extends SafeHtmlTemplates { 
        //@Template("<input type=\"text\" value=\"{0}\" tabindex=\"-1\"></input>")  
        //SafeHtml input(String value);

        //using textarea (instead of text) to add multiple lines of data in a cell.
        @Template("<textarea class=\"design_text_area_cell_table\" tabindex=\"-1\" rows=\"{1}\" cols=\"{2}\" >{0}</textarea>")
//    	@Template("<textarea  tabindex=\"-1\" rows=\"{1}\" cols=\"{2}\" >{0}</textarea>")
        SafeHtml input(String value, Integer rows, Integer cols); 
    } 


    static class ViewData { 

        private boolean isEditing; 

        /** 
         * If true, this is not the first edit. 
         */ 
        private boolean isEditingAgain; 

        /** 
         * Keep track of the original value at the start of the edit, which might be 
         * the edited value from the previous edit and NOT the actual value. 
         */ 
        private String original; 

        private String text; 

        /** 
         * Construct a new ViewData in editing mode. 
         * 
         * @param text the text to edit 
         */ 
        public ViewData(String text) { 
            this.original = text; 
            this.text = text; 
            this.isEditing = true; 
            this.isEditingAgain = false; 

        } 

        @Override 
        public boolean equals(Object o) { 
            if (o == null) { 
                return false; 
            } 
            ViewData vd = (ViewData) o; 
            return equalsOrBothNull(original, vd.original) 
            && equalsOrBothNull(text, vd.text) && isEditing == vd.isEditing 
            && isEditingAgain == vd.isEditingAgain; 
        } 

        public String getOriginal() { 
            return original; 
        } 

        public String getText() { 
            return text; 
        } 

        @Override 
        public int hashCode() { 
            return original.hashCode() + text.hashCode() 
            + Boolean.valueOf(isEditing).hashCode() * 29 
            + Boolean.valueOf(isEditingAgain).hashCode(); 
        } 

        public boolean isEditing() { 
            return isEditing; 
        } 

        public boolean isEditingAgain() { 
            return isEditingAgain; 
        } 

        public void setEditing(boolean isEditing) { 
            boolean wasEditing = this.isEditing; 
            this.isEditing = isEditing; 

            // This is a subsequent edit, so start from where we left off. 
            if (!wasEditing && isEditing) { 
                isEditingAgain = true; 
                original = text; 
            } 
        } 

        public void setText(String text) { 
            this.text = text; 
        } 

        private boolean equalsOrBothNull(Object o1, Object o2) { 
            return (o1 == null) ? o2 == null : o1.equals(o2); 
        } 
    } 

    private static Template template; 
    private int inputWidth; 
    private int inputLength;

    public int getInputWidth() { 
        return inputWidth; 
    } 

    public void setInputWidth(int inputWidth) { 
        this.inputWidth = inputWidth; 
    } 

    public int getInputLength() { 
        return inputLength; 
    } 

    public void setInputLength(int inputLength) { 
        this.inputLength = inputLength; 
    } 

    private final SafeHtmlRenderer<String> renderer; 


    public MpTextAreaEditCell() {    

        this(SimpleSafeHtmlRenderer.getInstance(), 1, 20); 
        this.inputWidth = 10;
        this.inputLength =2;    
    }
    
    public MpTextAreaEditCell(int row, int column) {    

        this(SimpleSafeHtmlRenderer.getInstance(), 1, 20); 
        this.inputWidth = column;
        this.inputLength =row;    
    }

    private int rows, cols;


    public MpTextAreaEditCell(SafeHtmlRenderer<String> renderer, int r, int c) { 
        super("click", "keyup", "keydown", "blur"); 
        rows = r; 
        cols = c; 


        if (template == null) { 
            template = GWT.create(Template.class); 
        } 
        if (renderer == null) { 
            throw new IllegalArgumentException("renderer == null"); 
        } 
        this.renderer = renderer; 
    } 

    @Override 
    public boolean isEditing(Context context, Element parent, String value) { 
        ViewData viewData = getViewData(context.getKey()); 
        return viewData == null ? false : viewData.isEditing(); 
    } 

    @Override 
    public void onBrowserEvent(Context context, Element parent, String value, 
            NativeEvent event, ValueUpdater<String> valueUpdater) { 

        Object key = context.getKey(); 
        ViewData viewData = getViewData(key); 
        if (viewData != null && viewData.isEditing()) { 
            // Handle the edit event. 
            editEvent(context, parent, value, viewData, event, valueUpdater); 
        } else { 
            String type = event.getType(); 
            @SuppressWarnings("unused")
			int keyCode = event.getKeyCode(); 
            boolean enterPressed = "keyup".equals(type) ;
            //  && keyCode == KeyCodes.KEY_ENTER; 


            if ("click".equals(type) || enterPressed) { 
                // Go into edit mode. 
                if (viewData == null) { 
                    viewData = new ViewData(value); 
                    setViewData(key, viewData); 
                } else { 
                    viewData.setEditing(true); 
                } 
                edit(context, parent, value); 
            } 
        } 
    } 

    @Override 
    public void render(Context context, String value, SafeHtmlBuilder sb) { 

        // Get the view data. 
        Object key = context.getKey(); 
        ViewData viewData = getViewData(key); 
        if (viewData != null && !viewData.isEditing() && value != null && value.equals(viewData.getText())) { 
            clearViewData(key); 
            viewData = null; 
        } 

        if (viewData != null) { 
            String text = viewData.getText(); 
            if (viewData.isEditing()) { 

                sb.append(template.input(text, inputLength, inputWidth)); 
            } 
            else { 
//            	String escaped_text = text.replace("\n","<BR>").replace("\r","");
            	String escaped_text = text;
                // The user pressed enter, but view data still exists. 
                sb.append(renderer.render(escaped_text)); 
            } 
        } else if (value != null) { 
//        	String escaped_value = value.replace("\n","<BR>").replace("\r","");
        	String escaped_value = value;
            sb.append(renderer.render(escaped_value)); 
        } 
    } 

    @Override 
    public boolean resetFocus(Context context, Element parent, String value) { 

        if (isEditing(context, parent, value)) { 
            getInputElement(parent).focus(); 
            return true; 
        } 
        return false; 
    } 


    protected void edit(Context context, Element parent, String value) { 
        setValue(context, parent, value); 
        TextAreaElement input = getInputElement(parent); 
        input.focus(); 
        input.select(); 
    } 


    private void cancel(Context context, Element parent, String value) { 
        clearInput(getInputElement(parent)); 
        setValue(context, parent, value); 
    } 


    private native void clearInput(Element input) /*-{ 
    if (input.selectionEnd) 
      input.selectionEnd = input.selectionStart; 
    else if ($doc.selection) 
      $doc.selection.clear(); 
  }-*/; 


    private void commit(Context context, Element parent, ViewData viewData, ValueUpdater<String> valueUpdater) { 
        String value = updateViewData(parent, viewData, false); 
        clearInput(getInputElement(parent)); 
        setValue(context, parent, viewData.getOriginal()); 
        if (valueUpdater != null) { 
            valueUpdater.update(value); 
        } 
    } 

    private void editEvent(Context context, Element parent, String value, ViewData viewData, NativeEvent event, ValueUpdater<String> valueUpdater) { 
        String type = event.getType(); 
        boolean keyUp = "keyup".equals(type); 
        boolean keyDown = "keydown".equals(type); 
        if (keyUp || keyDown) { 
            int keyCode = event.getKeyCode(); 


            if (keyUp && keyCode == KeyCodes.KEY_ESCAPE) { 
                // Cancel edit mode. 
                String originalText = viewData.getOriginal(); 
                if (viewData.isEditingAgain()) { 
                    viewData.setText(originalText); 
                    viewData.setEditing(false); 
                } else { 
                    setViewData(context.getKey(), null); 
                } 
                cancel(context, parent, value); 
            } else { 
                // Update the text in the view data on each key. 
                updateViewData(parent, viewData, true); 
            } 
        } else if ("blur".equals(type)) { 

            EventTarget eventTarget = event.getEventTarget(); 
            if (Element.is(eventTarget)) { 
                Element target = Element.as(eventTarget); 
                if ("textarea".equals(target.getTagName().toLowerCase())) { 
                    commit(context, parent, viewData, valueUpdater); 
                } 
            } 
        } 
    } 


    private TextAreaElement  getInputElement(Element parent) { 
        return parent.getFirstChild().<TextAreaElement> cast(); 
    } 


    private String updateViewData(Element parent, ViewData viewData, boolean isEditing) { 
        TextAreaElement input = (TextAreaElement) parent.getFirstChild();       
        String value = input.getValue(); 
        viewData.setText(value); 
        viewData.setEditing(isEditing); 
        return value; 
    } 
} 