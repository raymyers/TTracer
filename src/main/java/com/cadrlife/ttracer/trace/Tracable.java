package com.cadrlife.ttracer.trace;

public interface Tracable {
	void putCell(int row, int col, String text);
	void clear();
	void reportSolution(String path, int cost);
	void reportError(String message);
}
