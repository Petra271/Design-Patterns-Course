package hr.fer.ooup.lv_4.gui;

import hr.fer.ooup.lv_4.objects.Point;

public interface Renderer {

	void drawLine(Point s, Point e);

	void fillPolygon(Point[] points);
}
