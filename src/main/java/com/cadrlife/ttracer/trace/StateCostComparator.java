package com.cadrlife.ttracer.trace;

import java.util.Comparator;

import com.cadrlife.ttracer.State;

public class StateCostComparator implements Comparator<State> {

	@Override
	public int compare(State s1, State s2) {
		int c1 = s1.getCost();
		int c2 = s2.getCost();
		return Integer.valueOf(c1).compareTo(c2);
	}

}
