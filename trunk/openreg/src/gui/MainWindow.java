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
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;

public class MainWindow {

	protected Shell shlRegisterForTeachers;
	protected ArrayList<GuiModuleList> gmLists = new ArrayList<GuiModuleList>();
	protected GuiModule currentModule;
	
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
		shlRegisterForTeachers.setMinimumSize(new Point(800, 600));
		shlRegisterForTeachers.setSize(800, 600);
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
		
		ExpandBar expandBar = new ExpandBar(shlRegisterForTeachers, SWT.NONE);
		expandBar.setLayoutData(BorderLayout.WEST);
		
		final Composite compositeCenter = new Composite(shlRegisterForTeachers, SWT.NONE);
		compositeCenter.setLayoutData(BorderLayout.CENTER);
		compositeCenter.setLayout(new StackLayout());
		
		for(final GuiModuleList gmList : gmLists) {
			if(gmList.size() == 0) {
				continue;
			}
			ExpandItem item = new ExpandItem(expandBar, SWT.NONE);
			item.setExpanded(true);
			item.setText(gmList.getGroupType().toString());
			Composite composite = new Composite(expandBar, SWT.NONE);
			item.setControl(composite);
		
			int i = 0;
			for(final GuiModule module : gmList.getModules()) {
				Link li = new Link(composite, SWT.NONE);
				li.setBounds(5, 5+20*i, 200, 15);
				li.setText("<a>" + module.getName() + "</a>");
				module.createContent(compositeCenter);
				li.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						gmList.setVisibleModule(module);
						currentModule = module;
						module.reloadData(null);
					}
				});
				i++;
			}
			item.setHeight(item.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y + 10);
		}
		
		shlRegisterForTeachers.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent arg0) {
				if(currentModule != null) {
					currentModule.container.setVisible(true);
				}
			}
			@Override
			public void controlMoved(ControlEvent arg0) {
				if(currentModule != null) {
					currentModule.container.setVisible(true);
				}
			}
		});
		
	}
}
