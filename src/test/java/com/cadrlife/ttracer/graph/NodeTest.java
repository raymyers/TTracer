package com.cadrlife.ttracer.graph;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.trolltech.qt.gui.QApplication;

public class NodeTest {
	private GraphView graph;
	private Node startNode;

	@BeforeClass
	public static void beforeClass() {
		QApplication.initialize(new String[]{});
	}
	
	@Before
	public void beforeEach() {
		graph = new GraphView();
		startNode = new Node(graph).setName("A");
	}
	
	@Test
	public void expandWithNoNeighbors() {
		Map<Edge, Node> neighborsByEdge = startNode.getNeighborsByEdge();
		assertTrue(neighborsByEdge.isEmpty());
	}
	
	@Test
	public void expandWithOneNeighbor() {
		Node nodeB = new Node(graph).setName("B");
		graph.addNode(nodeB);
		graph.addEdge(startNode, nodeB);
		Map<Edge, Node> neighborsByEdge = startNode.getNeighborsByEdge();
		Entry<Edge, Node> entry = neighborsByEdge.entrySet().iterator().next();
		assertSame(nodeB, entry.getValue());
	}

	@Test
	public void expandWithTwoNeighborsShouldReturnInAngleOrder() {
		Node nodeB = new Node(graph).setName("B");
		Node nodeC = new Node(graph).setName("C");
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addEdge(startNode, nodeB);
		graph.addEdge(startNode, nodeC);
		Map<Edge, Node> neighborsByEdge = startNode.getNeighborsByEdge();
		Entry<Edge, Node> firstNeighbor = Iterables.get(neighborsByEdge.entrySet(), 0);
		Entry<Edge, Node> secondNeighbor = Iterables.get(neighborsByEdge.entrySet(), 1);
		assertSame(nodeB, firstNeighbor.getValue());
		assertSame(nodeC, secondNeighbor.getValue());
	}
}
