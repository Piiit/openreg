package gui.modules;

import gui.GuiModule;
import gui.GuiTools;
import gui.dialogs.StudentDialog;
import java.util.ArrayList;
import log.Log;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import database.Row;
import database.query.ClassQuery;
import database.query.StudentQuery;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class StudentsModule extends GuiModule {
	
	private Table table;
	private ToolItem tltmFilter;
	private Combo filterClass;

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
				StudentDialog addDialog = new StudentDialog(container.getShell());
				addDialog.open();
				reloadData();
			}
		});
		tltmAdd.setText("Add");
		
		ToolItem tltmRemove = new ToolItem(toolBar, SWT.NONE);
		tltmRemove.setText("Remove");
		
		filterClass = new Combo(toolBar, SWT.READ_ONLY);
		tltmFilter = new ToolItem(toolBar, SWT.SEPARATOR);
		tltmFilter.setControl(filterClass);
		tltmFilter.setWidth(100);
		filterClass.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Long classID = (Long)filterClass.getData(filterClass.getText());
				Log.debug("Class ID " + classID);
				reloadData(classID);
			}
		});
		
		table = new Table(group, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				StudentDialog addDialog = new StudentDialog(container.getShell());
				try {
					TableItem ti = table.getItem(table.getSelectionIndex());
					addDialog.loadData(ti.getData());
				} catch (Exception e) {
					e.printStackTrace();
					GuiTools.showMessageBox(container.getShell(), e.getMessage());
				}
				addDialog.open();
				reloadData();
			}
		});
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
				ArrayList<Long> selected = GuiTools.getSelectedItems(table);
				
				if(selected.size() == 0) {
					GuiTools.showMessageBox(container.getShell(), "No students selected.");
					reloadData();
					return;
				}
				
				int answer = GuiTools.showQuestionBox(container.getShell(), "Delete " + selected.size() + " students?");
				if(answer == SWT.NO) {
					return;
				}

				for(Long studentId : selected) {
					try {
						StudentQuery.delete(studentId);
					} catch (Exception e) {
						e.printStackTrace();
						GuiTools.showMessageBox(container.getShell(), e.getMessage());
					}
				}
				reloadData();
			}
		});
	}

	@Override
	public void reloadData(Object id) {
		table.removeAll();
		filterClass.removeAll();
		int i = 1;
		try {
			ArrayList<Row> dataset = (id == null ? StudentQuery.getFullDataset() : StudentQuery.getClassDataset(id));
			for(Row student : dataset) {
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setData(student.getValueAsLong("student_id"));
				tableItem.setText(new String[] {
						Integer.toString(i++), 
						student.getValueAsStringNotNull("surname") + " " + student.getValueAsStringNotNull("name"), 
						student.getValueAsStringNotNull("enrolment_year"), 
						student.getValueAsStringNotNull("birthday"),
						student.getValueAsStringNotNull("level") + student.getValueAsStringNotNull("stream")
						});
			}
			
			String classString = "Show all";
			filterClass.add(classString);
			filterClass.select(filterClass.indexOf(classString));
			for(Row cl : ClassQuery.getFullDataset()) {
				classString = cl.getValueAsStringNotNull("level") + cl.getValueAsStringNotNull("stream");
				filterClass.add(classString);
				filterClass.setData(classString, cl.getValue("id"));
				if(id != null && id.equals(cl.getValue("id"))) {
					filterClass.select(filterClass.indexOf(classString));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			GuiTools.showMessageBox(container.getShell(), 
					"Unable to fetch data from your Database! See stdout for more information!\n\n" + e.getMessage()
					);
		}	
	}

	@Override
	public String getName() {
		return "Students";
	}

	@Override
	public String getDescription() {
		return null;
	}
}
