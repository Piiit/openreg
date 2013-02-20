package gui;

import java.util.ArrayList;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import data.Student;
import org.eclipse.swt.widgets.TableItem;

public class Students extends GuiModule {
	
	public Students() throws Exception {
		super("Students");
	}

	private Table table;

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createContent(Composite parent) {
		
		final Group group = new Group(parent, SWT.NONE);
		group.setText(this.getName());
		group.setLayout(new GridLayout(1, false));

		container = group;
		
		ToolBar toolBar = new ToolBar(group, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		ToolItem tltmAdd = new ToolItem(toolBar, SWT.NONE);
		tltmAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				StudentsAdd addDialog = new StudentsAdd(container.getShell(), SWT.NONE);
				addDialog.open();
			}
		});
		tltmAdd.setText("Add");
		
		ToolItem tltmRemove = new ToolItem(toolBar, SWT.NONE);
		tltmRemove.setText("Remove");
		
		ToolItem tltmFilter = new ToolItem(toolBar, SWT.DROP_DOWN);
		tltmFilter.setText("All classes");
		
		table = new Table(group, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnControl = new TableColumn(table, SWT.NONE);
		tblclmnControl.setResizable(false);
		tblclmnControl.setWidth(30);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(30);
		tableColumn.setText("#");
		
		TableColumn tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(100);
		tblclmnName.setText("Name");
		
		TableColumn tblclmnEnrol = new TableColumn(table, SWT.NONE);
		tblclmnEnrol.setWidth(100);
		tblclmnEnrol.setText("Enrolment");
		
		TableColumn tblclmnBirthday = new TableColumn(table, SWT.NONE);
		tblclmnBirthday.setWidth(100);
		tblclmnBirthday.setText("Birthday");
		
		TableColumn tblclmnClass = new TableColumn(table, SWT.NONE);
		tblclmnClass.setWidth(100);
		tblclmnClass.setText("Class");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void update(final Object... parameters) throws IllegalArgumentException {
		if(parameters.length != 1 || !(parameters[0] instanceof ArrayList)) {
			throw new IllegalArgumentException("Parameter #1: ArrayList of students!");
		}
		ArrayList<Student> students = (ArrayList<Student>)parameters[0];
		table.removeAll();
		int i = 1;
		for(Student student : students) {
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(new String[] {
					"", 
					Integer.toString(i++), 
					student.getName() + " " + student.getSurname(), 
					Integer.toString(student.getEnrolmentYear()), 
					student.getBirthday().toString(),
					student.getStudentsClass().toString()
					});
		}	
	}
}
