package com.cadrlife.ttracer.trace;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cadrlife.ttracer.State;
import com.cadrlife.ttracer.graph.Edge;
import com.cadrlife.ttracer.graph.GraphView;
import com.cadrlife.ttracer.graph.Node;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Tracer {

	private final GraphView graphView;
	private boolean graphSearchMode = false;
	
	public Tracer(GraphView graphView) {
		this.graphView = graphView;
	}

	public void trace(Tracable traceTable) {
		Frontier frontier = new Frontier();
		frontier.add(new State("A",0));
		traceLine(traceTable, 0, frontier, Lists.<String>newArrayList(), Lists.<TraceLine>newArrayList());
	}

	private void traceLine(Tracable traceable, int row, Frontier frontier, List<String> explored, List<TraceLine> history) {
		String currentNodeName = frontier.getFirst().getNodeName();
		Node node = graphView.findNodeByName(currentNodeName);
		
		int currentCost = frontier.getFirst().getCost();
		TraceLine traceLine = new TraceLine();
		traceLine.getFrontier().addAll(frontier);
		traceLine.getExplored().addAll(explored);
		history.add(traceLine);
		frontier.popFirst();
		Map<Edge, Node> neighborsByEdge = node.getNeighborsByEdge();
		for (Entry<Edge,Node> e : neighborsByEdge.entrySet()) {
			Edge edge = e.getKey();
			Node neighbor = e.getValue();
			int cost = edge.getCost() + currentCost;
			if (!explored.contains(neighbor.getName())) {
				frontier.add(new State(neighbor.getName(),cost));
			}
		}
		State eval = putTraceLine(traceable, row, traceLine);
		if (node.isGoal()) {
			reportSolution(traceable,history);
		} else if (isLoopDetected(history)) {
			traceable.reportError("Infinite Loop");
		} else if (!eval.getNodeName().isEmpty() && !frontier.isEmpty()) {
			if (graphSearchMode) {
				explored.add(currentNodeName);
			}
			traceLine(traceable, row+1,frontier,explored,history);
		}
		
	}

	private boolean isLoopDetected(List<TraceLine> history) {
		if (graphSearchMode) {
			return false;
		}
		if (history.size() >20) {
			return true;
		}
		return false;
	}

	private void reportSolution(Tracable traceTable, List<TraceLine> history) {
		State goal = Iterables.getLast(history).getFirstOfFrontier();
		State prevState = goal;
		String path = goal.getNodeName();
		while ((prevState = computePrevState(prevState, history)) != null) {
			path = prevState.getNodeName() + " " + path;
		}
		traceTable.reportSolution(path, goal.getCost());
	}

	private State computePrevState(State goal, List<TraceLine> history) {
		int i = 0;
		int lastIndexContainingGoal = -1;
		for (TraceLine line : history) {
			if (line.getFrontier().contains(goal)) {
				lastIndexContainingGoal = i;
				if (i==0) {
					return null;
				}
				return history.get(lastIndexContainingGoal-1).getFirstOfFrontier();
			}
			i++;
		}
		return null;
	}

	private State putTraceLine(Tracable traceable, int row, TraceLine trace) {
		String frontierString = Joiner.on(" ").join(trace.getFrontier());
		String exploredString = Joiner.on(" ").join(trace.getExplored());
		State eval = trace.getFirstOfFrontier();
		putRow(traceable, row, frontierString, exploredString, eval);
		return eval;
	}	

	private <T> void putRow(Tracable traceTable, int row, String frontier,
			String explored, T eval) {
		System.out.println(row + ": " + frontier + ", " + explored + ", " + eval);
		traceTable.putCell(row, 0, frontier);
		traceTable.putCell(row, 1, explored);
		traceTable.putCell(row, 2, eval.toString());
	}

	public void enableGraphSearch() {
		this.graphSearchMode = true;
	}

	public void enableTreeSearch() {
		this.graphSearchMode = false;
	}

}
