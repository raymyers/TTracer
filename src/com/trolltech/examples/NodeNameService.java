package com.trolltech.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeNameService {
	private final List<String> names = Arrays.asList("A","B","C","D","E","F","G","H","I","J");
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
