package gui.dialogs;

import java.util.ArrayList;

import log.Log;

import gui.GuiDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import database.Row;
import database.query.ClassQuery;
import database.query.CourseQuery;
import database.query.TeacherQuery;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class TeacherClassCourseDialog extends GuiDialog {

	protected Object result;
	protected Shell shlAssignTeacherTo;
	private List listTeachers;
	private ArrayList<Combo> combosClasses = new ArrayList<Combo>();
	private ArrayList<Combo> combosCourses = new ArrayList<Combo>();
	private final int DEFAULT_COMBO_BOXES = 1;
	private final int MAX_COMBO_BOXES = 10;
	private final int SIZE_COMBO_X = 150;
	private final int SIZE_COMBO_Y = 25;
	private final int COMBO_OFFSET_X = 10;
	private final int COMBO_OFFSET_Y = 10;
	private final int STARTINGPOINT_UPPER_LEFT_CORNER = 150;
	private final int DEFAULT_POSITION_Y = 0;
	private int positionClassComboY = DEFAULT_POSITION_Y;
	private int positionCourseComboY = DEFAULT_POSITION_Y;
	private Link linkAddCourse;
	private Link linkAddClass;
	private final int LINK_OFFSET_X = 10;
	private final int LINK_OFFSET_Y = 5;
	private ArrayList<Row> loadedTeachers;
	private ArrayList<Row> loadedClasses;
	private ArrayList<Row> loadedCourses;

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
		shlAssignTeacherTo.open();
		shlAssignTeacherTo.layout();
		Display display = getParent().getDisplay();
		while (!shlAssignTeacherTo.isDisposed()) {
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
		shlAssignTeacherTo = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shlAssignTeacherTo.setText("Assign Teacher to Class and Course");
		shlAssignTeacherTo.setSize(538, 423);
		
		listTeachers = new List(shlAssignTeacherTo, SWT.BORDER | SWT.V_SCROLL);
		listTeachers.setBounds(10, 10, 137, 296);
		
		linkAddClass = new Link(shlAssignTeacherTo, SWT.NONE);
		linkAddClass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				createClassCombo();
				update();
			}
		});
		linkAddClass.setText("<a>Add Class</a>");
		
		linkAddCourse = new Link(shlAssignTeacherTo, SWT.NONE);
		linkAddCourse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				createCourseCombo();
				update();
			}
		});
		linkAddCourse.setText("<a>Add Course</a>");
		
		createClassCombo();
		createCourseCombo();
		
		update();
	}
	
	private void createClassCombo(){
		if (positionCourseComboY >= positionClassComboY){
			int posY = positionClassComboY;
			
			if (posY != DEFAULT_POSITION_Y)
				posY = positionCourseComboY < positionClassComboY ? positionClassComboY : positionCourseComboY;
			
			combosClasses.add(new Combo(shlAssignTeacherTo, SWT.NONE));
			combosClasses.get(combosClasses.size()-1).setBounds(STARTINGPOINT_UPPER_LEFT_CORNER, 
					posY, SIZE_COMBO_X, SIZE_COMBO_Y);
			
			linkAddClass.setBounds(STARTINGPOINT_UPPER_LEFT_CORNER + COMBO_OFFSET_X + LINK_OFFSET_X, 
					posY + SIZE_COMBO_Y + LINK_OFFSET_Y, 81, 15);
			
			positionClassComboY = posY + SIZE_COMBO_Y + COMBO_OFFSET_Y;
		}
	}
	 
	private void createCourseCombo(){
		int posX = STARTINGPOINT_UPPER_LEFT_CORNER + SIZE_COMBO_X + COMBO_OFFSET_X;
		
		combosCourses.add(new Combo(shlAssignTeacherTo, SWT.NONE));
		combosCourses.get(combosCourses.size()-1).setToolTipText((combosClasses.size()-1)+"");
		combosCourses.get(combosCourses.size()-1).setBounds(posX, 
				positionCourseComboY, SIZE_COMBO_X, SIZE_COMBO_Y);
		
		linkAddCourse.setBounds(posX + LINK_OFFSET_X, positionCourseComboY + SIZE_COMBO_Y + LINK_OFFSET_Y, 81, 15);
		
		positionCourseComboY = positionCourseComboY + SIZE_COMBO_Y + COMBO_OFFSET_Y;
	}
	
	

	@Override
	public void loadData(Object data) throws Exception {
		loadedTeachers = TeacherQuery.getFullDataset();
		loadedClasses = ClassQuery.getFullDataset();
		loadedCourses = CourseQuery.getFullDataset();
	}

	@Override
	public void store() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		int selected = listTeachers.getSelectionCount();
		listTeachers.removeAll();
		for (Row teacher : loadedTeachers){
			String s = teacher.getValueAsString("name") + " " + 
					teacher.getValueAsString("surname");
			listTeachers.add(s);
			listTeachers.setData(s, teacher.getValueAsString("teacher_id"));
		}
		listTeachers.setSelection(selected);
		for (Combo combo : combosClasses){
			for (Row c : loadedClasses){
				String s = c.getValueAsString("level") + c.getValueAsString("stream");
				combo.add(s);
				combo.setData(s, c.getValueAsString("id"));
			}
		}
		for (Combo combo : combosCourses){
			for (Row course : loadedCourses){
				String s = course.getValueAsString("name");
				combo.add(s);
				combo.setData(s, course.getValueAsString("id"));
			}
		}
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}
}
