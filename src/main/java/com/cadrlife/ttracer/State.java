package com.cadrlife.ttracer;

import org.apache.commons.lang.builder.EqualsBuilder;

public class State {
	private String nodeName = "";
	private int cost = 0;
	
	public State() {
		
	}
	
	public State(String nodeName, int cost) {
		this.nodeName = nodeName;
		this.cost = cost;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCost() {
		return cost;
	}
	
	@Override
	public String toString() {
		return nodeName + cost;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
