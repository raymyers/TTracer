package com.cadrlife.ttracer.trace;

import java.util.ArrayList;
import java.util.List;

import com.cadrlife.ttracer.State;

public class TraceLine {
	private List<State> frontier = new ArrayList<State>();
	private List<State> explored = new ArrayList<State>();
	private State eval;
	
	public List<State> getFrontier() {
		return frontier;
	}
	public List<State> getExplored() {
		return explored;
	}
	public State getEval() {
		return eval;
	}
	public void setEval(State eval) {
		this.eval = eval;
	}
	public State getFirstOfFrontier() {
		return frontier.isEmpty() ? new State() :frontier.get(0);
	}
	
}
