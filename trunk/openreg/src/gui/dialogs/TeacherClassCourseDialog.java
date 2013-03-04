package gui.dialogs;

import java.util.ArrayList;

import log.Log;

import gui.GuiDialog;
import gui.GuiTools;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;

import data.SimpleDate;
import database.Row;
import database.query.ClassQuery;
import database.query.CourseQuery;
import database.query.TeacherClassCourseQuery;
import database.query.TeacherQuery;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

public class TeacherClassCourseDialog extends GuiDialog {

	protected Object result;
	protected Shell shlAssignTeacher;
	private List listTeachers;
	private ArrayList<Combo> combosClasses = new ArrayList<Combo>();
	private ArrayList<Combo> combosCourses = new ArrayList<Combo>();
	private ArrayList<Label> lblSeparator = new ArrayList<Label>();
	private ArrayList<Link> linksRemove = new ArrayList<Link>();
	private Long teacherIndex = (long) 0;
	private final int SIZE_COMBO_X = 150;
	private final int SIZE_COMBO_Y = 25;
	private final int COMBO_OFFSET_X = 10;
	private final int COMBO_OFFSET_Y = 10;
	private final int STARTINGPOINT_UPPER_LEFT_CORNER = 160;
	private final int DEFAULT_POSITION_Y = 20;
	private int positionClassComboY = DEFAULT_POSITION_Y;
	private int positionCourseComboY = DEFAULT_POSITION_Y;
	private int positionLinksRemoveY = DEFAULT_POSITION_Y;
	private Link linkAddCourse;
	private Link linkAddClass;
	private final int LINK_OFFSET_X = 10;
	private final int LINK_OFFSET_Y = 5;
	private ArrayList<Row> loadedTeachers;
	private ArrayList<Row> loadedClasses;
	private ArrayList<Row> loadedCourses;
	private ArrayList<Row> loadedTeacherClassCourse;
	private Button buttonCancel;
	private boolean dataSet = false;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public TeacherClassCourseDialog(Shell parent) {
		super(parent);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAssignTeacher.open();
		shlAssignTeacher.layout();
		Display display = getParent().getDisplay();
		while (!shlAssignTeacher.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlAssignTeacher = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlAssignTeacher.setText("Assign Teacher to Class and Course");
		shlAssignTeacher.setSize(538, 467);
		
		listTeachers = new List(shlAssignTeacher, SWT.BORDER | SWT.V_SCROLL);
		listTeachers.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String [] selection = listTeachers.getSelection();
				String s = selection[0];
				try {
					loadData(Long.parseLong(listTeachers.getData(s).toString()));
					removeAndRecreateComboBoxes();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		listTeachers.setBounds(10, 10, 137, 296);
		
		linkAddClass = new Link(shlAssignTeacher, SWT.NONE);
		linkAddClass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				createClassCombo();
			}
		});
		linkAddClass.setText("<a>Add Class</a>");
		
