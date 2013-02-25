package gui;

import java.util.ArrayList;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import data.Class;
import data.Student;
import org.eclipse.swt.widgets.TableItem;

public class StudentsModule extends GuiModule {
	
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
				StudentsAddDialog addDialog = new StudentsAddDialog(container.getShell(), SWT.NONE);
				addDialog.open();
				update();
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
		
		tltmRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableItem tableItems[] = table.getItems();
				ArrayList<Long> selected = new ArrayList<Long>();
				for(int i = 0; i < tableItems.length; i++) {
					if(tableItems[i].getChecked() == true) {
						selected.add((Long)tableItems[i].getData("ID"));
					}
				}
				MessageBox messageBox = new MessageBox(container.getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				messageBox.setMessage("Delete " + selected.size() + " students?");
				messageBox.setText(container.getShell().getText());
				if(messageBox.open() == SWT.YES) {
					try {
						for(Long studentId : selected)
							Student.delete(studentId);
						update();
					} catch (Exception e) {
						e.printStackTrace();

						MessageBox message = new MessageBox(container.getShell(), SWT.ICON_INFORMATION | SWT.OK);
						message.setMessage(e.getMessage());
						message.setText(container.getShell().getText());
						message.open();
					}
				}
			}
		});
	}

	@Override
	public void update() {
		table.removeAll();
		int i = 1;
		try {
			//TODO Better don't use getAll because classes need to get fetched for every student... performance issue!
			for(Student student : Student.getAll()) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {
						Integer.toString(i++), 
						student.getName() + " " + student.getSurname(), 
						Integer.toString(student.getEnrolmentYear()), 
						student.getBirthday().toString(),
						Class.getClassByID(student.getClassID()).toString()
						});
				tableItem.setData("ID", student.getID());
			}
		} catch (Exception e) {
			e.printStackTrace();

			MessageBox message = new MessageBox(container.getShell(), SWT.ICON_ERROR | SWT.OK);
			message.setMessage("Unable to fetch data from your Database! See stdout for more information!\n\n" + e.getMessage());
			message.setText(this.getName());
			message.open();	
		}	
	}

	@Override
	public String getName() {
		return "Students";
	}
}
