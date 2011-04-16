package com.cadrlife.ttracer;

import com.cadrlife.ttracer.graph.GraphView;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QToolBar;
import com.trolltech.qt.gui.QWidget;

public final class MainWindow extends QMainWindow {
	private GraphView graphView = new GraphView();
	private TraceTable traceTable = new TraceTable(this, graphView);

	public MainWindow() {
		setWindowTitle("TTRacer Graph");
		createLayout();
		addToolBar(toolBoar());
		QMenuBar menuBar = menuBar();
		menuBar.addMenu(fileMenu());
		menuBar.addMenu(graphMenu());
	}

	private void createLayout() {
		QWidget centralWidget = new QWidget(this);
		setCentralWidget(centralWidget);
		QGridLayout grid = new QGridLayout(graphView);
		centralWidget.setLayout(grid);
		grid.addWidget(graphView, 0, 0);
		grid.addWidget(traceTable, 0, 1);
	}
	
	private QToolBar toolBoar() {
		QToolBar toolBar = new QToolBar(this);
		QComboBox comboBox = new QComboBox();
		comboBox.addItem("Tree Search");
		comboBox.addItem("Graph Search");
		comboBox.currentStringChanged.connect(this,"changeSearchMethod(String)");
		toolBar.addWidget(comboBox);
		toolBar.addAction("Execute", traceTable, "execute()");
		return toolBar;
	}
	
	@SuppressWarnings("unused")
	private void changeSearchMethod(String choice) {
		if (choice.contains("Graph")) {
			traceTable.getTracer().enableGraphSearch();
		} else {
			traceTable.getTracer().enableTreeSearch();
		}
	}

	private QMenu graphMenu() {
		QMenu menu = new QMenu("&Graph",this);
		menu.addAction("&Snap To Grid", this, "snapToGrid()");
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