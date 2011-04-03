package com.trolltech.examples;

import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QWidget;

public final class NodeMenu extends QMenu {
	NodeMenu(String title, QWidget parent) {
		super(title, parent);
		addAction("Delete");
		addAction("Add Edge");
	}
}