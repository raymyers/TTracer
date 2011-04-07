package com.cadrlife.ttracer;

import java.util.Arrays;

import com.cadrlife.ttracer.graph.GraphView;
import com.cadrlife.ttracer.trace.Tracable;
import com.cadrlife.ttracer.trace.Tracer;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import com.trolltech.qt.gui.QWidget;

public class TraceTable extends QTableWidget implements Tracable{
	private final GraphView graphView;

	public TraceTable(QWidget parent, GraphView graphView) {
		super(parent);
		this.graphView = graphView;
		setColumnCount(3);
		setRowCount(20);
		setHorizontalHeaderLabels(Arrays.asList("Frontier","Explored","Eval"));
		new Tracer(graphView).trace(this);
	}

	@Override
	public void putCell(int row, int col, String text) {
		setItem(row, col, new QTableWidgetItem(text));
	}
}