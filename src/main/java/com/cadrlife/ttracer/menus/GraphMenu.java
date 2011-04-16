package com.cadrlife.ttracer.menus;

import com.cadrlife.ttracer.graph.GraphView;
import com.cadrlife.ttracer.graph.Node;
import com.trolltech.qt.gui.QMenu;

public final class GraphMenu extends QMenu {

	private final GraphView graph;

	public GraphMenu(GraphView graph) {
		super("Graph", graph);
		this.graph = graph;
		addAction("Add &Node", this, "addNode()");
	}
	
	@SuppressWarnings("unused")
	private void addNode() {
		graph.addNode(new Node(graph));
		this.close();
	}
}