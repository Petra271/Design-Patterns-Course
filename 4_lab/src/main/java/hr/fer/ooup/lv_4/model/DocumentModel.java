package hr.fer.ooup.lv_4.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import hr.fer.ooup.lv_4.objects.GraphicalObject;
import hr.fer.ooup.lv_4.objects.GraphicalObjectListener;
import hr.fer.ooup.lv_4.objects.Point;
import hr.fer.ooup.lv_4.utils.GeometryUtil;


public class DocumentModel {

	public final static double SELECTION_PROXIMITY = 10;

	private List<GraphicalObject> objects = new ArrayList<>();
	private List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);
	private List<DocumentModelListener> listeners = new ArrayList<>();
	private List<GraphicalObject> selectedObjects = new ArrayList<>();
	private List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);

	// Promatrač koji će biti registriran nad svim objektima crteža...
	private final GraphicalObjectListener goListener = new GraphicalObjectListener() {

		@Override
		public void graphicalObjectChanged(GraphicalObject go) {
			notifyListeners();
		}

		@Override
		public void graphicalObjectSelectionChanged(GraphicalObject go) {
			if (!go.isSelected())
				selectedObjects.clear();
			else if (go.isSelected() && !selectedObjects.contains(go))
				selectedObjects.add(go);

			notifyListeners();
		}
	};

	public DocumentModel() {
	}

	public void clear() {
		for (GraphicalObject o : this.objects) {
			o.removeGraphicalObjectListener(this.goListener);
		}
		this.objects.clear();
		this.notifyListeners();
	}

	public void addGraphicalObject(GraphicalObject obj) {
		Objects.requireNonNull(obj);
		this.objects.add(obj);
		if (obj.isSelected())
			this.selectedObjects.add(obj);
		obj.addGraphicalObjectListener(goListener);
		this.notifyListeners();
	}

	public void removeGraphicalObject(GraphicalObject obj) {
		Objects.requireNonNull(obj);
		this.objects.remove(obj);
		obj.setSelected(false);
		//this.selectedObjects.remove(obj);
		obj.removeGraphicalObjectListener(goListener);
		this.notifyListeners();
	}

	public List<GraphicalObject> list() {
		return this.roObjects;
	}

	public void addDocumentModelListener(DocumentModelListener l) {
		this.listeners.add(l);
	}

	public void removeDocumentModelListener(DocumentModelListener l) {
		this.listeners.remove(l);
	}

	public void notifyListeners() {
		for (DocumentModelListener l : this.listeners) {
			l.documentChange();
		}
	}

	public List<GraphicalObject> getSelectedObjects() {
		return this.roSelectedObjects;
	}

	public void increaseZ(GraphicalObject go) {
		int index = this.objects.indexOf(go);
		if (index < 0 || index == this.objects.size() - 1)
			return;
		Collections.swap(this.objects, index, index + 1);
		this.notifyListeners();
	}

	public void decreaseZ(GraphicalObject go) {
		int index = this.objects.indexOf(go);
		if (index <= 0)
			return;
		Collections.swap(this.objects, index - 1, index);
		this.notifyListeners();
	}

	public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
		GraphicalObject selected = null;
		double minDistance = DocumentModel.SELECTION_PROXIMITY;

		for (GraphicalObject o : this.objects) {
			double dist = o.selectionDistance(mousePoint);
			if (dist < minDistance) {
				minDistance = dist;
				selected = o;
			}
		}
		return selected;
	}

	public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
		int index = -1;
		double minDistance = DocumentModel.SELECTION_PROXIMITY;
		if (this.objects.indexOf(object) < 0)
			return index;

		for (int i = 0; i < object.getNumberOfHotPoints(); i++) {
			double dist = GeometryUtil.distanceFromPoint(object.getHotPoint(i), mousePoint);
			if (dist < minDistance) {
				minDistance = dist;
				index = i;
			}
		}
		return index;
	}

}
