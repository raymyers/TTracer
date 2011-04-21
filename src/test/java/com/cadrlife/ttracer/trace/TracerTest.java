package com.cadrlife.ttracer.trace;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

import com.cadrlife.ttracer.graph.Edge;
import com.cadrlife.ttracer.graph.GraphView;
import com.cadrlife.ttracer.graph.Node;
import com.trolltech.qt.gui.QApplication;

public class TracerTest {
	private GraphView graphView;
	private Tracer tracer;
	private Tracable tracable;
	
	@Before
	public void beforeEach() {
		QApplication.initialize(new String[]{});
		graphView = new GraphView();
		tracer = new Tracer(graphView);
		tracable = mock(Tracable.class);
		setupGraph();
	}
	
	private void setupGraph() {
		createNode("A");
		createNode("B");
		createNode("C");
		createNode("D");
		createNode("E");
		createNode("F");
		createNode("G");
		createNode("H");
		createNode("I");
		graphView.findNodeByName("I").setGoal(true);
		createEdge("A", "D", 2);
		createEdge("A", "B", 2);
		createEdge("D", "G", 3);
		createEdge("D", "E", 3);
		createEdge("B", "C", 1);
		createEdge("C", "F", 2);
		createEdge("E", "F", 2);
		createEdge("E", "H", 2);
		createEdge("G", "H", 5);
		createEdge("F", "I", 6);
		createEdge("H", "I", 1);
	}

	private void createEdge(String sourceName, String destName, int cost) {
		Node sourceNode = graphView.findNodeByName(sourceName);
		Node destNode = graphView.findNodeByName(destName);
		Edge edge = graphView.addEdge(sourceNode, destNode);
		edge.setCost(cost);
	}

	private void createNode(String name) {
		Node a = new Node(graphView);
		a.setName(name);
		graphView.addNode(a);
	}

	@Test
	public void graphSearch() {
		tracer.enableGraphSearch();
		tracer.trace(tracable);
		
		verifyRow(0, "A0","","A0");
		verifyRow(1, "D2 B2","A","D2");
		verifyRow(2, "B2 G5 E5","A D","B2");
		verifyRow(3, "C3 G5 E5","A D B","C3");
		verifyRow(4, "G5 E5 F5","A D B C","G5");
		verifyRow(5, "E5 F5 H10","A D B C G","E5");
		verifyRow(6, "F5 H7","A D B C G E","F5");
		verifyRow(7, "H7 I11","A D B C G E F","H7");
		verifyRow(8, "I8","A D B C G E F H","I8");
		verify(tracable).reportSolution("A D E H I",8);
	}

	private void verifyRow(int row, String frontier, String explored, String eval) {
		verify(tracable).putCell(row, 0, frontier);
		verify(tracable).putCell(row, 1, explored);
		verify(tracable).putCell(row, 2, eval);
	}
}
