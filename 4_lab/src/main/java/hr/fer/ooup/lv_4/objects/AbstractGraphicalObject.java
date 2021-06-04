package hr.fer.ooup.lv_4.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.ooup.lv_4.utils.GeometryUtil;

public abstract class AbstractGraphicalObject implements GraphicalObject {

	private Point[] hotPoints;
	private boolean[] hotPointsSelected;
	private boolean selected;
	private List<GraphicalObjectListener> listeners = new ArrayList<>();

	public AbstractGraphicalObject(Point[] hotPoints) {
		super();
		Objects.requireNonNull(hotPoints);
		this.hotPoints = hotPoints;
		this.hotPointsSelected = new boolean[this.getNumberOfHotPoints()];
	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		this.notifySelectionListeners();
	}

	@Override
	public int getNumberOfHotPoints() {
		return this.hotPoints.length;
	}

	@Override
	public Point getHotPoint(int index) {
		return this.hotPoints[index];
	}

	@Override
	public Point[] getHotPoints() {
		return this.hotPoints;
	}

	@Override
	public void setHotPoint(int index, Point point) {
		this.hotPoints[index] = point;
		this.notifyListeners();
	}

	@Override
	public boolean isHotPointSelected(int index) {
		return this.hotPointsSelected[index];
	}

	@Override
	public void setHotPointSelected(int index, boolean selected) {
		this.hotPointsSelected[index] = selected;
	}

	@Override
	public double getHotPointDistance(int index, Point mousePoint) {
		return GeometryUtil.distanceFromPoint(this.hotPoints[index], mousePoint);
	}

	@Override
	public void translate(Point delta) {
		Point p = null;
		Rectangle box = this.getBoundingBox();
		for (int i = 0; i < this.getNumberOfHotPoints(); i++) {
			p = this.getHotPoint(i);
			p = p.translate(delta).difference(box.getCenter());
			this.setHotPoint(i, p);
		}
		this.notifyListeners();
	}

	@Override
	public void addGraphicalObjectListener(GraphicalObjectListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeGraphicalObjectListener(GraphicalObjectListener l) {
		this.listeners.remove(l);
	}

	protected void notifyListeners() {
		for (GraphicalObjectListener o : this.listeners) {
			o.graphicalObjectChanged(this);
		}
	}

	protected void notifySelectionListeners() {
		for (GraphicalObjectListener o : this.listeners) {
			o.graphicalObjectSelectionChanged(this);
		}
	}
	
	public Point[] swap(Point[] points) {
		Point p1 = points[0];
		Point p2 = points[1];
		if (p1.compare(p2) == 1) {
			Point tmp = p1;
			p1 = p2;
			p2 = tmp;
		}
		return new Point[] { p1, p2 };
	}

}
