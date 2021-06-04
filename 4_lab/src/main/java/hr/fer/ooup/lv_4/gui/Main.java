package hr.fer.ooup.lv_4.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import hr.fer.ooup.lv_4.objects.GraphicalObject;
import hr.fer.ooup.lv_4.objects.LineSegment;
import hr.fer.ooup.lv_4.objects.Oval;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
                List<GraphicalObject> objects = new ArrayList<>();
                objects.add(new LineSegment());
                objects.add(new Oval());
				new GUI(objects).setVisible(true);
			}
		});
	}
}
