package hr.fer.ooup.lv_4.states;

import java.util.ArrayList;
import java.util.List;

import hr.fer.ooup.lv_4.gui.Renderer;
import hr.fer.ooup.lv_4.objects.GraphicalObject;
import hr.fer.ooup.lv_4.objects.LineSegment;
import hr.fer.ooup.lv_4.objects.Point;
import hr.fer.ooup.lv_4.model.DocumentModel;

public class EraserState implements State {

	private DocumentModel model;
	private Point end;
	private GraphicalObject line;
	private List<GraphicalObject> toDelete = new ArrayList<>();

	public EraserState(DocumentModel model) {
		this.model = model;

	}

	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		this.line = new LineSegment(mousePoint, mousePoint);
		this.toDelete.add(this.line);
		this.model.addGraphicalObject(this.line);
		this.end = mousePoint;
	}

	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {

		for (GraphicalObject o : this.toDelete) {
			this.model.removeGraphicalObject(o);
		}
	}

	@Override
	public void mouseDragged(Point mousePoint) {
		this.line = new LineSegment(this.end, mousePoint);
		this.toDelete.add(this.line);
		this.end = mousePoint;
		this.model.addGraphicalObject(this.line);
		for (GraphicalObject o : this.model.list()) {
			if (!this.toDelete.contains(o)) {
				if ((int) o.selectionDistance(mousePoint) == 0)
					this.toDelete.add(o);
			}
		}

	}

	@Override
	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterDraw(Renderer r, GraphicalObject go) {

	}

	@Override
	public void afterDraw(Renderer r) {

	}

	@Override
	public void onLeaving() {
		// TODO Auto-generated method stub

	}

}
