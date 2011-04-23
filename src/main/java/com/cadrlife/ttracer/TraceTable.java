package com.cadrlife.ttracer;

import java.util.Arrays;

import com.cadrlife.ttracer.graph.GraphView;
import com.cadrlife.ttracer.trace.Tracable;
import com.cadrlife.ttracer.trace.Tracer;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import com.trolltech.qt.gui.QTextEdit;
import com.trolltech.qt.gui.QWidget;

public class TraceTable extends QTableWidget implements Tracable{
//	private final GraphView graphView;
	private Tracer tracer;
	private QTextEdit resultTextEdit;

	public TraceTable(QWidget parent, GraphView graphView) {
		super(parent);
//		this.graphView = graphView;
		this.tracer = new Tracer(graphView);
		setColumnCount(3);
		setHorizontalHeaderLabels(Arrays.asList("Frontier","Explored","Eval"));
	}

	@Override
	public void putCell(int row, int col, String text) {
		setRowCount(Math.max(row+1,rowCount()));
		setItem(row, col, new QTableWidgetItem(text));
	}

	@Override
	public void reportSolution(String path, int cost) {
		this.resultTextEdit.setText(String.format("Goal found;\nSolution = %s;\nCost = %d;", path, cost));
	}
	
	public void execute() {
		this.reset();
		this.setRowCount(0);
		tracer.trace(this);
	}

	public Tracer getTracer() {
		return tracer;
	}

	@Override
	public void reportError(String message) {
		this.resultTextEdit.setText(message);
	}

	public void setResultBox(QTextEdit qTextEdit) {
		this.resultTextEdit = qTextEdit;
		
	}
}