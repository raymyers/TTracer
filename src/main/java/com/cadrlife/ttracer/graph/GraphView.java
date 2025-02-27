package com.cadrlife.ttracer.graph;

import java.util.ArrayList;
import java.util.List;

import com.cadrlife.ttracer.KeyPressService;
import com.cadrlife.ttracer.NodeNameService;
import com.cadrlife.ttracer.menus.GraphMenu;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.MouseButton;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QCursor;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QLinearGradient;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPainterPath;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QRadialGradient;
import com.trolltech.qt.gui.QWheelEvent;

public class GraphView extends QGraphicsView {

	Node centerNode;
	List<Node> nodes = new ArrayList<Node>();
	private boolean mousePressed;

	private static final QBrush BRUSH_DARK_GRAY = new QBrush(QColor.darkGray);
	static final QPen QPEN_EDGE = new QPen(QColor.black, 1,
			Qt.PenStyle.SolidLine, Qt.PenCapStyle.RoundCap,
			Qt.PenJoinStyle.RoundJoin);
	static QRadialGradient GRADIENT_SUNKEN;
	static QRadialGradient GRADIENT_NORMAL;

	static QPainterPath NODE_SHAPE;
	private NodeNameService nodeNameService = new NodeNameService();
	private QPointF mousePosition = new QPointF();
	private boolean addingEdgeMode;
	private Edge addedEdge;

	static {
		NODE_SHAPE = new QPainterPath();
		NODE_SHAPE.addEllipse(-20, -20, 40, 40);

		GRADIENT_SUNKEN = new QRadialGradient(-3, -3, 20);
		GRADIENT_SUNKEN.setCenter(3, 3);
		GRADIENT_SUNKEN.setFocalPoint(3, 3);
		GRADIENT_SUNKEN.setColorAt(1, new QColor(QColor.yellow).lighter(120));
		GRADIENT_SUNKEN.setColorAt(0,
				new QColor(QColor.darkYellow).lighter(120));

		GRADIENT_NORMAL = new QRadialGradient(-3, -3, 20);
		GRADIENT_NORMAL.setColorAt(0, QColor.yellow);
		GRADIENT_NORMAL.setColorAt(1, QColor.darkYellow);
	}

	public GraphView() {
		QGraphicsScene scene = new QGraphicsScene(this);
		scene.setItemIndexMethod(QGraphicsScene.ItemIndexMethod.NoIndex);
		scene.setSceneRect(-300, -200, 600, 400);
		setScene(scene);

		setCacheMode(new QGraphicsView.CacheMode(
				QGraphicsView.CacheModeFlag.CacheBackground));

		setRenderHint(QPainter.RenderHint.Antialiasing);
		setTransformationAnchor(QGraphicsView.ViewportAnchor.AnchorUnderMouse);
		setResizeAnchor(QGraphicsView.ViewportAnchor.AnchorViewCenter);
		//scale(2, 2);
		setMinimumSize(400, 400);
		setWindowTitle(tr("Elastic Nodes"));
		setWindowIcon(new QIcon("classpath:com/trolltech/images/qt-logo.png"));
	}

	public void createDefaultScene() {
		Node node1 = new Node(this);
		addNode(node1);
		Node node2 = new Node(this);
		addNode(node2);

		node1.setPos(-50, -50);
		node2.setPos(0, -50);
		Edge edge = new Edge(node1, node2);
		scene().addItem(edge);
		node1.addEdge(edge);
		node2.addEdge(edge);
	}

	public void addNode(Node node1) {
		scene().addItem(node1);
		nodes.add(node1);
		if (node1.getName().isEmpty()) {
			node1.setName(nodeNameService.nextAvailableName(nodes));
		}
		node1.setPos(getMousePosition().x(), getMousePosition().y());
	}

	@Override
	protected void keyPressEvent(QKeyEvent event) {
		new KeyPressService(this).processKeyEvent(event);
	}

	@Override
	protected void mouseMoveEvent(QMouseEvent event) {
		QPointF mapToScene = this.mapToScene(event.pos());
		this.setMousePosition(mapToScene);
		if (this.mousePressed) {
			for (Node n : nodes) {
				n.adjustEdges();
			}
		}
		super.mouseMoveEvent(event);
	}

