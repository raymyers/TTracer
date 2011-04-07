package com.cadrlife.ttracer.trace;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;

import com.cadrlife.ttracer.graph.GraphView;
import com.trolltech.qt.gui.QApplication;

public class TracerTest {

	GraphView graphView;
	private Tracer tracer;
	private Tracable tracable;
	@Before
	public void beforeEach() {
		QApplication.initialize(new String[]{});
		graphView = new GraphView();
		tracer = new Tracer(graphView);
		tracable = mock(Tracable.class);
	}
	
	@Test
	public void test() {
		tracer.trace(tracable);
		verify(tracable).putCell(0, 0, "A0");
	}
}
