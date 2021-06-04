package hr.fer.ooup.lv_4.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import hr.fer.ooup.lv_4.objects.Point;

public class G2DRendererImpl implements Renderer {

	private Graphics2D g2d;

	public G2DRendererImpl(Graphics2D g2d) {
		this.g2d = g2d;
		this.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	public void drawLine(Point s, Point e) {
		this.g2d.setColor(Color.BLUE);
		this.g2d.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
	}

	@Override
	public void fillPolygon(Point[] points) {

		this.g2d.setColor(Color.BLUE);

		int nPoints = points.length;
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] = points[i].getX();
			yPoints[i] = points[i].getY();
		}
		this.g2d.fillPolygon(xPoints, yPoints, nPoints);

		this.g2d.setColor(Color.RED);
		this.g2d.drawPolygon(xPoints, yPoints, nPoints);
	}

}
