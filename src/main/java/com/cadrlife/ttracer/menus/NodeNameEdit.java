package com.cadrlife.ttracer.menus;

import com.cadrlife.ttracer.graph.Node;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QWidget;

public class NodeNameEdit extends QLineEdit {
		private final Node node;

		public NodeNameEdit(QWidget parent, Node node) {
			super(parent);
			this.setFocus();
			this.node = node;
			this.returnPressed.connect(this,"renameNode()");
			this.editingFinished.connect(this,"renameNode()");
		}
		
		
		@SuppressWarnings("unused")
		private void renameNode() {
			node.setName(this.text().trim());
			this.hide();
			this.destroy();
		}
	}