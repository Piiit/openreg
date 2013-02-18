package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Table;

public class MainWindow {

	protected Shell shlRegisterForTeachers;
	private Text txtName;

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
		mntmExit.setText("Exit");
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("Student");
		
		Menu menu_2 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_2);
		
		MenuItem mntmAddStudent = new MenuItem(menu_2, SWT.NONE);
		mntmAddStudent.setText("Add Student");
		
		txtName = new Text(shlRegisterForTeachers, SWT.BORDER);
		txtName.setText("Name");
		FormData fd_txtName = new FormData();
		fd_txtName.top = new FormAttachment(0, 10);
		fd_txtName.left = new FormAttachment(0, 10);
		txtName.setLayoutData(fd_txtName);

	}
}
