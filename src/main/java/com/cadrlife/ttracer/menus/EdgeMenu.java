package com.cadrlife.ttracer.menus;

import com.cadrlife.ttracer.graph.Edge;
import com.cadrlife.ttracer.graph.GraphView;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMenu;

public class EdgeMenu extends QMenu {
	private final Edge edge;
	private final GraphView graph;

	public EdgeMenu(Edge edge, GraphView graphView) {
		super(edge.scene().views().get(0));
		this.edge = edge;
		this.graph = graphView;
		addAction("&Delete",this,"deleteEdge()");
		addAction("&Edit Cost",this,"editCost()");
		addAction("&Toggle Direction",edge,"toggleDirection()");
	}
	
	@SuppressWarnings("unused")
	private void deleteEdge() {
		graph.removeEdge(edge);
		this.close();
	}
	
	@SuppressWarnings("unused")
	private void editCost() {
		QLineEdit edit = new EdgeCostEdit(graph,edge);
		edit.resize(80, 30);
		edit.move(graph.mapFromScene(edge.boundingRect().center().add(new QPointF(-40,-15))));
		edit.setText(Integer.toString(edge.getCost()));
		edit.show();
		this.close();
	}
}
