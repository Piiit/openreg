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
		shlRegisterForTeachers.setLayout(new BorderLayout(0, 0));
		
		Menu menu = new Menu(shlRegisterForTeachers, SWT.BAR);
		shlRegisterForTeachers.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.setText("Exit");
		
		CLabel lblNewLabel = new CLabel(shlRegisterForTeachers, SWT.NONE);
		lblNewLabel.setLayoutData(BorderLayout.SOUTH);
		lblNewLabel.setText("New Label");
		
		Composite composite = new Composite(shlRegisterForTeachers, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		
		ExpandBar expandBar = new ExpandBar(shlRegisterForTeachers, SWT.NONE);
		expandBar.setLayoutData(BorderLayout.WEST);
		
		ExpandItem xpndtmNewExpanditem = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setExpanded(true);
		xpndtmNewExpanditem.setText("Administration");
		
		Composite composite_1 = new Composite(expandBar, SWT.NONE);
		xpndtmNewExpanditem.setControl(composite_1);
		composite_1.setLayout(new FillLayout(SWT.VERTICAL));
		
		CLabel lblAdd = new CLabel(composite_1, SWT.NONE);
		lblAdd.setText("Students");
		
		CLabel lblDelete = new CLabel(composite_1, SWT.NONE);
		lblDelete.setText("Classes");
		xpndtmNewExpanditem.setHeight(xpndtmNewExpanditem.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		
		ExpandItem xpndtmNewExpanditem_1 = new ExpandItem(expandBar, SWT.NONE);
		xpndtmNewExpanditem_1.setExpanded(true);
		xpndtmNewExpanditem_1.setText("Information");

	}
}
