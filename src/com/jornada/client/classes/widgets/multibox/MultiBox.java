package com.jornada.client.classes.widgets.multibox;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;

public class MultiBox extends Composite {
	List<String> itemsSelected = new ArrayList<String>();
	List<ListItem> itemsHighlighted = new ArrayList<ListItem>();

	private final BulletList list;
	private final ListItem item;
	private final TextBox itemBox;
	private final SuggestBox box;

	public MultiBox(MultiWordSuggestOracle oracle) {
		FlowPanel panel = new FlowPanel();
		initWidget(panel);

		list = new BulletList();
		list.setStyleName("multiValueSuggestBox-list");
		item = new ListItem();
		item.setStyleName("multiValueSuggestBox-input-token");
		itemBox = new TextBox();
		itemBox.getElement()
				.setAttribute(
						"style",
						"outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;");
		box = new SuggestBox(oracle, itemBox);
		box.getElement().setId("suggestion_box");
		item.add(box);
		list.add(item);

		itemBox.addKeyDownHandler(new KeyDownHandler() {

			public void onKeyDown(KeyDownEvent event) {
				switch (event.getNativeKeyCode()) {
				case KeyCodes.KEY_ENTER:

					if (itemBox.getValue().contains("@")) {
						deselectItem(itemBox, list);
					}
					break;

				case KeyCodes.KEY_BACKSPACE:
					if (itemBox.getValue().trim().isEmpty()) {
						if (itemsHighlighted.isEmpty()) {
							if (itemsSelected.size() > 0) {
								ListItem li = (ListItem) list.getWidget(list
										.getWidgetCount() - 2);
								Paragraph p = (Paragraph) li.getWidget(0);
								if (itemsSelected.contains(p.getText())) {
									itemsSelected.remove(p.getText());
								}
								list.remove(li);
							}
						}
					}

				case KeyCodes.KEY_DELETE:
					if (itemBox.getValue().trim().isEmpty()) {
						for (ListItem li : itemsHighlighted) {
							list.remove(li);
							Paragraph p = (Paragraph) li.getWidget(0);
							itemsSelected.remove(p.getText());
						}
						itemsHighlighted.clear();
					}
					itemBox.setFocus(true);
					break;
				}
			}
		});

		box.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			public void onSelection(
					SelectionEvent<SuggestOracle.Suggestion> selectionEvent) {
				deselectItem(itemBox, list);
			}
		});

		panel.add(list);

		panel.getElement().setAttribute("onclick",
				"document.getElementById('suggestion_box').focus()");
		box.setFocus(true);

	}

	private void deselectItem(final TextBox itemBox, final BulletList list) {
		if (itemBox.getValue() != null && !"".equals(itemBox.getValue().trim())) {

			final ListItem displayItem = new ListItem();
			displayItem.setStyleName("multiValueSuggestBox-token");
			Paragraph p = new Paragraph(itemBox.getValue());

			displayItem.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent clickEvent) {
					if (itemsHighlighted.contains(displayItem)) {
						displayItem.removeStyleDependentName("selected");
						itemsHighlighted.remove(displayItem);
					} else {
						displayItem.addStyleDependentName("selected");
						itemsHighlighted.add(displayItem);
					}
				}
			});

			Span span = new Span(" x ");
			span.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent clickEvent) {
					removeListItem(displayItem, list);
				}
			});

			displayItem.add(p);
			displayItem.add(span);

			itemsSelected.add(itemBox.getValue());

			list.insert(displayItem, list.getWidgetCount() - 1);
			itemBox.setValue("");
			itemBox.setFocus(true);
		}
	}

	private void removeListItem(ListItem displayItem, BulletList list) {
		itemsSelected.remove(displayItem.getWidget(0).getElement()
				.getInnerHTML());
		list.remove(displayItem);
	}

	public BulletList getList() {
		return list;
	}
}
