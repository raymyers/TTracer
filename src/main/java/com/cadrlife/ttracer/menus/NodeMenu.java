package com.cadrlife.ttracer.menus;

import com.cadrlife.ttracer.graph.GraphView;
import com.cadrlife.ttracer.graph.Node;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMenu;

public final class NodeMenu extends QMenu {
	

	private final GraphView graph;
	private final Node node;

	public NodeMenu(Node node, GraphView graph) {
		super("Node", graph);
		this.node = node;
		this.graph = graph;
		addAction("&Delete",this,"deleteNode()");
		addAction("&Rename",this,"renameNode()");
		addAction("Add &Edge", this, "addEdge()");
		addAction("Toggle &Goal", this, "toggleGoal()");
	}
	
	@SuppressWarnings("unused")
	private void deleteNode() {
		graph.removeNode(node);
		this.close();
	}
	
	@SuppressWarnings("unused")
	private void renameNode() {
		QLineEdit edit = new NodeNameEdit(graph,node);
		edit.resize(100, 30);
		edit.move(graph.mapFromScene(node.pos().subtract(new QPointF(50,15))));
		edit.setText(node.getName());
		edit.show();
		this.close();
	}
	
	@SuppressWarnings("unused")
	private void toggleGoal() {
		this.node.setGoal(!node.isGoal());
		this.close();
	}
	
	@SuppressWarnings("unused")
	private void addEdge() {
		graph.addEdgeWithSource(this.node);
		this.close();
	}
}