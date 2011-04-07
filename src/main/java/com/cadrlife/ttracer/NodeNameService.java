package com.cadrlife.ttracer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cadrlife.ttracer.graph.Node;

public class NodeNameService {
	private final List<String> names = Arrays.asList("A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(","));
	public NodeNameService() {
	}
	public String nextAvailableName(List<Node> nodes) {
		ArrayList<String> availableNames = new ArrayList<String>(names);
		for (Node n : nodes) {
			availableNames.remove(n.getName());
		}
		if (availableNames.isEmpty()) {
			
			return "!";
		}
		return availableNames.get(0);
	}

}
