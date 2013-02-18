package gui;

import java.util.ArrayList;
import data.Class;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import database.DatabaseTools;
import database.Row;

public class MainWindow {

	protected Shell shlRegisterForTeachers;

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlRegisterForTeachers.open();
		shlRegisterForTeachers.layout();
		while (!shlRegisterForTeachers.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlRegisterForTeachers = new Shell();
		shlRegisterForTeachers.setMinimumSize(new Point(320, 240));
		shlRegisterForTeachers.setSize(600, 500);
		shlRegisterForTeachers.setText("Register for teachers");
		shlRegisterForTeachers.setLayout(new FormLayout());
		
		Menu menu = new Menu(shlRegisterForTeachers, SWT.BAR);
		shlRegisterForTeachers.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		mntmExit.setText("Exit");
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("Student");
		
		Menu menu_2 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_2);
		
		MenuItem mntmAddStudent = new MenuItem(menu_2, SWT.NONE);
		mntmAddStudent.setText("Add Student");
		
		Group grpStudents = new Group(shlRegisterForTeachers, SWT.NONE);
		grpStudents.setText("Students");
		grpStudents.setLayout(new FormLayout());
		FormData fd_grpStudents = new FormData();
		fd_grpStudents.top = new FormAttachment(0, 10);
		fd_grpStudents.left = new FormAttachment(0, 10);
		fd_grpStudents.bottom = new FormAttachment(0, 468);
		fd_grpStudents.right = new FormAttachment(0, 590);
		grpStudents.setLayoutData(fd_grpStudents);
		
		Button btnNew = new Button(grpStudents, SWT.NONE);
		btnNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
			}
		});
		FormData fd_btnNew = new FormData();
		fd_btnNew.right = new FormAttachment(0, 93);
		fd_btnNew.top = new FormAttachment(0, 3);
		fd_btnNew.left = new FormAttachment(0, 3);
		btnNew.setLayoutData(fd_btnNew);
		btnNew.setText("New");
		
		Button btnRemove = new Button(grpStudents, SWT.NONE);
		FormData fd_btnRemove = new FormData();
		fd_btnRemove.top = new FormAttachment(0, 3);
		fd_btnRemove.left = new FormAttachment(0, 101);
		btnRemove.setLayoutData(fd_btnRemove);
		btnRemove.setText("Remove");
		
		final Combo comboStudents = new Combo(grpStudents, SWT.READ_ONLY);
		FormData fd_comboStudents = new FormData();
		fd_comboStudents.top = new FormAttachment(0, 3);
		fd_comboStudents.right = new FormAttachment(100);
		fd_comboStudents.left = new FormAttachment(100, -160);
		comboStudents.setLayoutData(fd_comboStudents);
		
		final Combo comboClasses = new Combo(grpStudents, SWT.READ_ONLY);
		try {
			ArrayList<Class> classes = Class.getAllClasses(); 
			for(Class cl : classes) {
				comboClasses.add(cl.toString());
				comboClasses.setData(cl.toString(), cl.getID());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		comboClasses.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					comboStudents.removeAll();
					Long classID = Long.parseLong(comboClasses.getData(comboClasses.getText()).toString());
					ArrayList<Row> rows = DatabaseTools.getQueryResult("SELECT * FROM student WHERE class_id = ?", classID);
					int i = 0;
					for(Row row : rows) {
						i++;
						String student = "(" + i + ") " + row.getValue("name") + " " + row.getValue("surname");
						comboStudents.add(student);
						comboStudents.setData(student, row.getValue("id"));
						comboStudents.setEnabled(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		fd_btnRemove.right = new FormAttachment(comboClasses, -123);
		FormData fd_comboClasses = new FormData();
		fd_comboClasses.top = new FormAttachment(0, 3);
		fd_comboClasses.left = new FormAttachment(comboStudents, -92, SWT.LEFT);
		fd_comboClasses.right = new FormAttachment(comboStudents, -17);
		comboClasses.setLayoutData(fd_comboClasses);
		comboClasses.setText("Class");
		
		Button btnClasses = new Button(grpStudents, SWT.NONE);
		btnClasses.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnClasses.setText("New class");
		FormData fd_btnClasses = new FormData();
		fd_btnClasses.right = new FormAttachment(btnRemove, 96, SWT.RIGHT);
		fd_btnClasses.top = new FormAttachment(btnNew, 0, SWT.TOP);
		fd_btnClasses.left = new FormAttachment(btnRemove, 6);
		btnClasses.setLayoutData(fd_btnClasses);
		
		Label lblNewLabel = new Label(grpStudents, SWT.CENTER);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(btnNew, 1);
		fd_lblNewLabel.left = new FormAttachment(btnNew, 0, SWT.LEFT);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("New Label");
	}
}
