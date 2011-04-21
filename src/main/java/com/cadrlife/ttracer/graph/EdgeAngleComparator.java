package com.cadrlife.ttracer.graph;

import java.util.Comparator;

final class EdgeAngleComparator implements Comparator<Edge> {
	private final Node startNode;

	public EdgeAngleComparator(Node node) {
		this.startNode = node;
	}

	@Override
	public int compare(Edge e1, Edge e2) {
		Node neighbor1 = findNeighbor(e1);
		Node neighbor2 = findNeighbor(e2);
		double angle1 = computeAngle(neighbor1);
		double angle2 = computeAngle(neighbor2);
		return Double.compare(angle1, angle2);
	}

	private double computeAngle(Node neighbor) {
		double pi = 3.1415927;
		double dx = startNode.x() - neighbor.x();
		double dy = neighbor.y() - startNode.y();
		double theta;
		if (dx == 0) {
			theta = pi / 2;
		}
		else {
			theta = Math.atan(Math.abs(dy / dx));
		}
		if (dx <= 0 && dy > 0){
			return pi - theta;
		}
		if (dx <= 0 && dy <= 0) {
			return pi + theta;
		}
		if (dx >= 0 && dy <= 0) {
			return 2 * pi - theta;
		}
		return theta;
	}

	private Node findNeighbor(Edge e1) {
		return e1.isConnected(startNode, e1.getSource()) ? e1.getSource() : e1
				.getDest();
	}
}