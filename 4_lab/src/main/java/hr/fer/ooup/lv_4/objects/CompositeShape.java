package hr.fer.ooup.lv_4.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.ooup.lv_4.gui.Renderer;

public class CompositeShape implements GraphicalObject {

	private List<GraphicalObject> objects;
	private boolean selected;
	private List<GraphicalObjectListener> listeners = new ArrayList<>();

	public CompositeShape(List<GraphicalObject> objects) {
		this.objects = objects;
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

	public List<GraphicalObject> getObjects() {
		return this.objects;
	}

	@Override
	public Rectangle getBoundingBox() {
		Rectangle box = null;
		int size = this.objects.size();
		int[] xValues = new int[size * 2];
		int[] yValues = new int[size * 2];
		for (int i = 0; i < size * 2; i += 2) {
			box = this.objects.get(i / 2).getBoundingBox();
			xValues[i] = box.getX();
			yValues[i] = box.getY();
			xValues[i + 1] = box.getX() + box.getWidth();
			yValues[i + 1] = box.getY() + box.getHeight();
		}
		Arrays.sort(xValues);
		Arrays.sort(yValues);
		int x = xValues[0];
		int y = yValues[0];
		int w = xValues[size * 2 - 1] - x;
		int h = yValues[size * 2 - 1] - y;
		return new Rectangle(x, y, w, h);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		Rectangle box = this.getBoundingBox();
		int dx = Math.max(box.getX() - mousePoint.getX(), mousePoint.getX() - (box.getX() + box.getWidth()));
		if (dx < 0)
			dx = 0;
		int dy = Math.max(box.getY() - mousePoint.getY(), mousePoint.getY() - (box.getY() + box.getHeight()));
		if (dy < 0)
			dy = 0;
		return Math.sqrt(dx * dx + dy * dy);
	}

	@Override
	public void render(Renderer r) {
		for (GraphicalObject o : this.objects)
			o.render(r);
	}

	@Override
	public GraphicalObject duplicate() {
		CompositeShape duplicate = new CompositeShape(this.objects);
		duplicate.setSelected(this.isSelected());
		return duplicate;
	}

	@Override
	public void translate(Point delta) {
		for (GraphicalObject o : this.objects)
			o.translate(delta);
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

	@Override
	public String getShapeName() {
		return "Kompozit";
	}

	@Override
	public int getNumberOfHotPoints() {
		return 0;
	}

	@Override
	public Point[] getHotPoints() {
		return null;
	}

	@Override
	public Point getHotPoint(int index) {
		return null;
	}

	@Override
	public void setHotPoint(int index, Point point) {
	}

	@Override
	public boolean isHotPointSelected(int index) {
		return false;
	}

	@Override
	public void setHotPointSelected(int index, boolean selected) {
	}

	@Override
	public double getHotPointDistance(int index, Point mousePoint) {
		return 0;
	}

	@Override
	public String getShapeID() {
		return "@COMP";
	}

	@Override
	public void save(List<String> rows) {
		for (GraphicalObject o : this.objects)
			o.save(rows);

		rows.add(this.getShapeID() + " " + this.objects.size() + "\n");
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {
		String[] splitData = data.split(" ");
		int num = Integer.parseInt(splitData[1]);
		List<GraphicalObject> objects = new ArrayList<>();
		while (num > 0) {
			objects.add(stack.pop());
			num -= 1;
		}
		stack.push(new CompositeShape(objects));
	}
}
