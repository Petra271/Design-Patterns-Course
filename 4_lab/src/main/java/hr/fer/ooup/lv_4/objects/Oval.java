package hr.fer.ooup.lv_4.objects;

import java.util.List;
import java.util.Stack;

import hr.fer.ooup.lv_4.gui.Renderer;
import hr.fer.ooup.lv_4.utils.GeometryUtil;

public class Oval extends AbstractGraphicalObject {

	private Point center;
	private Point[] points;

	public Oval(Point a, Point b) {
		super(new Point[] { a, b });
		this.center = new Point(a.getX(), b.getY());
		this.points = this.calcPoints();
	}

	public Oval() {
		this(new Point(250, 160), new Point(280, 140));
	}

	public Point getCenter() {
		return new Point(getHotPoint(0).getX(), getHotPoint(1).getY());
	}

	@Override
	public Rectangle getBoundingBox() {
		Point[] hotPoints = this.swap(this.getHotPoints());
		Point p1 = hotPoints[0];
		Point p2 = hotPoints[1];

		Point diff = p1.difference(p2);
		int width = diff.getX() * 2;
		int height = diff.getY() * 2;
		return new Rectangle(p1.getX() - width / 2, p1.getY() - height, width, height);
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		Rectangle box = this.getBoundingBox();
		int max = Math.max(box.getHeight(), box.getWidth());
		return GeometryUtil.distanceFromPoint(this.center, mousePoint) - max;
	}

	@Override
	public String getShapeName() {
		return "Oval";
	}

	@Override
	public GraphicalObject duplicate() {
		Oval duplicate = new Oval(this.getHotPoint(0), this.getHotPoint(1));
		duplicate.setSelected(this.isSelected());
		for (int i = 0; i < this.getNumberOfHotPoints(); i++)
			this.setHotPointSelected(i, this.isHotPointSelected(i));
		return duplicate;
	}

	@Override
	public void render(Renderer r) {
		this.center = this.getCenter();
		this.points = this.calcPoints();
		r.fillPolygon(this.points);
	}

	public Point[] getPoints() {
		return this.points;
	}

	private Point[] calcPoints() {
		int num_points = 360;
		Point p1 = getHotPoint(0);
		Point p2 = getHotPoint(1);

		if (p1.compare(p2) == 1) {
			Point tmp = p1;
			p1 = p2;
			p2 = tmp;
		}

		Point[] points = new Point[num_points];
		double major = p2.getX() - p1.getX();
		double minor = p1.getY() - p2.getY();

		int x, y;
		for (int i = 0; i < num_points; i++) {
			x = (int) (major * Math.sin(Math.toRadians(i)));
			y = (int) (minor * Math.cos(Math.toRadians(i)));

			points[i] = new Point(x + p1.getX(), y + p2.getY());
		}
		return points;
	}

	@Override
	public String getShapeID() {
		return "@OVAL";
	}

	@Override
	public void save(List<String> rows) {
		Point[] hotPoints = this.swap(this.getHotPoints());
		Point p1 = hotPoints[1];
		Point p2 = hotPoints[0];
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
		stack.push(new Oval(a, b));
	}

}
