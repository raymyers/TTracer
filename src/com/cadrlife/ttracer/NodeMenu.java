package com.cadrlife.ttracer;

import com.trolltech.qt.gui.QMenu;

public final class NodeMenu extends QMenu {
	private final GraphView graph;
	private final Node node;

	public NodeMenu(Node node, GraphView graph) {
		super("Node", graph);
		this.node = node;
		this.graph = graph;
		addAction("&Delete",this,"deleteNode()");
		addAction("Add Edge", this, "addEdge()");
	}
	
	@SuppressWarnings("unused")
	private void deleteNode() {
		graph.removeNode(node);
		this.close();
	}
	
	@SuppressWarnings("unused")
	private void addEdge() {
		graph.addEdgeWithSource(this.node);
		this.close();
	}
}