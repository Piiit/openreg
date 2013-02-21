package gui;

import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.custom.StackLayout;

import data.Student;

public class MainWindow {

	protected Shell shlRegisterForTeachers;
	protected ArrayList<GuiModuleList> gmLists = new ArrayList<GuiModuleList>();
	
	public void addModuleList(GuiModuleList gmList) {
		gmLists.add(gmList);
	}
	
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
		shlRegisterForTeachers.setMinimumSize(new Point(640, 480));
		shlRegisterForTeachers.setSize(640, 480);
		shlRegisterForTeachers.setText("Register for teachers");
		shlRegisterForTeachers.setLayout(new BorderLayout(0, 0));
		
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
				System.exit(0);
			}
		});
		mntmExit.setText("Exit");
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("Student");
		
		Menu menu_2 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_2);
		
		MenuItem mntmAddStudent = new MenuItem(menu_2, SWT.NONE);
		mntmAddStudent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				StudentsAddDialog addDialog = new StudentsAddDialog(shlRegisterForTeachers, SWT.NONE);
				addDialog.open();
			}
		});
		mntmAddStudent.setText("Add Student");
		
		ExpandBar expandBar = new ExpandBar(shlRegisterForTeachers, SWT.NONE);
		expandBar.setLayoutData(BorderLayout.WEST);
		
		Composite compositeCenter = new Composite(shlRegisterForTeachers, SWT.NONE);
		compositeCenter.setLayoutData(BorderLayout.CENTER);
		compositeCenter.setLayout(new StackLayout());
		
		for(final GuiModuleList gmList : gmLists) {
			ExpandItem item = new ExpandItem(expandBar, SWT.NONE);
			item.setExpanded(true);
			item.setText(gmList.getGroupType().toString());
			Composite composite = new Composite(expandBar, SWT.NONE);
			item.setControl(composite);
		
			int i = 0;
			for(final GuiModule module : gmList.getModules()) {
				Link li = new Link(composite, SWT.NONE);
				li.setBounds(10, 5+20*i, 55, 15);
				li.setText("<a>" + module.getName() + "</a>");
				module.createContent(compositeCenter);
				li.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						gmList.setVisibleModule(module);
						try {
							module.update(Student.getAllStudents());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				i++;
			}
			item.setHeight(item.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 10);
		}
			
		
	}
}
