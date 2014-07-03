package com.jornada.client.classes.resources;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;

public interface MyTemplate extends SafeHtmlTemplates {
	  @Template("<img style='position: relative; top: -3px; left: 0;' src=\"{0}\" />&nbsp;<font class='MainViewSubTitleLink' style='position: relative; top: -5px; left: 0;'>{1}</font>")
	  public SafeHtml createItem(SafeUri uri, SafeHtml message);
	  @Template("<font class='MainViewSubTitleLink' style='position: relative; top: -5px; left: 0;'>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;</font><img style='position: relative; top: -3px; left: 0;' src=\"{0}\" />&nbsp;<font class='MainViewSubTitleLink' style='position: relative; top: -5px; left: 0;'>{1}</font>")
	  public SafeHtml createItemWithSeparator(SafeUri uri, SafeHtml message);
	}
