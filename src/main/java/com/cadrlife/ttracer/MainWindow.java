package com.cadrlife.ttracer;

import com.cadrlife.ttracer.graph.GraphView;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;

public final class MainWindow extends QMainWindow {
	private GraphView graphView;

	public MainWindow() {
		setWindowTitle("TTRacer Graph");
		QMenuBar menuBar = menuBar();
		menuBar.addMenu(fileMenu());
		menuBar.addMenu(graphMenu());
	}
	
	private QMenu graphMenu() {
		QMenu menu = new QMenu("&Graph",this);
		menu.addAction("&Snap To Grid",this, "snapToGrid()");
		return menu;
	}

	private QMenu fileMenu() {
		QMenu menu = new QMenu("&File",this);
		menu.addAction("&Open",this, "open()");
		menu.addAction("&Save",this, "save()");
		menu.addAction("Save As",this, "saveAs()");
		menu.addSeparator();
		menu.addAction("&Exit",this, "exit()");
		return menu;
	}
	
	@SuppressWarnings("unused")
	private void open() {

	}
	
	@SuppressWarnings("unused")
	private void save() {
		
	}
	
	@SuppressWarnings("unused")
	private void saveAs() {
		
	}
	
	@SuppressWarnings("unused")
	private void exit() {
		QApplication.exit();
	}

	@SuppressWarnings("unused")
	private void snapToGrid() {
		graphView.snapToGrid();
	}

	public void setGraphView(GraphView graphView) {
		this.graphView = graphView;
		
	}
}