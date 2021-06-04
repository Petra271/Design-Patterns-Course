package hr.fer.ooup.lv_4.utils;

import hr.fer.ooup.lv_4.objects.Point;

public class GeometryUtil {

	public static double distanceFromPoint(Point p1, Point p2) {
		double d1 = Math.abs(p2.getY() - p1.getY());
		double d2 = Math.abs(p2.getX() - p1.getX());
		return Math.hypot(d1, d2);
	}

	public static double distanceFromLineSegment(Point s, Point e, Point p) {
		// ax + by + c = 0; a=-m; b=1; c=m*x1-y1
		// m = (y2-y1)/(x2-x1)
		double d1 = e.getY() - s.getY();
		double d2 = e.getX() - s.getX();
		double m = d1 / d2;
		double a = -m;
		double b = 1;
		double c = m * s.getX() - s.getY();
		double root = Math.hypot(a, b);
		double res = Math.abs(a * p.getX() + b * p.getY() + c) / root;
		
		if (s.compare(e) == 1) {
			Point tmp = s;
			s = e;
			e = tmp;
		}
		if ((int) res == 0)
			if (p.compare(e) == 1)
				return GeometryUtil.distanceFromPoint(e, p);
			else if (p.compare(s) == -1)
				return GeometryUtil.distanceFromPoint(p,s);
		return res;
	}

}