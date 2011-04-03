package com.cadrlife.ttracer;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QWidget;

public class TTracerMain {
	public static void main(String args[]) {
		QApplication.initialize(args);
		MainWindow mainWindow = new MainWindow();
		
		GraphView graphView = new GraphView();
		
		QWidget centralWidget = new QWidget(mainWindow);
		mainWindow.setCentralWidget(centralWidget);
		QGridLayout grid = new QGridLayout(graphView);
		centralWidget.setLayout(grid);
		grid.addWidget(graphView,0,0);
		grid.addWidget(new QTableWidget(mainWindow),0,1);
		//graphView.show();
		mainWindow.show();
		
		QApplication.exec();
	}
}