		linkAddCourse = new Link(shlAssignTeacher, SWT.NONE);
		linkAddCourse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				createCourseCombo();
				createRemoveLinks();
			}
		});
		linkAddCourse.setText("<a>Add Course</a>");
		
		buttonCancel = new Button(shlAssignTeacher, SWT.NONE);
		buttonCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				cancel();
			}
		});
		buttonCancel.setText("Done");
		buttonCancel.setBounds(419, 403, 103, 25);
		
		createClassCombo();
		setData();
	}
	
	private void removeAndRecreateComboBoxes() {
		setComboBoxesInvisible();
		combosClasses.removeAll(combosClasses);
		combosCourses.removeAll(combosCourses);
		createComboBoxes();
	}
	
	private void setComboBoxesInvisible(){
		for (Combo c : combosClasses)
			c.setVisible(false);
		for (Combo c : combosCourses)
			c.setVisible(false);
		for (Label l : lblSeparator)
			l.setVisible(false);
	}
	
	private void createComboBoxes() {
		positionClassComboY = DEFAULT_POSITION_Y;
		positionCourseComboY = DEFAULT_POSITION_Y;
		try {
			loadData(teacherIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createClassCombo();
		dataSet = false;
		setData();
	}
	
	private void createClassCombo(){
		if (positionCourseComboY >= positionClassComboY){
			int posY = positionClassComboY;
			
			if (posY != DEFAULT_POSITION_Y){
				posY = positionCourseComboY < positionClassComboY ? positionClassComboY : positionCourseComboY;
				lblSeparator.add(new Label(shlAssignTeacher, SWT.SEPARATOR | SWT.HORIZONTAL));
				int lengthX = SIZE_COMBO_X * 2 + COMBO_OFFSET_X + 10;
				lblSeparator.get(lblSeparator.size()-1).setBounds(STARTINGPOINT_UPPER_LEFT_CORNER - 5, positionCourseComboY - (COMBO_OFFSET_Y/2), lengthX, 2);
			}
			
			combosClasses.add(new Combo(shlAssignTeacher, SWT.READ_ONLY));
			combosClasses.get(combosClasses.size()-1).setBounds(STARTINGPOINT_UPPER_LEFT_CORNER, 
					posY, SIZE_COMBO_X, SIZE_COMBO_Y);
			
			linkAddClass.setBounds(STARTINGPOINT_UPPER_LEFT_CORNER + COMBO_OFFSET_X + LINK_OFFSET_X, 
					posY + SIZE_COMBO_Y + LINK_OFFSET_Y, 81, 15);
			
			positionClassComboY = posY + SIZE_COMBO_Y + COMBO_OFFSET_Y;
			
			createCourseCombo();
		}
	}
	 
	private void createCourseCombo(){
		int posX = STARTINGPOINT_UPPER_LEFT_CORNER + SIZE_COMBO_X + COMBO_OFFSET_X;
		
		combosCourses.add(new Combo(shlAssignTeacher, SWT.READ_ONLY));
		combosCourses.get(combosCourses.size()-1).addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int i = 0;
				for (Combo combo : combosCourses) {
					if (combo.equals(arg0.getSource()))
						store(combo, i);
					i++;
				}
			}
		});
		combosCourses.get(combosCourses.size()-1).setToolTipText((combosClasses.size()-1)+"");
		combosCourses.get(combosCourses.size()-1).setBounds(posX, 
				positionCourseComboY, SIZE_COMBO_X, SIZE_COMBO_Y);
		
		linkAddCourse.setBounds(posX + LINK_OFFSET_X, positionCourseComboY + SIZE_COMBO_Y + LINK_OFFSET_Y, 81, 15);
		
		positionCourseComboY = positionCourseComboY + SIZE_COMBO_Y + COMBO_OFFSET_Y;
		update();
	}
	
	private void createRemoveLinks(){
		removeRemoveLinks();
		int posX = STARTINGPOINT_UPPER_LEFT_CORNER + SIZE_COMBO_X*2 + COMBO_OFFSET_X;
		for (int i = 0; i < combosCourses.size(); i++) {
			linksRemove.add(new Link(shlAssignTeacher, SWT.NONE));
			linksRemove.get(linksRemove.size()-1).addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					int i = 0;
					for (Link link : linksRemove){
						if (link.equals(arg0.getSource())){
							remove(i);
							removeAndRecreateComboBoxes();
						}
						i++;
					}
					
				}
			});
			linksRemove.get(linksRemove.size()-1).setText("<a>Remove</a>");
			linksRemove.get(linksRemove.size()-1).setBounds(posX + LINK_OFFSET_X, positionLinksRemoveY, 81, 15);
			
			positionLinksRemoveY = positionLinksRemoveY + SIZE_COMBO_Y + COMBO_OFFSET_Y;
		}
	}
	
	private void removeRemoveLinks(){
		positionLinksRemoveY = DEFAULT_POSITION_Y;
		for (int i = 0; i < linksRemove.size(); i++) {
			linksRemove.get(i).setVisible(false);
		}
		linksRemove = new ArrayList<Link>();
	}
	
	private void remove(int i) {
		Combo combo = combosCourses.get(i);
		String [] selection = listTeachers.getSelection();
		if (selection.length == 1){
			long teacherId = Long.parseLong(listTeachers.getData(selection[0]).toString());
			long classId;
			try {
			classId = Long.parseLong(
					combosClasses.get(Integer.parseInt(combo.getToolTipText())).getData(
					combosClasses.get(Integer.parseInt(combo.getToolTipText())).getItem(
					combosClasses.get(Integer.parseInt(combo.getToolTipText())).getSelectionIndex())).toString());
			} catch (Exception e) {
				Log.error("No classId selected");
				return;
			}
			
			long courseId = Long.parseLong(combo.getData(combo.getItem(combo.getSelectionIndex())).toString());
	
			Row deleteTeacherClassCourse = new Row();
			deleteTeacherClassCourse.setValue("teacher_id", teacherId);
			deleteTeacherClassCourse.setValue("class_id", classId);
			deleteTeacherClassCourse.setValue("course_id", courseId);
			
			try {
				TeacherClassCourseQuery.delete(deleteTeacherClassCourse);
			} catch (Exception e) {
				e.printStackTrace();
//				comboDuplicateKeyExceptionHandling(combo);
			}
			
		}
	}
	
	public void loadData() throws Exception {
		loadedTeachers = TeacherQuery.getFullDataset();
		loadedClasses = ClassQuery.getFullDataset();
		loadedCourses = CourseQuery.getFullDataset();
		dataSet = true;
	}
	
	@Override
	public void loadData(Object data) throws Exception {
		loadedTeachers = TeacherQuery.getFullDataset();
		loadedClasses = ClassQuery.getFullDataset();
		loadedCourses = CourseQuery.getFullDataset();
		loadedTeacherClassCourse = TeacherClassCourseQuery.getDataset(data);
		teacherIndex = Long.parseLong(data.toString());
	}

	@Override
	public void store() throws Exception {
		String [] selection = listTeachers.getSelection();
		long teacherId = Long.parseLong(listTeachers.getData(selection[0]).toString());
		int i = 0;
		for (Combo c : combosClasses){
			long classId = Long.parseLong(c.getData(c.getItem(c.getSelectionIndex())).toString());
			for (Combo course : combosCourses){
				long courseId = Long.parseLong(course.getData(course.getItem(course.getSelectionIndex())).toString());
				if (Integer.parseInt(course.getToolTipText()) == i){
					Row newTeacherClassCourse = new Row();
					newTeacherClassCourse.setValue("teacher_id", teacherId);
					newTeacherClassCourse.setValue("class_id", classId);
					newTeacherClassCourse.setValue("course_id", courseId);
					
					TeacherClassCourseQuery.insert(newTeacherClassCourse);
				}
			}
			i += 1;
		}
//		shlAssignTeacher.dispose();
	}
	
	private void store(Combo combo, int comboCourseIndex) {
		String [] selection = listTeachers.getSelection();
		if (selection.length >= 1){
			long teacherId = Long.parseLong(listTeachers.getData(selection[0]).toString());
			long classId;
			try {
				int comboClassIndex = Integer.parseInt(combo.getToolTipText());
				classId = Long.parseLong(
					combosClasses.get(comboClassIndex).getData(
					combosClasses.get(comboClassIndex).getItem(
					combosClasses.get(comboClassIndex).getSelectionIndex())).toString());
			} catch (Exception e) {
				Log.error("No classId selected");
				return;
			}
			
			long courseId = Long.parseLong(combo.getData(combo.getItem(combo.getSelectionIndex())).toString());
	
			Row newTeacherClassCourse = new Row();
			newTeacherClassCourse.setValue("teacher_id", teacherId);
			newTeacherClassCourse.setValue("class_id", classId);
			newTeacherClassCourse.setValue("course_id", courseId);
			
			boolean updateTuple = false;
			Row updateRow = new Row();
			if (loadedTeacherClassCourse.size() > comboCourseIndex){
				Row updateValues = loadedTeacherClassCourse.get(comboCourseIndex);
				updateRow.setValue("teacher_id", updateValues.getValueAsLong("teacher_id"));
				updateRow.setValue("class_id", updateValues.getValueAsLong("class_id"));
				updateRow.setValue("course_id", updateValues.getValueAsLong("course_id"));
				updateTuple = true;
			}
				
			if (!updateTuple){
				try {
					TeacherClassCourseQuery.insert(newTeacherClassCourse);
				} catch (Exception e) {
					comboDuplicateKeyExceptionHandling(combo);
				}
			} else {
				try {
					TeacherClassCourseQuery.update(newTeacherClassCourse, updateRow);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	@Override
	public void update() {
		updateTeachersList();
		updateCombos();
		createRemoveLinks();
	}
	
	private void updateTeachersList(){
		int selected = listTeachers.getSelectionIndex();
		listTeachers.removeAll();
		for (Row teacher : loadedTeachers){
			String s = teacher.getValueAsString("name") + " " + 
					teacher.getValueAsString("surname");
			listTeachers.add(s);
			listTeachers.setData(s, teacher.getValueAsString("teacher_id"));
		}
		listTeachers.setSelection(selected);
	}
	
	private void updateCombos(){
		for (Combo combo : combosClasses){
			int p = combo.getSelectionIndex();
			combo.removeAll();
			for (Row c : loadedClasses){
				String s = c.getValueAsString("level") + c.getValueAsString("stream");
				combo.add(s);
				combo.setData(s, c.getValueAsString("id"));
			}
			combo.select(p);
		}
		for (Combo combo : combosCourses){
			int p = combo.getSelectionIndex();
			combo.removeAll();
			for (Row course : loadedCourses){
				String s = course.getValueAsString("name");
				combo.add(s);
				combo.setData(s, course.getValueAsString("id"));
			}
			combo.select(p);
		}
	}
	
	public void setData(){
		if (!dataSet && loadedTeacherClassCourse != null && loadedTeacherClassCourse.size() != 0){
			setListData();
			setComboData();
			dataSet = true;
		}
	}
	
	private void setListData(){
		String name = loadedTeacherClassCourse.get(0).getValueAsString("teacher_name") + " " 
				+ loadedTeacherClassCourse.get(0).getValueAsString("surname");

		String [] items = listTeachers.getItems();
		int index = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(name))
				index = i;
		}
		listTeachers.setSelection(index);
	}
	
	private void setComboData(){
		int class_counter = 0;
		int course_counter = 0;
		int id = 0;
		long teacher_id = loadedTeacherClassCourse.get(id).getValueAsLong("teacher_id");
		long class_id = loadedTeacherClassCourse.get(id).getValueAsLong("class_id");
		long course_id = loadedTeacherClassCourse.get(id).getValueAsLong("course_id");
		
		combosClasses.get(class_counter).select(getComboClassIndex(combosClasses, class_id, class_counter));
		combosCourses.get(course_counter).select(getComboClassIndex(combosCourses, course_id, class_counter));
		class_counter += 1;
		course_counter += 1;
		
		for (int i = 0; i < loadedTeacherClassCourse.size(); i++) {
			Row ltcc = loadedTeacherClassCourse.get(i);
			if (class_id != ltcc.getValueAsLong("class_id")){
				createClassCombo();
				class_id = ltcc.getValueAsLong("class_id");
				course_id = ltcc.getValueAsLong("course_id");
				combosClasses.get(class_counter).select(getComboClassIndex(combosClasses, class_id, class_counter));
				combosCourses.get(course_counter).select(getComboClassIndex(combosCourses, course_id, class_counter));
				class_counter += 1;
				course_counter += 1;
			} else if (course_id != ltcc.getValueAsLong("course_id")){
				createCourseCombo();
				course_id = ltcc.getValueAsLong("course_id");
				combosCourses.get(course_counter).select(getComboClassIndex(combosCourses, course_id, class_counter));
				course_counter +=1;
			}
			Log.info("Inserted the following tuples: " + teacher_id+" "+class_id +" "+course_id);
		}
	}
	
	private int getComboClassIndex(ArrayList<Combo> combos, long id, int counter){
		int i = 0;
		for (String s : combos.get(counter).getItems()){
			if (Integer.parseInt(combos.get(counter).getData(s).toString()) == (int)id){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	private void comboDuplicateKeyExceptionHandling(Combo combo){
		boolean found = false;
		if (combosCourses.indexOf(combo) < loadedTeacherClassCourse.size()-1){
			int i = 0;
			for (String c : combo.getItems()) {
				Log.info(loadedTeacherClassCourse.get(combosCourses.indexOf(combo)).getValueAsString("name"));
				if (c.equalsIgnoreCase(loadedTeacherClassCourse.get(combosCourses.indexOf(combo)).getValueAsString("name"))){
					combo.select(i);
					found = true;
				}
				i++;
			}
			
		}
		if (!found)
			combo.deselectAll();
		Log.error("Insertion on row " + combosCourses.indexOf(combo) + " not possible, DUPLICATE KEY EXCEPTION!");
	}

	@Override
	public void cancel() {
		shlAssignTeacher.dispose();
	}
}
