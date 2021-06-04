package hr.fer.ooup.lv_4.states;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import hr.fer.ooup.lv_4.gui.Renderer;
import hr.fer.ooup.lv_4.objects.CompositeShape;
import hr.fer.ooup.lv_4.objects.GraphicalObject;
import hr.fer.ooup.lv_4.objects.Point;
import hr.fer.ooup.lv_4.objects.Rectangle;
import hr.fer.ooup.lv_4.model.DocumentModel;

public class SelectShapeState implements State {

	private DocumentModel model;

	public SelectShapeState(DocumentModel model) {
		this.model = model;
	}

	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		List<GraphicalObject> obj = model.getSelectedObjects();
		if (obj.size() == 1) {
			GraphicalObject o = obj.get(0);
			int index = this.model.findSelectedHotPoint(o, mousePoint);
			if (index >= 0) {
				o.setHotPointSelected(index, true);
				return;
			}
		}

		GraphicalObject o = this.model.findSelectedGraphicalObject(mousePoint);
		if (o == null)
			return;
		if (!ctrlDown) {
			List<GraphicalObject> sel = this.model.list();
			for (int i = 0; i < sel.size(); i++) {
				sel.get(i).setSelected(false);
			}
		}

		if (!o.isSelected())
			o.setSelected(true);

	}

	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		List<GraphicalObject> obj = model.getSelectedObjects();
		if (obj.size() == 1) {
			GraphicalObject o = obj.get(0);
			for (int i = 0; i < o.getNumberOfHotPoints(); i++) {
				o.setHotPointSelected(i, false);
			}
		}
	}

	@Override
	public void mouseDragged(Point mousePoint) {
		List<GraphicalObject> obj = model.getSelectedObjects();
		if (obj.size() == 1) {
			GraphicalObject o = obj.get(0);
			for (int i = 0; i < o.getNumberOfHotPoints(); i++) {
				if (o.isHotPointSelected(i)) {
					o.setHotPoint(i, mousePoint);
				}
			}
		}
	}

	@Override
	public void keyPressed(int keyCode) {
		Point p = null;
		switch (keyCode) {
		case KeyEvent.VK_UP -> p = new Point(0, -1);
		case KeyEvent.VK_DOWN -> p = new Point(0, 1);
		case KeyEvent.VK_LEFT -> p = new Point(-1, 0);
		case KeyEvent.VK_RIGHT -> p = new Point(1, 0);
		}

		List<GraphicalObject> selected = this.model.getSelectedObjects();
		if (p != null) {
			for (GraphicalObject o : selected) {
				Point center = o.getBoundingBox().getCenter();
				o.translate(center.translate(p));
			}
		} else {
			if (selected.size() == 1) {
				GraphicalObject o = selected.get(0);
				if (o instanceof CompositeShape) {
					if (keyCode == KeyEvent.VK_U) {
						model.removeGraphicalObject(o);
						for (GraphicalObject go : ((CompositeShape) o).getObjects()) {
							this.model.addGraphicalObject(go);
							go.setSelected(true);
						}
					}
				}
				switch (keyCode) {
				case KeyEvent.VK_PLUS -> this.model.increaseZ(o);
				case KeyEvent.VK_MINUS -> this.model.decreaseZ(o);
				}
			} else if (selected.size() > 1) {
				if (keyCode == KeyEvent.VK_G) {
					List<GraphicalObject> newList = new ArrayList<>(selected);

					CompositeShape composite = new CompositeShape(newList);
					for (int i = 0; i < newList.size(); i++) {
						GraphicalObject o = newList.get(i);
						model.removeGraphicalObject(o);
					}

					composite.setSelected(true);
					model.addGraphicalObject(composite);
				}
			}
		}
	}

	@Override
	public void afterDraw(Renderer r, GraphicalObject go) {
		if (go.isSelected()) {
			Rectangle box = go.getBoundingBox();
			this.drawRectangle(r, box.getX(), box.getY(), box.getWidth(), box.getHeight());
		}

		if (model.getSelectedObjects().size() == 1 && go.isSelected() && !(go instanceof CompositeShape)) {
			Point[] hotPoints = go.getHotPoints();
			int dim = 6;
			for (Point p : hotPoints)
				this.drawRectangle(r, p.getX() - dim / 2, p.getY() - dim / 2, dim, dim);
		}
	}

	private void drawRectangle(Renderer r, int x, int y, int w, int h) {
		Point p1 = new Point(x, y);
		Point p2 = new Point(x + w, y);
		r.drawLine(p1, p2);

		p1 = new Point(x + w, y + h);
		r.drawLine(p2, p1);

		p2 = new Point(x, y + h);
		r.drawLine(p1, p2);

		p1 = new Point(x, y);
		r.drawLine(p2, p1);
	}

	@Override
	public void afterDraw(Renderer r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLeaving() {
		List<GraphicalObject> sel = this.model.getSelectedObjects();
		for (int i = 0; i < sel.size(); i++) {
			sel.get(i).setSelected(false);
		}
	}

}
