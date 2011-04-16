package com.cadrlife.ttracer.trace;

import java.util.ArrayList;
import java.util.List;

import com.cadrlife.ttracer.State;

public class TraceLine {
	private Frontier frontier = new Frontier();
	private List<String> explored = new ArrayList<String>();
	private State eval;
	
	public Frontier getFrontier() {
		return frontier;
	}
	public List<String> getExplored() {
		return explored;
	}
	public State getEval() {
		return eval;
	}
	public void setEval(State eval) {
		this.eval = eval;
	}
	public State getFirstOfFrontier() {
		return frontier.isEmpty() ? new State() :frontier.getFirst();
	}
	
}
