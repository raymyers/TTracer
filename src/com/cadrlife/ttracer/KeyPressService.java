package com.cadrlife.ttracer;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QKeyEvent;

public class KeyPressService {
	private final GraphView graphView;

	public KeyPressService(GraphView elasticNodes) {
		this.graphView = elasticNodes;
	}

	public void processKeyEvent(QKeyEvent event) {
		Qt.Key key = Qt.Key.resolve(event.key());
		switch (key) {
		case Key_Up:
			//graphView.moveBy(0, -20);
			break;
		case Key_Down:
			//graphView.centerNode.moveBy(0, 20);
			break;
		case Key_Left:
			//graphView.centerNode.moveBy(-20, 0);
			break;
		case Key_Right:
			//graphView.centerNode.moveBy(20, 0);
			break;
		case Key_Plus:
		case Key_Equal:
			graphView.scaleView(1.2);
			break;
		case Key_Minus:
			graphView.scaleView(1 / 1.2);
			break;
		case Key_Space:
		case Key_Enter:
			Node node1 = new Node(graphView);
			graphView.addNode(node1);
			//node1.setPos(graphView.getMousePosition());
			break;
		case Key_Tab:
			graphView.snapToGrid();
			break;
		default:
			graphView.rootkeyPressEvent(event);
		}
	}

}
