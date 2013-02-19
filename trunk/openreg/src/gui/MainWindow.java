package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.custom.StackLayout;

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
		shlRegisterForTeachers.setMinimumSize(new Point(640, 480));
		shlRegisterForTeachers.setSize(600, 500);
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
		mntmAddStudent.setText("Add Student");
		
		ExpandBar expandBar = new ExpandBar(shlRegisterForTeachers, SWT.NONE);
		expandBar.setLayoutData(BorderLayout.WEST);
		
		ExpandItem xpndtmAdministration = new ExpandItem(expandBar, SWT.NONE);
		xpndtmAdministration.setExpanded(true);
		xpndtmAdministration.setText("Administration");
		
		Composite composite = new Composite(expandBar, SWT.NONE);
		xpndtmAdministration.setControl(composite);
		xpndtmAdministration.setHeight(xpndtmAdministration.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		composite.setLayout(new FormLayout());
		
		Composite compositeCenter = new Composite(shlRegisterForTeachers, SWT.NONE);
		compositeCenter.setLayoutData(BorderLayout.CENTER);
		compositeCenter.setLayout(new StackLayout());
		
		final Classes widgetClasses = new Classes(compositeCenter, SWT.NONE);
		widgetClasses.setVisible(false);
		
		final Students widgetStudent = new Students(compositeCenter, SWT.NONE);
		widgetStudent.setVisible(false);
		


		Link link = new Link(composite, SWT.NONE);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				widgetClasses.setVisible(false);
				widgetStudent.setVisible(true);
			}
		});
		FormData fd_link = new FormData();
		fd_link.top = new FormAttachment(0, 10);
		fd_link.left = new FormAttachment(0, 10);
		link.setLayoutData(fd_link);
		link.setText("<a>Students</a>");
		
		Link link_1 = new Link(composite, SWT.NONE);
		link_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				widgetStudent.setVisible(false);
				widgetClasses.setVisible(true);
			}
		});
		FormData fd_link_1 = new FormData();
		fd_link_1.bottom = new FormAttachment(100, -10);
		fd_link_1.left = new FormAttachment(0, 10);
		link_1.setLayoutData(fd_link_1);
		link_1.setText("<a>Classes</a>");
	}
}
