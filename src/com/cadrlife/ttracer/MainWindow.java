package com.cadrlife.ttracer;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;

public final class MainWindow extends QMainWindow {
	public MainWindow() {
		setWindowTitle("TTRacer Graph");
		QMenuBar menuBar = menuBar();
		menuBar.addMenu(fileMenu());
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
	
}