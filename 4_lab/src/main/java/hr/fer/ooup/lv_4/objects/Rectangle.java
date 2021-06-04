package hr.fer.ooup.lv_4.objects;

public class Rectangle {
	private int x;
	private int y;
	private int width;
	private int height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	};

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	
	public Point getCenter() {
		int x = this.getX() + this.getWidth() / 2;
		int y = this.getY() + this.getHeight() / 2;
		return new Point(x,y);
	}

}
