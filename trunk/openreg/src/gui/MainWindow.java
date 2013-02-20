package gui;

import java.util.ArrayList;
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
	private ArrayList<GuiModule> modules = new ArrayList<GuiModule>();
	
	public void addModule(GuiModule module) {
		modules.add(module);
	}
	
	public void removeAllModules() {
		modules.clear();
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
		
		Composite compositeAdministration = new Composite(expandBar, SWT.NONE);
		xpndtmAdministration.setControl(compositeAdministration);
		xpndtmAdministration.setHeight(xpndtmAdministration.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		compositeAdministration.setLayout(new FormLayout());
		
		ExpandItem xpndtmReports = new ExpandItem(expandBar, SWT.NONE);
		xpndtmReports.setExpanded(true);
		xpndtmReports.setText("Reports");
		
		Composite compositeReports = new Composite(expandBar, SWT.NONE);
		xpndtmReports.setControl(compositeReports);
		xpndtmReports.setHeight(xpndtmReports.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		
		Composite compositeCenter = new Composite(shlRegisterForTeachers, SWT.NONE);
		compositeCenter.setLayoutData(BorderLayout.CENTER);
		compositeCenter.setLayout(new StackLayout());
		
//		final Classes widgetClasses = new Classes(compositeCenter, SWT.NONE);
//		widgetClasses.setVisible(false);
		
//		final Students widgetStudent = new Students(compositeCenter, SWT.NONE);
//		widgetStudent.setVisible(false);
		
		final GuiModule widgetStudent = modules.get(0);
		widgetStudent.setVisible(true);
		
		int i = 0;
		int j = 0;
		for(GuiModule module : modules) {
			Composite currentComposite = compositeAdministration;
			if(module.getGroupType() == GroupType.Reports) {
				currentComposite = compositeReports;
			}
			Link li = new Link(currentComposite, SWT.NONE);
			FormData fd = new FormData();
			fd.top = new FormAttachment(0, 10+25*i);
			fd.left = new FormAttachment(0, 10);
			li.setLayoutData(fd);
			li.setText("<a>" + module.getName() + "</a>");
			i++;
		}
	}
}
