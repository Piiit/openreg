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
	private final int DEFAULT_COMBO_BOXES = 1;
	private final int MAX_COMBO_BOXES = 10;
	private final int SIZE_COMBO_X = 150;
	private final int SIZE_COMBO_Y = 25;
	private final int COMBO_OFFSET_X = 10;
	private final int COMBO_OFFSET_Y = 10;
	private final int STARTINGPOINT_UPPER_LEFT_CORNER = 160;
	private final int DEFAULT_POSITION_Y = 20;
	private int positionClassComboY = DEFAULT_POSITION_Y;
	private int positionCourseComboY = DEFAULT_POSITION_Y;
	private Link linkAddCourse;
	private Link linkAddClass;
	private final int LINK_OFFSET_X = 10;
	private final int LINK_OFFSET_Y = 5;
	private ArrayList<Row> loadedTeachers;
	private ArrayList<Row> loadedClasses;
	private ArrayList<Row> loadedCourses;
	private ArrayList<Row> loadedTeacherClassCourse;
	private Button buttonCancel;
	private Button buttonSave;
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
		buttonCancel.setText("Cancel");
		buttonCancel.setBounds(302, 403, 103, 25);
		
		buttonSave = new Button(shlAssignTeacher, SWT.CENTER);
		buttonSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					store();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		buttonSave.setText("Save");
		buttonSave.setBounds(411, 403, 111, 25);
		
		//if (loadedTeacherClassCourse == null)
		createClassCombo();
		setData();
	}
	
	private void createClassCombo(){
		if (positionCourseComboY >= positionClassComboY){
			int posY = positionClassComboY;
			
			if (posY != DEFAULT_POSITION_Y){
				posY = positionCourseComboY < positionClassComboY ? positionClassComboY : positionCourseComboY;
				Label lblSeparator = new Label(shlAssignTeacher, SWT.SEPARATOR | SWT.HORIZONTAL);
				int lengthX = SIZE_COMBO_X * 2 + COMBO_OFFSET_X + 10;
				lblSeparator.setBounds(STARTINGPOINT_UPPER_LEFT_CORNER - 5, positionCourseComboY - (COMBO_OFFSET_Y/2), lengthX, 2);
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
		combosCourses.get(combosCourses.size()-1).setToolTipText((combosClasses.size()-1)+"");
		combosCourses.get(combosCourses.size()-1).setBounds(posX, 
				positionCourseComboY, SIZE_COMBO_X, SIZE_COMBO_Y);
		
		linkAddCourse.setBounds(posX + LINK_OFFSET_X, positionCourseComboY + SIZE_COMBO_Y + LINK_OFFSET_Y, 81, 15);
		
		positionCourseComboY = positionCourseComboY + SIZE_COMBO_Y + COMBO_OFFSET_Y;
		
		update();
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

	@Override
	public void update() {
		int selected = listTeachers.getSelectionIndex();
		listTeachers.removeAll();
		for (Row teacher : loadedTeachers){
			String s = teacher.getValueAsString("name") + " " + 
					teacher.getValueAsString("surname");
			listTeachers.add(s);
			listTeachers.setData(s, teacher.getValueAsString("teacher_id"));
		}
		listTeachers.setSelection(selected);
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
		if (!dataSet && loadedTeacherClassCourse.size() != 0){
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
		
		combosClasses.get(class_counter).select((int) class_id - 1);
		combosCourses.get(course_counter).select((int) course_id - 1);
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
			Log.info(teacher_id+" "+class_id +" "+course_id);
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

	@Override
	public void cancel() {
		shlAssignTeacher.dispose();
	}
}
