package com.cadrlife.ttracer.graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.trolltech.qt.gui.QApplication;

public class EdgeAngleComparatorTest {
	private EdgeAngleComparator comparator;
	private Node sourceNode;
	private GraphView graph;
	private Node destNode1;
	private Node destNode2;

	@BeforeClass
	public static void beforeClass() {
		QApplication.initialize(new String[]{});
	}
	
	@Before
	public void beforeEach() {
		graph = new GraphView();
		sourceNode = new Node(graph);
		destNode1 = new Node(graph);
		destNode2 = new Node(graph);
		comparator = new EdgeAngleComparator(sourceNode);
		sourceNode.setX(0);
		sourceNode.setY(0);
	}
	
	@Test
	public void test() {
		destNode1.setX(10);
		destNode1.setY(0);
		destNode2.setX(10);
		destNode2.setY(10);
		Edge edge1 = new Edge(sourceNode, destNode1);
		Edge edge2 = new Edge(sourceNode, destNode2);

		assertEquals(1, comparator.compare(edge1, edge2));
	}
}
