package com.cadrlife.ttracer.trace;

import com.cadrlife.ttracer.State;
import com.cadrlife.ttracer.graph.GraphView;
import com.cadrlife.ttracer.graph.Node;
import com.google.common.base.Joiner;

public class Tracer {

	private final GraphView graphView;
	
	public Tracer(GraphView graphView) {
		this.graphView = graphView;
	}

	public void trace(Tracable traceTable) {
		traceLine(traceTable, 0, "A");
	}

	private void traceLine(Tracable traceTable, int row, String startNode) {
		Node node = graphView.findNodeByName(startNode);
//		Map<Edge, Node> neighborsByEdge = node.getNeighborsByEdge();
		TraceLine trace = new TraceLine();
		trace.getFrontier().add(new State(node.getName(),0));
		String frontierString = Joiner.on(" ").join(trace.getFrontier());
		String exploredString = Joiner.on(" ").join(trace.getExplored());
		State eval = trace.getFirstOfFrontier();
		putRow(traceTable, row++, frontierString, exploredString, eval);
		if (row < 5 && !eval.getNodeName().isEmpty()) {
			//traceLine(traceTable, row,)
		}
	}	

	private <T> void putRow(Tracable traceTable, int row, String frontier,
			String explored, T eval) {
		traceTable.putCell(row, 0, frontier);
		traceTable.putCell(row, 1, explored);
		traceTable.putCell(row, 2, eval.toString());
	}
	
	

}
