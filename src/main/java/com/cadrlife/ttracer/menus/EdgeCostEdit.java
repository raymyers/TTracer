package com.cadrlife.ttracer.menus;

import com.cadrlife.ttracer.graph.Edge;
import com.trolltech.qt.gui.QIntValidator;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QWidget;

public class EdgeCostEdit extends QLineEdit {

	private final Edge edge;


	public EdgeCostEdit(QWidget parent, Edge edge) {
		super(parent);
		this.setFocus();
		this.edge = edge;
		this.setValidator(new QIntValidator());
		this.returnPressed.connect(this,"renameEdge()");
		this.editingFinished.connect(this,"renameEdge()");
	}
	
	@SuppressWarnings("unused")
	private void renameEdge() {
		edge.setCost(Integer.parseInt(this.text().trim()));
		this.hide();
		this.destroy();
	}
}
