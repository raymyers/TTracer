package com.cadrlife.ttracer;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPolygonF;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class Edge extends QGraphicsItem {
    private Node source;
    private Node dest;

    private QPointF sourcePoint = new QPointF();
    private QPointF destPoint = new QPointF();
    private double arrowSize = 10;
    private double penWidth = 1;
    private double extra = (penWidth + arrowSize) / 2.0;

    private QRectF boundingRect = new QRectF();


    QPointF sourceArrowP1 = new QPointF();
    QPointF sourceArrowP2 = new QPointF();
    QPointF destArrowP1 = new QPointF();
    QPointF destArrowP2 = new QPointF();

    QPolygonF pol1 = new QPolygonF();
    QPolygonF pol2 = new QPolygonF();

    public Edge(Node sourceNode, Node destNode) {
        source = sourceNode;
        dest = destNode;
        source.addEdge(this);
        dest.addEdge(this);
        adjust();
    }

    public Edge(Node sourceNode) {
    	 source = sourceNode;
         source.addEdge(this);
         adjust();
	}

	Node getSource() {
    	
        return source;
    }

    Node getDest() {
        return dest;
    }

    @Override
    public String toString() {
    	return "Edge " + source.getName() + " -> " + (getDestName());
    }

	private String getDestName() {
		return dest == null ? " NULL " : dest.getName();
	}
    
    void adjust() {
        QPointF destPos = destPos();
		QPointF sourcePos = source.pos();
		double dx = sourcePos.x() - destPos.x();
        double dy = sourcePos.y() - destPos.y();

        double length = Math.sqrt(dx*dx+dy*dy);
        if (length == 0.0) return;

        double paddingX = dx/length*20;
        double paddingY = dy/length*20;

        prepareGeometryChange();
        sourcePoint.setX(sourcePos.x() - paddingX);
        sourcePoint.setY(sourcePos.y() - paddingY);

        destPoint.setX(destPos.x() + paddingX);
        destPoint.setY(destPos.y() + paddingY);

        boundingRect.setBottomLeft(sourcePos);
        boundingRect.setTopRight(destPos);

        boundingRect = boundingRect.normalized();

        boundingRect.adjust(-extra, -extra, extra, extra);
    }

	private QPointF destPos() {
		return dest==null ? this.getSource().getGraph().getMousePosition() : dest.pos();
	}

    @Override
    public QRectF boundingRect() {
        return boundingRect;
    }

    @Override
    public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {

        if (source == null)
            return;

        // Draw the line itself
        QLineF line = new QLineF(sourcePoint, destPoint);

        painter.setPen(GraphView.QPEN_EDGE);
        painter.drawLine(line);

        // Draw the arrows if there's enough room
        double angle;
        if (line.length() > 0) {
            angle = Math.acos(line.dx() / line.length());
        }
        else {
            angle = 0;
        }

        if (line.dy() >= 0)
            angle = (Math.PI * 2) - angle;

        sourceArrowP1.setX(sourcePoint.x() + Math.sin(angle + Math.PI / 3) * arrowSize);
        sourceArrowP1.setY(sourcePoint.y() + Math.cos(angle + Math.PI / 3) * arrowSize);

        sourceArrowP2.setX(sourcePoint.x() + Math.sin(angle + Math.PI - Math.PI / 3) * arrowSize);
        sourceArrowP2.setY(sourcePoint.y() + Math.cos(angle + Math.PI - Math.PI / 3) * arrowSize);

        destArrowP1.setX(destPoint.x() + Math.sin(angle - Math.PI / 3) * arrowSize);
        destArrowP1.setY(destPoint.y() + Math.cos(angle - Math.PI / 3) * arrowSize);

        destArrowP2.setX(destPoint.x() + Math.sin(angle - Math.PI + Math.PI / 3) * arrowSize);
        destArrowP2.setY(destPoint.y() + Math.cos(angle - Math.PI + Math.PI / 3) * arrowSize);

        pol1.clear();
        pol2.clear();

        pol1.append(line.p1());
        pol1.append(sourceArrowP1);
        pol1.append(sourceArrowP2);

        pol2.append(line.p2());
        pol2.append(destArrowP1);
        pol2.append(destArrowP2);

        painter.setBrush(QColor.black);
        painter.drawPolygon(pol1);
        painter.drawPolygon(pol2);
    }

	public void setDest(Node node) {
		this.dest = node;
	}
}