package com.cadrlife.ttracer;

import com.trolltech.qt.gui.QApplication;

public class TTracerMain {
	public static void main(String args[]) {
		QApplication.initialize(args);
		
		GraphView graphView = new GraphView();
		graphView.show();
		QApplication.exec();
	}
}
