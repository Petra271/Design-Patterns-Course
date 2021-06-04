package hr.fer.ooup.lv_4.states;

import hr.fer.ooup.lv_4.gui.Renderer;
import hr.fer.ooup.lv_4.objects.GraphicalObject;
import hr.fer.ooup.lv_4.objects.Point;
import hr.fer.ooup.lv_4.model.DocumentModel;

public class AddShapeState implements State {

	private GraphicalObject prototype;
	private DocumentModel model;

	public AddShapeState(DocumentModel model, GraphicalObject prototype) {
		this.model = model;
		this.prototype = prototype;
	}

	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		GraphicalObject copy = this.prototype.duplicate();
		copy.translate(mousePoint);
		this.model.addGraphicalObject(copy);
	}

	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(Point mousePoint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterDraw(Renderer r, GraphicalObject go) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterDraw(Renderer r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeaving() {
		// TODO Auto-generated method stub

	}

}
