package com.cadrlife.ttracer;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QCursor;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QStyle;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class Node extends QGraphicsItem {

	private List<Edge> edges = new ArrayList<Edge>();
	private GraphView graph;
	private double adjust = 2;
	private QRectF boundingRect = new QRectF(-20 - adjust, -20 - adjust,
			43 + adjust, 43 + adjust);
	private String name;

	public Node(GraphView graphWidget) {
		setGraph(graphWidget);
		setFlag(QGraphicsItem.GraphicsItemFlag.ItemIsMovable);
		setZValue(1);
	}

	void addEdge(Edge edge) {
		edges.add(edge);
		edge.adjust();
	}

	@Override
	public QRectF boundingRect() {
		return boundingRect;
	}

	@Override
	public QPainterPath shape() {
		return GraphView.NODE_SHAPE;
	}
	
	
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		painter.setPen(Qt.PenStyle.NoPen);
		painter.setBrush(QColor.fromRgba(QColor.black.rgb() & 0x7fffffff));

		if ((option.state().isSet(QStyle.StateFlag.State_Sunken))) {
			painter.setBrush(GraphView.GRADIENT_SUNKEN);
		} else {
			painter.setBrush(GraphView.GRADIENT_NORMAL);
		}

		painter.setPen(GraphView.QPEN_BLACK);
		painter.drawEllipse(-20, -20, 40, 40);
		QPointF textOffset = new QPointF((double) 5, (double) -3);
		painter.drawText(this.boundingRect.center().subtract(textOffset), name);
	}

	@Override
	public Object itemChange(GraphicsItemChange change, Object value) {
		switch (change) {
		case ItemPositionChange:
			adjustEdges();
			getGraph().itemMoved();
			break;
		default:
			break;
		}

		return super.itemChange(change, value);
	}

	public void adjustEdges() {
		for (Edge edge : edges) {
			edge.adjust();
		}
	}

	@Override
	public void mousePressEvent(QGraphicsSceneMouseEvent event) {
		updateNode();
		super.mousePressEvent(event);
	}

	@Override
	public void mouseReleaseEvent(QGraphicsSceneMouseEvent event) {
		updateNode();
		super.mouseReleaseEvent(event);
	}
	
	public void updateNode() {
		update();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void rightClick(QMouseEvent event) {
		QMenu qMenu = new NodeMenu(this,this.getGraph());
		qMenu.exec(QCursor.pos());
	}

	public void removeAllEdges() {
		for (Edge e : new ArrayList<Edge>(this.edges)) {
			e.getSource().edges.remove(e);
			e.getDest().edges.remove(e);
			this.getGraph().scene().removeItem(e);
		}
	}

	public void setGraph(GraphView graph) {
		this.graph = graph;
	}

	public GraphView getGraph() {
		return graph;
	}
}