	@Override
	protected void mousePressEvent(QMouseEvent event) {
		this.mousePressed = true;
		boolean isRightClick = event.button() == MouseButton.RightButton;
		QGraphicsItemInterface clickedItem = this.itemAt(event.pos());
		if (clickedItem != null && clickedItem instanceof Node) {
			Node node = (Node) clickedItem;
			if (isRightClick) {
				node.rightClick(event);
			} else if (addingEdgeMode && addedEdge.getSource() != node) {
				addingEdgeMode = false;
				addedEdge.setDest(node);
				node.addEdge(addedEdge);
				addedEdge.adjust();
			}
		}
		if (clickedItem != null && clickedItem instanceof Edge) {
			Edge edge = (Edge) clickedItem;
			if (isRightClick) {
				edge.rightClick(event);
			}
		}
		if (clickedItem == null && isRightClick) {
			new GraphMenu(this).exec(QCursor.pos());
		}
		super.mousePressEvent(event);
	}

	@Override
	protected void mouseReleaseEvent(QMouseEvent event) {
		this.mousePressed = false;
		super.mouseReleaseEvent(event);
		for (Node n : nodes) {
			n.adjustEdges();
		}
	}

	@Override
	protected void wheelEvent(QWheelEvent event) {
		scaleView(Math.pow(2, -event.delta() / 240.0));
	}

	@Override
	protected void drawBackground(QPainter painter, QRectF rect) {
		// Shadow
		QRectF sceneRect = this.sceneRect();
		QRectF rightShadow = new QRectF(sceneRect.right(), sceneRect.top() + 5,
				5, sceneRect.height());
		QRectF bottomShadow = new QRectF(sceneRect.left() + 5,
				sceneRect.bottom(), sceneRect.width(), 5);
		if (rightShadow.intersects(rect) || rightShadow.contains(rect))
			painter.fillRect(rightShadow, BRUSH_DARK_GRAY);
		if (bottomShadow.intersects(rect) || bottomShadow.contains(rect))
			painter.fillRect(bottomShadow, BRUSH_DARK_GRAY);

		// Fill
		QLinearGradient gradient = new QLinearGradient(sceneRect.topLeft(),
				sceneRect.bottomRight());
		gradient.setColorAt(0, QColor.white);
		gradient.setColorAt(1, QColor.lightGray);
		painter.fillRect(rect.intersected(sceneRect), new QBrush(gradient));
		painter.setBrush(QBrush.NoBrush);
		painter.drawRect(sceneRect);
	}

	public void scaleView(double scaleFactor) {
		QMatrix m = matrix();
		m.scale(scaleFactor, scaleFactor);
		double factor = m.mapRect(new QRectF(0, 0, 1, 1)).width();
		if (factor < 0.07 || factor > 100)
			return;

		scale(scaleFactor, scaleFactor);
	}

	public void rootkeyPressEvent(QKeyEvent event) {
		super.keyPressEvent(event);
	}

	public void itemMoved() {

	}

	public void setMousePosition(QPointF qPoint) {
		this.mousePosition = qPoint;
	}

	public QPointF getMousePosition() {
		return mousePosition;
	}

	public void removeNode(Node node) {
		this.nodes.remove(node);
		node.removeAllEdges();

		this.scene().removeItem(node);
	}
	
	public void removeEdge(Edge edge) {
		edge.getSource().removeEdge(edge);
		edge.getDest().removeEdge(edge);
		scene().removeItem(edge);
	}

	public void addEdgeWithSource(Node node) {
		Edge e = new Edge(node);
		scene().addItem(e);
		node.addEdge(e);
		node.update(node.boundingRect());
		addingEdgeMode = true;
		addedEdge = e;
	}

	public void snapToGrid() {
		for (Node n : this.nodes) {
			n.setX((Math.round(n.pos().x()/25) * 25));
			n.setY((Math.round(n.pos().y()/25) * 25));
			n.adjustEdges();
		}
	}

	public Node findNodeByName(String name) {
		for (Node n : this.nodes) {
			if (name.equals(n.getName())) {
				return n;
			}
		}
		throw new IllegalArgumentException("Cannot find node named: " + name);
	}

	public Edge addEdge(Node source, Node dest) {
		Edge edge = new Edge(source, dest);
		scene().addItem(edge);
		source.addEdge(edge);
		dest.addEdge(edge);
		return edge;
	}

}