package hr.fer.ooup.lv_4.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.ooup.lv_4.objects.Point;

public class SVGRendererImpl implements Renderer {

	private List<String> lines = new ArrayList<>();
	private String fileName;

	public SVGRendererImpl(String fileName) {
		this.fileName = fileName;
		lines.add("<svg xmlns=\"http://www.w3.org/2000/svg\"\r\n"
				+ "    xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n");
	}

	public void close() throws IOException {
		this.lines.add("</svg>");
		if (!this.fileName.endsWith(".svg"))
			this.fileName += ".svg";

		File file = new File(this.fileName);
		file.createNewFile();
		try (FileWriter w = new FileWriter(file)) {
			for (String s : this.lines)
				w.write(s);
		}
	}

	@Override
	public void drawLine(Point s, Point e) {
		StringBuilder str = new StringBuilder();
		str.append("<line x1=\"").append(s.getX() + "\" ").append("y1=\"").append(s.getY() + "\" ");
		str.append("x2=\"").append(e.getX() + "\" ").append("y2=\"").append(e.getY() + "\" ");
		str.append("style=\"stroke:#1E90FF;\"/>\n");
		this.lines.add(str.toString());
	}

	@Override
	public void fillPolygon(Point[] points) {
		StringBuilder str = new StringBuilder();
		str.append("<polygon points=\"");
		for (Point p : points) {
			str.append(p.getX() + "," + p.getY() + " ");
		}
		str.append("\" style=\"stroke:#DC143C; fill:#1E90FF;\"/>\n");
		this.lines.add(str.toString());

	}

}
