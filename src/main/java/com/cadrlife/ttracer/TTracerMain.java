package com.cadrlife.ttracer;

import com.trolltech.qt.gui.QApplication;

public class TTracerMain {
	public static void main(String args[]) {
		QApplication.initialize(args);
		MainWindow mainWindow = new MainWindow();
		mainWindow.show();
		
		QApplication.exec();
	}
}
