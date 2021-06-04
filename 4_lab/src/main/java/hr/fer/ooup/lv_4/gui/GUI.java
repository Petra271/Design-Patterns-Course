package hr.fer.ooup.lv_4.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import hr.fer.ooup.lv_4.objects.CompositeShape;
import hr.fer.ooup.lv_4.objects.GraphicalObject;
import hr.fer.ooup.lv_4.objects.LineSegment;
import hr.fer.ooup.lv_4.objects.Oval;
import hr.fer.ooup.lv_4.objects.Point;
import hr.fer.ooup.lv_4.model.DocumentModel;
import hr.fer.ooup.lv_4.states.AddShapeState;
import hr.fer.ooup.lv_4.states.EraserState;
import hr.fer.ooup.lv_4.states.IdleState;
import hr.fer.ooup.lv_4.states.SelectShapeState;
import hr.fer.ooup.lv_4.states.State;

public class GUI extends JFrame {

	private static final long serialVersionUID = -1893901860407322305L;
	List<GraphicalObject> objects;
	private DocumentModel model;
	private Canvas canvas;
	private State currentState;

	public GUI() {
		this(new ArrayList<>());
	}

	public GUI(List<GraphicalObject> objects) {
		Objects.requireNonNull(objects);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(700, 700);
		this.setTitle("Draw stuff");

		this.currentState = new IdleState();
		this.objects = objects;
		this.model = new DocumentModel();

		this.initGUI();
	}

	public void initGUI() {
		this.addToolbar();
		
		this.canvas = new Canvas();
		this.add(canvas, BorderLayout.CENTER);

		this.canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point mousePoint = new Point(e.getX(), e.getY());
				currentState.mouseDown(mousePoint, e.isShiftDown(), e.isControlDown());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Point mousePoint = new Point(e.getX(), e.getY());
				currentState.mouseUp(mousePoint, e.isShiftDown(), e.isControlDown());
			}
		});

		this.canvas.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point mousePoint = new Point(e.getX(), e.getY());
				currentState.mouseDragged(mousePoint);
			}
		});

		this.canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					currentState.onLeaving();
					currentState = new IdleState();
				} else {
					currentState.keyPressed(e.getKeyCode());
				}
			}
		});

		this.model.addDocumentModelListener(() -> canvas.repaint());

	}

	public class Canvas extends JComponent {

		private static final long serialVersionUID = 8063051431259786090L;

		public Canvas() {
			super();
			this.setFocusable(true);
			this.requestFocusInWindow();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			Renderer r = new G2DRendererImpl(g2d);
			for (GraphicalObject o : model.list()) {
				o.render(r);
				currentState.afterDraw(r, o);
			}
			currentState.afterDraw(r);
			this.setFocusable(true);
			this.requestFocusInWindow();
		}
	}

	private class ObjectAction extends AbstractAction {

		private static final long serialVersionUID = 6721620378563778776L;
		private GraphicalObject o;

		private ObjectAction(GraphicalObject o) {
			this.o = o;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			currentState.onLeaving();
			currentState = new AddShapeState(model, this.o);
		}
	}

	private class LoadAction extends AbstractAction {

		private static final long serialVersionUID = 3396377401988771072L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify a file");
			int userSel = fileChooser.showSaveDialog(GUI.this);
			if (userSel == JFileChooser.APPROVE_OPTION) {
				String fileName = fileChooser.getSelectedFile().getAbsolutePath();
				File file = new File(fileName);

				Map<String, GraphicalObject> map = new HashMap<>();
				Oval oval = new Oval();
				LineSegment line = new LineSegment();
				CompositeShape comp = new CompositeShape(new ArrayList<GraphicalObject>());
				map.put(oval.getShapeID(), oval);
				map.put(line.getShapeID(), line);
				map.put(comp.getShapeID(), comp);

				List<String> list = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(file))) {
					String row = "";
					do {
						row = br.readLine();
						if (row == null)
							break;
						list.add(row);
					} while (true);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				Stack<GraphicalObject> stack = new Stack<>();
				for (String s : list) {
					String[] split = s.split(" ");
					GraphicalObject o = map.get(split[0]);
					o.load(stack, s);
				}

				model.clear();
				while (stack.size() > 0) {
					model.addGraphicalObject(stack.pop());
				}

			}
		}
	}

	private class SaveAction extends AbstractAction {

		private static final long serialVersionUID = 1096675693992317515L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify a file");
			int userSel = fileChooser.showSaveDialog(GUI.this);
			if (userSel == JFileChooser.APPROVE_OPTION) {
				String fileName = fileChooser.getSelectedFile().getAbsolutePath();
				List<String> list = new ArrayList<>();
				for (GraphicalObject o : model.list()) {
					o.save(list);
				}

				if (!fileName.endsWith(".txt")) {
					fileName += ".txt";
				}
				File file = new File(fileName);

				try (BufferedWriter bf = new BufferedWriter(new FileWriter(file))) {
					file.createNewFile();
					for (String s : list)
						bf.write(s);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class ExportAction extends AbstractAction {

		private static final long serialVersionUID = -1462456903725684357L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify a file");
			int userSel = fileChooser.showSaveDialog(GUI.this);
			if (userSel == JFileChooser.APPROVE_OPTION) {
				String file = fileChooser.getSelectedFile().getAbsolutePath();
				SVGRendererImpl r = new SVGRendererImpl(file);
				for (GraphicalObject o : model.list()) {
					o.render(r);
				}
				try {
					r.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class SelectAction extends AbstractAction {

		private static final long serialVersionUID = -3884365476165994135L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentState.onLeaving();
			currentState = new SelectShapeState(model);
		}
	}

	private class EraseAction extends AbstractAction {

		private static final long serialVersionUID = -6063671466191873824L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentState.onLeaving();
			currentState = new EraserState(model);
		}
	}

	private Action createAction(String text, Action action) {
		action.putValue(Action.NAME, text);
		return action;
	}

	private void addToolbar() {
		JToolBar toolbar = new JToolBar();

		toolbar.add(new JButton(createAction("Učitaj", new LoadAction())));
		toolbar.add(new JButton(createAction("Pohrani", new SaveAction())));
		toolbar.add(new JButton(createAction("SVG export", new ExportAction())));

		for (GraphicalObject go : objects) {
			Action action = new ObjectAction(go);
			toolbar.add(new JButton(createAction(go.getShapeName(), action)));

		}

		toolbar.add(new JButton(createAction("Selektiraj", new SelectAction())));
		toolbar.add(new JButton(createAction("Brisalo", new EraseAction())));

		this.add(toolbar, BorderLayout.NORTH);
	}

}
