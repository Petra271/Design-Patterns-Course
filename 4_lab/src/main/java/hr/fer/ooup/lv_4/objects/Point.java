package hr.fer.ooup.lv_4.objects;

import java.util.Objects;

public class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int compare(Point b) {
		if (this.x < b.getX())
			return -1;

		if (this.x == b.getX()) {
			if (this.y < b.getY())
				return -1;

			int res = this.y == b.getY() ? 0 : 1;
			return res;
		}

		return 1;
	}

	public Point translate(Point dp) {
		Objects.requireNonNull(dp);
		return new Point(this.x + dp.getX(), this.y + dp.getY());
	}

	public Point difference(Point p) {
		Objects.requireNonNull(p);
		return new Point(this.x - p.getX(), this.y - p.getY());
	}

}
