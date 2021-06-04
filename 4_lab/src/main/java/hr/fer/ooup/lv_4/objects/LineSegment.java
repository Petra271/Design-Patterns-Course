package hr.fer.ooup.lv_4.objects;

import java.util.List;
import java.util.Stack;

import hr.fer.ooup.lv_4.gui.Renderer;
import hr.fer.ooup.lv_4.utils.GeometryUtil;

public class LineSegment extends AbstractGraphicalObject {

	public LineSegment(Point a, Point b) {
		super(new Point[] { a, b });
	}

	public LineSegment() {
		this(new Point(20, 20), new Point(150, 80));
	}

	@Override
	public Rectangle getBoundingBox() {
		Point[] hotPoints = this.swap(this.getHotPoints());
		Point p1 = hotPoints[0];
		Point p2 = hotPoints[1];

		Point diff = p2.difference(p1);
		int width = diff.getX();
		int height = diff.getY();
		return new Rectangle(p1.getX(), p1.getY(), width, height);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		Point[] hotPoints = this.swap(this.getHotPoints());
		Point p1 = hotPoints[0];
		Point p2 = hotPoints[1];
		return GeometryUtil.distanceFromLineSegment(p1, p2, mousePoint);
	}

	@Override
	public String getShapeName() {
		return "Linija";
	}

	@Override
	public GraphicalObject duplicate() {
		LineSegment duplicate = new LineSegment(this.getHotPoint(0), this.getHotPoint(1));
		duplicate.setSelected(this.isSelected());
		for (int i = 0; i < this.getNumberOfHotPoints(); i++)
			this.setHotPointSelected(i, this.isHotPointSelected(i));
		return duplicate;
	}

	@Override
	public void render(Renderer r) {
		r.drawLine(this.getHotPoint(0), this.getHotPoint(1));
	}

	@Override
	public String getShapeID() {
		return "@LINE";
	}

	@Override
	public void save(List<String> rows) {
		Point[] hotPoints = this.swap(this.getHotPoints());
		Point p1 = hotPoints[0];
		Point p2 = hotPoints[1];
		StringBuilder s = new StringBuilder();
		s.append(this.getShapeID() + " ").append(p1.getX() + " ").append(p1.getY() + " ");
		s.append(p2.getX() + " ").append(p2.getY() + "\n");
		rows.add(s.toString());
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {
		String[] splitData = data.split(" ");
		Point a = new Point(Integer.parseInt(splitData[1]), Integer.parseInt(splitData[2]));
		Point b = new Point(Integer.parseInt(splitData[3]), Integer.parseInt(splitData[4]));
		stack.push(new LineSegment(a, b));
	}
}
