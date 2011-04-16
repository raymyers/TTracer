package com.cadrlife.ttracer.trace;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.cadrlife.ttracer.State;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Frontier implements Iterable<State> {
	private StateCostComparator stateComparator = new StateCostComparator();
	private List<State> states = Lists.newArrayList();
	
	@Override
	public Iterator<State> iterator() {
		return this.states.iterator();
	}


	public void add(State newState) {
		for (State s : Lists.newArrayList(states)) {
			if (s.getNodeName().equals(newState.getNodeName())) {
				if (newState.getCost() >= s.getCost()) {
					return;
				}
				states.remove(s);
			}
		}
		this.states.add(newState);
		Collections.sort(this.states,stateComparator);
	}


	public State getFirst() {
		return Iterables.get(states, 0);
	}


	public void addAll(Frontier frontier) {
		for (State s : frontier) {
			add(s);
		}
	}


	public State popFirst() {
		State first = getFirst();
		this.states.remove(first);
		return first;
	}


	public boolean isEmpty() {
		return states.isEmpty();
	}


	public boolean contains(State goal) {
		return states.contains(goal);
	}

}
